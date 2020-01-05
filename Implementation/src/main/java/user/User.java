/** */
package user;

import java.util.Set;
import accounts.Account;
import exceptions.SWE_Exception;
import iteration.CustomContainer;
import iteration.CustomList;
import Patterns.observing.Database;
import Patterns.observing.SWE_Observable;
import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.categories.CategoryStore;
import transactions.categories.TransactionCategoryFunctionality;
import transactions.strategy.BalanceChange;
import transactions.strategy.SimpleDeposit;
import transactions.strategy.SimplePayout;

/**
 * Representation of a user.
 */
public class User extends SWE_Observable {
  private final String firstname;
  private final String lastname;
  private final int userID;
  private final String password;
  private final CustomContainer<Account> accounts;
  private final TransactionStore transactions;
  private final CategoryStore categories;

  /**
   * Creates a new User without any Accounts or Transactions.
   *
   * @param userID The ID within the system and needed to log in
   * @param firstname The users first name.
   * @param lastname The users last name.
   * @param password The password for the user
   */
  @Deprecated
  public User(final int userID, final String firstname, final String lastname,
      final String password) {
    this.userID = userID;
    this.firstname = firstname;
    this.lastname = lastname;
    accounts = new CustomList<>();
    transactions = new TransactionStore();
    this.password = password;
    this.categories = new CategoryStore().withDefaultCategories(); //withDefaultCatoegires braucht man, sonst geht nix!!!

  }


  // VON PAUL  neuer Konstruktor fürs Observer Pattern,
  // wenn User erstellt wird erwartet er jz die Database und subscribed sich automatisch
    /**
     * Creates a new User without any Accounts or Transactions.
     *
     * @param userID The ID within the system and needed to log in
     * @param firstname The users first name.
     * @param lastname The users last name.
     * @param password The password for the user.
     * @param  database The local Database(Observer) that handles Persistency.
     */
    public User(final int userID, final String firstname, final String lastname,
                final String password, Database database) throws Exception {
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        accounts = new CustomList<>();
        transactions = new TransactionStore();
        this.password = password;
        categories = new CategoryStore();

        this.subscribe(database);
        updateObservers(this);
    }

    //</Paul>

  /**
   * Adds a new Account to the CustomContainer of this Users Accounts.
   *
   * @param acc the new account
   */
  public void addAccount(final Account acc) {

      accounts.add(acc);
      updateObservers(this); // VON PAUL fürs observer
  }

  public void updateAccount(final Account oldAcc, final Account newAcc) {
    newAcc.updateAccountNumberAndBalance(oldAcc.getAccount_number(),oldAcc.getBalance());
    accounts.update(newAcc);
    updateObservers(this);
  }

  public void deleteAccount(final Account acc) {
    accounts.delete(acc);
    updateObservers(this);
  }

  /**
   * Performs either a Deposit or a Payout
   *
   * @param transaction Transaction which should be performed
   * @param account Account where the transaction will be performed
   *
   * @throws SWE_Exception if the resulting account-balance would be smaller than the limit or if
   *         the transaction is neither a Deposit nor a Payout
   */
  public void applyAndSaveTransaction(final Transaction transaction, final Account account) throws SWE_Exception {
    BalanceChange strategy;

    if (transaction instanceof Deposit) {
      strategy = new SimpleDeposit(transaction, account);
    } else if (transaction instanceof Payout) {
      strategy = new SimplePayout(transaction, account);
    } else
      throw new SWE_Exception("Unknown Transaction !");

    strategy.applyBalanceChange();
    transactions.addTransactionUnderKey(account.getAccount_number(), transaction);
    updateObservers(this); // VON PAUL fürs observer
  }

  public void updateTransaction(final int accountID, final Transaction transaction)  {
    transactions.updateTransaction(accountID,transaction);
    updateObservers(this);
  }


  public void deleteTransaction(final int accountID, final Transaction transaction) {
    transactions.deleteTransaction(accountID,transaction);
    updateObservers(this);
  }

  /**
   * @param strategy the strategy which determines what kind of categories are retrieved
   * @return a {@linkplain Set} of categories
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
    updateObservers(this); // VON PAUL fürs observer

  }

  /**
   * Removes a category if it is supported.
   *
   * @param categoryname the name of the to-be removed category (NOTE: the comparison of names is
   *        case-insensitive)
   */
  public void removeTransactionCategory(final String categoryname) {
    categories.removeCategory(categoryname);
    updateObservers(this); // VON PAUL fürs observer
  }

  /**
   * @param categoryname the name of the category in question
   * @return {@code true} if the category is supported, else {@code false} (NOTE: the comparison of
   *         names is case-insensitive)
   */
  public boolean categorySupported(final String categoryname) {
    return categories.categorySupported(categoryname);
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
   * Returns a {@linkplain TransactionStore} of all the {@code Transactions} by the User.
   *
   * @return a store with all transactions of the user
   */
  public TransactionStore getTransactionStore() {
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

  /**
   * Returns the {@linkplain CategoryStore} of the user.
   *
   * @return all categories of the user
   */
  public CategoryStore getCategoryStore() {
    return categories;
  }
}


