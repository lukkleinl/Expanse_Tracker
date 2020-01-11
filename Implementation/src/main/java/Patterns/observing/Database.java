package Patterns.observing;

import MongoDb.ReadOperation;
import MongoDb.WriteOperation;
import accounts.Account;
import iteration.CustomIterator;
import iteration.CustomList;
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

                if(user.getTransactionStore().getTransactions().containsValue(trans))
                {
                    writeOperation.updateTransaction(trans);
                    return;
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
