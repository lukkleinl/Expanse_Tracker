/** */
package user;

import java.util.Set;
import Patterns.observing.Database;
import Patterns.observing.SWE_Observable;
import accounts.Account;
import iteration.CustomContainer;
import iteration.CustomIterator;
import iteration.CustomList;
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
  private final String userID;
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
  public User(final String userID, final String firstname, final String lastname,
      final String password) {
    this.userID = userID;
    this.firstname = firstname;
    this.lastname = lastname;
    this.accounts = new CustomList<>();
    this.transactions = new TransactionStore();
    this.password = password;
    this.categories = new CategoryStore().withDefaultCategories(); // withDefaultCatoegires braucht
                                                                   // man, sonst geht nix!!!

  }


  // VON PAUL neuer Konstruktor fürs Observer Pattern,
  // wenn User erstellt wird erwartet er jz die Database und subscribed sich automatisch
  /**
   * Creates a new User without any Accounts or Transactions.
   *
   * @param userID The ID within the system and needed to log in
   * @param firstname The users first name.
   * @param lastname The users last name.
   * @param password The password for the user.
   * @param database The local Database(Observer) that handles Persistency.
   */
  public User(final String userID, final String firstname, final String lastname,
      final String password, final Database database) throws Exception {
    this.userID = userID;
    this.firstname = firstname;
    this.lastname = lastname;
    this.accounts = new CustomList<>();
    this.transactions = new TransactionStore();
    this.password = password;
    this.categories = new CategoryStore().withDefaultCategories();

    this.subscribe(database);
    updateObservers(this);
  }

  // </Paul>

  /**
   * Adds a new Account to the CustomContainer of this Users Accounts.
   *
   * @param acc the new account
   */
  public void addAccount(final Account acc) {

    this.accounts.add(acc);
    updateObservers(this); // VON PAUL fürs observer
  }

  public void updateAccount(final Account oldAcc, final Account newAcc) {
    newAcc.updateAccountNumberAndBalance(oldAcc.getAccount_number(), oldAcc.getBalance());
    this.accounts.update(newAcc);
    updateObservers(this);
  }

  public void deleteAccount(final Account acc) {
    this.accounts.delete(acc);
    updateObservers(this);
  }

  /**
   * Performs either a Deposit or a Payout
   *
   * @param transaction Transaction which should be performed
   * @param account Account where the transaction will be performed
   *
   * @throws RuntimeException if the resulting account-balance would be smaller than the limit or if
   *         the transaction is neither a Deposit nor a Payout
   */
  public void applyAndSaveTransaction(final Transaction transaction, final Account account)
      throws RuntimeException {
    BalanceChange strategy;

    if (transaction instanceof Deposit) {
      strategy = new SimpleDeposit(transaction, account);
    } else if (transaction instanceof Payout) {
      strategy = new SimplePayout(transaction, account);
    } else
      throw new RuntimeException("Unknown Transaction !");

    strategy.applyBalanceChange();
    this.transactions.addTransactionUnderKey(account.getAccount_number(), transaction);
    updateObservers(this, account, transaction); // VON PAUL fürs observer
  }

  public void updateTransaction(final int accountID, final Transaction transaction,final float old_amount) {
    this.transactions.updateTransaction(accountID, transaction);

    for (CustomIterator<Account> it = this.accounts.getIterator(); it.hasNext(); it.next()) {
      if (it.element().getAccount_number() == accountID) {

        float new_amount=transaction.getAmount()-old_amount;


        if(transaction instanceof Payout)
          it.element().updateAccountNumberAndBalance(accountID, it.element().getBalance() - new_amount);
        else if(transaction instanceof Deposit)
          it.element().updateAccountNumberAndBalance(accountID, it.element().getBalance() + new_amount);
        else
          assert true : "Should not reach this argument";

        updateObservers(this,transaction);
      }
    }
    // while (acc_iterator.hasNext()) {
    // if (acc_iterator.element().getAccount_number() == accountID) {
    // acc_iterator.element().updateAccountNumberAndBalance(accountID,
    // acc_iterator.element().getBalance() - transaction.getAmount());
    // updateObservers(this, acc_iterator.next(), transaction);
    // }
    // }
  }


  public void deleteTransaction(final int accountID, final Transaction transaction) {

    for (CustomIterator<Account> it = this.accounts.getIterator(); it.hasNext(); it.next()) {
      if (it.element().getAccount_number() == accountID) {
        if(transaction instanceof Payout)
            it.element().updateAccountNumberAndBalance(accountID, it.element().getBalance() + transaction.getAmount());
        else if(transaction instanceof Deposit)
            it.element().updateAccountNumberAndBalance(accountID, it.element().getBalance() - transaction.getAmount());
        else
          assert true : "Should not reach this argument";

        updateObservers(this);
      }

    }
    // while (iterator.hasNext()) {
    // if (accountID == iterator.element().getAccount_number()) {
    // iterator.element().updateAccountNumberAndBalance(accountID,
    // iterator.element().getBalance() - transaction.getAmount());
    // updateObservers(this);
    // }
    // iterator.next();
    // }
    this.transactions.deleteTransaction(accountID, transaction);
    updateObservers(this, transaction.getID());

  }

  /**
   * @param strategy the strategy which determines what kind of categories are retrieved
   * @return a {@linkplain Set} of categories
   */
  public Set<String> getCategories(final TransactionCategoryFunctionality strategy) {
    return this.categories.getCategories(strategy);
  }

  /**
   * Adds a new transaction category.
   *
   * @param strategy the strategy which determines what kind of category is added
   */
  public void newTransactionCategory(final TransactionCategoryFunctionality strategy) {
    this.categories.addTransactionCategory(strategy);
    updateObservers(this); // VON PAUL fürs observer

  }

  /**
   * Removes a category if it is supported.
   *
   * @param categoryname the name of the to-be removed category (NOTE: the comparison of names is
   *        case-insensitive)
   */
  public void removeTransactionCategory(final String categoryname) {
    this.categories.removeCategory(categoryname);
    updateObservers(this); // VON PAUL fürs observer
  }

  /**
   * @param categoryname the name of the category in question
   * @return {@code true} if the category is supported, else {@code false} (NOTE: the comparison of
   *         names is case-insensitive)
   */
  public boolean categorySupported(final String categoryname) {
    return this.categories.categorySupported(categoryname);
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
  public String getUserID() {
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
   * Returns a {@linkplain TransactionStore} of all the {@code Transactions} by the User.
   *
   * @return a store with all transactions of the user
   */
  public TransactionStore getTransactionStore() {
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

  /**
   * Returns the {@linkplain CategoryStore} of the user.
   *
   * @return all categories of the user
   */
  public CategoryStore getCategoryStore() {
    return this.categories;
  }
}


