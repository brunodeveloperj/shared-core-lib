package com.mds.shared.core.pattern.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Wrapper that carries the {@link Throwable} captured during a null-safe execution.
 *
 * <p>Passed to {@link com.mds.shared.core.pattern.interfaces.SupplierPatternObjectAndThrowable}
 * so the fallback supplier can inspect the original exception.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectNullSafe {

  private Throwable exception;
}
