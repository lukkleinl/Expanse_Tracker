package Stratey_test;

import static transactions.DepositCategory.SALARY;
import static transactions.PayoutCategory.FOOD;

import accounts.Account;
import accounts.Stocks;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import swe_IteratorPattern.CustomContainer;
import swe_IteratorPattern.CustomIterator;
import swe_IteratorPattern.CustomList;
import transactions.Deposit;
import transactions.DepositCategory;
import transactions.Payout;
import transactions.PayoutCategory;
import transactions.Transaction;
import user.User;

public class strategy_test {

  public static void main(String[] args) {
    Date date = new Date();
    Account account = new Stocks("ad", date, 0);
    User user = new User(1, "aav", "vf", "asd");
    user.addAccount(account);
    DepositCategory cat = SALARY;
    Deposit trans = new Deposit(date, 1000, cat, "avfbbs");
    PayoutCategory pay=FOOD;
    Payout payout=new Payout(date,100,pay,"sfv");

    user.getAccounts().getIterator();
    user.addTransaction(trans, account);

    Map<Integer, CustomContainer<Transaction>> map;
    map = user.getTransactions();

    CustomIterator<Account> abc = user.getAccounts().getIterator();
    while (abc.hasNext()) {
      System.out.println(abc.next().getBalance());
    }
    for (
        Entry e : map.entrySet()) {
      System.out.println(e.getKey());
    }
    System.out.println(map.size());

    user.addTransaction(payout, account);

    abc = user.getAccounts().getIterator();
    while (abc.hasNext()) {
      System.out.println(abc.next().getBalance());
    }

    map = user.getTransactions();
    CustomList<Transaction> list=new CustomList<>();
    for (
        Entry e : map.entrySet()) {
          list=(CustomList) e.getValue();
    }
    System.out.println(list.size());
  }
}
