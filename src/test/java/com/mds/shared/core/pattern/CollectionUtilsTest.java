package com.mds.shared.core.pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mds.shared.core.pattern.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollectionUtilsTest {

  @Test
  void convertToCollection() {
    List<String> list = CollectionUtils.convertToList("a", "b", "c");

    assertEquals(3, list.size());
    assertEquals("a", list.get(0));
    assertEquals("b", list.get(1));
    assertEquals("c", list.get(2));
  }

  @Test
  void convertToList() {
    List<String> list = CollectionUtils.convertToList("a", "b", "c");

    assertEquals(list, CollectionUtils.convertToList(list.toArray(new String[0])));
  }

  @Test
  void unmodifiableSet() {
    Set<String> set = Set.of("a", "b", "c");
    Set<String> unmodifiableSet = CollectionUtils.unmodifiableSet(set);

    assertTrue(unmodifiableSet.contains("a"));
    assertTrue(unmodifiableSet.contains("b"));
    assertTrue(unmodifiableSet.contains("c"));

    // Trying to modify the unmodifiable set should throw an exception.
    assertThrows(UnsupportedOperationException.class, () -> unmodifiableSet.add("d"));
  }

  @Test
  void unmodifiableList() {
    List<String> list = List.of("a", "b", "c");
    List<String> unmodifiableList = CollectionUtils.unmodifiableList(list);

    assertTrue(unmodifiableList.contains("a"));
    assertTrue(unmodifiableList.contains("b"));
    assertTrue(unmodifiableList.contains("c"));

    // Trying to modify the unmodifiable list should throw an exception.
    assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("d"));
  }

  @Test
  void isEmpty() {
    List<String> list = new ArrayList<>();
    assertTrue(CollectionUtils.isEmpty(list));
  }

  @Test
  void isNotEmpty() {
    List<String> list = List.of("a");
    assertFalse(CollectionUtils.isEmpty(list));
  }

  @Test
  void join() {
    List<String> list1 = List.of("a", "b");
    List<String> list2 = List.of("c", "d");

    List<String> joinedList = CollectionUtils.join(list1, list2);

    assertEquals(4, joinedList.size());
    assertTrue(joinedList.contains("a"));
    assertTrue(joinedList.contains("b"));
    assertTrue(joinedList.contains("c"));
    assertTrue(joinedList.contains("d"));
  }

  @Test
  void isNotEmptyWithNullList() {
    List<String> list = null;
    assertFalse(CollectionUtils.isNotEmpty(list));
  }

  @Test
  void isNotEmptyWithEmptyList() {
    List<String> list = new ArrayList<>();
    assertFalse(CollectionUtils.isNotEmpty(list));
  }

  @Test
  void isNotEmptyWithSet() {
    Set<String> set = Set.of("a");
    assertTrue(CollectionUtils.isNotEmpty(set));
  }

  @Test
  void isNotEmptyWithNullSet() {
    Set<String> set = null;
    assertFalse(CollectionUtils.isNotEmpty(set));
  }

  @Test
  void isNotEmptyWithEmptySet() {
    Set<String> set = new HashSet<>();
    assertFalse(CollectionUtils.isNotEmpty(set));
  }
}
