package com.mds.shared.core.pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mds.shared.core.pattern.utils.ReflectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for the {@link ReflectionUtils} class. */
@ExtendWith(MockitoExtension.class)
class ReflectionUtilsTest {

  @Test
  void shouldGenerateInstanceOfClass() {
    Object object = ReflectionUtils.generateInstance(String.class);
    assertTrue(object instanceof String);
  }

  @Test
  void shouldThrowExceptionNullWhenClassIsNull() {
    assertThrows(IllegalArgumentException.class, () -> ReflectionUtils.generateInstance(null));
  }

  @Test
  void shouldThrowExceptionWhenFieldIsNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ReflectionUtils.getField(null, String.class, new Object()));
  }

  @Test
  void shouldThrowExceptionWhenTargetIsNull() {
    assertThrows(
        IllegalArgumentException.class, () -> ReflectionUtils.getField("name", String.class, null));
  }

  @Test
  void shouldReturnNullWhenNameIsNotFound() {
    String field = (String) ReflectionUtils.getField("notFound", String.class, new Object());
    assertNull(field);
  }

  @Test
  void shouldThrowExceptionWhenTypeIsNotFound() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ReflectionUtils.getField(null, Object.class, new Object()));
  }

  @Test
  void shouldClearCache() {
    ReflectionUtils.clearCache();
    assertEquals(11, ReflectionUtils.getDeclaredFields(String.class).length);
  }
}
