package exceptions;

/**
 * @author: Patrick Gmasz
 */

public class LimitException extends SWE_Exception {

  /**
   * Limit Exception will be thrown, when amount of payout exceeds limit
   * @param message message of the exception
   */
  public LimitException(String message){
    super(message);
  }
}
