package iteration;

import exceptions.SWE_RuntimeException;

/**
 * Defines the necessary functionality of all CustomIterators.
 *
 * @param <T> Type of the elements this Iterator is pointing to.
 *
 * @author Michael Watholowitsch
 */
public interface CustomIterator<T> {
  /**
   * Return {@code true} if there is another element after the current one, else {@code false}.
   *
   * @return {@code true} if there is another element after the current one, else {@code false}.
   */
  public boolean hasNext();
  /**
   * Return the current Object if there is one and move to the next element.
   *
   * @return the current Object
   *
   * @throws SWE_RuntimeException if this {@code CustomIterator} has already processed all elements in the list
   */
  public T next() throws SWE_RuntimeException;
  /**
   * Return the current Object if there is one.
   *
   * @return the current Object
   *
   * @throws SWE_RuntimeException if this {@code CustomIterator} has already processed all elements in the list
   */
  public T element() throws SWE_RuntimeException;
}
