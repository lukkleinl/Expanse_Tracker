package patterns.observing;

import accounts.Account;
import transactions.Transaction;
import user.User;

/**
 * DIY Observer Pattern - defines the Observer.
 * @author Paul Kraft
 * @author Lukas Kleinl
 */
 public interface SWE_Observer {
     /** Method to implement, what to do with the new given Object.
      * @param obj Object that changed, which interests the observer.
      */
     void update(User obj);
     void update(User obj,Transaction transaction);
     void update(User user, Account acc,Transaction trans);
     void update(User user,int Transaction_ID);
}
