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

    @Override
    public void update(User user,int Transaction_ID)
    {
        writeOperation.deleteTransaction(user,Transaction_ID);
    }

    @Override
    public void update(User obj)
    {

        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            if(it.element().getUserID().equals(obj.getUserID()))
            {

                writeOperation.updateUser(obj);
                return;
            }

            it.next();
        }

        listOfUsers.add(obj);
        writeOperation.insertUser(obj);
    }

    @Override
    public void update(User obj, Transaction trans)
    {
        Map<Integer, CustomContainer<Transaction>> map=obj.getTransactionStore ().getTransactions();

        for(Entry entry : map.entrySet())
        {
            CustomContainer<Object> list = (CustomList<Object>) entry.getValue();
            CustomIterator<Object> iterator = list.getIterator();

            while (iterator.hasNext())
            {
                Transaction transaction= (Transaction) iterator.element();
                if(trans.equals(transaction))
                {
                    writeOperation.updateUser(obj);
                    writeOperation.updateTransaction(trans);
                    return;
                }
                iterator.next();
            }
        }
    }


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

