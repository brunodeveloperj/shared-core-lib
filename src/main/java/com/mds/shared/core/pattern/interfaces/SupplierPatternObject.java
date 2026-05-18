package com.mds.shared.core.pattern.interfaces;

/**
 * Null-safe supplier that always produces a typed result.
 *
 * <p>Extends {@link SupplierPattern} overriding {@link #execute()} to require
 * a concrete return value. Typically used as a fallback supplier in
 * {@link com.mds.shared.core.pattern.utils.FunctionUtils} and
 * {@link com.mds.shared.core.pattern.entity.ExecutableFuture} when an operation
 * fails and a default value must be provided.
 *
 * @param <T> the type of the result of the execution.
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public interface SupplierPatternObject<T> extends SupplierPattern<T> {

  /**
   * Executes the object.
   *
   * @return The result of the execution.
   */
  T execute();
}
