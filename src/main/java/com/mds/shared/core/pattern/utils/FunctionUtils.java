package com.mds.shared.core.pattern.utils;

import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.ARRAY_EXCEPTION_MESSAGE;
import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.CUSTOMIZED_EXCEPTION_PARAM;
import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.OBJECT_PARAM;
import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.OPTIONAL_RESPONSE_SUPPLIER_PARAM;
import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.PARAM_EXCEPTION_MESSAGE;
import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.VOID_PARAM;
import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.ZERO_INDEX;
import static com.mds.shared.core.pattern.utils.ObjectUtils.format;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isValidNull;
import static java.util.concurrent.CompletableFuture.allOf;

import com.mds.shared.core.pattern.base.AbstractFunctionBase;
import com.mds.shared.core.pattern.entity.Breaker;
import com.mds.shared.core.pattern.entity.ExecutableFuture;
import com.mds.shared.core.pattern.interfaces.ExecutablePatternObject;
import com.mds.shared.core.pattern.interfaces.ExecutablePatternVoid;
import com.mds.shared.core.pattern.interfaces.SupplierPatternObject;
import com.mds.shared.core.pattern.interfaces.SupplierPatternObjectAndThrowable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.CompletionException;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A utility class that provides methods for working with functions.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FunctionUtils extends AbstractFunctionBase {

  /**
   * Executes the specified executable void.
   *
   * @param exeVoid The executable void to execute.
   * @see #executableVoid(ExecutablePatternVoid, boolean)
   */
  public static void executableVoid(ExecutablePatternVoid exeVoid) {
    // Execute the executable void.
    executableVoid(exeVoid, true);
  }

  /**
   * Executes the specified executable voids asynchronously and does not return a value, handling null values gracefully for the exeVoid parameter.
   *
   * @param exeVoid The executable voids to execute.
   */
  public static void executableVoidAsyncJoin(ExecutablePatternVoid... exeVoid) {
    // Execute the executable voids.
    executableAsync(true, null, null, (Object[]) exeVoid);
  }

  /**
   * Executes the specified executable voids and returns the result.
   *
   * @param exeVoid The executable voids to execute.
   * @throws IllegalArgumentException If the exeVoid parameter is null.
   */
  public static void executableVoidJoin(ExecutablePatternVoid... exeVoid) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(ARRAY_EXCEPTION_MESSAGE, VOID_PARAM));

    // Execute the executable voids.
    for (ExecutablePatternVoid ev : exeVoid) {
      executableVoid(ev);
    }
  }

  /**
   * Executes the specified executable void and returns the result.
   *
   * @param exeVoid             The executable void to execute.
   * @param customizedException The customized exception to throw if the executable throws an exception.
   * @throws IllegalArgumentException If the exeVoid or customizedException parameter is null.
   */
  public static void executableVoid(ExecutablePatternVoid exeVoid, Throwable customizedException) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the customizedException parameter is null.
    isValidNull(customizedException, generateMessage(PARAM_EXCEPTION_MESSAGE, CUSTOMIZED_EXCEPTION_PARAM));

    // Execute the executable void.
    executable(exeVoid, null, customizedException, true, false, null);
  }

  /**
   * Executes the specified executable void asynchronously and does not return a value, handling null values gracefully for the exeVoid parameter and throwing a customized exception if the executable object throws an exception.
   *
   * @param exeVoid             The executable void to execute.
   * @param customizedException The customized exception to throw if the executable object throws an exception.
   * @throws IllegalArgumentException If the exeVoid or customizedException parameter is null.
   */
  public static void executableVoidAsync(Throwable customizedException, ExecutablePatternVoid... exeVoid) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the customizedException parameter is null.
    isValidNull(customizedException, generateMessage(PARAM_EXCEPTION_MESSAGE, CUSTOMIZED_EXCEPTION_PARAM));

    executableAsync(true, customizedException, null, (Object[]) exeVoid);
  }

  /**
   * Executes the specified executable void and returns the result.
   *
   * @param exeVoid       The executable void to execute.
   * @param showException Whether to show the exception.
   * @throws IllegalArgumentException If the exeVoid parameter is null.
   */
  public static void executableVoid(ExecutablePatternVoid exeVoid, boolean showException) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Execute the executable void.
    executable(exeVoid, null, null, showException, false, null);
  }

  /**
   * Executes the specified executable void asynchronously and does not return a value, handling null values gracefully for the exeVoid parameter and showing the exception if set to true.
   *
   * @param exeVoid       The executable void to execute.
   * @param showException Whether to show the exception.
   * @throws IllegalArgumentException If the exeVoid parameter is null.
   */
  public static void executableVoidAsync(boolean showException, ExecutablePatternVoid... exeVoid) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    executableAsync(showException, null, null, (Object[]) exeVoid);
  }

  // ---------------------------------------------------------------------------------------------------------------------------

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj The executable object to execute.
   * @return The result of the execution.
   * @see #executableObject(ExecutablePatternObject, boolean)
   */
  public static <T> T executableObject(ExecutablePatternObject<? extends T> exeObj) {
    // Execute the executable object.
    return executableObject(exeObj, true);
  }

  /**
   * Executes the specified executable objects and returns a list of the results.
   *
   * @param exeObj The executable objects to execute.
   * @return A list of the results of the execution.
   * @throws IllegalArgumentException If the exeObj parameter is null.
   */
  @SafeVarargs
  public static <T> List<T> executableObjectJoin(ExecutablePatternObject<? extends T>... exeObj) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Execute the executable objects.
    List<T> response = new ArrayList<>(exeObj.length);
    for (ExecutablePatternObject<? extends T> eo : exeObj) {
      response.add(executableObject(eo));
    }

    return response;
  }

  /**
   * Executes the specified executable objects asynchronously and returns the results, handling null values gracefully for the exeObj parameter.
   *
   * @param exeObj The executable objects to execute.
   * @return The results of the execution.
   * @throws IllegalArgumentException If the exeObj parameter is null.
   */
  @SafeVarargs
  public static <T> Object[] executableObjectAsyncJoin(ExecutablePatternObject<? extends T>... exeObj) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    return executableAsync(true, null, null, (Object[]) exeObj);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj              The executable object to execute.
   * @param customizedException The customized exception to throw if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or customizedException parameter is null.
   */
  public static <T> T executableObject(ExecutablePatternObject<? extends T> exeObj, Throwable customizedException) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the customizedException parameter is null.
    isValidNull(customizedException, generateMessage(PARAM_EXCEPTION_MESSAGE, CUSTOMIZED_EXCEPTION_PARAM));

    // Execute the executable object.
    return executable(null, exeObj, customizedException, true, false, null);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully for the exeObj parameter and throwing a customized exception if the executable object throws an exception.
   *
   * @param exeObj              The executable object to execute.
   * @param customizedException The customized exception to throw if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or customizedException parameter is null.
   */
  @SafeVarargs
  public static <T> Object[] executableObjectAsync(Throwable customizedException, ExecutablePatternObject<? extends T>... exeObj) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the customizedException parameter is null.
    isValidNull(customizedException, generateMessage(PARAM_EXCEPTION_MESSAGE, CUSTOMIZED_EXCEPTION_PARAM));

    return executableAsync(true, customizedException, null, (Object[]) exeObj);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj        The executable object to execute.
   * @param showException Whether to show the exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj parameter is null.
   */
  public static <T> T executableObject(ExecutablePatternObject<? extends T> exeObj, boolean showException) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Execute the executable object.
    return executable(null, exeObj, null, showException, false, null);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully for the exeObj parameter and showing the exception if set to true.
   *
   * @param exeObj        The executable object to execute.
   * @param showException Whether to show the exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj parameter is null.
   */
  @SafeVarargs
  public static <T> Object[] executableObjectAsync(boolean showException, ExecutablePatternObject<? extends T>... exeObj) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    return executableAsync(showException, null, null, (Object[]) exeObj);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj                   The executable object to execute.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableObjectNullSafe(ExecutablePatternObject<? extends T> exeObj, SupplierPatternObject<? extends T> optionalResponseSupplier) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executableObjectNullSafe(exeObj, false, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj                   The executable object to execute.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableObjectAndThrowableByNullSafe(ExecutablePatternObject<? extends T> exeObj, SupplierPatternObjectAndThrowable<? extends T> optionalResponseSupplier) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executableObjectAndThrowableByNullSafe(exeObj, false, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj                   The executable object to execute.
   * @param printExceptionMessage    Whether to exception message is printed.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableObjectNullSafe(ExecutablePatternObject<? extends T> exeObj, boolean printExceptionMessage, SupplierPatternObject<? extends T> optionalResponseSupplier) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executable(null, exeObj, null, false, printExceptionMessage, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeObj                   The executable object to execute.
   * @param printExceptionMessage    Whether to exception message is printed.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableObjectAndThrowableByNullSafe(ExecutablePatternObject<? extends T> exeObj, boolean printExceptionMessage, SupplierPatternObjectAndThrowable<? extends T> optionalResponseSupplier) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executable(null, exeObj, null, false, printExceptionMessage, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully for the exeObj parameter and providing an optional response supplier for the case when the executable object throws an exception.
   *
   * @param exeObj                   The executable object to execute.
   * @param optionalResponseSupplier A supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeObj or optionalResponseSupplier parameter is null.
   */
  @SafeVarargs
  public static <T> Object[] executableObjectAsyncNullSafe(SupplierPatternObject<? extends T> optionalResponseSupplier, ExecutablePatternObject<? extends T>... exeObj) {
    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    return executableAsync(false, null, optionalResponseSupplier, (Object[]) exeObj);
  }

  // ---------------------------------------------------------------------------------------------------------------------------

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid The executable void to execute.
   * @param exeObj  The executable object to execute.
   * @return The result of the execution.
   * @see #executable(ExecutablePatternVoid, ExecutablePatternObject, boolean)
   */
  public static <T> T executable(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj) {
    // Execute the executable object.
    return executable(exeVoid, exeObj, true);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully and showing the exception by default.
   *
   * @param exeVoid The executable void to execute.
   * @param exeObj  The executable object to execute.
   * @return The result of the execution.
   * @see #executableAsync(ExecutablePatternVoid, ExecutablePatternObject, boolean)
   */
  public static <T> T executableAsync(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj) {
    // Execute the executable object asynchronously.
    return executableAsync(exeVoid, exeObj, true);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid       The executable void to execute.
   * @param exeObj        The executable object to execute.
   * @param showException Whether to show the exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj parameter is null.
   */
  public static <T> T executable(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, boolean showException) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Execute the executable object.
    return executable(exeVoid, exeObj, null, showException, false, null);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully and showing the exception if set to true.
   *
   * @param exeVoid       The executable void to execute.
   * @param exeObj        The executable object to execute.
   * @param showException Whether to show the exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj parameter is null.
   */
  public static <T> T executableAsync(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, boolean showException) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    return (T) executableAsync(showException, null, null, exeVoid, exeObj)[ZERO_INDEX];
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid             The executable void to execute.
   * @param exeObj              The executable object to execute.
   * @param customizedException The customized exception to throw if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or customizedException parameter is null.
   */
  public static <T> T executable(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, Throwable customizedException) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the customizedException parameter is null.
    isValidNull(customizedException, generateMessage(PARAM_EXCEPTION_MESSAGE, CUSTOMIZED_EXCEPTION_PARAM));

    // Execute the executable object.
    return executable(exeVoid, exeObj, customizedException, true, false, null);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully and throwing a customized exception if the executable object throws an exception.
   *
   * @param exeVoid             The executable void to execute.
   * @param exeObj              The executable object to execute.
   * @param customizedException The customized exception to throw if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or customizedException parameter is null.
   */
  public static <T> T executableAsync(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, Throwable customizedException) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the customizedException parameter is null.
    isValidNull(customizedException, generateMessage(PARAM_EXCEPTION_MESSAGE, CUSTOMIZED_EXCEPTION_PARAM));

    return (T) executableAsync(true, customizedException, null, exeVoid, exeObj)[ZERO_INDEX];
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid                  The executable void to execute.
   * @param exeObj                   The executable object to execute.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableNullSafe(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, SupplierPatternObject<? extends T> optionalResponseSupplier) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executableNullSafe(exeVoid, exeObj, false, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid                  The executable void to execute.
   * @param exeObj                   The executable object to execute.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableThrowableByNullSafe(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, SupplierPatternObjectAndThrowable<? extends T> optionalResponseSupplier) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executableThrowableByNullSafe(exeVoid, exeObj, false, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid                  The executable void to execute.
   * @param exeObj                   The executable object to execute.
   * @param printExceptionMessage    Whether to exception message is printed.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableNullSafe(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, boolean printExceptionMessage, SupplierPatternObject<? extends T> optionalResponseSupplier) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executable(exeVoid, exeObj, null, false, printExceptionMessage, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object and returns the result.
   *
   * @param exeVoid                  The executable void to execute.
   * @param exeObj                   The executable object to execute.
   * @param printExceptionMessage    Whether to exception message is printed.
   * @param optionalResponseSupplier The supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableThrowableByNullSafe(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, boolean printExceptionMessage, SupplierPatternObjectAndThrowable<? extends T> optionalResponseSupplier) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    // Execute the executable object.
    return executable(exeVoid, exeObj, null, false, printExceptionMessage, optionalResponseSupplier);
  }

  /**
   * Executes the specified executable object asynchronously and returns the result, handling null values gracefully.
   *
   * @param exeVoid                  The executable void to execute.
   * @param exeObj                   The executable object to execute.
   * @param optionalResponseSupplier A supplier of the optional response to return if the executable object throws an exception.
   * @return The result of the execution.
   * @throws IllegalArgumentException If the exeVoid or exeObj or optionalResponseSupplier parameter is null.
   */
  public static <T> T executableAsyncNullSafe(ExecutablePatternVoid exeVoid, ExecutablePatternObject<? extends T> exeObj, SupplierPatternObject<? extends T> optionalResponseSupplier) {
    // Check if the exeVoid parameter is null.
    isValidNull(exeVoid, generateMessage(PARAM_EXCEPTION_MESSAGE, VOID_PARAM));

    // Check if the exeObj parameter is null.
    isValidNull(exeObj, generateMessage(PARAM_EXCEPTION_MESSAGE, OBJECT_PARAM));

    // Check if the optionalResponseSupplier parameter is null.
    isValidNull(optionalResponseSupplier, generateMessage(PARAM_EXCEPTION_MESSAGE, OPTIONAL_RESPONSE_SUPPLIER_PARAM));

    return (T) executableAsync(false, null, optionalResponseSupplier, exeVoid, exeObj)[ZERO_INDEX];
  }

  // ---------------------------------------------------------------------------------------------------------------------------

  /**
   * Method responsible for customizing the For loop using stream
   *
   * @param stream   The parameter responsible for the process.
   * @param consumer The parameter responsible for the custom breaker.
   * @param <T>      The result contained in the stream.
   */
  public static <T> void forEach(Stream<T> stream, BiConsumer<T, Breaker> consumer) {
    Spliterator<T> spliterator = stream.spliterator();
    boolean hadNext = true;
    Breaker breaker = new Breaker();

    while (hadNext && !breaker.isShouldBreak()) {
      hadNext = spliterator.tryAdvance(elem -> consumer.accept(elem, breaker));
    }
  }

  /**
   * Method responsible for customizing the For loop using stream
   *
   * @param collection The parameter responsible for the process.
   * @param consumer   The parameter responsible for the custom breaker.
   * @param <T>        The result contained in the collection.
   */
  public static <T> void forEach(Collection<T> collection, BiConsumer<T, Breaker> consumer) {
    Spliterator<T> spliterator = collection.spliterator();
    boolean hadNext = true;
    Breaker breaker = new Breaker();

    while (hadNext && !breaker.isShouldBreak()) {
      hadNext = spliterator.tryAdvance(elem -> consumer.accept(elem, breaker));
    }
  }

  /**
   * Method to sleep and avoid interruption between threads.
   *
   * @param timeToSleep Time to sleep.
   */
  public static void sleep(long timeToSleep) {
    long start, end, slept;

    while (timeToSleep > 0) {
      start = System.currentTimeMillis();
      try {
        Thread.sleep(timeToSleep);
        break;
      } catch (InterruptedException ie) {

        // work out how much more time to sleep for
        end = System.currentTimeMillis();
        slept = end - start;
        timeToSleep -= slept;
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Executes the specified executable object asynchronously and returns the result.
   *
   * @param showException            Whether to show the exception.
   * @param customizedException      The customized exception to throw if the executable object throws an exception.
   * @param optionalResponseSupplier A supplier of the optional response to return if the executable object throws an exception.
   * @param executable               The executable object to execute.
   * @return The result of the execution.
   * @throws CompletionException If the executable throws an exception and the customized exception is not null, or if the showException parameter is true.
   */
  private static <T> Object[] executableAsync(boolean showException, Throwable customizedException, SupplierPatternObject<? extends T> optionalResponseSupplier, Object... executable) {
    // Create a ExecutableFutureAsyncDTO object.
    final ExecutableFuture executableFuture = ExecutableFuture.join(showException, customizedException, optionalResponseSupplier, executable);

    // Execute the executable object asynchronously.
    return allOf(executableFuture.getFutures()).thenApplyAsync(c -> executableFuture.joinFutures()).join();
  }

  // ---------------------------------------------------------------------------------------------------------------------------

  /**
   * Generates a message with the specified format and parameter.
   *
   * @param format The format of the message.
   * @param param  The parameter to be substituted into the format string.
   * @return The generated message.
   */
  private static String generateMessage(String format, String param) {
    return format(format, Map.of("0", param));
  }
}
