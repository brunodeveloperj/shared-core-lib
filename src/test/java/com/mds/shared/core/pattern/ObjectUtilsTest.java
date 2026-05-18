package com.mds.shared.core.pattern;

import static com.mds.shared.core.pattern.utils.ObjectUtils.equalsStringIgnoreCase;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isStringBlank;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isStringEmpty;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isValidNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isValidTrue;
import static com.mds.shared.core.pattern.utils.ObjectUtils.join;
import static com.mds.shared.core.pattern.utils.ObjectUtils.joinWithDelimiter;
import static com.mds.shared.core.pattern.utils.ObjectUtils.length;
import static com.mds.shared.core.pattern.utils.ObjectUtils.nullSafeSupplierGet;
import static com.mds.shared.core.pattern.utils.ObjectUtils.removeCharactersSpecial;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.mds.shared.core.pattern.utils.ObjectUtils;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for the {@link ObjectUtils} class. */
@ExtendWith(MockitoExtension.class)
class ObjectUtilsTest {

  @Data
  static class RemoveSpecialObject {
    private String field;
  }

  @Test
  void shouldReturnTrueWhenValueIsNull() {
    assertDoesNotThrow(() -> assertTrue(isNull(null)));
  }

  @Test
  void shouldReturnFalseWhenValueIsNotNull() {
    assertDoesNotThrow(() -> assertFalse(isNull("value")));
  }

  @Test
  void shouldThrowExceptionWhenValueIsNullAndMessageIsNotNull() {
    String message = "The value must not be null.";
    try {
      isValidNull(null, message);
      fail("Expected an exception to be thrown.");
    } catch (IllegalArgumentException e) {
      assertEquals(message, e.getMessage());
    }
  }

  @Test
  void shouldNotThrowExceptionWhenValueIsNullAndMessageIsNull() {
    assertDoesNotThrow(() -> isValidNull("1000", "message"));
  }

  @Test
  void shouldReturnTrueWhenExpressionIsTrue() {
    assertDoesNotThrow(() -> isValidTrue(true, String::new));
  }

  @Test
  void shouldThrowExceptionWhenExpressionIsFalseAndMessageIsNotNull() {
    String message = "The expression must be true.";
    try {
      isValidTrue(false, message);
      fail("Expected an exception to be thrown.");
    } catch (IllegalArgumentException e) {
      assertEquals(message, e.getMessage());
    }
  }

  @Test
  void shouldNotThrowExceptionWhenExpressionIsFalseAndMessageIsNull() {
    assertDoesNotThrow(() -> isValidTrue(true, "message"));
  }

  @Test
  void shouldReturnTrueWhenLengthIsZero() {
    assertEquals(0, length(""));
  }

  @Test
  void shouldReturnTrueWhenLengthIsGreaterThanZero() {
    assertEquals(5, length("Hello"));
  }

  @Test
  void shouldReturnTrueWhenValueIsEmptyString() {
    assertTrue(isStringEmpty(""));
  }

  @Test
  void shouldReturnFalseWhenValueIsNotEmptyString() {
    assertFalse(isStringEmpty("Hello"));
  }

  @Test
  void shouldReturnTrueWhenValueIsBlankString() {
    assertTrue(isStringBlank(" "));
  }

  @Test
  void shouldReturnFalseWhenValueIsNotBlankString() {
    assertFalse(isStringBlank("Hello"));
  }

  @Test
  void shouldReturnJoinedStringWithDelimiter() {
    assertEquals("a,b,c", joinWithDelimiter(",", "a", "b", "c"));
  }

  @Test
  void shouldReturnJoinedStringWithoutDelimiter() {
    assertEquals("abc", join("a", "b", "c"));
  }

  @Test
  void shouldReturnNullWhenSupplierIsNull() {
    assertNull(nullSafeSupplierGet(null));
  }

  @Test
  void shouldReturnValueWhenSupplierIsNotNull() {
    assertEquals("value", nullSafeSupplierGet(() -> "value"));
  }

  @Test
  void shouldReturnTrueWhenEquals() {
    assertTrue(ObjectUtils.equals("Test", "Test"));
  }

  @Test
  void shouldReturnFalseWhenEquals() {
    assertFalse(ObjectUtils.equals("Source", "Target"));
  }

  @Test
  void shouldReturnFalseWhenClassNotEqualsEquals() {
    assertFalse(ObjectUtils.equals("Source", 100));
  }

  @Test
  void shouldReturnTrueWhenEqualsStringIgnoreCase() {
    assertTrue(ObjectUtils.equalsStringIgnoreCase("Test", "test"));
  }

  @Test
  void shouldReturnFalseWhenEqualsStringIgnoreCase() {
    assertFalse(equalsStringIgnoreCase("Source", "target"));
  }

  @Test
  void shouldThrowExceptionWhenValueNonInstanceOfStringEqualsStringIgnoreCase() {
    assertThrows(ClassCastException.class, () -> equalsStringIgnoreCase("source", 100));
  }

  @Test
  void shouldThrowExceptionWhenRemoveCharactersSpecial() {
    RemoveSpecialObject removeSpecialObject = new RemoveSpecialObject();
    removeSpecialObject.setField("Teste \n Teste");
    assertDoesNotThrow(() -> removeCharactersSpecial(removeSpecialObject));
  }
}
