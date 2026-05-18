package com.mds.shared.core.pattern.utils;

import static com.mds.shared.core.pattern.utils.ObjectUtils.nonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A utility class that provides methods for working with collections.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtils {

  /**
   * Converts the specified elements to a collection.
   *
   * @param elements The elements to convert.
   * @param <T>      The type of the elements.
   * @return The collection.
   */
  @SafeVarargs
  public static <T> Collection<T> convertToCollection(T... elements) {
    return new ArrayList<>(List.of(elements));
  }

  /**
   * Converts the specified elements to a list.
   *
   * @param elements The elements to convert.
   * @param <T>      The type of the elements.
   * @return The list.
   */
  @SafeVarargs
  public static <T> List<T> convertToList(T... elements) {
    return (List<T>) convertToCollection(elements);
  }

  /**
   * Returns an unmodifiable set of the specified elements.
   *
   * @param set The set to make unmodifiable.
   * @param <T> The type of the elements in the set.
   * @return The unmodifiable set.
   */
  public static <T> Set<T> unmodifiableSet(final Set<? extends T> set) {
    return Collections.unmodifiableSet(set);
  }

  /**
   * Returns an unmodifiable list of the specified elements.
   *
   * @param list The list to make unmodifiable.
   * @param <T>  The type of the elements in the list.
   * @return The unmodifiable list.
   */
  public static <T> List<T> unmodifiableList(final List<? extends T> list) {
    return Collections.unmodifiableList(list);
  }

  /**
   * Checks if the specified list is empty.
   *
   * @param list The list to check.
   * @param <T>  The type of the elements in the list.
   * @return True if the list is empty, False otherwise.
   */
  public static <T> boolean isEmpty(final List<? extends T> list) {
    return list == null || list.isEmpty();
  }

  /**
   * Checks if the specified list is not empty.
   *
   * @param list The list to check.
   * @param <T>  The type of the elements in the list.
   * @return True if the list is not empty, False otherwise.
   */
  public static <T> boolean isNotEmpty(final List<? extends T> list) {
    return !isEmpty(list);
  }

  /**
   * Checks if the specified set is empty.
   *
   * @param set The set to check.
   * @param <T> The type of the elements in the set.
   * @return True if the set is empty, False otherwise.
   */
  public static <T> boolean isEmpty(final Set<? extends T> set) {
    return set == null || set.isEmpty();
  }

  /**
   * Checks if the specified set is not empty.
   *
   * @param set The set to check.
   * @param <T> The type of the elements in the set.
   * @return True if the set is not empty, False otherwise.
   */
  public static <T> boolean isNotEmpty(final Set<? extends T> set) {
    return !isEmpty(set);
  }

  /**
   * Joins a list of lists into a single list.
   *
   * @param list The lists to join.
   * @return The joined list.
   */
  @SafeVarargs
  public static <T> List<T> join(List<? extends T>... list) {
    List<T> result = new ArrayList<>();
    if (nonNull(list)) {
      for (List<? extends T> l : list) {
        result.addAll(l);
      }
    }
    result.removeAll(Collections.singleton(null));
    return result;
  }
}
