package iteration;

import java.util.ArrayList;
import java.util.List;

public class CustomList<T> implements CustomContainer<T> {
	private List<T> list = new ArrayList<>();
	
	public CustomList() { }
	public CustomList(List<T> list) {
		this.list = list;
	}
	
	public void put(T element) {
		list.add(element);
	}
	
	@Override
	public CustomIterator getIterator() {
		return new CustomListIterator();
	}
	
	private class CustomListIterator implements CustomIterator {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < list.size()-1 ? true : false;
		}

		@Override
		public T next() {
			if (hasNext()) {
				return list.get(position++);
			}
			return null;
		}
	}
}
