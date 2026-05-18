package com.mds.shared.core.keys;

import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constant holder for string literals, regex patterns, index values,
 * and date formatters used across the communication module.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilKeys {

  public static final char CHAR_ZERO = '0';
  public static final char CHAR_DOT = '.';

  public static final String ALL = "TODOS";
  public static final String DECIMAL_REGEX = "([\\d]{2})$";
  public static final String SUBST_REGEX_VALUE = "$1";
  public static final String DECIMAL_REGEX_SUBST = "." + SUBST_REGEX_VALUE;
  public static final String MINUS_SIGNAL = "-";
  public static final String STR_MINUS_SIGNAL_BETWEEN_SPACE = " - ";
  public static final String PLUS_SIGNAL = "+";

  public static final String DOUBLE_VALUE_STRING = "0.00";
  public static final String STR_ZERO_SCALE_TWO = "0,00";
  public static final String STR_PERCENT = "%";
  public static final String STR_DOT = ".";
  public static final String STR_TWO_DOTS_SPACE = ": ";
  public static final String STR_EMPTY = "";
  public static final String STR_SPACE = " ";
  public static final String STR_COMMA = ",";
  public static final String STR_ONE = "1";
  public static final String STR_SLASH = "/";
  public static final String STR_T = "T";
  public static final String STR_PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
  public static final String STR_SPACE_HHMMSS = " 00:00:00";
  public static final String STR_STARTING_SPACE = "Inicializando ";
  public static final String STR_ENDING_SPACE = "Encerrando ";
  public static final String STR_SEMICOLON = ";";
  public static final String STR_ARROBA = "@";
  public static final String STR_LEFT_PARENTHESES = "(";
  public static final String STR_RIGHT_PARENTHESES = ")";
  public static final String COMPANY_KEY = "company";
  public static final String COLLABORATOR_KEY = "collaborator";
  public static final String PIPE = "|";
  public static final String BACKSLASH = "\\";
  public static final String STR_UPPER_S = "S";
  public static final String STR_UPPER_N = "N";
  public static final String STR_LOWER_S = "s";
  public static final String STR_LOWER_N = "n";
  public static final String STR_UPPER_TRUE = "TRUE";
  public static final String STR_UPPER_FALSE = "FALSE";
  public static final String STR_LOWER_TRUE = "true";
  public static final String STR_LOWER_FALSE = "false";
  public static final String STR_NULL = "null";
  public static final String STR_ZERO = "0";
  public static final String STR_SEQUENCE_NINE_NINES = "999999999";
  public static final String STR_NUMBER_REGEX = "[0-9]+";

  public static final Integer ZERO_INDEX = 0;
  public static final Integer ONE_INDEX = 1;
  public static final Integer SECOND_INDEX = 2;
  public static final Integer THIRD_INDEX = 3;
  public static final Integer FOURTH_INDEX = 4;
  public static final Integer FIFTH_INDEX = 5;
  public static final Integer SIXTH_INDEX = 6;
  public static final Integer SEVENTH_INDEX = 7;
  public static final Integer EIGHTH_INDEX = 8;
  public static final Integer NINTH_INDEX = 9;
  public static final Integer TENTH_INDEX = 10;
  public static final Integer ELEVENTH_INDEX = 11;
  public static final Integer TWELFTH_INDEX = 12;
  public static final Integer THIRTEENTH_INDEX = 13;
  public static final Integer FOURTEENTH_INDEX = 14;
  public static final Integer FIFTEENTH_INDEX = 15;
  public static final Integer SIXTEENTH_INDEX = 16;
  public static final Integer SEVENTEENTH_INDEX = 17;
  public static final Integer EIGHTEENTH_INDEX = 18;
  public static final Integer NINETEENTH_INDEX = 19;
  public static final Integer THIRTY_INDEX = 30;
  public static final Integer SIXTY_INDEX = 60;
  public static final Integer FIFTIETH_INDEX = 50;
  public static final Integer MAX_DIFFERENCE = 60;

  public static final DateTimeFormatter FORMAT_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern(STR_PATTERN_YYYYMMDDHHMMSS);
}
