package MongoDb;

import accounts.Account;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import iteration.CustomList;
import org.bson.Document;
import transactions.Transaction;
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
  @Override
  public CustomList<User> getUsers() {
    collection = database.getCollection("User");

    Document query = new Document();
    collection.find();
    return null;
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
