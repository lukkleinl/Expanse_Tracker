package transactions.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import accounts.Account;
import accounts.Cash;
import exceptions.SWE_Exception;
import exceptions.SWE_RuntimeException;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.CategoryStore;

class StrategyTests {
  private final float limit = -200f;
  private final float amountdepo = -limit/2;
  private final float amountpay = -limit/4;
  private final Transaction deposit = TransactionCreator.newTransaction("SALARY", amountdepo, "", new CategoryStore().withDefaultCategories());
  private final Transaction payout = TransactionCreator.newTransaction("FOOD", amountpay, "", new CategoryStore().withDefaultCategories());
  // since costs usually do not go lower than 0.1 cent (see fuel prices), checking with 0.01 cent is good enough
  private final Transaction tooLargePayout = TransactionCreator.newTransaction("FOOD", 3*amountdepo + 0.0001f, "", new CategoryStore().withDefaultCategories());

  private Account acc;
  private BalanceChange change;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    acc = new Cash("Cash", limit, "Euro");
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void depositOfHundred_shouldChangeBalanceToHundred() throws SWE_RuntimeException, SWE_Exception {
    change = new SimpleDeposit(deposit,acc);
    change.applyBalanceChange();
    assertEquals(amountdepo,acc.getBalance());
  }

  @Test
  void payoutOfHundred_shouldChangeBalanceToNegativeHundred() throws SWE_RuntimeException, SWE_Exception {
    change = new SimplePayout(payout,acc);
    change.applyBalanceChange();
    assertEquals(-amountpay,acc.getBalance());
  }

  @Test
  void payoutExceedingLimit_shouldThrowException() {
    change = new SimplePayout(tooLargePayout,acc);
    assertThrows(SWE_Exception.class, () -> { change.applyBalanceChange(); });
  }
}








