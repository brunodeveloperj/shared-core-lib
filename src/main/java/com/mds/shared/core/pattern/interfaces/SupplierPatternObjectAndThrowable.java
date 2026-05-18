package com.mds.shared.core.pattern.interfaces;

import com.mds.shared.core.pattern.entity.ObjectNullSafe;

/**
 * Null-safe supplier whose {@link #execute(ObjectNullSafe)} receives an
 * {@link ObjectNullSafe} wrapper carrying any captured exception.
 *
 * <p>Used as the error-aware fallback variant in
 * {@link com.mds.shared.core.pattern.utils.ObjectUtils#nullSafeSupplierPatternExecute}
 * allowing the caller to inspect the original throwable while still
 * returning a typed default value.
 *
 * @param <T> the type of the result of the execution.
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public interface SupplierPatternObjectAndThrowable<T> extends SupplierPattern<T> {

  /**
   * Executes the object.
   *
   * @return The result of the execution.
   */
  T execute(ObjectNullSafe objectNullSafe);
}
