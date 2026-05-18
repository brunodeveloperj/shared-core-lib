package com.mds.shared.core.pattern.keys;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * String and index constants used for parameter validation and
 * error messages inside {@link com.mds.shared.core.pattern.utils.FunctionUtils}
 * and {@link com.mds.shared.core.pattern.utils.ObjectUtils}.
 *
 * <p>Includes parameter names ({@link #VOID_PARAM}, {@link #OBJECT_PARAM}),
 * message templates ({@link #PARAM_EXCEPTION_MESSAGE},
 * {@link #ARRAY_EXCEPTION_MESSAGE}), and general-purpose defaults.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternUtilsKeys {

  public static final String EMPTY = "";
  public static final int ZERO_INDEX = 0;
  public static final String VOID_PARAM = "exeVoid";
  public static final String OBJECT_PARAM = "exeObj";
  public static final String CUSTOMIZED_EXCEPTION_PARAM = "customizedException";
  public static final String OPTIONAL_RESPONSE_SUPPLIER_PARAM = "optionalResponseSupplier";
  public static final String PARAM_EXCEPTION_MESSAGE = "The ${0} parameter must not be null.";
  public static final String ARRAY_EXCEPTION_MESSAGE = "The ${0} parameter array cannot be null.";
}
