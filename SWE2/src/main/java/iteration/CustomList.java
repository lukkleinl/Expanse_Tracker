package iteration;

import java.util.ArrayList;
import java.util.List;

/** * 
 * Used to store the Accounts and Transactions of a User with safe access.
 *
 * @param <T> Element-Type to be stored
 * 
 * @author Michael Watholowitsch
 */
public class CustomList<T> implements CustomContainer<T> {
	private List<T> list = new ArrayList<>();
	
	/**
	 * Creates an empty CustomList.
	 */
	public CustomList() { }
	
	/**
	 * Creates a CustomList from an existing List.
	 * @param list The list used to create a CustomList.
	 */
	public CustomList(List<T> list) {
		this.list = new ArrayList<T>(list);
	}
	
	/**
	 * Adds a new element to the CustomList.
	 * @param element The element to be added to the list.
	 */
	public void add(T element) {
		list.add(element);
	}
	
	/**
	 * Returns a CustomIterator pointing to the first element in the CustomList.
	 * @return A CustomIterator for this List.
	 */
	@Override
	public CustomIterator getIterator() {
		return new CustomListIterator();
	}
	
	/**
	 * The Iterator working on instances of CustomList.
	 * By default, this Iterator always points at the first element of the CustomList.
	 */
	private class CustomListIterator implements CustomIterator {
		private int position = 0;
		
		private CustomListIterator() { }
		
		/**
		 * @return true if there is another element in the list after the current one, else false.
		 */
		@Override
		public boolean hasNext() {
			return position < list.size() ? true : false;
		}
		
		/**
		 * @return The current element
		 */
		@Override
		public T next() {
			if (hasNext()) {
				return list.get(position++);
			}
			return null;
		}
	}
}
