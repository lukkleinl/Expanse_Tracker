package user;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import accounts.Account;
import iteration.CustomList;
import transactions.Transaction;

public class User implements Observer {
	private String firstname;
	private String lastname;
	private int userID;
	private CustomList<Account> accounts;
	private Map<String,CustomList<Transaction>> transactions;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public int getUserID() {
		return userID;
	}

	public CustomList<Account> getAccounts() {
		return accounts;
	}

	public Map<String, CustomList<Transaction>> getTransactions() {
		return transactions;
	}
}
