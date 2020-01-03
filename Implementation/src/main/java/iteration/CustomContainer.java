package iteration;

/**
 * Defines the necessary functionality of all {@code CustomContainer}s.
 *
 * @param <T> Element-Type to be stored
 * 
 * @author Michael Watholowitsch
 */
public interface CustomContainer<T> {
  /** Creates and returns a {@linkplain CustomIterator} for this {@code CustomContainer}. */
  CustomIterator<T> getIterator();

  /**
   * Adds a new element in this {@code CustomContainer}.
   * 
   * @param element the new element
   */
  void add(T element);

  /** Returns the number of elements in this {@code CustomContainer}. */
  int size();

  /**
   * Checks if the passed {@code T} is stored in this {@code CustomContainer}
   * 
   * @param obj the passed {@code T}
   * @return {@code true} if the {@code obj} is already stored, else {@code false}
   */
  boolean contains(T obj);

  void update(T element);

  void delete(T element);
}
