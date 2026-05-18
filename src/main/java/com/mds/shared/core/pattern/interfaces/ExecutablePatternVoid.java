package com.mds.shared.core.pattern.interfaces;

/**
 * Functional interface for a void operation that may throw any {@link Throwable}.
 *
 * <p>Used by {@link com.mds.shared.core.pattern.utils.FunctionUtils#executableVoid}
 * and {@link com.mds.shared.core.pattern.entity.ExecutableFuture} as the contract
 * for side-effect lambdas with checked-exception support.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public interface ExecutablePatternVoid {

  /**
   * Executes the object.
   *
   * @throws Throwable If an error occurs during the execution.
   */
  void execute() throws Throwable;
}
