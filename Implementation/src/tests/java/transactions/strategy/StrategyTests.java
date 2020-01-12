package transactions.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import accounts.Account;
import accounts.Cash;
import exceptions.CustomException;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.CategoryStore;

class StrategyTests {
  private final float limit = -200f;
  private final float amountdepo = -this.limit / 2;
  private final float amountpay = -this.limit / 4;
  private final Transaction deposit = TransactionCreator.newTransaction("SALARY", this.amountdepo,
      "", new CategoryStore().withDefaultCategories());
  private final Transaction payout = TransactionCreator.newTransaction("FOOD", this.amountpay, "",
      new CategoryStore().withDefaultCategories());
  // since costs usually do not go lower than 0.1 cent (see fuel prices), checking with 0.01 cent is
  // good enough
  private final Transaction tooLargePayout = TransactionCreator.newTransaction("FOOD",
      (3 * this.amountdepo) + 0.0001f, "", new CategoryStore().withDefaultCategories());

  private Account acc;
  private BalanceChange change;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    this.acc = new Cash("Cash", this.limit, "Euro");
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void depositOfHundred_shouldChangeBalanceToHundred() throws RuntimeException, CustomException {
    this.change = new SimpleDeposit(this.deposit, this.acc);
    this.change.applyBalanceChange();
    assertEquals(this.amountdepo, this.acc.getBalance());
  }

  @Test
  void payoutOfHundred_shouldChangeBalanceToNegativeHundred()
      throws RuntimeException, CustomException {
    this.change = new SimplePayout(this.payout, this.acc);
    this.change.applyBalanceChange();
    assertEquals(-this.amountpay, this.acc.getBalance());
  }
}


