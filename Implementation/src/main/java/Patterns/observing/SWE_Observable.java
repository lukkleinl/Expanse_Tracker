package Patterns.observing;

import accounts.Account;
import exceptions.CustomException;
import java.util.ArrayList;
import java.util.List;
import transactions.Transaction;
import user.User;


/**
 * DIY Observer Pattern - defines the Observable.
 * @author Paul Kraft
 * @author Lukas Kleinl
 */
public class SWE_Observable
{


  /** List of all Observers that are Interested in the Data of the Observer!
   */
  private final List<SWE_Observer> observerList = new ArrayList<>();

  /** Adds a certain Observer to the observerList
   * @param obs the Observer that should be added.
   * @throws Exception if Observer already subscribed.
   */
  public void subscribe(final SWE_Observer obs) throws CustomException {
    if(observerList.contains(obs))
      throw new CustomException("Observable ALREADY SUBSCRIBED to this Observer - can not subscribe!");
    else {
      observerList.add(obs);
    }
  }

  /** Removes a certain Observer from the observerList
   * @param obs the Observer that should be removed.
   * @throws Exception if Observer not subscribed at this time.
   */
  public void unSubscribe(final SWE_Observer obs) throws CustomException {
    if(!observerList.contains(obs))
      throw new CustomException("Observable was NOT SUBSCRIBED to this Observer - can not unsubscribe!");
    else {
      observerList.remove(obs);
    }
  }
//--------------------------------------------------------------------------------------

  /** Different updateObservers because of usage
   *  Doesnt need to update both collections users and transactions all the time
   *
   *
   * Updates all Observers, to use if smthg. observable has changed.
   * @param user User that changed, which interests the observer.
   */
  public void updateObservers(final User user){
    for(SWE_Observer obs : observerList )
    {
      obs.update(user);
    }
  }

  /** Updates all Observers, to use if smthg. observable has changed.
   * @param user User that changed, which interests the observer.
   * @param trans_id the transaction  which shall get deleted
   */
  public void updateObservers(final User user,final int trans_id){
    for(SWE_Observer obs : observerList )
    {
      obs.update(user,trans_id);
    }
  }

  /** Updates all Observers, to use if smthg. observable has changed.
   * @param user User that changed, which interests the observer.
   * @param trans the id which shall get deleted
   */
  public void updateObservers(final User user,final Transaction trans){
    for(SWE_Observer obs : observerList )
    {
      obs.update(user,trans);
    }
  }

  /** Updates all Observers, to use if smthg. observable has changed.
   * @param user User that changed, which interests the observer.
   * @param trans the id which shall get deleted
   * @param acc The account on which the transaction shall be inserted
   */
  public void updateObservers(final User user,final Account acc,final Transaction trans){
    for(SWE_Observer obs : observerList )
    {
      obs.update(user,acc,trans);
    }
  }

}

