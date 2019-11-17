package swe_ObserverPattern;

import java.util.ArrayList;
import java.util.List;
import exceptions.SWE_Exception;
import exceptions.SWE_RuntimeException;
import exceptions.SubscriptionException;


/**
 * DIY Observer Pattern - defines the Observable.
 * @author Paul Kraft
 */
public class SWE_Observable{

    //TODO: maybe logging?

    /** List of all Observers that are Interested in the Data of the Observer!
     */
    private List<SWE_Observer> observerList = new ArrayList<>();

    /** Adds a certain Observer to the observerList
     * @param obs the Observer that should be added.
     * @throws Exception if Observer already subscribed.
     */
    public void subscribe(SWE_Observer obs) throws SWE_Exception{
        if(observerList.contains(obs))
            throw new SubscriptionException("Observable ALREADY SUBSCRIBED to this Observer - can not subscribe!");
        else
            observerList.add(obs);
    }

    /** Removes a certain Observer from the observerList
     * @param obs the Observer that should be removed.
     * @throws Exception if Observer not subscribed at this time.
     */
    public void unSubscribe(SWE_Observer obs) throws SWE_Exception{
        if(!observerList.contains(obs))
            throw new SubscriptionException("Observable was NOT SUBSCRIBED to this Observer - can not unsubscribe!");
        else
            observerList.remove(obs);
    }

    /** Updates all Observers, to use if smthg. observable has changed.
     * @param obj Object that changed, which interests the observer.
     */
    public void updateObservers(Object obj){
        for(SWE_Observer obs : observerList ){
            obs.update(obj);
        }
    }
}

