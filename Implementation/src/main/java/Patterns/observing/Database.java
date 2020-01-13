package Patterns.observing;

import MongoDb.ReadOperation;
import MongoDb.WriteOperation;
import accounts.Account;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.util.Map;
import java.util.Map.Entry;
import transactions.Transaction;
import user.User;

/**
 * DIY Observer Pattern - Database for the observable users
 * Because of the two collections
 * @author Paul Kraft
 * @author Lukas Kleinl
 */
public class Database implements SWE_Observer {

    private ReadOperation readOperations = new ReadOperation();
    private WriteOperation writeOperation = new WriteOperation();
    private CustomList<User> listOfUsers;

    public Database() throws Exception
    {
        listOfUsers = readOperations.getUsers();


        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            it.next().subscribe(this);
        }

    }

    /**
     *
     *  update the Users and the transactions
      */
    /** @author Lukas Kleinl */
    /** Different updateObservers because of usage
     *  Doesnt need to update both collections users and transactions all the time
     *
     *
     /**  Updates the values within the database
     * @param user User that changed, which interests the observer.
     * @param trans the id which shall get deleted
     * @param acc The account on which the transaction shall be inserted
     */

    @Override
    public void update(User user, Account acc,Transaction trans )
    {
        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            if(it.element().getUserID().equals(user.getUserID()))
            {
                writeOperation.updateUser(user);
                writeOperation.insertTransaction(user,acc,trans);
                return;
            }
            it.next();
        }
    }

    /** Updates the values within the database
     * @param user User that changed, which interests the observer.
     * @param trans_ID the transaction  which shall get deleted
     */
    @Override
    public void update(User user,int trans_ID)
    {
        writeOperation.deleteTransaction(user,trans_ID);
    }

    /**
    *  Updates the values within the database
   * @param user User that changed, which interests the observer.
   */

    @Override
    public void update(User user)
    {

        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            if(it.element().getUserID().equals(user.getUserID()))
            {

                writeOperation.updateUser(user);
                return;
            }

            it.next();
        }

        listOfUsers.add(user);
        writeOperation.insertUser(user);
    }


    /** Updates the values within the database
     * @param user User that changed, which interests the observer.
     * @param trans the id which shall get deleted
     */
    @Override
    public void update(User user, Transaction trans)
    {
        Map<Integer, CustomContainer<Transaction>> transaction_map=user.getTransactionStore ().getTransactions();

        for(Entry entry : transaction_map.entrySet())
        {
            CustomContainer<Object> transaction_list = (CustomList<Object>) entry.getValue();
            CustomIterator<Object> transaction_iterator = transaction_list.getIterator();

            while (transaction_iterator.hasNext())
            {
                Transaction transaction= (Transaction) transaction_iterator.element();
                if(trans.equals(transaction))
                {
                    writeOperation.updateUser(user);
                    writeOperation.updateTransaction(trans);
                    return;
                }
                transaction_iterator.next();
            }
        }
    }

    /**
     * @param id  the id of the wanted user
     * @return if a user is found the method retunrns the wanted user otherwise it returns null
     */
    public User getUser(String id)
    {
        CustomIterator<User> it = listOfUsers.getIterator();
        while (it.hasNext())
        {
            if(it.element().getUserID().equals(id))
            {
                return it.next();
            }
            it.next();
        }
        return null;
    }

    public CustomList<User> getUsers()
    {
        return listOfUsers;
    }

}

