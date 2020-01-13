package mongoDB;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import iteration.CustomContainer;
import iteration.CustomIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;
import user.User;


/**
 * Handles all CUD operations within the database
 *
 */

public class WriteOperation implements Write_Operation {

  private MongoClient mongo;
  private MongoDatabase database;
  private MongoCollection collection;

  public WriteOperation() {
    try {
      this.mongo = new MongoClient();
      this.database = this.mongo.getDatabase("ExpanseTracker");
      collection=this.database.getCollection("User");
    } catch (Exception e) {
      System.out.println("Could not connect to the database");
    }
  }


  /**
   * Inserts a document of a transaction into the collection
   *
   * @param transaction transaction which shall be
   * @param account_id account in which the transaction shall be inserted
   * @param user_ID id of the user
   *
   */

  private void insert_transaction_for_non_existing_user(final Transaction transaction, final int account_id, final String user_ID) {
    Document doc ;


    this.collection = this.database.getCollection("Transactions");


    if (transaction.toString().contains("PAYOUT")) {
      Payout payout = (Payout) transaction;
      doc = new Document("_id", payout.getID()).append("Date", payout.getCreationDate().toString())
          .append("amount", payout.getAmount()).append("category_name", payout.getPayoutCategory())
          .append("category", "PAYOUT").append("Account_Number", account_id)
          .append("Description", payout.getDescription()).append("User_ID", user_ID);

      try
      {

        this.collection.insertOne(doc);
      }
      catch (Exception e)
      {
        System.out.println("Couldnt insert into database"+ e);
      }
    } else if (transaction.toString().contains("DEPOSIT")) {

      Deposit deposit = (Deposit) transaction;
      doc =
          new Document("_id", deposit.getID()).append("Date", deposit.getCreationDate().toString())
              .append("amount", deposit.getAmount()).append("category_name", deposit.getCategory())
              .append("category", "DEPOSIT").append("Account_Number", account_id)
              .append("Description", deposit.getDescription()).append("User_ID", user_ID);

      try {
        this.collection.insertOne(doc);
      }
      catch (Exception e)
      {
        System.out.println("Couldnt insert into database"+ e);
      }
    } else
      assert true : "Should not reach this argument";
  }

  /**
   * Creates a document of accounts to insert into database
   *
   * @param account Account which shall be inserted into database
   * @return a account document
   *
   */
  private Document create_account_doc(final Account account) {
    Document doc;

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

  /**
   * Creates a document of accounts to insert into database
   *
   * @param category name of the category which shall be inserted
   * @return a document with the category
   *
   */

  private Document create_categories_doc(final String category)
  {
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

    this.collection = this.database.getCollection("User");

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
      Document doc = create_categories_doc(deposit.next());
      deposit_array.add(doc);
    }

    while (payout.hasNext()) {
      Document doc = create_categories_doc(payout.next());
      payout_array.add(doc);
    }

    while (iter.hasNext()) {
      Document doc = this.create_account_doc(iter.next());
      accounts_array.add(doc);
    }

    for (Entry e : trans_map.entrySet()) {
      CustomContainer<Transaction> list = (CustomContainer<Transaction>) e.getValue();
      CustomIterator<Transaction> iterator = list.getIterator();
      Integer account_number = (Integer) e.getKey();
      while (iterator.hasNext()) {
        this.insert_transaction_for_non_existing_user(iterator.element(), account_number, user.getUserID());
        iterator.next();
      }
    }

    Document doc = new Document("_id", user.getUserID()).append("First Name", user.getFirstname())
        .append("Last Name", user.getLastname()).append("Password", user.getPassword())
        .append("Accounts", accounts_array).append("Payout Categories", payout_array)
        .append("Deposit Categories", deposit_array);


    try {
      this.collection.insertOne(doc);
    }
    catch (Exception e)
    {
      System.out.println("Couldnt insert into database"+ e);
    }


  }

  /**
   * inserts the transaction into the DB
   *
   * @param user User in which the transaction shall be inserted
   * @param account  Account in which the transaction shall be inserted
   * @param transaction transaction which shall be inserted
   *
   */
  @Override
  public void insertTransaction(final User user, final Account account, final Transaction transaction)
  {
    this.collection = this.database.getCollection("Transactions");

    String category = null;
    if (transaction instanceof Payout)
      category = "PAYOUT";
    else if (transaction instanceof Deposit)
      category = "DEPOSIT";
    else
      assert true : "Shouldnt reach this argument";

    Document doc ;
    doc = new Document("_id", transaction.getID()).append("Date", transaction.getCreationDate().toString())
        .append("amount", transaction.getAmount()).append("category_name", transaction.getCategory())
        .append("category", category).append("Account_Number", account.getAccount_number())
        .append("Description", transaction.getDescription()).append("User_ID", user.getUserID());



    try {
      this.collection.insertOne(doc);
    }
    catch (Exception e)
    {
      System.out.println("Couldnt insert into database"+ e);
    }


  }


  /**
   * updates the transaction within the database
   *
   * @param transaction transaction which shall be inserted
   *
   */
  @Override
  public void updateTransaction(final Transaction transaction) {

    this.collection = this.database.getCollection("Transactions");

    Document query = new Document();
    query.append("_id", transaction.getID());
    Document setData = new Document();
    setData.append("Date", transaction.getCreationDate().toString()).append("amount", transaction.getAmount())
        .append("category", transaction.getCategory()).append("Description", transaction.getDescription());
    Document update = new Document();
    update.append("$set", setData);

    try {
      this.collection.updateOne(query, update);
    }
    catch (Exception e)
    {
      System.out.println("Couldnt update transaction"+ e);
    }

  }

  /**
   * updates the user within the database
   *
   * @param user user which shall be inserted
   *
   */
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
      Document doc = create_categories_doc(deposit.next());
      deposit_array.add(doc);
    }
    while (payout.hasNext()) {
      Document doc = create_categories_doc(payout.next());
      payout_array.add(doc);
    }
    while (iter.hasNext()) {
      Document doc = this.create_account_doc(iter.next());
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
    try {
      this.collection.updateOne(query, update);
    }
    catch (Exception e)
    {
      System.out.println("Couldnt update user"+ e);
    }
  }

  /**
   * deletes the user within the database
   *
   * @param user user which shall be inserted
   *
   */
  @Override
  public void deleteUser(final User user) {

    BasicDBObject document = new BasicDBObject();
    document.put("_id", user.getUserID());
    this.collection = this.database.getCollection("User");
    this.collection.deleteOne(document);

    this.collection = this.database.getCollection("Transactions");
    BasicDBObject filter = new BasicDBObject();
    filter.put("User_ID", user.getUserID());
    try {
      this.collection.deleteMany(filter);
    }
    catch (Exception e) {
      System.out.println("Couldnt delete user" + e);
    }

    // System.out.println(deleteResult.getDeletedCount());
  }

  /**
   * deletes all data from database
   * only used for testing
   *
   *
   */
  @Override
  public void clearDatabase() {
    this.collection = this.database.getCollection("User");
    this.collection.deleteMany(new BasicDBObject());

    this.collection = this.database.getCollection("Transactions");
    this.collection.deleteMany(new BasicDBObject());
  }


  /**
   * deletes the transaction within the database
   *
   * @param trans_id transaction which shall be inserted
   *
   */
  @Override
  public void deleteTransaction(final User user, final int trans_id) {
    this.collection = this.database.getCollection("Transactions");

    BasicDBObject document = new BasicDBObject();
    document.put("_id", trans_id);
    document.put("User_ID", user.getUserID());

    try {
      this.collection.deleteMany(document);
    }
    catch (Exception e)
    {
      System.out.println("Couldnt delete transaction"+ e);
    }
    // System.out.println(deleteResult.getDeletedCount());
  }
}
