package MongoDb;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import exceptions.SWE_RuntimeException;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import user.User;

public class WriteOperation implements Write_Operation {

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection collection;

    public WriteOperation()
    {
        try
        {
            mongo=new MongoClient();
            database=mongo.getDatabase("ExpanseTracker");
        }
        catch(Exception e)
        {
            System.out.println("Probleme beim erstellen der Datenkbank");
        }
    }

    /**
     * Returns the document of a transaction to insert into MongoDB
     *
     *
     * @throws SWE_RuntimeException if the object type is neither a deposit nor a payout
     */

    private void getTrans(final Object trans,final int key,final int user_ID)
    {
        Document doc=null;


        if(trans.toString().contains("PAYOUT"))
        {
            Payout payout = (Payout) trans;
            doc = new Document("_id", payout.getID())
                .append("Date", payout.getCreationDate().toString())
                .append("amount", payout.getAmount())
                .append("category", payout.getPayoutCategory())
                .append("Account_Number", key)
                .append("Description", payout.getDescription())
                .append("User_ID",user_ID);

            collection = database.getCollection("Transactions");
            collection.insertOne(doc);
        }
        else if(trans.toString().contains("DEPOSIT"))
        {

            Deposit deposit = (Deposit) trans;
            doc = new Document("_id", deposit.getID())
                .append("Date", deposit.getCreationDate().toString())
                .append("amount", deposit.getAmount())
                .append("category", deposit.getCategory())
                .append("Account_Number", key)
                .append("Description", deposit.getDescription())
                .append("User_ID",user_ID);

            collection = database.getCollection("Transactions");
            collection.insertOne(doc);
        }
        else
            throw new SWE_RuntimeException("Unknown transaction");
    }

    /**
     * Returns a Document of an account
     *
     * @return a account document
     *
     * @throws SWE_RuntimeException throe when the object type is not an account
     */
    private Document Account(final Object account)
    {
        Document doc;

        switch(account.toString()) {
            case "STOCKS":
                Stocks stock= (Stocks) account;
                doc = new Document("id", stock.getAccount_number())
                    .append("Balance",stock.getBalance())
                    .append("Name",stock.getName())
                    .append("Buy Date",stock.getBuyDate());
                return doc;

            case "DEBITCARD":
                DebitCard debit= (DebitCard) account;
                doc = new Document("id", debit.getAccount_number())
                    .append("Balance",debit.getBalance())
                    .append("IBAN",debit.getIBAN())
                    .append("Name",debit.getName());
                return doc;

            case "CREDITCARD":
                CreditCard credit= (CreditCard) account;
                doc = new Document("id", credit.getAccount_number())
                    .append("Balance",credit.getBalance())
                    .append("Expiry Date",credit.getExpiryDate())
                    .append("Name",credit.getName());
                return doc;

            case "CASH":
                Cash cash= (Cash) account;
                doc = new Document("id", cash.getAccount_number())
                    .append("Balance",cash.getBalance())
                    .append("Currency",cash.getCurrency())
                    .append("Name",cash.getName());
                return doc;
        }
        throw new SWE_RuntimeException("Unknown account type");
    }

    /**
     * inserts the user into the DB
     *
     * @param user User which should be inserted
     */
    @Override
    public void insertUser(final User user)
    {

        CustomContainer<Account>  accounts =user.getAccounts();
        List<Document> accounts_array = new ArrayList<>();
        CustomIterator<Account> iter=accounts.getIterator();
        Map<Integer, CustomContainer<Transaction>> Transactions =user.getTransactionStore().getTransactions();

        while (iter.hasNext()) {
            Document doc=this.Account(iter.next());
            accounts_array.add(doc);
        }

        for (Entry e : Transactions.entrySet())
        {
            CustomContainer<Object> list = (CustomList<Object>) e.getValue();
            CustomIterator<Object> iterator = list.getIterator();
            Integer account_number = (Integer) e.getKey();
            while (iterator.hasNext()) {
                this.getTrans(iterator.next(),account_number,user.getUserID());
            }
        }

        Document dep = new Document("_id", user.getUserID())
            .append("First Name",user.getFirstname())
            .append("Last Name", user.getLastname())
            .append("Accounts", accounts_array);

        collection = database.getCollection("User");
        collection.insertOne(dep);

    }

    @Override
    public void insertTransaction(User user, Account acc,Transaction trans)
    {



    }


    @Override
    public void updateTransaction(User user,Transaction trans)
    {

    }
    @Override
    public void updateUser(User user)
    {

    }

    @Override
    public void deleteUser(User user)
    {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", user.getUserID());
        collection = database.getCollection("User");
        collection.deleteOne(document);

        collection = database.getCollection("Transactions");
        BasicDBObject filter = new BasicDBObject();
        filter.put("User_ID", user.getUserID());
        DeleteResult deleteResult = collection.deleteMany(filter);
        System.out.println(deleteResult.getDeletedCount());
    }

    @Override
    public void clearDatabase()
    {
        collection = database.getCollection("User");
        collection.deleteMany(new BasicDBObject());

        collection = database.getCollection("Transactions");
        collection.deleteMany(new BasicDBObject());
    }



    @Override
    public void deleteTransaction(User user, int trans_id)
    {
        collection = database.getCollection("Transactions");

        BasicDBObject document = new BasicDBObject();
        document.put("_id", trans_id);
        document.put("User_ID", user.getUserID());

        DeleteResult deleteResult = collection.deleteMany(document);
        System.out.println(deleteResult.getDeletedCount());
    }
}
