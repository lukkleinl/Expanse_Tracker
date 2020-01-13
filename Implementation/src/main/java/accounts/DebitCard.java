package accounts;

/**
 * @author Patrick Gmasz
 */
public class DebitCard extends BankAccount {
  private final String IBAN;

  /**
   * Creates a new DebitCard with a random generated IBAN, random generated account number and 0
   * balance.
   *
   * @param accountName The name of the account
   * @param bankName The name of the bank
   * @param limit The limit for overdrawing the bank account
   * @param IBAN the IBAN of the account
   */
  public DebitCard(final String accountName, final String bankName, final float limit,
      final String IBAN) {
    super(accountName, bankName, limit);
    this.IBAN = IBAN;
  }

  public DebitCard(final String accountName, final String bankName, final float limit,
      final String IBAN, final Integer ID, final float balance) {
    super(accountName, bankName, limit, ID, balance);
    this.IBAN = IBAN;
  }


  /**
   * Returns the IBAN of the DebitCard.
   *
   * @return the IBAN
   */
  public String getIBAN() {
    return this.IBAN;
  }

  @Override
  public String toString() {
    return "DEBITCARD";
  }
}
