package MongoDb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import user.User;

public class WriteOperation implements Write_Operation {

  private MongoClient mongo;
  private MongoDatabase database;
  private MongoCollection collection;

  public WriteOperation() {
    try {
      this.mongo = new MongoClient();
      this.database = this.mongo.getDatabase("ExpanseTracker");
    } catch (Exception e) {
      System.out.println("Probleme beim erstellen der Datenkbank");
    }
  }

  /**
   * Returns the document of a transaction to insert into MongoDB
   *
   *
   */

  private void getTrans(final Transaction trans, final int key, final String user_ID) {
    Document doc ;

    if (trans.toString().contains("PAYOUT")) {
      Payout payout = (Payout) trans;
      doc = new Document("_id", payout.getID()).append("Date", payout.getCreationDate().toString())
          .append("amount", payout.getAmount()).append("category_name", payout.getPayoutCategory())
          .append("category", "PAYOUT").append("Account_Number", key)
          .append("Description", payout.getDescription()).append("User_ID", user_ID);

      this.collection = this.database.getCollection("Transactions");
      this.collection.insertOne(doc);
    } else if (trans.toString().contains("DEPOSIT")) {

      Deposit deposit = (Deposit) trans;
      doc =
          new Document("_id", deposit.getID()).append("Date", deposit.getCreationDate().toString())
              .append("amount", deposit.getAmount()).append("category_name", deposit.getCategory())
              .append("category", "DEPOSIT").append("Account_Number", key)
              .append("Description", deposit.getDescription()).append("User_ID", user_ID);

      this.collection = this.database.getCollection("Transactions");
      this.collection.insertOne(doc);
    } else
      throw new RuntimeException("Unknown transaction");
  }

  /**
   * Returns a Document of an account
   *
   * @return a account document
   *
   */
  private Document Account(final Account account) {
    Document doc;

    System.out.println(account.getAccount_number() + " account number");

    switch (account.toString()) {
      case "STOCKS":
        Stocks stock = (Stocks) account;
        doc = new Document("id", account.getAccount_number()).append("Limit", account.getLimit())
            .append("Balance", account.getBalance()).append("Name", account.getName())
            .append("Accounttype", account.toString())
            .append("Buy Date", stock.getBuyDate().getTime());
        return doc;

      case "DEBITCARD":
        DebitCard debit = (DebitCard) account;
        doc = new Document("id", account.getAccount_number()).append("Limit", account.getLimit())
            .append("Balance", account.getBalance()).append("Name", account.getName())
            .append("IBAN", debit.getIBAN()).append("Bankname", debit.getBankName())
            .append("Accounttype", account.toString());
        return doc;

      case "CREDITCARD":
        CreditCard credit = (CreditCard) account;
        long l = credit.getExpiryDate().getTime();
        doc = new Document("id", account.getAccount_number()).append("Limit", account.getLimit())
            .append("Balance", account.getBalance()).append("Name", account.getName())
            .append("Expiry Date", l).append("Bankname", credit.getBankName())
            .append("Accounttype", account.toString());
        return doc;

      case "CASH":
        Cash cash = (Cash) account;
        doc = new Document("id", account.getAccount_number()).append("Limit", account.getLimit())
            .append("Balance", account.getBalance()).append("Name", account.getName())
            .append("Currency", cash.getCurrency()).append("Accounttype", account.toString());
        return doc;
    }
    throw new RuntimeException("Unknown account type");
  }


  private Document Categories(final String category) {
    Document doc ;
    doc = new Document("_id", category);

    return doc;
  }

  /**
   * inserts the user into the DB
   *
   * @param user User which should be inserted
   */
  @Override
  public void insertUser(final User user) {
    CustomContainer<Account> accounts = user.getAccounts();
    List<Document> accounts_array = new ArrayList<>();
    CustomIterator<Account> iter = accounts.getIterator();
    Map<Integer, CustomContainer<Transaction>> trans_map =
        user.getTransactionStore().getTransactions();
    Iterator<String> payout =
        user.getCategoryStore().getCategories(new PayoutCategory()).iterator();
    Iterator<String> deposit =
        user.getCategoryStore().getCategories(new DepositCategory()).iterator();
    List<Document> deposit_array = new ArrayList<>();
    List<Document> payout_array = new ArrayList<>();

    while (deposit.hasNext()) {
      Document doc = Categories(deposit.next());
      deposit_array.add(doc);
    }

    while (payout.hasNext()) {
      Document doc = Categories(payout.next());
      payout_array.add(doc);
    }

    while (iter.hasNext()) {
      Document doc = this.Account(iter.next());
      accounts_array.add(doc);
    }

    for (Entry e : trans_map.entrySet()) {
      CustomContainer<Object> list = (CustomList<Object>) e.getValue();
      CustomIterator<Object> iterator = list.getIterator();
      Integer account_number = (Integer) e.getKey();
      while (iterator.hasNext()) {
        this.getTrans((Transaction) iterator.element(), account_number, user.getUserID());
        iterator.next();
      }
    }

    Document dep = new Document("_id", user.getUserID()).append("First Name", user.getFirstname())
        .append("Last Name", user.getLastname()).append("Password", user.getPassword())
        .append("Accounts", accounts_array).append("Payout Categories", payout_array)
        .append("Deposit Categories", deposit_array);

    this.collection = this.database.getCollection("User");
    this.collection.insertOne(dep);

  }

  @Override
  public void insertTransaction(final User user, final Account acc, final Transaction trans) {
    String category = null;
    if (trans instanceof Payout)
      category = "PAYOUT";
    else if (trans instanceof Deposit)
      category = "DEPOSIT";
    else
      assert true : "Shouldnt reach this argument";

    Document doc ;
    doc = new Document("_id", trans.getID()).append("Date", trans.getCreationDate().toString())
        .append("amount", trans.getAmount()).append("category_name", trans.getCategory())
        .append("category", category).append("Account_Number", acc.getAccount_number())
        .append("Description", trans.getDescription()).append("User_ID", user.getUserID());

    this.collection = this.database.getCollection("Transactions");
    this.collection.insertOne(doc);
  }


  @Override
  public void updateTransaction(final Transaction trans) {
    this.collection = this.database.getCollection("Transactions");

    Document query = new Document();
    query.append("_id", trans.getID());
    Document setData = new Document();
    setData.append("Date", trans.getCreationDate().toString()).append("amount", trans.getAmount())
        .append("category", trans.getCategory()).append("Description", trans.getDescription());
    Document update = new Document();
    update.append("$set", setData);
    this.collection.updateOne(query, update);
  }

  @Override
  public void updateUser(final User user) {
    this.collection = this.database.getCollection("User");
    CustomContainer<Account> accounts = user.getAccounts();
    List<Document> accounts_array = new ArrayList<>();
    CustomIterator<Account> iter = accounts.getIterator();
    Iterator<String> payout =
        user.getCategoryStore().getCategories(new PayoutCategory()).iterator();
    Iterator<String> deposit =
        user.getCategoryStore().getCategories(new DepositCategory()).iterator();
    List<Document> deposit_array = new ArrayList<>();
    List<Document> payout_array = new ArrayList<>();

    while (deposit.hasNext()) {
      Document doc = Categories(deposit.next());
      deposit_array.add(doc);
    }
    while (payout.hasNext()) {
      Document doc = Categories(payout.next());
      payout_array.add(doc);
    }
    while (iter.hasNext()) {
      Document doc = this.Account(iter.next());
      accounts_array.add(doc);
    }

    Document query = new Document();
    query.append("_id", user.getUserID());
    Document setData = new Document();
    setData.append("First Name", user.getFirstname()).append("Last Name", user.getLastname())
        .append("Password", user.getPassword()).append("Accounts", accounts_array)
        .append("Payout Categories", payout_array).append("Deposit Categories", deposit_array);

    Document update = new Document();
    update.append("$set", setData);
    this.collection.updateOne(query, update);
  }


  @Override
  public void deleteUser(final User user) {
    BasicDBObject document = new BasicDBObject();
    document.put("_id", user.getUserID());
    this.collection = this.database.getCollection("User");
    this.collection.deleteOne(document);

    this.collection = this.database.getCollection("Transactions");
    BasicDBObject filter = new BasicDBObject();
    filter.put("User_ID", user.getUserID());
    DeleteResult deleteResult = this.collection.deleteMany(filter);
    // System.out.println(deleteResult.getDeletedCount());
  }

  @Override
  public void clearDatabase() {
    this.collection = this.database.getCollection("User");
    this.collection.deleteMany(new BasicDBObject());

    this.collection = this.database.getCollection("Transactions");
    this.collection.deleteMany(new BasicDBObject());
  }



  @Override
  public void deleteTransaction(final User user, final int trans_id) {
    this.collection = this.database.getCollection("Transactions");

    BasicDBObject document = new BasicDBObject();
    document.put("_id", trans_id);
    document.put("User_ID", user.getUserID());

    DeleteResult deleteResult = this.collection.deleteMany(document);
    // System.out.println(deleteResult.getDeletedCount());
  }
}
