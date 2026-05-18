package com.mds.shared.core.pattern.utils;

import com.mds.shared.core.pattern.annotation.RemoveSpecialCharacters;
import com.mds.shared.core.pattern.entity.ObjectNullSafe;
import com.mds.shared.core.pattern.interfaces.SupplierPattern;
import com.mds.shared.core.pattern.interfaces.SupplierPatternObject;
import com.mds.shared.core.pattern.interfaces.SupplierPatternObjectAndThrowable;
import com.mds.shared.core.pattern.keys.PatternUtilsKeys;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A utility class that provides methods for working with objects.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtils {

  /**
   * Returns true if the specified value is null.
   *
   * @param value The value to check.
   * @return True if the value is null, false otherwise.
   */
  public static <T> boolean isNull(T value) {
    return value == null;
  }

  /**
   * Validates that the specified value is not null.
   *
   * @param value   The value to validate.
   * @param message The error message to throw if the value is null.
   * @param <T>     The type of the value.
   */
  public static <T> void isValidNull(T value, String message) {
    if (value == null) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Validates that the specified value is not null.
   *
   * @param value           The value to validate.
   * @param messageSupplier A supplier of the error message to throw if the value is null.
   * @param <T>             The type of the value.
   */
  public static <T> void isValidNull(T value, Supplier<String> messageSupplier) {
    isValidNull(value, nullSafeSupplierGet(messageSupplier));
  }

  /**
   * Returns true if the specified value is not null.
   *
   * @param value The value to check.
   * @return True if the value is not null, false otherwise.
   */
  public static boolean nonNull(Object value) {
    return (value != null);
  }

  /**
   * Validates that the specified expression is true.
   *
   * @param expression The expression to validate.
   * @param message    The error message to throw if the expression is false.
   */
  public static void isValidTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Validates that the specified expression is true.
   *
   * @param expression      The expression to validate.
   * @param messageSupplier A supplier of the error message to throw if the expression is false.
   */
  public static void isValidTrue(boolean expression, Supplier<String> messageSupplier) {
    isValidTrue(expression, nullSafeSupplierGet(messageSupplier));
  }

  /**
   * Returns the length of the specified character sequence.
   *
   * @param cs The character sequence to get the length of.
   * @return The length of the character sequence, or 0 if the character sequence is null.
   */
  public static int length(final CharSequence cs) {
    return cs == null ? 0 : cs.length();
  }

  /**
   * Returns true if the specified value is a string and it is empty.
   *
   * @param value The value to check.
   * @return True if the value is a string, and it is empty, false otherwise.
   */
  public static <T> boolean isStringEmpty(T value) {
    boolean response = true;
    if (nonNull(value) && value instanceof String str) {
      response = str.isEmpty();
    }
    return response;
  }

  /**
   * Returns true if the specified value is a string and it is not empty.
   *
   * @param value The value to check.
   * @return True if the value is a string, and it is not empty, false otherwise.
   */
  public static <T> boolean isStringNotEmpty(T value) {
    return !isStringEmpty(value);
  }

  /**
   * Returns true if the specified value is a string and it is empty.
   *
   * @param value The value to check.
   * @return True if the specified value is a string, and it is empty, false otherwise.
   */
  public static <T> boolean isStringBlank(final T value) {
    if (value instanceof CharSequence cs) {
      final int strLen = length(cs);
      if (strLen == 0) {
        return true;
      }
      for (int i = 0; i < strLen; i++) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns true if the specified value is a string and it is not blank.
   *
   * @param value The value to check.
   * @return True if the value is a string, and it is not blank, false otherwise.
   */
  public static <T> boolean isStringNotBlank(T value) {
    return !isStringBlank(value);
  }

  /**
   * Returns a string that joins the specified values with the specified delimiter.
   *
   * @param delimiter The delimiter to use between the values.
   * @param value     The values to join.
   * @return The joined string.
   */
  public static <T> String joinWithDelimiter(T delimiter, Object... value) {
    StringBuilder strResponse = new StringBuilder();
    if (nonNull(value)) {
      for (int i = 0; i < value.length; i++) {
        if (i > 0 && delimiter != null) {
          strResponse.append(delimiter);
        }
        strResponse.append(value[i]);
      }
    }
    return strResponse.toString();
  }

  /**
   * Returns a string that joins the specified values with a space delimiter.
   *
   * @param value The values to join.
   * @return The joined string.
   */
  public static String join(Object... value) {
    return joinWithDelimiter(null, value);
  }

  /**
   * Gets the value of the supplier, or null if the supplier is null.
   *
   * @param optionalObjectSupplier The supplier to get the value from.
   * @return The value of the supplier, or null.
   */
  public static <T> T nullSafeSupplierGet(Supplier<T> optionalObjectSupplier) {
    return (optionalObjectSupplier != null ? optionalObjectSupplier.get() : null);
  }

  /**
   * Gets the value of the supplier, or null if the supplier is null.
   *
   * @param optionalObjectSupplier The supplier to get the value from.
   * @return The value of the supplier, or null.
   */
  public static <T> T nullSafeSupplierPatternExecute(SupplierPattern<? extends T> optionalObjectSupplier, Throwable exception) {
    T objectReturn = null;
    if (optionalObjectSupplier != null) {
      if (optionalObjectSupplier instanceof SupplierPatternObjectAndThrowable<? extends T> spot) {
        objectReturn = spot.execute(new ObjectNullSafe(exception));
      } else if (optionalObjectSupplier instanceof SupplierPatternObject<? extends T> spo) {
        objectReturn = spo.execute();
      } else {
        objectReturn = optionalObjectSupplier.execute();
      }
    }
    return objectReturn;
  }

  /**
   * Compares two objects for equality.
   *
   * @param source The first object.
   * @param target The second object.
   * @return True if the objects are equal, false otherwise.
   */
  public static boolean equals(Object source, Object target) {
    // Check if the source and target are null.
    if (isNull(source) || isNull(target)) {
      return false;
    }

    // Check if the source and target are the same object.
    if (source == target) {
      return true;
    }

    // Check if the source and target are of the same type.
    if (source.getClass() != target.getClass()) {
      return false;
    }

    // Compare the source and target using the `equals()` method of their class.
    return source.equals(target);
  }

  /**
   * Compares two strings ignoring case.
   *
   * @param source The first string.
   * @param target The second string.
   * @return True if the strings are equal ignoring case, false otherwise.
   */
  public static boolean equalsStringIgnoreCase(Object source, Object target) {
    // Check if the source and target are null.
    if (isNull(source) || isNull(target)) {
      return false;
    }

    // Check if the source and target are strings.
    if (!(source instanceof String) || !(target instanceof String)) {
      throw new ClassCastException("Expected a String, but got source is " + source.getClass().getName() + " and target is " + target.getClass().getName());
    }

    // Compare the strings ignoring case.
    return ((String) source).equalsIgnoreCase((String) target);
  }

  /**
   * Formats a string using a map of values. <br>
   * <br>
   * Example:<br> FormatTextUtil.format("Hello ${0}", Map.of("0", "World!")); <br> Result: "Hello World!"
   *
   * @param format The format string.
   * @param values The map of values.
   * @return The formatted string.
   */
  public static String format(String format, Map<String, Object> values) {
    // Create a StringBuilder to hold the formatted string.
    StringBuilder formatter = new StringBuilder(format);

    // Create a list to hold the values.
    List<Object> valueList = new ArrayList<>();

    // Create a matcher to find the placeholders in the format string.
    Matcher matcher = Pattern.compile("\\$\\{(\\w+)}").matcher(format);

    // Iterate over the placeholders and replace them with the corresponding values.
    while (matcher.find()) {
      // Get the name of the placeholder.
      String key = matcher.group(1);

      // Create a format string for the placeholder.
      String formatKey = String.format("${%s}", key);

      // Get the index of the placeholder in the format string.
      int index = formatter.indexOf(formatKey);

      // If the placeholder is found, replace it with the corresponding value.
      if (index != -1) {
        formatter.replace(index, index + formatKey.length(), "%s");
        valueList.add(values.get(key));
      }
    }

    // Return the formatted string.
    return String.format(formatter.toString(), valueList.toArray());
  }

  /**
   * Method with responsible to remove special characters
   *
   * @param object The reference class to remove characters special in the fields.
   */
  public static void removeCharactersSpecial(Object object) {
    Class<?> clazz = object.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      FunctionUtils.executableVoid(() -> {
        if (field.isAnnotationPresent(RemoveSpecialCharacters.class)) {
          ReflectionUtils.makeAccessible(field);
          RemoveSpecialCharacters removeSpecialCharactersAnnotation = field.getAnnotation(RemoveSpecialCharacters.class);
          String value = removeSpecialCharactersAnnotation.value();
          String currentValue = (String) field.get(object);
          if (isStringNotEmpty(currentValue)) {
            String cleanedValue = currentValue.replaceAll(value, PatternUtilsKeys.EMPTY);
            field.set(object, cleanedValue);
          }
        }
      });
    }
  }

  public static String substring(String str, int start, int end) {
    if (str == null) {
      return null;
    } else {
      if (end < 0) {
        end += str.length();
      }
      if (start < 0) {
        start += str.length();
      }
      if (end > str.length()) {
        end = str.length();
      }
      if (start > end) {
        return "";
      } else {
        if (start < 0) {
          start = 0;
        }
        if (end < 0) {
          end = 0;
        }
        return str.substring(start, end);
      }
    }
  }
}
