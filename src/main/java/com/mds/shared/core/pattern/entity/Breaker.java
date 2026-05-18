package com.mds.shared.core.pattern.entity;

import lombok.Getter;

/**
 * Control object used by custom {@code forEach} loops in {@link com.mds.shared.core.pattern.utils.FunctionUtils}
 * to signal an early break from iteration.
 *
 * <p>Call {@link #stop()} inside the loop consumer to terminate the iteration.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Getter
public class Breaker {

  private boolean shouldBreak = false;

  public void stop() {
    shouldBreak = true;
  }
}
