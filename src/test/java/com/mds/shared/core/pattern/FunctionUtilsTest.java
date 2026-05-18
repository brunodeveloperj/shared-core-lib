package com.mds.shared.core.pattern;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mds.shared.core.pattern.interfaces.ExecutablePatternObject;
import com.mds.shared.core.pattern.interfaces.ExecutablePatternVoid;
import com.mds.shared.core.pattern.utils.FunctionUtils;
import com.mds.shared.core.pattern.utils.ObjectUtils;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FunctionUtilsTest {

  private static final ExecutablePatternVoid NO_OP = () -> {};
  private static final ExecutablePatternObject<String> STRING_OBJECT = () -> "Hello, world!";

  @Test
  void shouldNotReturnWhenExecutableVoid() {
    assertDoesNotThrow(() -> FunctionUtils.executableVoid(NO_OP));
  }

  @Test
  void shouldThrowExceptionWhenParamIsNull() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableVoid(null));
  }

  @Test
  void shouldThrowExceptionWhenParamIsNullAsync() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableVoidAsync(null, NO_OP));
  }

  @Test
  void shouldNotReturnWhenExecutableVoidJoin() {
    assertDoesNotThrow(() -> FunctionUtils.executableVoidJoin(NO_OP));
  }

  @Test
  void shouldThrowExceptionWhenParamIsNullByExecutableVoidJoin() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableVoidJoin(null, null));
  }

  @Test
  void shouldNotReturnWhenExecutableVoidAsyncJoin() {
    assertDoesNotThrow(() -> FunctionUtils.executableVoidAsyncJoin(NO_OP));
  }

  @Test
  void shouldThrowExceptionWhenExecutableVoidAndCustomizedException() {
    assertThrows(
        RuntimeException.class,
        () ->
            FunctionUtils.executableVoid(
                () -> {
                  throw new Exception();
                },
                new IllegalArgumentException()));
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableVoidAndCustomizedException() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableVoid(null, null));
  }

  @Test
  void shouldThrowExceptionWhenCustomizedExceptionIsNull() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableVoid(NO_OP, null));
  }

  @Test
  void shouldThrowExceptionWhenExecutableVoidAsyncAndCustomizedException() {
    assertThrows(
        RuntimeException.class,
        () ->
            FunctionUtils.executableVoid(
                () -> {
                  throw new Exception();
                },
                new IllegalArgumentException()));
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableVoidAsyncAndCustomizedException() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableVoid(null, null));
  }

  @Test
  void shouldThrowExceptionWhenCustomizedExceptionIsNullByAsync() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableVoidAsync(null, NO_OP));
  }

  @Test
  void shouldThrowExceptionWhenExecutableVoidAndShowException() {
    assertDoesNotThrow(
        () ->
            FunctionUtils.executableVoid(
                () -> {
                  throw new Exception();
                },
                false));
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableVoidAndShowException() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableVoid(null, false));
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableVoidAsyncAndShowException() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableVoidAsync(false, null));
  }

  // ----------------------------------------------------------------------------------------------------------------------------

  @Test
  void shouldNotReturnWhenExecutableObject() {
    assertNotNull(FunctionUtils.executableObject(STRING_OBJECT));
  }

  @Test
  void shouldThrowExceptionWhenParamIsNullBYExecutableObject() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableObject(null));
  }

  @Test
  void shouldThrowExceptionWhenParamIsNullBYExecutableObjectAsync() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableObjectAsync(null));
  }

  @Test
  void shouldNotReturnWhenExecutableObjectJoin() {
    assertDoesNotThrow(() -> FunctionUtils.executableObjectJoin(STRING_OBJECT, Object::new));
  }

  @Test
  void shouldThrowExceptionWhenParamIsNullByExecutableObjectJoin() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableObjectJoin(null, null));
  }

  @Test
  void shouldThrowExceptionWhenExecutableObjectAndCustomizedException() {
    assertThrows(
        RuntimeException.class,
        () ->
            FunctionUtils.executableObject(
                () -> {
                  throw new Exception();
                },
                new IllegalArgumentException()));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableObjectAndCustomizedException() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableObject(null, null));
  }

  @Test
  void shouldThrowExceptionWhenCustomizedExceptionIsNullByExecutableObject() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableObject(STRING_OBJECT, null));
  }

  @Test
  void shouldThrowExceptionWhenExecutableObjectAndCustomizedExceptionAsync() {
    assertThrows(
        RuntimeException.class,
        () ->
            FunctionUtils.executableObjectAsync(
                new IllegalArgumentException(),
                () -> {
                  throw new Exception();
                }));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableObjectAndCustomizedExceptionAsync() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableObjectAsync(null, null));
  }

  @Test
  void shouldThrowExceptionWhenCustomizedExceptionIsNullByExecutableObjectAsync() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableObjectAsync(null, STRING_OBJECT));
  }

  @Test
  void shouldThrowExceptionWhenExecutableObjectAndShowException() {
    assertDoesNotThrow(
        () ->
            FunctionUtils.executableObject(
                () -> {
                  throw new Exception();
                },
                false));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableObjectAndShowException() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableObject(null, false));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableObjectAndShowExceptionAsync() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableObjectAsync(false, null));
  }

  @Test
  void shouldReturnNotBeNullWhenExecutableObjectNullSafe() {
    assertNotNull(
        FunctionUtils.executableObjectNullSafe(
            () -> {
              throw new Exception();
            },
            String::new));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableObjectNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableObjectNullSafe(null, String::new));
  }

  @Test
  void shouldThrowExceptionWhenOptionalResponseSupplierIsNullByExecutableObjectNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableObjectNullSafe(STRING_OBJECT, null));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableObjectAsyncNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableObjectAsyncNullSafe(null, String::new));
  }

  @Test
  void shouldThrowExceptionWhenOptionalResponseSupplierIsNullByExecutableObjectAsyncNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableObjectAsyncNullSafe(null, STRING_OBJECT));
  }

  // *************************************************************************************************************************************

  @Test
  void shouldReturnEqualsWhenExecutable() {
    String result = FunctionUtils.executable(NO_OP, STRING_OBJECT);

    assertEquals("Hello, world!", result);
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutable() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executable(null, STRING_OBJECT));
  }

  @Test
  void shouldThrowExceptionWhenExeObjectIsNullByExecutable() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executable(NO_OP, null));
  }

  @Test
  void shouldReturnEqualsWhenExecutableAsync() {
    String result = FunctionUtils.executableAsync(NO_OP, STRING_OBJECT);

    assertEquals("Hello, world!", result);
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableAsync() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableAsync(null, STRING_OBJECT));
  }

  @Test
  void shouldThrowExceptionWhenExeObjectIsNullByExecutableAsync() {
    assertThrows(IllegalArgumentException.class, () -> FunctionUtils.executableAsync(NO_OP, null));
  }

  @Test
  void shouldThrowExceptionWhenCustomizedExceptionIsNullByExecutable() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executable(NO_OP, STRING_OBJECT, null));
  }

  @Test
  void shouldThrowExceptionWhenCustomizedExceptionIsNullByExecutableAsync() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableAsync(NO_OP, STRING_OBJECT, null));
  }

  @Test
  void shouldReturnEqualsWhenExecutableNullSafe() {
    String result = FunctionUtils.executableNullSafe(NO_OP, STRING_OBJECT, String::new);

    assertEquals("Hello, world!", result);
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableNullSafe(null, STRING_OBJECT, null));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableNullSafe() {
    assertThrows(
        IllegalArgumentException.class, () -> FunctionUtils.executableNullSafe(NO_OP, null, null));
  }

  @Test
  void shouldThrowExceptionWhenOptionalResponseSupplierIsNullByExecutableNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableNullSafe(NO_OP, STRING_OBJECT, null));
  }

  @Test
  void shouldReturnEqualsWhenExecutableAsyncNullSafe() {
    String result = FunctionUtils.executableAsyncNullSafe(NO_OP, STRING_OBJECT, String::new);

    assertEquals("Hello, world!", result);
  }

  @Test
  void shouldThrowExceptionWhenExeVoidIsNullByExecutableAsyncNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableAsyncNullSafe(null, STRING_OBJECT, null));
  }

  @Test
  void shouldThrowExceptionWhenExeObjIsNullByExecutableAsyncNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableAsyncNullSafe(NO_OP, null, null));
  }

  @Test
  void shouldThrowExceptionWhenOptionalResponseSupplierIsNullByExecutableAsyncNullSafe() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FunctionUtils.executableAsyncNullSafe(NO_OP, STRING_OBJECT, null));
  }

  @Test
  void shouldEqualsWhenStreamByForEach() {
    FunctionUtils.forEach(
        Stream.of("88888888888"),
        (value, breaker) -> {
          if (ObjectUtils.isStringNotBlank(value)) {
            assertEquals("88888888888", value);
          }
        });
  }

  @Test
  void shouldNotEqualsWhenStreamByForEach() {
    FunctionUtils.forEach(
        Stream.of("88888888888"),
        (value, breaker) -> {
          if (ObjectUtils.isStringNotBlank(value)) {
            assertNotEquals("88888888889", value);
          }
        });
  }

  @Test
  void shouldBlankWhenStreamByForEach() {
    assertDoesNotThrow(
        () ->
            FunctionUtils.forEach(
                Stream.of(""),
                (value, breaker) -> {
                  if (ObjectUtils.isStringBlank(value)) {
                    breaker.stop();
                  }
                }));
  }

  @Test
  void shouldEqualsWhenListByForEach() {
    FunctionUtils.forEach(
        List.of("88888888888"),
        (value, breaker) -> {
          if (ObjectUtils.isStringNotBlank(value)) {
            assertEquals("88888888888", value);
          }
        });
  }

  @Test
  void shouldNotEqualsWhenListByForEach() {
    FunctionUtils.forEach(
        List.of("88888888888"),
        (value, breaker) -> {
          if (ObjectUtils.isStringNotBlank(value)) {
            assertNotEquals("88888888889", value);
          }
        });
  }

  @Test
  void shouldBlankWhenListByForEach() {
    assertDoesNotThrow(
        () ->
            FunctionUtils.forEach(
                List.of(""),
                (value, breaker) -> {
                  if (ObjectUtils.isStringBlank(value)) {
                    breaker.stop();
                  }
                }));
  }
}
