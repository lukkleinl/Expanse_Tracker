package Patterns.observing;

import accounts.Account;
import exceptions.SWE_Exception;
import java.util.ArrayList;
import java.util.List;
import transactions.Transaction;
import user.User;


/**
 * DIY Observer Pattern - defines the Observable.
 * @author Paul Kraft
 */
public class SWE_Observable
{

  //TODO: maybe logging?

  /** List of all Observers that are Interested in the Data of the Observer!
   */
  private final List<SWE_Observer> observerList = new ArrayList<>();

  /** Adds a certain Observer to the observerList
   * @param obs the Observer that should be added.
   * @throws Exception if Observer already subscribed.
   */
  public void subscribe(final SWE_Observer obs) throws SWE_Exception{
    if(observerList.contains(obs))
      throw new SWE_Exception("Observable ALREADY SUBSCRIBED to this Observer - can not subscribe!");
    else {
      observerList.add(obs);
    }
  }

  /** Removes a certain Observer from the observerList
   * @param obs the Observer that should be removed.
   * @throws Exception if Observer not subscribed at this time.
   */
  public void unSubscribe(final SWE_Observer obs) throws SWE_Exception{
    if(!observerList.contains(obs))
      throw new SWE_Exception("Observable was NOT SUBSCRIBED to this Observer - can not unsubscribe!");
    else {
      observerList.remove(obs);
    }
  }

  /** Updates all Observers, to use if smthg. observable has changed.
   * @param obj User that changed, which interests the observer.
   */
  public void updateObservers(final User obj){
    for(SWE_Observer obs : observerList )
    {
      obs.update(obj);
    }
  }

  public void updateObservers(final User obj,final int trans_id){
    for(SWE_Observer obs : observerList )
    {
      obs.update(obj,trans_id);
    }
  }

  public void updateObservers(final User user,final Transaction trans){
    for(SWE_Observer obs : observerList )
    {
      obs.update(user,trans);
    }
  }

  public void updateObservers(final User user,final Account acc,final Transaction trans){
    for(SWE_Observer obs : observerList )
    {
      obs.update(user,acc,trans);
    }
  }

}

