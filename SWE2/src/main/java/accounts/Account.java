package accounts;

/** @author Patrick Gmasz */
public abstract class Account {
  private String account_number;
  private String name;
  private float balance;

  /**
   * Creates a new Account with a random generated account number and a balance of 0.
   *
   * @param name The name of the account
   */
  public Account(String name) {
    this.account_number = NumberGenerator.generateAccountNumber();
    this.name = name;
    this.balance = 0;
  }

  /**
   * Returns the account number.
   *
   * @return the account number
   */
  public String getAccount_number() {
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
}
