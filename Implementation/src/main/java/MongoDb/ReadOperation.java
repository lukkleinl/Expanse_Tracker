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
import exceptions.SWE_Exception;
import iteration.CustomIterator;
import iteration.CustomList;
import java.time.ZonedDateTime;
import java.util.Date;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import transactions.Transaction;
import transactions.TransactionCreator;
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

      user.addAccount(new Cash(account.get("Name").toString(),account.getFloat("Limit"),account.get("Currency").toString()));
    }
    else if(account.get("Accounttype").equals("STOCKS"))
    {
      Date date=new Date(account.get("Buy Date").toString());
      user.addAccount(new Stocks(account.get("Name").toString(),date,account.getFloat("Limit")));
    }
    else if(account.get("Accounttype").equals("DEBITCARD"))
    {
      user.addAccount(new DebitCard(account.get("Name").toString(),account.get("Bankname").toString(),
          account.getFloat("Limit"),account.get("IBAN").toString()));
    }
    else if(account.get("Accounttype").equals("CREDITCARD"))
    {
      Date date=new Date(account.get("Buy Date").toString());
      user.addAccount(new CreditCard(account.get("Name").toString(),account.get("Bankname").toString(),
          account.getFloat("Limit"),date));
    }
    else
    {
      assert true : "shoudln't get here";
    }
  }

  @Override
  public User getUsers()
  {
    collection = database.getCollection("User");
    MongoCursor<Document> cursor=collection.find().cursor();
    User user=null;

    CustomList<User> UserList=new CustomList<>();
    while (cursor.hasNext())
    {
      JSONObject json=new JSONObject(cursor.next().toJson());

      user=new User(json.get("_id").toString(),
                    json.get("First Name").toString(),
                    json.get("Last Name").toString(),
                    json.get("Password").toString());
      JSONArray accounts=json.getJSONArray("Accounts");
      int[] array=new int[accounts.length()];
      for (int i=0;i<accounts.length();i++)
      {
        JSONObject account=accounts.getJSONObject(i);
        Account(user,account);
        array[i]=account.getInt("id");
      }

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

      collection = database.getCollection("Transactions");
      Document query = new Document();
      query.append("User_ID",user.getUserID());
      cursor=collection.find(query).cursor();
      while (cursor.hasNext())
      {
        json=new JSONObject(cursor.next().toJson());
        ZonedDateTime date = ZonedDateTime.parse(json.getString("Date"));
        Transaction trans = TransactionCreator.transactionFromDatabaseData(date,json.getString("category"),json.getFloat("amount"),
                                                              json.getString("Description"),user.getCategoryStore(),json.getInt("_id"));
        CustomIterator<Account> account_iterator=user.getAccounts().getIterator();

        for(int i=0;i<array.length;i++)
        {

          if(array[i]==json.getInt("Account_Number"))
          {
            try
            {
              System.out.println("yes");
              user.applyAndSaveTransaction(trans,account_iterator.element());
            }
            catch (SWE_Exception e)
            {
              System.out.println("Couldn't insert Transaction"+e);
            }
          }
        }
      }
    }
    return user;
  }

  @Override
  public CustomList<User> getUsers(String ID) {
    collection = database.getCollection("User");

    Document query = new Document();
    query.append("_id",ID);
    //Document user =collection.f;
    return null;
  }

  @Override
  public CustomList<Transaction> getTransactions() {
    return null;
  }

  @Override
  public CustomList<Account> getAccounts() {
    return null;
  }
}
