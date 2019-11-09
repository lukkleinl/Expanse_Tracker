package accounts;

public class BankAccount extends Account {
  private String bankName;
  //private float limit;

  /**
   * Creates a bank account.
   *
   * @param accountName The name for the account
   * @param bankName The name of the bank
   * @param limit The limit for overdrawing the bank account
   */
  public BankAccount(String accountName, String bankName, float limit) {
    super(accountName,limit);
    this.bankName = bankName;
    //this.limit = limit;
  }

  /**
   * Returns the name of the bank.
   *
   * @return the bank name
   */
  public String getBankName() {
    return bankName;
  }

  /**
   * Returns the limit for overdrawing the bank account.
   *
   * @return the limit
   */
  //public float getLimit() { return limit; }

  @Override
  public String toString()
  {
    return "BANKACCOUNT";
  }
}
