/** */
package user;

import accounts.Account;
import accounts.Cash;
import accounts.CreditCard;
import accounts.DebitCard;
import accounts.Stocks;
import iteration.CustomList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Date;
import transactions.DepositCategory;
import transactions.PayoutCategory;
import transactions.Transaction;

/** @author Michael Watholowitsch */
public class User implements Observer {
  private String firstname;
  private String lastname;
  private int userID;
  private CustomList<Account> accounts;
  private Map<String, CustomList<Transaction>> transactions;

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
  }

  /** Adds a DebitCard Account to the List of this Users Accounts. */
  public void addDebitCard(String Name,String Bank,float limit,String IBAN) {
    DebitCard debit=new DebitCard(Name,Bank,limit,IBAN);
    accounts.add(debit);
  }

  /** Adds a CrebitCard Account to the List of this Users Accounts*  */
  public void addCreditCard(String Name,String Bank,float limit, Date expireDate) {
    CreditCard credit=new CreditCard(Name,Bank,limit,expireDate);
    accounts.add(credit);
  }

  /** Adds a Stocks Account to the List of this Users Accounts. */
  public void addStocks(String Name,Date buyDate) {
    Stocks stock = new Stocks(Name,buyDate);
    accounts.add(stock);
  }

  /** Adds a Cash Account to the List of this Users Accounts. */
  public void addCash(String Name) {
    Cash cash = new Cash(Name, "Euro");
    accounts.add(cash);
  }

  /**
   * @param category The Category of the Transaction
   * @param amount The Amount of Money
   * @param description The Description of the Transaction
   */
  public void payOut(PayoutCategory category, float amount, String description) {
    // TODO
  }

  /**
   * @param category The Category of the Transaction
   * @param amount The Amount of Money
   * @param description The Description of the Transaction
   */
  public void deposit(DepositCategory category, float amount, String description) {
    // TODO
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
  public Map<String, CustomList<Transaction>> getTransactions() {
    return transactions;
  }
}
