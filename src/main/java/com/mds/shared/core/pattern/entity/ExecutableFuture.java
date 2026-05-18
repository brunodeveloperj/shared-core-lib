package com.mds.shared.core.pattern.entity;

import static com.mds.shared.core.pattern.utils.FunctionUtils.executableObject;
import static com.mds.shared.core.pattern.utils.FunctionUtils.executableVoid;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.nullSafeSupplierPatternExecute;
import static java.util.Collections.unmodifiableList;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.mds.shared.core.pattern.interfaces.ExecutablePatternObject;
import com.mds.shared.core.pattern.interfaces.ExecutablePatternVoid;
import com.mds.shared.core.pattern.interfaces.SupplierPatternObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Orchestrator for parallel {@link CompletableFuture} execution.
 *
 * <p>Wraps a list of {@link CompletableFuture} instances built from
 * {@link ExecutablePatternVoid} and {@link ExecutablePatternObject} lambdas,
 * providing {@link #joinFutures()} to block and collect all results.
 * Supports custom exception propagation and fallback suppliers through the
 * static {@link #join(boolean, Throwable, SupplierPatternObject, Object...)} factory.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public class ExecutableFuture {

  private final List<CompletableFuture<?>> futures;

  /**
   * Creates a new instance of the `ExecutableFutureAsync` class.
   *
   * @param futures The list of asynchronous executable objects.
   */
  public ExecutableFuture(List<CompletableFuture<?>> futures) {
    this.futures = unmodifiableList(futures);
  }

  /**
   * Joins all the asynchronous executable objects and returns the results.
   *
   * @return The results of the asynchronous executable objects.
   */
  public Object[] joinFutures() {
    List<Object> response = new ArrayList<>();
    for (CompletableFuture<?> cf : futures) {
      Object obj = cf.join();
      if (obj != null) {
        response.add(obj);
      }
    }
    return response.toArray();
  }

  /**
   * Gets the list of asynchronous executable objects.
   *
   * @return The list of asynchronous executable objects.
   */
  public CompletableFuture<?>[] getFutures() {
    return futures.toArray(CompletableFuture<?>[]::new);
  }

  /**
   * Creates a static method that joins a list of executable objects and returns the results.
   *
   * @param showException Whether to show the exception.
   * @param customizedException The customized exception to throw if the executable object throws an
   *     exception.
   * @param optionalResponseSupplier A supplier of the optional response to return if the executable
   *     object throws an exception.
   * @param executable The list of executable objects to join.
   * @param <T> The type of the response from the executable object.
   * @return The results of the joined executable objects.
   */
  public static <T> ExecutableFuture join(
      boolean showException,
      Throwable customizedException,
      SupplierPatternObject<? extends T> optionalResponseSupplier,
      Object... executable) {
    List<CompletableFuture<?>> futures = new ArrayList<>();

    for (Object obj : executable) {
      if (obj instanceof ExecutablePatternVoid) {
        futures.add(
            buildCompletableFuture(
                (ExecutablePatternVoid) obj, showException, customizedException));
      } else if (obj instanceof ExecutablePatternObject) {
        futures.add(
            buildCompletableFuture(
                (ExecutablePatternObject<T>) obj,
                showException,
                customizedException,
                optionalResponseSupplier));
      }
    }

    return new ExecutableFuture(futures);
  }

  /**
   * Creates a private static method that builds a completable future from an executable object.
   *
   * @param exeVoid The executable void to build the completable future from.
   * @param showException Whether to show the exception.
   * @param customizedException The customized exception to throw if the executable object throws an
   *     exception.
   * @return The completable future for the executable object.
   */
  private static CompletableFuture<?> buildCompletableFuture(
      ExecutablePatternVoid exeVoid, boolean showException, Throwable customizedException) {
    return validateFuture(
        runAsync(() -> executableVoid(exeVoid)), showException, customizedException, null);
  }

  /**
   * Creates a private static method that builds a completable future from an executable object.
   *
   * @param exeObj The executable object to build the completable future from.
   * @param showException Whether to show the exception.
   * @param customizedException The customized exception to throw if the executable object throws an
   *     exception.
   * @param optionalResponseSupplier A supplier of the optional response to return if the executable
   *     object throws an exception.
   * @param <T> The type of the response from the executable object.
   * @return The completable future for the executable object.
   */
  private static <T> CompletableFuture<T> buildCompletableFuture(
      ExecutablePatternObject<T> exeObj,
      boolean showException,
      Throwable customizedException,
      SupplierPatternObject<? extends T> optionalResponseSupplier) {
    return validateFuture(
        supplyAsync(() -> executableObject(exeObj)),
        showException,
        customizedException,
        optionalResponseSupplier);
  }

  /**
   * Validates a completable future.
   *
   * @param future The completable future to validate.
   * @param showException Whether to show the exception.
   * @param customizedException The customized exception to throw if the executable object throws an
   *     exception.
   * @param optionalResponseSupplier A supplier of the optional response to return if the executable
   *     object throws an exception.
   * @param <T> The type of the response from the executable object.
   * @return The validated completable future.
   */
  private static <T> CompletableFuture<T> validateFuture(
      CompletableFuture<T> future,
      boolean showException,
      Throwable customizedException,
      SupplierPatternObject<? extends T> optionalResponseSupplier) {
    // Check if the future is completed exceptionally.
    return future.exceptionally(
        ex -> {
          // If the showException flag is set, throw the exception.
          if (showException) {
            throw new CompletionException(isNull(customizedException) ? ex : customizedException);
          }
          // Otherwise, return the optional response.
          return nullSafeSupplierPatternExecute(optionalResponseSupplier, ex);
        });
  }
}
