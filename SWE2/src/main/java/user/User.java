/** */
package user;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import exceptions.LimitException;
import iteration.CustomList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Date;
import transactions.Deposit;
import transactions.DepositCategory;
import transactions.Payout;
import transactions.PayoutCategory;
import transactions.Transaction;

/** @author Michael Watholowitsch */
public class User implements Observer {
  private String firstname;
  private String lastname;
  private int userID;
  private CustomList<Account> accounts;
  private Map<Integer, CustomList<Transaction>> transactions;
  private CustomList<Transaction> transaction_list;

  /**
   * Creates a new User without any Accounts or Transactions.
   *
   * @param userID The ID within the System.
   * @param firstname The users first name.
   * @param lastname The users last name.
   */
  public User(int userID, String firstname, String lastname) {
    this.userID = userID;
    this.firstname = firstname;
    this.lastname = lastname;
    accounts=new CustomList<>();
    transactions=new HashMap<>();
  }

  /** Adds a DebitCard Account to the List of this Users Accounts. */
  public void addDebitCard(DebitCard debit) {
    accounts.add(debit);
  }

  /** Adds a CrebitCard Account to the List of this Users Accounts*  */
  public void addCreditCard(CreditCard credit) {
    accounts.add(credit);
  }

  /** Adds a Stocks Account to the List of this Users Accounts. */
  public void addStocks(Stocks stock) {
    accounts.add(stock);
  }

  /** Adds a Cash Account to the List of this Users Accounts. */
  public void addCash(Cash cash) {
    accounts.add(cash);
  }

  /**
   * Throws LimitException if amount of payout is higher than limit
   *
   * @param category The Category of the Transaction
   * @param amount The Amount of Money
   * @param description The Description of the Transaction
   */
  public void payOut(PayoutCategory category, float amount, String description,Account acc) throws LimitException {
    Date date=new Date();
    Payout payout=new Payout(date,amount,category,description);

    float condition = acc.getBalance()-amount;
    if(condition<acc.getLimit()) {
      throw new LimitException("Limit exceeded!");
      //System.out.println("unterhalb des Limits");
    }
    else
      acc.payout(amount);


    if(transactions.containsKey(acc.getAccount_number()))
    {
      //System.out.println("containing");
      transaction_list=transactions.get(acc.getAccount_number());
      transaction_list.add(payout);
      transactions.put(acc.getAccount_number(),transaction_list);
    }
    else
    {
      //System.out.println("not containing");
      transaction_list=new CustomList<>();
      transaction_list.add(payout);
      transactions.put(acc.getAccount_number(),transaction_list);
    }
  }

  /**
   * @param category The Category of the Transaction
   * @param amount The Amount of Money
   * @param description The Description of the Transaction
   */
  public void deposit(DepositCategory category, float amount, String description,Account acc) {
    Date date=new Date();
    Deposit deposit=new Deposit(date,amount,category,description);
    acc.deposit(amount);

    if(transactions.containsKey(acc.getAccount_number()))
    {
      //System.out.println("containing");
      transaction_list=transactions.get(acc.getAccount_number());
      transaction_list.add(deposit);
      transactions.put(acc.getAccount_number(),transaction_list);
    }
    else
    {
      transaction_list=new CustomList<>();
      transaction_list.add(deposit);
      transactions.put(acc.getAccount_number(),transaction_list);
    }
  }

  /** Updates the data of the User according to the input of the UserInterface. */
  @Override
  public void update(Observable o, Object arg) {
    // TODO Auto-generated method stub

  }

  /**
   * Returns the Firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Returns the Lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * Returns the userID.
   *
   * @return the userID
   */
  public int getUserID() {
    return userID;
  }

  /**
   * Returns a CustomList of this Users' Accounts.
   *
   * @return A CustomList of this Users' Accounts
   */
  public CustomList<Account> getAccounts() {
    return accounts;
  }

  /**
   * Returns a Map of all the Transactions by the User.
   *
   * @return A Map of all the Transactions by the User
   */
  public Map<Integer, CustomList<Transaction>> getTransactions() {
    return transactions;
  }
}
