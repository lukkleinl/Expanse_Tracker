package MongoDb;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.CategoryStore;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import user.User;

public class ReadOperation implements Read_Operation
{

  private MongoClient mongo;
  private MongoCollection collection;
  private MongoDatabase database;

  public ReadOperation()
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

  private void Account(User user,JSONObject account)
  {

    if(account.get("Accounttype").equals("CASH"))
    {

      user.addAccount(new Cash(account.getString("Name"),account.getFloat("Limit"),account.getString("Currency"),account.getInt("id")));
    }
    else if(account.get("Accounttype").equals("STOCKS"))
    {
      Date date=new Date(account.get("Buy Date").toString());
      user.addAccount(new Stocks(account.get("Name").toString(),date,account.getFloat("Limit"),account.getInt("id")));
    }
    else if(account.get("Accounttype").equals("DEBITCARD"))
    {
      user.addAccount(new DebitCard(account.get("Name").toString(),account.get("Bankname").toString(),
          account.getFloat("Limit"),account.get("IBAN").toString(),account.getInt("id")));
    }
    else if(account.get("Accounttype").equals("CREDITCARD"))
    {
      Date date=new Date(account.get("Buy Date").toString());
      user.addAccount(new CreditCard(account.get("Name").toString(),account.get("Bankname").toString(),
          account.getFloat("Limit"),date,account.getInt("id")));
    }
    else
    {
      assert true : "shoudln't get here";
    }
  }

  @Override
  public CustomList<User> getUsers()
  {
    collection = database.getCollection("User");
    MongoCursor cursor_acc=null;
    MongoCursor<Document> cursor=collection.find().iterator();
    CustomList<User> user_list=new CustomList<>();
    User user=null;

    while (cursor.hasNext())
    {
      JSONObject json=new JSONObject(cursor.next().toJson());

      user=new User(json.get("_id").toString(),
                    json.get("First Name").toString(),
                    json.get("Last Name").toString(),
                    json.get("Password").toString());

      JSONArray PayoutCategories=json.getJSONArray("Payout Categories");
      JSONArray DepositCategories=json.getJSONArray("Deposit Categories");

      for(int i=0;i<PayoutCategories.length();i++)
      {
        user.getCategoryStore().addTransactionCategory(new PayoutCategory(PayoutCategories.getJSONObject(i).getString("_id")));
      }

      for(int i=0;i<DepositCategories.length();i++)
      {
        user.getCategoryStore().addTransactionCategory(new DepositCategory(DepositCategories.getJSONObject(i).getString("_id")));
      }

      JSONArray accounts_array=json.getJSONArray("Accounts");

      for(int i=0;i<accounts_array.length();i++)
      {
        JSONObject acc = accounts_array.getJSONObject(i);
        Date date=null;

        if(acc.getString("Accounttype").equals("CASH"))
          user.addAccount(new Cash(acc.getString("Name"),acc.getFloat("Limit"),acc.getString("Currency"),acc.getInt("id")));
        else if(acc.getString("Accounttype").equals("DEBITCARD"))
          user.addAccount((new DebitCard(acc.getString("Name"),acc.getString("Bankname"),acc.getFloat("Limit"),acc.getString("IBAN"),acc.getInt("id"))));
        else if(acc.getString("Accounttype").equals("CREDITCARD"))
        {
          JSONObject date_object=acc.getJSONObject("Expiry Date");
          date=new Date(date_object.getLong("$numberLong"));
          user.addAccount(new CreditCard(acc.getString("Name"),acc.getString("Bankname"),acc.getFloat("Limit"),date,acc.getInt("id")));
        }
        else if(acc.getString("Accounttype").equals("STOCKS"))
        {
          JSONObject date_object=acc.getJSONObject("Buy Date");
          date=new Date(date_object.getLong("$numberLong"));
          user.addAccount((new Stocks(acc.getString("Name"),date,acc.getFloat("Limit"),acc.getInt("id"))));
        }
        else
          assert true : "Shouldnt reach this statement";

      }

      Map<Integer,CustomContainer<Transaction>> list_trans=getTransactions(user);

      for (Entry e : list_trans.entrySet())
      {
        CustomContainer<Object> list = (CustomList<Object>) e.getValue();
        CustomIterator<Object> iterator = list.getIterator();
        Integer account_number = (Integer) e.getKey();

        while (iterator.hasNext())
        {
          Transaction trans=(Transaction) iterator.next();
          user.getTransactionStore().addTransactionUnderKey(account_number,trans);
        }
      }
      user_list.add(user);
      //user_list.add(getUsers(cursor.next().getString("_id")));
    }
    return user_list;
  }

  @Override
  public User getUsers(String ID)
  {
    collection = database.getCollection("User");
    Document query = new Document();
    MongoCursor<Document> cursor;
    query.append("_id",ID);
    try
    {
      cursor=collection.find(query).cursor();
    }
    catch (Exception e)
    {
      return null;
    }

    User user=null;

    while (cursor.hasNext())
    {
      JSONObject json=new JSONObject(cursor.next().toJson());

      user=new User(json.get("_id").toString(),
          json.get("First Name").toString(),
          json.get("Last Name").toString(),
          json.get("Password").toString());

      JSONArray PayoutCategories=json.getJSONArray("Payout Categories");
      JSONArray DepositCategories=json.getJSONArray("Deposit Categories");

      for(int i=0;i<PayoutCategories.length();i++)
      {
        user.getCategoryStore().addTransactionCategory(new PayoutCategory(PayoutCategories.getJSONObject(i).getString("_id")));
      }

      for(int i=0;i<DepositCategories.length();i++)
      {
        user.getCategoryStore().addTransactionCategory(new DepositCategory(DepositCategories.getJSONObject(i).getString("_id")));
      }

      JSONArray accounts_array=json.getJSONArray("Accounts");

      for(int i=0;i<accounts_array.length();i++)
      {
        JSONObject acc = accounts_array.getJSONObject(i);
        Date date=null;

        if(acc.getString("Accounttype").equals("CASH"))
          user.addAccount(new Cash(acc.getString("Name"),acc.getFloat("Limit"),acc.getString("Currency"),acc.getInt("id")));
        else if(acc.getString("Accounttype").equals("DEBITCARD"))
          user.addAccount((new DebitCard(acc.getString("Name"),"afv"/*acc.getString("Bankname")*/,acc.getFloat("Limit"),acc.getString("IBAN"),acc.getInt("id"))));
        else if(acc.getString("Accounttype").equals("CREDITCARD"))
        {
          date= new Date(acc.getString("Expiry Date"));
          user.addAccount(new CreditCard(acc.getString("Name"),"afv"/*acc.getString("Bankname")*/,acc.getFloat("Limit"),date,acc.getInt("id")));
        }
        else if(acc.getString("Accounttype").equals("STOCKS"))
        {
          date= new Date(acc.getString("Buy Date"));
          user.addAccount((new Stocks(acc.getString("Name"),date,acc.getFloat("Limit"),acc.getInt("id"))));
        }
        else
          assert true : "Shouldnt reach this statement";

      }

      Map<Integer,CustomContainer<Transaction>> list_trans=getTransactions(user);

      for (Entry e : list_trans.entrySet())
      {
        CustomContainer<Object> list = (CustomList<Object>) e.getValue();
        CustomIterator<Object> iterator = list.getIterator();
        Integer account_number = (Integer) e.getKey();
        while (iterator.hasNext())
        {
          Transaction trans=(Transaction) iterator.next();
          user.getTransactionStore().addTransactionUnderKey(account_number,trans);
        }
      }
    }
    return user;

  }

  @Override
  public Map<Integer, CustomContainer<Transaction>> getTransactions(User user)
  {
    collection = database.getCollection("Transactions");
    Document query = new Document();
    query.append("User_ID", user.getUserID());
    MongoCursor<Document> cursor = collection.find(query).iterator();
    CategoryStore category_store=new CategoryStore();
    category_store.withDefaultCategories();
    Map<Integer, CustomContainer<Transaction>> Transactions_map=new HashMap<>();

    while (cursor.hasNext())
    {
      JSONObject json = new JSONObject(cursor.next().toJson());
      ZonedDateTime date = ZonedDateTime.parse(json.getString("Date"));

      if(!(user.categorySupported(json.getString("category_name"))))
      {

        if(json.getString("category").equals("PAYOUT"))
          user.getCategoryStore().addTransactionCategory(new PayoutCategory(json.getString("category_name")));
        else if(json.getString("category").equals("DEPOSIT"))
          user.getCategoryStore().addTransactionCategory(new DepositCategory(json.getString("category_name")));
        else
          assert true : "Shouldnt reach this argument";
      }

      Transaction trans = TransactionCreator.transactionFromDatabaseData(date, json.getString("category_name"), json.getFloat("amount"),
          json.getString("Description"), user.getCategoryStore(), json.getInt("_id"));


      Transactions_map=insertTransIntoMap(user,json.getInt("Account_Number"),trans,Transactions_map);

    }
    return Transactions_map;
  }

  private Map<Integer, CustomContainer<Transaction>>  insertTransIntoMap(User user,int acc_id,Transaction trans, Map<Integer, CustomContainer<Transaction>> map)
  {
    CustomIterator<Account> account_iterator=user.getAccounts().getIterator();


    while (account_iterator.hasNext())
    {
      if(account_iterator.element().getAccount_number()==acc_id)
      {
        map.putIfAbsent(acc_id,new CustomList<>());
        map.get(acc_id).add(trans);
      }
      account_iterator.next();
    }
    return map;
  }
}
