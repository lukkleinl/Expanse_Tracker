package accounts;

import java.util.concurrent.atomic.AtomicInteger;
import exceptions.SWE_Exception;

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
    account_number = nextId.incrementAndGet();
    this.name = name;
    this.limit = limit;
    balance = 0;
  }

  /**
   * Creates a new Account with a random generated account number and a balance of 0.
   *
   * @param name The name of the account
   * @param limit Limit of the account
   */
  public Account(final String name, final float limit, final Integer ID) {
    account_number = ID;
    nextId.set(ID);
    this.name = name;
    this.limit = limit;
    balance = 0;
  }


  /**
   * Returns the account number.
   *
   * @return the account number
   */
  public int getAccount_number() {
    return account_number;
  }

  /**
   * Returns the name of the account.
   *
   * @return the account name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the current balance of the account.
   *
   * @return the balance
   */
  public float getBalance() {
    return balance;
  }

  /**
   * Performs a payout on the account
   *
   * @param amount Amount of the payout
   */
  public void payout(final float amount) throws SWE_Exception {
    if (balance-amount < limit) throw new SWE_Exception("Limit exceeded !");
    balance -= amount;
  }

  /**
   * Performs a deposit on the account
   *
   * @param amount Amount of the deposit
   */
  public void deposit(final float amount) {
    balance += amount;
  }

  /**
   * Returns the limit of the account.
   *
   * @return the limit
   */
  public float getLimit() {
    return limit;
  }

  @Override
  public final boolean equals(final Object obj) {
    if (!(obj instanceof Account))
      return false;
    return account_number == ((Account) obj).account_number;
  }

  public void updateAccountNumberAndBalance(int account_number, float balance) {
    nextId.decrementAndGet();
    this.account_number = account_number;
    this.balance = balance;
  }
}


