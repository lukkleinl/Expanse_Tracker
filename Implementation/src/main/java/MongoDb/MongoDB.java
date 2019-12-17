package MongoDb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import exceptions.SWE_RuntimeException;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import user.User;

public class MongoDB implements Persistency
{
  private MongoClient mongo;
  private MongoDatabase database;
  private MongoCollection collection;


  /**
   * Creates a new Connection with the MongoDB and uses the DB ExpanseTracker
   *

   */

  public MongoDB()
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
   * Returns a document of a transaction
   *
   * @return a transaction document
   *
   * @throws UnknownTransactionException if the object type is neither a deposit nor a payout
   */

  private Document getTrans(final Object trans,final int key)
  {
    Document doc;
    switch (trans.toString()) {
      case "PAYOUT":
        Payout payout = (Payout) trans;
        doc = new Document("id", payout.getID())
            .append("Date", payout.getCreationDate())
            .append("amount", payout.getAmount())
            .append("category", payout.getPayoutCategory())
            .append("Account_Number",key)
            .append("Description", payout.getDescription());
        return doc;

      case "DEPOSIT":
        Deposit deposit = (Deposit) trans;
        doc = new Document("id", deposit.getID())
            .append("Date", deposit.getCreationDate())
            .append("amount", deposit.getAmount())
            .append("category", deposit.getDepositCategory())
            .append("Account_Number",key)
            .append("Description", deposit.getDescription());
        return doc;
    }
    throw new SWE_RuntimeException("Unknown transaction");
  }

  /**
   * inserts the user into the DB
   *
   * @param user User which should be inserted
   */
  @Override
  public void insertUser(final User user)
  {
    Map<Integer, CustomContainer<Transaction>>  Transactions =user.getTransactionStore().getTransactions();
    CustomContainer<Account>  accounts =user.getAccounts();
    List<Document> accounts_array = new ArrayList<>();
    List<Document> trans_array = new ArrayList<>();
    CustomIterator<Account> iter=accounts.getIterator();

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
        Document doc=this.getTrans(iterator.next(),account_number);
        trans_array.add(doc);
      }
    }

    Document dep = new Document("_id", user.getUserID())
        .append("First Name",user.getFirstname())
        .append("Last Name", user.getLastname())
        .append("Transactions",trans_array )
        .append("Accounts", accounts_array);


    collection = database.getCollection("User");
    collection.insertOne(dep);
  }


  /**
   * Returns a Document of an account
   *
   * @return a account document
   *
   * @throws UnknownAccountException throe when the object type is not an account
   */
  private Document Account(final Object account)
  {
    Date date = new Date();
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
   * inserts the account into the DB
   *
   * @param acc Account which should be inserted
   */
  public void insertAccount(final Account acc)
  {
    // TODO
  }

  /**
   * inserts the transaction into the DB
   *
   * @param trans User which should be inserted
   */
  public void insertTransaction(final Transaction trans)
  {
    // TODO
  }

  /**
   * updates the user within the DB
   *
   * @param user User which should be updated
   */
  @Override
  public void updateUser(final User user)
  {
    // TODO
  }

  /**
   * updates the account within the DB
   *
   * @param acc Account which should be updated
   */
  public void updateAccount(final Account acc)
  {
    // TODO
  }

  /**
   * updates the transaction within the DB
   *
   * @param trans Transaction which should be updated
   */
  public void updateTransaction(final Transaction trans)
  {
    // TODO
  }

  /**
   * deletes the user within the DB
   *
   * @param user User which should be deleted
   */
  @Override
  public void deleteUser(final User user)
  {
    // TODO
  }

  /**
   * deletes the account within the DB
   *
   * @param acc Account which should be deleted
   */
  public void deleteAccount(final Account acc)
  {
    // TODO
  }

  /**
   * deletes the transaction within the DB
   *
   * @param trans Transaction which should be deleted
   */
  public void deleteTransaction(final Transaction trans)
  {
    // TODO
  }
}