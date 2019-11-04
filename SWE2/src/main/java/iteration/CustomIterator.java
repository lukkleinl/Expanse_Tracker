package iteration;

import java.util.Iterator;

/**
 * Defines the necessary functionality of all CustomIterators.
 * 
 * @param <T> Type of the elements this Iterator is pointing to.
 * 
 * @author Michael Watholowitsch
 */
public interface CustomIterator<T> {
	/**
	 * Return true if there is another element after the current one, else false.
	 * @return true if there is another element after the current one, else false. 
	 */
	public boolean hasNext();
	/**
	 * Return the current Object if there is one and move to the next element, else return null.
	 * @return The current Object
	 */
	public T next();
}
