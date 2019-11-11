package exceptions;

/**
 * thrown by a {@linkplain CustomIterator} if it has processed all elements of the related
 * {@linkplain CustomContainer}
 * 
 * @author Michael Watholowitsch
 */
public class NoElementException extends RuntimeExceptionClass {

  public NoElementException(String message) {
    super(message);
  }

}
