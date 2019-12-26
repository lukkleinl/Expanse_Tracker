package iteration;

import java.util.ArrayList;
import java.util.List;
import exceptions.SWE_RuntimeException;

/**
 * Used to store the Accounts and Transactions of a User with safe access.
 *
 * @param <T> Element-Type to be stored
 * @author Michael Watholowitsch
 */
public class CustomList<T> implements CustomContainer<T> {
  private List<T> list = new ArrayList<>();

  /** Creates an empty CustomList. */
  public CustomList() {}

  /**
   * Creates a CustomList from an existing List.
   *
   * @param list The list used to create a CustomList.
   */
  public CustomList(final List<T> list) {
    this.list = new ArrayList<>(list);
  }

  /**
   * Adds a new element in this {@code CustomList}.
   *
   * @param element the new element
   */
  @Override
  public void add(final T element) {
    this.list.add(element);
  }

  /**
   * Returns the number of elements in this {@code CustomList}.
   *
   * @return the number of elements in this {@code CustomList}
   */
  @Override
  public int size() {
    return this.list.size();
  }

  /**
   * Returns a {@linkplain CustomIterator} pointing to the first element in the {@code CustomList}.
   *
   * @return A {@code CustomIterator} for this CustomList.
   */
  @Override
  public CustomIterator<T> getIterator() {
    return new CustomListIterator<>();
  }

  /**
   * The Iterator working on instances of CustomList. By default, this Iterator always points at the
   * first element of the CustomList. Implemented as a private nested class inside the
   * CustomList since this Iterator directly belongs to the CustomList.
   */
  @SuppressWarnings({"unchecked", "hiding"})
  private class CustomListIterator<T> implements CustomIterator<T> {
    private int position = 0;

    private CustomListIterator() {}

    @Override
    public boolean hasNext() {
      return this.position < CustomList.this.list.size();
    }

    @Override
    public T next() throws SWE_RuntimeException {
      if (!this.hasNext()) throw new SWE_RuntimeException("This iterator has already processed all elements !");
      T elem = (T) CustomList.this.list.get(this.position);
      this.position++;
      return elem;
    }

    @Override
    public T element() throws SWE_RuntimeException {
      if (!this.hasNext()) throw new SWE_RuntimeException("This iterator has already processed all elements !");
      T elem = (T) CustomList.this.list.get(this.position);
      return elem;
    }
  }
}
