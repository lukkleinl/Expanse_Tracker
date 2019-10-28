package iteration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Watholowitsch
 *
 * @param <T> Element-Type to be stored
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
	 * @return CustomIterator
	 */
	@Override
	public CustomIterator getIterator() {
		return new CustomListIterator();
	}
	
	private class CustomListIterator implements CustomIterator {
		int position = 0;
		
		/**
		 * @return boolean
		 * Returns true if there is another element in the list after the current one, else false.
		 */
		@Override
		public boolean hasNext() {
			return position < list.size() ? true : false;
		}
		
		/**
		 * @return T
		 * Returns the current element and moves the position of the Iterator to the next element in the CustomList.
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
