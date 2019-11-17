package exceptions;

/**
 * thrown by a {@linkplain swe_IteratorPattern.CustomIterator} if it has processed all elements of the related
 * {@linkplain swe_IteratorPattern.CustomContainer}
 * 
 * @author Michael Watholowitsch
 */
public class NoElementException extends SWE_RuntimeException {

  public NoElementException(String message) {
    super(message);
  }

}
