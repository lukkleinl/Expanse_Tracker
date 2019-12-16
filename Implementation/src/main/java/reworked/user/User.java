/** */
package reworked.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import accounts.Account;
import exceptions.LimitException;
import exceptions.UnknownTransactionException;
import reworked.transactions.Deposit;
import reworked.transactions.Payout;
import reworked.transactions.Transaction;
import reworked.transactions.categories.CategoryStore;
import reworked.transactions.categories.TransactionCategoryFunctionality;
import reworked.transactions.strategy.BalanceChange;
import reworked.transactions.strategy.SimpleDeposit;
import reworked.transactions.strategy.SimplePayout;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomList;

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
    accounts = new CustomList<>();
    transactions = new HashMap<>();
    this.password = password;
    categories = new CategoryStore();
  }

  /**
   * Adds a new Account to the CustomContainer of this Users Accounts.
   *
   * @param acc the new account
   */
  public void addAccount(final Account acc) {
    accounts.add(acc);
  }


  /**
   * Performs either a Deposit or a Payout
   *
   * @param transaction Transaction which should be performed
   * @param account Account where the transaction will be performed
   *
   * @throws UnknownTransactionException if the Object type is neither a Deposit nor a Payout
   */
  public void handleTransaction(final Transaction transaction, final Account account) {
    BalanceChange strategy;

    if (transaction instanceof Deposit) {
      strategy = new SimpleDeposit();
    } else if (transaction instanceof Payout) {
      strategy = new SimplePayout();
    } else
      throw new UnknownTransactionException("Unknown Transaction");

    try {
      strategy.applyBalanceChange(transaction, account);
      transactions.putIfAbsent(account.getAccount_number(), new CustomList<>());
      transactions.get(account.getAccount_number()).add(transaction);
    } catch (LimitException e) {
      // TODO
      e.printStackTrace();
    }
  }

  /**
   * @param strategy the strategy which determines what kind of categories are retrieved
   * @return a {@linkplain List} of categories
   */
  public Set<String> getCategories(final TransactionCategoryFunctionality strategy) {
    return categories.getCategories(strategy);
  }

  /**
   * Adds a new transaction category.
   *
   * @param strategy the strategy which determines what kind of category is added
   */
  public void newTransactionCategory(final TransactionCategoryFunctionality strategy) {
    categories.addTransactionCategory(strategy);
  }

  /**
   * Removes a category if it is supported.
   *
   * @param categoryname the name of the to-be removed category (NOTE: the comparison of names is case-insensitive)
   */
  public void removeTransactionCategory(final String categoryname) {
    categories.removeCategory(categoryname);
  }

  /**
   * @param categoryname the name of the category in question
   * @return {@code true} if the category is supported, else {@code false} (NOTE: the comparison of names is case-insensitive)
   */
  public boolean categorySupported(final String categoryname) {
    return categories.categorySupported(categoryname);
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
