package com.mds.shared.core.pattern.utils;

import static com.mds.shared.core.pattern.utils.ObjectUtils.isNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isStringNotBlank;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isValidNull;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isValidTrue;
import static com.mds.shared.core.pattern.utils.ObjectUtils.nonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A class that provides static methods for working with reflection.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReflectionUtils {

  // An array of empty fields.
  private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

  // An array of empty fields.
  private static final Map<Class<?>, Field[]> declaredFieldsCache = new LinkedHashMap<>(256);

  private static final String CLASS_EXCEPTION_MESSAGE = "Class must not be null.";

  /**
   * Generates an instance of the specified class.
   *
   * @param clazz The class to instantiate.
   * @param <T>   The type of the class.
   * @return The generated instance.
   */
  public static <T> T generateInstance(Class<? extends T> clazz) {
    // Validate the class parameter.
    isValidNull(clazz, CLASS_EXCEPTION_MESSAGE);

    // Execute the executable method.
    return FunctionUtils.executableObject(() -> clazz.getDeclaredConstructor().newInstance());
  }

  /**
   * Gets the declared field with the specified name from the specified class.
   *
   * @param fieldName The name of the field.
   * @param clazz     The class to get the field from.
   * @return The declared field, or null if the field does not exist.
   */
  public static Field getDeclaredField(String fieldName, Class<?> clazz) {
    // Validate the class parameter.
    isValidNull(clazz, CLASS_EXCEPTION_MESSAGE);

    // Search for the field in the class.
    Field field = null;
    for (Field f : getDeclaredFields(clazz)) {
      if (f.getName().equalsIgnoreCase(fieldName)) {
        field = f;
        break;
      }
    }

    // Return the field.
    return field;
  }

  /**
   * Gets all declared fields from the specified class.
   *
   * @param clazz The class to get the fields from.
   * @return An array of declared fields.
   */
  public static Field[] getDeclaredFields(Class<?> clazz) {
    // Validate the class parameter.
    isValidNull(clazz, CLASS_EXCEPTION_MESSAGE);

    // Check the cache for the declared fields.
    Field[] fields = declaredFieldsCache.get(clazz);

    // If the fields are not in the cache, get them from the class.
    if (fields == null) {
      fields = clazz.getDeclaredFields();
      declaredFieldsCache.put(clazz, (fields.length == 0 ? EMPTY_FIELD_ARRAY : fields));
    }

    // Return the fields.
    return fields;
  }

  /**
   * Finds the first field with the specified name or type from the specified class.
   *
   * @param clazz The class to search the field in.
   * @param name  The name of the field, or null if the field name is not known.
   * @param type  The type of the field, or null if the field type is not known.
   * @return The found field, or null if no matching field was found.
   */
  public static Field findField(Class<?> clazz, String name, Class<?> type) {
    // Validate the class parameter.
    isValidNull(clazz, CLASS_EXCEPTION_MESSAGE);

    // Check if the name or type parameters are specified.
    isValidTrue(isStringNotBlank(name) || nonNull(type), "Either name or type of the field must be specified");

    // Initialize the field.
    Field field = null;

    // Search for the field in the class.
    Class<?> searchType = clazz;
    while (Object.class != searchType && nonNull(searchType)) {
      Field[] fields = getDeclaredFields(searchType);
      for (Field f : fields) {
        if ((isNull(name) || name.equals(f.getName())) && (isNull(type) || type.equals(f.getType()))) {
          field = f;
          break;
        }
      }
      searchType = searchType.getSuperclass();
    }

    // Return the field.
    return field;
  }

  /**
   * Makes the specified field accessible.
   *
   * @param field The field to make accessible.
   */
  public static void makeAccessible(Field field) {
    // Validate the field parameter.
    isValidNull(field, "Field must not be null");

    // Make the field accessible if it is not already.
    org.springframework.util.ReflectionUtils.makeAccessible(field);
  }

  /**
   * Makes the specified method accessible.
   *
   * @param method The method to make accessible.
   */
  public static void makeAccessible(Method method) {
    // Validate the method parameter.
    isValidNull(method, "Method must not be null");

    // Make the method accessible if it is not already.
    org.springframework.util.ReflectionUtils.makeAccessible(method);
  }

  /**
   * Gets the value of the field with the specified name from the specified class and object.
   *
   * @param fieldName The name of the field.
   * @param clazz     The class of the object.
   * @param target    The object to get the field value from.
   * @return The value of the field, or null if the field does not exist.
   */
  public static Object getField(String fieldName, Class<?> clazz, Object target) {
    // Validate the parameters.
    isValidNull(fieldName, "Field name must not be null");
    isValidNull(clazz, "Class must not be null");
    isValidNull(target, "Target object must not be null");

    // Get the field.
    Field field = getDeclaredField(fieldName, clazz);

    // Return the value of the field.
    return field == null ? null : FunctionUtils.executable(() -> makeAccessible(field), () -> field.get(target));
  }

  /**
   * Sets the value of the specified field in the specified object.
   *
   * @param field  The field to set.
   * @param target The object to set the field value in.
   * @param value  The new value of the field.
   */
  public static void setField(Field field, Object target, Object value) {
    // Validate the parameters.
    isValidNull(field, "Field must not be null");
    isValidNull(target, "Target object must not be null");

    // Make the field accessible.
    makeAccessible(field);

    // Set the value of the field.
    FunctionUtils.executableVoid(() -> field.set(target, value));
  }

  /**
   * Clears the cache of declared fields.
   */
  public static void clearCache() {
    declaredFieldsCache.clear();
  }
}
