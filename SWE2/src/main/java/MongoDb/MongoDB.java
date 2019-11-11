package MongoDb;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
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


    private Document getTrans(Deposit trans,int key)
    {
      Document doc = new Document("id", trans.getID())
          .append("Date", trans.getCreationDate())
          .append("amount", trans.getAmount())
          .append("category", trans.getDepositCategory().name())
          .append("Account_Number",key)
          .append("Description", trans.getDescription());

      return doc;
    }

  private Document getTrans(Payout trans,int key)
  {
    Document doc = new Document("id", trans.getID())
        .append("Date", trans.getCreationDate())
        .append("amount", trans.getAmount())
        .append("category", trans.getPayoutCategory().name())
        .append("Account_Number",key)
        .append("Description", trans.getDescription());

    return doc;
  }



    private Document getTrans(Object trans,int key)
    {
      switch (trans.toString()) {
        case "PAYOUT":
          System.out.println("PAYOUT");
          Payout payout = (Payout) trans;
          return getTrans(payout, key);
        case "DEPOSIT":
          System.out.println("DEPOSIT");
          Deposit deposit = (Deposit) trans;
          return getTrans(deposit, key);
      }
      return null;
    }

    public void insert(User user)
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
          System.out.println(transaction_array.size());
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
    switch(account.toString()) {
      case "STOCKS":
        System.out.println("Stocks");
        Stocks stock= (Stocks) account;
        return getAccount(stock);
      case "DEBITCARD":
        System.out.println("DebitCard");
        DebitCard debit= (DebitCard) account;
        return getAccount(debit);
      case "CREDITCARD":
        System.out.println("CreditCard");
        CreditCard credit= (CreditCard) account;
        return getAccount(credit);
      case "CASH":
        System.out.println("Cash");
        Cash cash= (Cash) account;
        return getAccount(cash);
    }
    return null;
  }

  private Document getAccount(Stocks account)
  {
    Document doc = new Document("id", account.getAccount_number())
        .append("Balance",account.getBalance())
        .append("Name",account.getName())
        .append("Buy Date",account.getBuyDate());

    return doc;
  }

  private Document getAccount(Cash account)
  {
    Document doc = new Document("id", account.getAccount_number())
        .append("Balance",account.getBalance())
        .append("Currency",account.getCurrency())
        .append("Name",account.getName());

    return doc;
  }

  private Document getAccount(DebitCard account)
  {
    Document doc = new Document("id", account.getAccount_number())
        .append("Balance",account.getBalance())
        .append("IBAN",account.getIBAN())
        .append("Name",account.getName());

    return doc;
  }

  private Document getAccount(CreditCard account)
  {
    Document doc = new Document("id", account.getAccount_number())
        .append("Balance",account.getBalance())
        .append("Expiry Date",account.getExpiryDate())
        .append("Name",account.getName());

    return doc;
  }

  public void deleteUser()
    {
      // TODO
    }

    public void deleteTransaction()
    {
      // TODO
    }

    public void deleteAccount()
    {
      // TODO
    }




}
