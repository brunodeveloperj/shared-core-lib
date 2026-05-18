package com.mds.shared.core.pattern.interfaces;

/**
 * Root interface for null-safe supplier patterns.
 *
 * <p>Provides a default {@link #execute()} that returns {@code null},
 * making it safe to invoke without a null-check. Sub-interfaces
 * ({@link SupplierPatternObject}, {@link SupplierPatternObjectAndThrowable})
 * specialize the contract for value-producing and error-aware variants.
 *
 * @param <T> the type of the result of the execution.
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public interface SupplierPattern<T> {

  /**
   * Executes the object.
   *
   * @return The result of the execution.
   */
  default T execute() {
    return null;
  }
}
