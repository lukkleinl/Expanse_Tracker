package user;

import accounts.Account;

public interface AddTransaction {
  void add(String Category, float amount, String description, Account acc);

}
