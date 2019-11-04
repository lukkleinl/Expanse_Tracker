package accounts;

/**
 * @author Patrick Gmasz
 */
public class DebitCard extends BankAccount {
  private String IBAN;

  /**
   * Creates a new DebitCard with a random generated IBAN, random generated account number and 0 balance.
   *
   * @param accountName The name of the account
   * @param bankName The name of the bank
   * @param limit The limit for overdrawing the bank account
   */
  public DebitCard(String accountName, String bankName, float limit,String IBAN) {
    super(accountName, bankName, limit);
    this.IBAN=IBAN;
    //this.IBAN = NumberGenerator.genenerateIBAN();
  }

  /**
   * Returns the IBAN of the DebitCard.
   *
   * @return the IBAN
   */
  public String getIBAN() {
    return IBAN;
  }
}