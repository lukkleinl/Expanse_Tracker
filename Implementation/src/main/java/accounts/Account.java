package accounts;

import java.util.concurrent.atomic.AtomicInteger;

/** @author Patrick Gmasz */
public abstract class Account {
  private int account_number;
  private String name;
  private float balance;
  private float limit;
  private static AtomicInteger nextId = new AtomicInteger();

  /**
   * Creates a new Account with a random generated account number and a balance of 0.
   *
   * @param name The name of the account
   * @param limit Limit of the account
   */
  public Account(String name,float limit) {
    account_number=nextId.incrementAndGet();
    this.name = name;
    this.limit=limit;
    this.balance = 0;
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
   *  Performs a payout on the account
   *
   * @param amount Amount of the payout
   */
  public void payout(float amount) {
    balance-=amount;
  }

  /**
   *  Performs a deposit on the account
   *
   * @param amount Amount of the deposit
   */
  public void deposit(float amount) {
    balance+=amount;
  }

  /**
   * Returns the limit of the account.
   *
   * @return the limit
   */
  public float getLimit() { return limit; }

}
