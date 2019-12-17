import transactions.Deposit;
import transactions.Payout;
import transactions.Transaction;
import transactions.TransactionCreator;
import transactions.categories.CategoryStore;
import transactions.categories.DepositCategory;
import transactions.categories.PayoutCategory;

public class NonJUnitTesting {

  public static void main(final String[] args) {
    timeTesting();
  }

  private static void timeTesting() {
    Transaction t = TransactionCreator.newTransactionWith("FOOD", 50, "McD", new CategoryStore());
    System.out.println("Stored Creation Date: " + t.getCreationDate());
    System.out.println("Stored Creation Date after formatting: " + t.getFormattedCreationDate());
    System.out.println("Is Deposit: " + (t instanceof Deposit));
    System.out.println("Is Payout: " + (t instanceof Payout));
  }

  private static void storeTesting() {
    CategoryStore cs = new CategoryStore();
    System.out.println(cs.getCategories(null));
    cs.addTransactionCategory(new PayoutCategory("Sprit"));
    cs.addTransactionCategory(new PayoutCategory("Coiffeur"));
    cs.addTransactionCategory(new DepositCategory("Pocketmoney"));
    cs.addTransactionCategory(new PayoutCategory("IceSkating"));
    cs.addTransactionCategory(new PayoutCategory("Oktoberfest"));
    System.out.println(cs.getCategories(null));
  }
}
