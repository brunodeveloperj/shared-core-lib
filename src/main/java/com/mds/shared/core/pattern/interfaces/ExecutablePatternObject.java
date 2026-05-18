package com.mds.shared.core.pattern.interfaces;

/**
 * Functional interface for an operation that returns a result and may throw
 * any {@link Throwable}.
 *
 * <p>Used by {@link com.mds.shared.core.pattern.utils.FunctionUtils#executableObject}
 * and {@link com.mds.shared.core.pattern.entity.ExecutableFuture} as the primary
 * contract for value-producing lambdas with checked-exception support.
 *
 * @param <T> the type of the result produced by this executable.
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public interface ExecutablePatternObject<T> {

  /**
   * Executes the object.
   *
   * @return The result of the execution.
   * @throws Throwable If an error occurs during the execution.
   */
  T execute() throws Throwable;
}
