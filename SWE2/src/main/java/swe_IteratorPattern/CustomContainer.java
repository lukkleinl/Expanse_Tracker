package swe_IteratorPattern;

/**
 * Defines the necessary functionality of all {@code CustomContainer}s.
 *
 * @param <T> Element-Type to be stored
 * 
 * @author Michael Watholowitsch
 */
public interface CustomContainer<T> {
  /**
   * Creates and returns a {@linkplain CustomIterator} for this {@code CustomContainer}.
   *
   * @return A {@linkplain CustomIterator} for this {@code CustomContainer}.
   */
  public CustomIterator<T> getIterator();
  
  /**
   * Adds a new element in this {@code CustomContainer}.
   * 
   * @param element the new element
   */
  public void add(T element);
  
  /**
   * Returns the number of elements in this {@code CustomContainer}.
   *   
   * @return the number of elements in this {@code CustomContainer}
   */
  public int size();
}
