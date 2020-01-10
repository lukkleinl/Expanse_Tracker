package Patterns.observing;

import MongoDb.ReadOperation;
import MongoDb.WriteOperation;
import iteration.CustomIterator;
import iteration.CustomList;
import user.User;

public class Database implements SWE_Observer {

    private ReadOperation readOperations = new ReadOperation();
    private WriteOperation writeOperation = new WriteOperation();
    private CustomList<User> listOfUsers;
    private int i=0;


    public Database() throws Exception
    {
        listOfUsers = readOperations.getUsers();

        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            it.next().subscribe(this);
        }

    }

   /* @Override
    public void update(Transaction trans,User user, Account acc )
    {
        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            if(it.element().getUserID().equals(user.getUserID()))
            {

                return;
            }

            it.next();
        }

        writeOperation.insertUser(user);
        listOfUsers.add(user);

    }*/

    @Override
    public void update(User obj)
    {

        //TODO Define for specific changes, to just update Transactions etc..

        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext())
        {
            if(it.element().getUserID().equals(obj.getUserID()))
            {

                writeOperation.updateUser(obj);
                obj.getTransactionStore().getTransactions();

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
