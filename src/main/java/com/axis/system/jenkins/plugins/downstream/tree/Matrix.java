package com.axis.system.jenkins.plugins.downstream.tree;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * An expandable and variable width matrix backed by ArrayLists.
 *
 * <p>Every cell in the Matrix is backed by an Entry&lt;T&gt;.
 *
 * @param <T> Data type of Entry payload.
 */
public class Matrix<T> {
  private final List<List<Entry<T>>> matrix = new ArrayList<>();
  /**
   * Returns the datastore. I.e. a List (rows) of Lists (columns) of entries.
   *
   * @return the complete matrix.
   */
  public List<List<Entry<T>>> get() {
    return matrix;
  }

  /**
   * Returns the specified row.
   *
   * @param rowPos row index.
   * @return a row (List of entries in each column).
   */
  public List<Entry<T>> get(int rowPos) {
    return matrix.get(rowPos);
  }

  /**
   * Fetches an Entry at the specified row and column position.
   *
   * @param rowPos row index
   * @param colPos column index
   * @return Entry
   */
  public Entry<T> get(int rowPos, int colPos) {
    return matrix.get(rowPos).get(colPos);
  }

  /**
   * Puts an Entry at the specified row and column position.
   *
   * <p>Rows and columns will expand to match the specified coordinates. Empty cells will be filled
   * with nulls.
   *
   * @param rowPos row index
   * @param colPos col index
   * @param value Entry to store
   */
  public void put(int rowPos, int colPos, @Nullable Entry<T> value) {
    while (rowPos >= matrix.size()) {
      matrix.add(new ArrayList<>());
    }
    List<Entry<T>> row = matrix.get(rowPos);
    while (colPos >= row.size()) {
      row.add(null);
    }
    row.set(colPos, value);
  }

  @SuppressWarnings("WeakerAccess")
  public static class Entry<T> {
    final Arrow arrow;
    final T data;

    public Entry(@Nullable Arrow arrow, @Nullable T data) {
      this.arrow = arrow;
      this.data = data;
    }

    public Entry(@Nullable Arrow arrow) {
      this.arrow = arrow;
      this.data = null;
    }

    @Override
    public String toString() {
      return "Entry{arrow=" + arrow + ", data=" + data + '}';
    }
  }

  public enum Arrow {
    NS {
      @Override
      public String toString() {
        return "┃";
      }
    },
    WE {
      @Override
      public String toString() {
        return "━";
      }
    },
    WES {
      @Override
      public String toString() {
        return "┳";
      }
    },
    NES {
      @Override
      public String toString() {
        return "┣";
      }
    },
    NE {
      @Override
      public String toString() {
        return "┗";
      }
    }
  }
}
