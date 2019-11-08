package ObserverPattern;
/**
 * DIY Observer Pattern - defines the Observer.
 * @author Paul Kraft
 */
 public interface Observer {
     /** Method to implement, what to do with the new given Object.
      * @param obj Object that changed, which interests the observer.
      */
     void update(Object obj);
}
