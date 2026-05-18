package com.mds.shared.core.pattern.annotation;

import static com.mds.shared.core.pattern.keys.PatternUtilsKeys.EMPTY;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@link String} field for automatic special-character removal.
 *
 * <p>When processed by {@link com.mds.shared.core.pattern.utils.ObjectUtils#removeCharactersSpecial(Object)},
 * the field value is cleaned using the regex provided in {@link #value()}.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RemoveSpecialCharacters {
  String value() default EMPTY;
}
