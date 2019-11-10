package iteration;

/**
 * Defines the necessary functionality of all CustomContainers.
 *
 * @param <T> Element-Type to be stored
 * @author Michael Watholowitsch
 */
public interface CustomContainer<T> {
  /**
   * Creates and returns a CustomIterator for this CustomContainer.
   *
   * @return A CustomIterator for this CustomContainer.
   */
  public CustomIterator<T> getIterator();
}
