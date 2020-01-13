package accounts;

import java.util.concurrent.atomic.AtomicInteger;

/** @author Patrick Gmasz */
public abstract class Account {
  private int account_number;
  private final String name;
  private float balance;
  private final float limit;
  private static AtomicInteger nextId = new AtomicInteger();

  /**
   * Creates a new Account with a random generated account number and a balance of 0.
   *
   * @param name The name of the account
   * @param limit Limit of the account
   */
  public Account(final String name, final float limit) {
    this.account_number = nextId.incrementAndGet();
    this.name = name;
    this.limit = limit;
    this.balance = 0;
  }

  /**
   * Creates a new Account (from database data).
   *
   * @param name The name of the account
   * @param limit Limit of the account
   * @param ID the account id
   * @param balance the balance of the account
   */
  public Account(final String name, final float limit, final Integer ID, final float balance) {
    nextId.set(ID);
    this.account_number=nextId.get();
    this.name = name;
    this.limit = limit;
    this.balance = balance;
  }


  /**
   * Returns the account number.
   *
   * @return the account number
   */
  public int getAccount_number() {
    return this.account_number;
  }

  /**
   * Returns the name of the account.
   *
   * @return the account name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the current balance of the account.
   *
   * @return the balance
   */
  public float getBalance() {
    return this.balance;
  }

  /**
   * Performs a payout on the account
   *
   * @param amount Amount of the payout
   */
  public void payout(final float amount) {
    this.balance -= amount;
  }

  /**
   * Performs a deposit on the account
   *
   * @param amount Amount of the deposit
   */
  public void deposit(final float amount) {
    this.balance += amount;
  }

  /**
   * Returns the limit of the account.
   *
   * @return the limit
   */
  public float getLimit() {
    return this.limit;
  }

  /** Returns {@code true} if the balance is equal or higher than the limit, else {@code false}. */
  public boolean limitExceeded() {
    return this.balance < this.limit;
  }

  @Override
  public final boolean equals(final Object obj) {
    if (!(obj instanceof Account))
      return false;
    return this.account_number == ((Account) obj).account_number;
  }

  public void updateAccountNumberAndBalance(final int account_number, final float balance) {
    nextId.decrementAndGet();
    this.account_number = account_number;
    this.balance = balance;
  }

}


