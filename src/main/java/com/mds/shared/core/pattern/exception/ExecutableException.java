package com.mds.shared.core.pattern.exception;

/**
 * Unchecked exception thrown by {@link com.mds.shared.core.pattern.utils.FunctionUtils}
 * when an executable pattern fails and the caller opted to propagate the error.
 *
 * <p>Wraps the original checked {@link Throwable} as a {@link RuntimeException}
 * so it can escape functional interfaces without requiring explicit catch blocks.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public class ExecutableException extends RuntimeException {

  public ExecutableException() {
    super();
  }

  public ExecutableException(String message) {
    super(message);
  }

  public ExecutableException(String message, Throwable tx) {
    super(message, tx);
  }

  public ExecutableException(Throwable tx) {
    super(tx);
  }
}
