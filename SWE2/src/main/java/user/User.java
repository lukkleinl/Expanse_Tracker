/** */
package user;

import accounts.Account;
import exceptions.LimitException;
import iteration.CustomContainer;
import iteration.CustomList;
import java.util.HashMap;
import java.util.Map;
import ObserverPattern.SWE_Observer;
import java.util.Date;
import transactions.Deposit;
import transactions.DepositCategory;
import transactions.Payout;
import transactions.PayoutCategory;
import transactions.Transaction;

/** @author Michael Watholowitsch */
public class User implements SWE_Observer {
  private String firstname;
  private String lastname;
  private int userID;
  private String password;
  private CustomContainer<Account> accounts;
  private Map<Integer, CustomContainer<Transaction>> transactions;

  /**
   * Creates a new User without any Accounts or Transactions.
   *
   * @param userID The ID within the system and needed to log in
   * @param firstname The users first name.
   * @param lastname The users last name.
   * @param password The password for the user
   */
  public User(int userID, String firstname, String lastname, String password) {
    this.userID = userID;
    this.firstname = firstname;
    this.lastname = lastname;
    this.accounts=new CustomList<>();
    this.transactions=new HashMap<>();
    this.password = password;
  }

  /**
   * Adds a new Account to the CustomContainer of this Users Accounts.
   * 
   * @param acc the new account
   */
  public void addAccount(Account acc) {
    accounts.add(acc);
  }

  /**
   * Deductes the specified amount of money from the specified account.
   *
   * @param category the category of the {@code Transaction}
   * @param amount the amount of money
   * @param description the description of the {@code Transaction}
   * @param acc the account where money is deducted from
   * 
   * @throws LimitException if the resulting account-balance would be smaller than the limit
   */
  public void payOut(PayoutCategory category, float amount, String description,Account acc) throws LimitException {
    Date date=new Date();

    if(acc.getBalance()-amount<acc.getLimit()) throw new LimitException("Limit exceeded!");

    Payout payout=new Payout(date,amount,category,description);
    acc.payout(amount);

    if(transactions.containsKey(acc.getAccount_number())) transactions.get(acc.getAccount_number()).add(payout);
    else
    {
      CustomContainer<Transaction> cont =new CustomList<>();
      cont.add(payout);
      transactions.put(acc.getAccount_number(),cont);
    }
  }

  /**
   * Adds the specified amount of money to the specified account.
   *
   * @param category the category of the {@code Transaction}
   * @param amount the amount of money
   * @param description the description of the {@code Transaction}
   * @param acc the account where money is added to
   */
  public void deposit(DepositCategory category, float amount, String description,Account acc) {
    Date date=new Date();
    
    Deposit deposit=new Deposit(date,amount,category,description);
    acc.deposit(amount);

    if(transactions.containsKey(acc.getAccount_number())) transactions.get(acc.getAccount_number()).add(deposit);
    else
    {
      CustomContainer<Transaction> cont =new CustomList<>();
      cont.add(deposit);
      transactions.put(acc.getAccount_number(),cont);
    }
  }

  /** Updates the data of the User according to the input of the UserInterface. */
  @Override
  public void update(Object obj) {
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
   * Returns a {@linkplain CustomContainer} of this Users' {@linkplain Account}s.
   *
   * @return A CustomContainer of this Users' Accounts
   */
  public CustomContainer<Account> getAccounts() {
    return accounts;
  }

  /**
   * Returns a {@linkplain Map} of all the {@code Transactions} by the User.
   *
   * @return A Map of all the Transactions by the User
   */
  public Map<Integer, CustomContainer<Transaction>> getTransactions() {
    return transactions;
  }

  /**
   * Returns the password of the user.
   *
   * @return The password of the user
   */
  public String getPassword() {
    return password;
  }
}
