package observing;

import MongoDb.MongoDB;
import MongoDb.ReadOperation;
import MongoDb.Read_Operation;
import MongoDb.WriteOperation;
import accounts.Account;
import exceptions.SWE_Exception;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Transaction;
import user.User;

public class Database implements SWE_Observer {

    private ReadOperation readOperations = new ReadOperation();
    private WriteOperation writeOperation = new WriteOperation();
    CustomList<User> listOfUsers;

    public Database() throws Exception {
        listOfUsers = readOperations.getUsers();

        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext()){
            it.next();
            it.element().subscribe(this);
        }

    }

    @Override
    public void update(Object obj) {

        //TODO Define for specific changes, to just update Transactions etc..

        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext()){
            it.next();

            if(it.element() == (User)obj) {
                writeOperation.updateUser((User) obj);
                return;
            }
        }

        writeOperation.insertUser((User) obj);
        listOfUsers.add((User)obj);
    }

    public User getUser(int id){
        CustomIterator<User> it = listOfUsers.getIterator();

        while (it.hasNext()){
            it.next();

            if(it.element().getUserID() == id) {
                return it.element();
            }
        }

        return null;
    }

}
