package com.mds.shared.core.pattern.base;

import static com.mds.shared.core.pattern.utils.ObjectUtils.isNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.nonNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.nullSafeSupplierPatternExecute;

import com.mds.shared.core.pattern.exception.ExecutableException;
import com.mds.shared.core.pattern.interfaces.ExecutablePatternObject;
import com.mds.shared.core.pattern.interfaces.ExecutablePatternVoid;
import com.mds.shared.core.pattern.interfaces.SupplierPattern;
import java.util.logging.Logger;

/**
 * The abstract utility class that provides methods for working with functions.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public abstract class AbstractFunctionBase {

  static Logger logger = Logger.getLogger(AbstractFunctionBase.class.getName());

  /**
   * Executes the specified executable and returns the result.
   *
   * @param exeVoid The executable to execute.
   * @param exeObj The executable to execute and return the result.
   * @param customizedException The customized exception to throw if the executable throws an
   *     exception.
   * @param showException Whether to show the exception.
   * @param printExceptionMessage Whether to exception message is printed.
   * @param optionalResponseSupplier The optional response to return if the executable throws an
   *     exception.
   * @return The result of the execution, or the optional response if the executable throws an
   *     exception.
   * @throws ExecutableException If the executable throws an exception and the customized exception
   *     is not null, or if the showException parameter is true.
   */
  protected static <T> T executable(
      ExecutablePatternVoid exeVoid,
      ExecutablePatternObject<? extends T> exeObj,
      Throwable customizedException,
      boolean showException,
      boolean printExceptionMessage,
      SupplierPattern<? extends T> optionalResponseSupplier) {
    T result = null;
    try {
      if (nonNull(exeVoid)) exeVoid.execute(); // Execute the executable void.
      if (nonNull(exeObj)) result = exeObj.execute(); // Execute the executable object.
    } catch (Throwable tx) {
      // Check if the exception message is printed.
      if (printExceptionMessage) logger.warning(tx.getLocalizedMessage());
      // Check if exceptions throws
      if (showException) validateExecutableException(tx, customizedException);
      // If no exception is thrown, it is treated as null safe.
      else result = nullSafeSupplierPatternExecute(optionalResponseSupplier, tx);
    }
    return result;
  }

  static void validateExecutableException(Throwable sourceException, Throwable targetException) {
    throw new ExecutableException(isNull(targetException) ? sourceException : targetException);
  }
}
