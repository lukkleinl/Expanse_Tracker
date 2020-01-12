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
                Map<Integer, CustomContainer<Transaction>> map=user.getTransactionStore ().getTransactions();

                for(Entry entry : map.entrySet())
                {
                     CustomContainer<Object> list = (CustomList<Object>) entry.getValue();
                     CustomIterator<Object> iterator = list.getIterator();

                     while (iterator.hasNext())
                     {
                         Transaction transaction= (Transaction) iterator.element();
                         if(trans.equals(transaction))
                         {
                             writeOperation.updateTransaction(trans);
                             return;
                         }
                         iterator.next();
                     }
                }


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

