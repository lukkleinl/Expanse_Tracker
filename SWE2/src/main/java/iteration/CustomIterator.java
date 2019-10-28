package iteration;

/**
 * Defines the necessary functionality of all CustomIterators.
 * 
 * @author Michael Watholowitsch
 */
public interface CustomIterator {
	/**
	 * Return true if there is another element after the current one, else false.
	 * @return true if there is another element after the current one, else false. 
	 */
	public boolean hasNext();
	/**
	 * Return the current Object if there is one and move to the next element, else return null.
	 * @return The current Object
	 */
	public Object next();
}
