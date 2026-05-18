package com.mds.shared.core.cache;

import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;
import org.springframework.http.CacheControl;

/**
 * Utility class for creating {@link CacheControl} directives.
 *
 * <p>Provides a factory method returning a {@code max-age=0} cache control
 * to ensure responses are not cached by clients or proxies.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = PRIVATE)
public class CacheControlUtil {

  public static CacheControl createCacheControl() {
    return CacheControl.maxAge(0, TimeUnit.SECONDS);
  }
}
