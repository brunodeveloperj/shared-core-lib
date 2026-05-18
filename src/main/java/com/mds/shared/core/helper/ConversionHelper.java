package com.mds.shared.core.helper;

import static com.mds.shared.core.pattern.utils.FunctionUtils.executableObjectAndThrowableByNullSafe;
import static com.mds.shared.core.pattern.utils.FunctionUtils.executableObjectNullSafe;
import static com.mds.shared.core.pattern.utils.ObjectUtils.isStringNotBlank;
import static com.mds.shared.core.pattern.utils.ReflectionUtils.generateInstance;
import static com.mds.shared.core.keys.UtilKeys.DECIMAL_REGEX;
import static com.mds.shared.core.keys.UtilKeys.DECIMAL_REGEX_SUBST;
import static com.mds.shared.core.keys.UtilKeys.DOUBLE_VALUE_STRING;
import static com.mds.shared.core.keys.UtilKeys.FORMAT_YYYYMMDDHHMMSS;
import static com.mds.shared.core.keys.UtilKeys.MINUS_SIGNAL;
import static com.mds.shared.core.keys.UtilKeys.NINETEENTH_INDEX;
import static com.mds.shared.core.keys.UtilKeys.ONE_INDEX;
import static com.mds.shared.core.keys.UtilKeys.PLUS_SIGNAL;
import static com.mds.shared.core.keys.UtilKeys.STR_COMMA;
import static com.mds.shared.core.keys.UtilKeys.STR_EMPTY;
import static com.mds.shared.core.keys.UtilKeys.STR_NUMBER_REGEX;
import static com.mds.shared.core.keys.UtilKeys.STR_ONE;
import static com.mds.shared.core.keys.UtilKeys.STR_SLASH;
import static com.mds.shared.core.keys.UtilKeys.STR_SPACE;
import static com.mds.shared.core.keys.UtilKeys.STR_SPACE_HHMMSS;
import static com.mds.shared.core.keys.UtilKeys.STR_T;
import static com.mds.shared.core.keys.UtilKeys.STR_UPPER_S;
import static com.mds.shared.core.keys.UtilKeys.TENTH_INDEX;
import static com.mds.shared.core.keys.UtilKeys.ZERO_INDEX;
import static java.nio.charset.StandardCharsets.UTF_8;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mds.shared.core.adapter.LocalDateTimeTypeAdapter;
import com.mds.shared.core.adapter.LocalDateTypeAdapter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class providing type conversion methods for common Java types.
 *
 * <p>Supports conversions between {@link Object}, {@link String}, {@link Long},
 * {@link Double}, {@link Integer}, {@link Boolean}, {@link LocalDateTime},
 * JSON (via {@link Gson} and {@link ObjectMapper}), and Base64 encoding.
 * All conversions are null-safe and log errors instead of propagating exceptions.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConversionHelper {

  @Getter private static final Gson gson;
  @Getter private static final ObjectMapper objectMapper;
  @Getter private static final ObjectWriter objectWriter;

  static {
    objectMapper = JsonMapper.builder().build();
    objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                            .create();
  }

  // *************************** UTILITY_METHODS ***************************
  public static String convertStringToDecimal(final String value) {
    AtomicReference<String> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          if (isStringNotBlank(value)) {
            String valueWrapper = value;
            String signal = getSignal(valueWrapper);
            valueWrapper = removeSignal(valueWrapper, signal);
            valueWrapper = new BigInteger(valueWrapper).toString();
            if (new BigDecimal(valueWrapper).compareTo(BigDecimal.ZERO) == 0) {
              return DOUBLE_VALUE_STRING;
            }
            String result =
                createMatcherRegex(DECIMAL_REGEX, valueWrapper).replaceFirst(DECIMAL_REGEX_SUBST);
            response.set(putSignalInFront(result, signal));
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("(convertStringToDecimal) - Error converting the object of type String to DecimalString", objectNullSafe.getException());
          return response.get();
        });
  }

  public static Long convertObjectToLong(Object value) {
    AtomicReference<Long> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          if (value != null) {
            response.set(Long.valueOf(convertObjectToString(value)));
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("(convertObjectToLong): Error converting the query object to Long", objectNullSafe.getException());
          return response.get();
        });
  }

  public static Double convertObjectToDouble(Object value) {
    AtomicReference<Double> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          if (value != null) {
            response.set(Double.valueOf(convertObjectToString(value)));
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("(convertObjectToDouble): Error converting the query object to Double", objectNullSafe.getException());
          return response.get();
        });
  }

  public static String convertObjectToString(Object value) {
    AtomicReference<String> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          if (value != null) {
            response.set(String.valueOf(value));
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("(convertObjectToString): Error converting the query object to String", objectNullSafe.getException());
          return response.get();
        });
  }

  /**
   * Convert Object to Boolean Expected values : Numerics: 1(true) or other number(false) Char:
   * 'S'(true) or other char(false) String: "true" ignoring case or other String
   *
   * @param value The Object for converting.
   * @return value as Boolean.
   */
  public static Boolean convertObjectToBoolean(Object value) {
    AtomicReference<Boolean> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          String strValue = convertObjectToString(value);
          if (strValue != null) {
            if (checkNumberCharacters(strValue)) {
              response.set(STR_ONE.equals(strValue));
            } else if (ONE_INDEX == strValue.length()) {
              response.set(STR_UPPER_S.equalsIgnoreCase(strValue));
            } else {
              response.set(Boolean.valueOf(strValue));
            }
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("(convertObjectToBoolean): Error converting the query object to Boolean", objectNullSafe.getException());
          return response.get();
        });
  }

  /**
   * Convert value using {@link Gson}
   *
   * @param value The object for converting.
   * @return value as string.
   */
  public static String convertObjectToJson(Object value) {
    return executableObjectNullSafe(
        () -> {
          if (value != null) {
            return gson.toJson(value);
          }
          return null;
        },
        () -> STR_EMPTY);
  }

  /**
   * Convert value using {@link Gson}
   *
   * @param value The object for converting.
   * @param clazz The class of object for converting.
   * @return value as string.
   */
  public static <T> T convertJsonToObject(Object value, Class<? extends T> clazz) {
    return executableObjectNullSafe(
        () -> {
          if (value != null) {
            String json;
            if (!(value instanceof String)) {
              json = convertObjectToJson(value);
            } else {
              json = (String) value;
            }
            return getGson().fromJson(json, clazz);
          }
          return null;
        },
        () -> generateInstance(clazz));
  }

  /**
   * Convert value using {@link Gson}
   *
   * @param value The object for converting.
   * @param typeToken The type of object for converting.
   * @return value as string.
   */
  public static <T> T convertJsonToObject(Object value, TypeToken<T> typeToken) {
    return executableObjectNullSafe(
        () -> {
          if (value != null) {
            String json;
            if (!(value instanceof String)) {
              json = convertObjectToJson(value);
            } else {
              json = (String) value;
            }
            return getGson().fromJson(json, typeToken);
          }
          return null;
        },
        () -> null);
  }

  /**
   * Convert value using {@link ObjectWriter}
   *
   * @param value The object for converting.
   * @return value as string.
   */
  public static String convertObjectWriteToJson(Object value) {
    return executableObjectNullSafe(
        () -> {
          if (value != null) {
            return getObjectWriter().writeValueAsString(value);
          }
          return null;
        },
        () -> STR_EMPTY);
  }

  /**
   * Convert value using {@link ObjectWriter}
   *
   * @param value The object for converting.
   * @param clazz The class of object for converting.
   * @return value as Object.
   */
  public static <T> T convertObjectWriteToJson(Object value, Class<? extends T> clazz) {
    return executableObjectNullSafe(
        () -> {
          if (value != null) {
            String json;
            if (!(value instanceof String)) {
              json = convertObjectWriteToJson(value);
            } else {
              json = (String) value;
            }
            return getObjectMapper().readValue(json, clazz);
          }
          return null;
        },
        () -> generateInstance(clazz));
  }

  /**
   * Convert value using {@link ObjectWriter}
   *
   * @param value The object for converting.
   * @param typeReference The type of object for converting.
   * @return value as Object.
   */
  public static <T> T convertObjectWriteToJson(Object value, TypeReference<T> typeReference) {
    return executableObjectNullSafe(
        () -> {
          if (value != null) {
            String json;
            if (!(value instanceof String)) {
              json = convertObjectWriteToJson(value);
            } else {
              json = (String) value;
            }
            return getObjectMapper().readValue(json, typeReference);
          }
          return null;
        },
        () -> null);
  }

  /**
   * Converts an object to a LocalDateTime.
   *
   * <p>This method attempts to convert the provided object to a LocalDateTime instance. It uses a
   * null-safe executable object pattern to handle potential null values and exceptions.
   *
   * @param value The object to be converted. It should be a string representation of a date and
   *     time.
   * @return The converted LocalDateTime instance, or null if the conversion fails.
   */
  public static LocalDateTime convertObjectToLocalDateTime(Object value) {
    AtomicReference<LocalDateTime> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          if (value != null) {
            response.set(LocalDateTime.parse(createStrDate(value), FORMAT_YYYYMMDDHHMMSS));
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("(convertObjectToLocalDateTime): Error converting the object to LocalDateTime", objectNullSafe.getException());
          return response.get();
        });
  }

  /**
   * Converts an object to an Integer.
   *
   * <p>This method attempts to convert the provided object to an Integer instance. It uses a
   * null-safe executable object pattern to handle potential null values and exceptions.
   *
   * @param value The object to be converted. It should be a string representation of an integer.
   * @return The converted Integer instance, or null if the conversion fails.
   */
  public static Integer convertObjectToInteger(Object value) {
    AtomicReference<Integer> response = new AtomicReference<>(null);
    return executableObjectAndThrowableByNullSafe(
        () -> {
          if (value != null) {
            response.set(Integer.valueOf(convertObjectToString(value)));
          }
          return response.get();
        },
        (objectNullSafe) -> {
          log.error("[FormatValueUtil] - (convertObjectToInteger): Error converting the object to Integer", objectNullSafe.getException());
          return response.get();
        });
  }

  /**
   * Writes a BOM (Byte Order Mark) to the beginning of a file and then copies the content from the
   * source file to the destination file.
   *
   * @param src The path to the source file.
   * @param dest The path to the destination file.
   */
  public static void writeBomFile(Path src, Path dest) {
    try (FileOutputStream fos = new FileOutputStream(dest.toFile())) {
      byte[] bom = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
      fos.write(bom);
      Files.copy(src, fos);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * Converts a comma-separated string of product codes into a list of product codes.
   *
   * @param codeProduct The comma-separated string of product codes.
   * @return A list of product codes. If the input is null, returns null. If the input does not
   *     contain commas, returns a list with a single element.
   */
  public static List<String> listProductCodesFromCommaSepVal(String codeProduct) {
    List<String> listCodeProduct;
    if (codeProduct != null && codeProduct.contains(STR_COMMA)) {
      listCodeProduct = new ArrayList<>();
      String[] codeProductArray = codeProduct.split(STR_COMMA);
      codeProductArray = Arrays.copyOf(codeProductArray, codeProductArray.length);
      Collections.addAll(listCodeProduct, codeProductArray);
    } else {
      if (codeProduct == null) {
        listCodeProduct = null;
      } else {
        listCodeProduct = new ArrayList<>();
        listCodeProduct.add(codeProduct);
      }
    }
    return listCodeProduct;
  }

  /**
   *
   *
   * <pre>
   *   Convert Object in base64 to String.
   *   Example:
   *    - Parameter in base64: OTk5OTk5OTk5OTkuOTk5OTk5OTk5OTk5OTk=
   *    - return in String: 99999999999.99999999999999
   * </pre>
   *
   * @param value Object in base64
   * @return String in base64
   */
  public static String converterBase64ToString(Object value, boolean defaultOnErrorValue) {
    AtomicReference<String> response = new AtomicReference<>((defaultOnErrorValue ? convertObjectToString(value) : null));
    return executableObjectAndThrowableByNullSafe(
        () -> {
          byte[] decodedBytes = Base64.getDecoder().decode(response.get());
          response.set(new String(decodedBytes, UTF_8));
          return response.get();
        },
        (objectNullSafe) -> response.get());
  }

  // *************************** PRIVATE_METHODS ***************************

  private static Matcher createMatcherRegex(String regex, String value) {
    final Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(value);
  }

  private static String getSignal(String value) {
    String signal = STR_EMPTY;
    if (value.contains(MINUS_SIGNAL)) {
      signal = MINUS_SIGNAL;
    } else if (value.contains(PLUS_SIGNAL)) {
      signal = PLUS_SIGNAL;
    }
    return signal;
  }

  private static String removeSignal(String value, String signal) {

    return value.replace(signal, STR_EMPTY);
  }

  private static String putSignalInFront(String value, String signal) {
    if (signal.equals(PLUS_SIGNAL)) {
      signal = STR_EMPTY;
    }
    return signal + value;
  }

  private static String createStrDate(Object object) {
    String strDate = convertObjectToString(object);
    if (null != strDate && NINETEENTH_INDEX <= strDate.length()) {
      strDate =
          strDate
              .substring(ZERO_INDEX, NINETEENTH_INDEX)
              .replace(STR_T.charAt(ZERO_INDEX), STR_SPACE.charAt(ZERO_INDEX))
              .replace(STR_SLASH.charAt(ZERO_INDEX), MINUS_SIGNAL.charAt(ZERO_INDEX));
    } else if (null != strDate && TENTH_INDEX == strDate.length()) {
      strDate =
          strDate
              .replace(STR_SLASH.charAt(ZERO_INDEX), MINUS_SIGNAL.charAt(ZERO_INDEX))
              .concat(STR_SPACE_HHMMSS);
    }
    return strDate;
  }

  private static boolean checkNumberCharacters(String sequence) {
    Pattern pattern = Pattern.compile(STR_NUMBER_REGEX);
    Matcher matcher = pattern.matcher(sequence);
    return matcher.matches();
  }
}
