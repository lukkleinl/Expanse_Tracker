package MongoDb;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomIterator;
import swe_IteratorPattern.CustomList;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import user.User;

public class MongoDB implements Persistency
{
    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection collection;

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


    private Document getTrans(Object trans,int key)
    {
      Document doc;
      switch (trans.toString()) {
        case "PAYOUT":
          Payout payout = (Payout) trans;
          doc = new Document("id", payout.getID())
              .append("Date", payout.getCreationDate())
              .append("amount", payout.getAmount())
              .append("category", payout.getPayoutCategory().name())
              .append("Account_Number",key)
              .append("Description", payout.getDescription());
          return doc;

        case "DEPOSIT":
          Deposit deposit = (Deposit) trans;
          doc = new Document("id", deposit.getID())
              .append("Date", deposit.getCreationDate())
              .append("amount", deposit.getAmount())
              .append("category", deposit.getDepositCategory().name())
              .append("Account_Number",key)
              .append("Description", deposit.getDescription());
          return doc;
      }
      throw new IllegalArgumentException("wrong object");
    }

    public void insertUser(User user)
    {
      Map<Integer, CustomContainer<Transaction>>  Transactions =user.getTransactions();
      CustomContainer<Account>  accounts =user.getAccounts();
      List<Document> accounts_array = new ArrayList<>();
      List<Document> trans_array = new ArrayList<>();
      CustomIterator<Account> iter=accounts.getIterator();

      while (iter.hasNext()) {
        Document doc=Account(iter.next());
        accounts_array.add(doc);
      }

      for (Entry e : Transactions.entrySet())
      {
        CustomContainer<Object> list = (CustomList<Object>) e.getValue();
        CustomIterator<Object> iterator = list.getIterator();
        List<Document> transaction_array = new ArrayList<>();
        Integer account_number = (Integer) e.getKey();
        while (iterator.hasNext()) {
          Document doc=getTrans(iterator.next(),account_number);
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


  private Document Account(Object account)
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
    throw new IllegalArgumentException("wrong object");
  }

  public void insertAccount(Account acc)
  {
  // TODO
  }
  public void insertTransaction(Transaction trans)
  {
    // TODO
  }

  public void updateUser(User user)
  {
    // TODO
  }
  public void updateAccount(Account acc)
  {
    // TODO
  }
  public void updateTransaction(Transaction trans)
  {
    // TODO
  }
  public void deleteUser(User user)
    {
      // TODO
    }
  public void deleteAccount(Account acc)
  {
    // TODO
  }
  public void deleteTransaction(Transaction trans)
  {
    // TODO
  }
}
