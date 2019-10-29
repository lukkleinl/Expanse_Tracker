package accounts;

public class BankAccount extends Account {
  private String bankName;
  private float limit;

  public String getBankName() {
    return bankName;
  }

  public float getLimit() {
    return limit;
  }
}
