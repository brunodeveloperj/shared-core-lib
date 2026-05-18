package com.mds.shared.core.base;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Generic record wrapping Spring Data {@link Page} metadata for serialization-friendly responses.
 *
 * <p>Converts a {@link Page} into a flat, JSON-safe representation and provides
 * a {@link #toPage()} method for round-trip conversion.
 *
 * @param <T> the element type of the page content.
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public record GenericPageResponse<T>(List<T> content, int number, int size, int numberOfElements, int totalPages, long totalElements, List<String> sort) {

  public GenericPageResponse(Page<T> page) {
    this(page.getContent(), page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalPages(), page.getTotalElements(), sortToList(page.getSort()));
  }

  public static List<String> sortToList(Sort sort) {
    return sort.stream()
               .map(order -> order.getProperty() + ":" + order.getDirection())
               .toList();
  }

  public static Sort listToSort(List<String> sortList) {
    if (sortList == null || sortList.isEmpty()) {
      return Sort.unsorted();
    }
    return Sort.by(
        sortList.stream()
            .map(s -> {
              String[] parts = s.split(":");
              String property = parts[0];
              Sort.Direction direction = parts.length > 1 ? Sort.Direction.fromString(parts[1]) : Sort.Direction.ASC;
              return new Sort.Order(direction, property);
            })
            .toList()
    );
  }

  public boolean hasContent() {
    return content != null && !content.isEmpty();
  }

  public Page<T> toPage() {
    return new PageImpl<>(content, PageRequest.of(number, size, listToSort(sort)), totalElements);
  }
}
