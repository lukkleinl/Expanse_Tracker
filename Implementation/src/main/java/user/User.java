/** */
package user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import Transaction_Strategy.AddTransaction;
import Transaction_Strategy.Add_Deposit;
import Transaction_Strategy.Add_Payout;
import accounts.Account;
import exceptions.UnknownTransactionException;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomList;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.changes.CategoryStore;

/** @author Michael Watholowitsch */
public class User {
  private final String firstname;
  private final String lastname;
  private final int userID;
  private final String password;
  private final CustomContainer<Account> accounts;
  private final Map<Integer, CustomContainer<Transaction>> transactions;
  private final CategoryStore categories;

  /**
   * Creates a new User without any Accounts or Transactions.
   *
   * @param userID The ID within the system and needed to log in
   * @param firstname The users first name.
   * @param lastname The users last name.
   * @param password The password for the user
   */
  public User(final int userID, final String firstname, final String lastname,
      final String password) {
    this.userID = userID;
    this.firstname = firstname;
    this.lastname = lastname;
    this.accounts = new CustomList<>();
    this.transactions = new HashMap<>();
    this.password = password;
    this.categories = new CategoryStore();
  }

  /**
   * Adds a new Account to the CustomContainer of this Users Accounts.
   * 
   * @param acc the new account
   */
  public void addAccount(final Account acc) {
    this.accounts.add(acc);
  }


  /**
   * Performs either a Deposit or a Payout
   *
   * @param transaction Transaction which should be performed
   * @param account Account where the transaction will be performed
   *
   * @throws UnknownTransactionException if the Object type is neither a Deposit nor a Payout
   */
  public void addTransaction(final Object transaction, final Account account) {
    AddTransaction add;
    if (transaction.getClass().equals(Deposit.class)) {
      add = new Add_Deposit();
      try {
        add.add(transaction, this.transactions, account);
      } catch (Exception e) {
        System.out.println(e);
      }
    } else if (transaction.getClass().equals(Payout.class)) {
      add = new Add_Payout();
      try {
        add.add(transaction, this.transactions, account);
      } catch (Exception e) {
        System.out.println(e);
      }
    } else {
      throw new UnknownTransactionException("Unknown Transaction");
    }
  }

  /**
   * Adds a new category for this users transactions.
   * 
   * @param categoryname the name of the new category
   */
  public void newTransactionCategory(final String categoryname) {
    this.categories.addCategory(categoryname);
  }

  /**
   * Removes a category of transaction of this user if it is present.
   * 
   * @param categoryname the name of the to-be removed category
   */
  public void removeTransactionCategory(final String categoryname) {
    this.categories.removeCategory(categoryname);
  }

  /**
   * @param categoryname the name of the requested category
   * @return an Optional with the category if it is provided.
   */
  public Optional<String> getCategory(final String categoryname) {
    return this.categories.getCategory(categoryname);
  }

  /** Updates the data of the User according to the input of the UserInterface. */
  public void update(final Object obj) {
    // TODO Auto-generated method stub
  }

  /* ---------- getters ---------- */

  /**
   * Returns the Firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    return this.firstname;
  }

  /**
   * Returns the Lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    return this.lastname;
  }

  /**
   * Returns the userID.
   *
   * @return the userID
   */
  public int getUserID() {
    return this.userID;
  }

  /**
   * Returns a {@linkplain CustomContainer} of this Users' {@linkplain Account}s.
   *
   * @return A CustomContainer of this Users' Accounts
   */
  public CustomContainer<Account> getAccounts() {
    return this.accounts;
  }

  /**
   * Returns a {@linkplain Map} of all the {@code Transactions} by the User.
   *
   * @return A Map of all the Transactions by the User
   */
  public Map<Integer, CustomContainer<Transaction>> getTransactions() {
    return this.transactions;
  }

  /**
   * Returns the password of the user.
   *
   * @return The password of the user
   */
  public String getPassword() {
    return this.password;
  }
}
