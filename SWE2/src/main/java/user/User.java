/**
 * 
 */
package user;

import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import accounts.Account;
import iteration.CustomList;
import transactions.DepositCategory;
import transactions.PayoutCategory;
import transactions.Transaction;

/**
 * @author Michael Watholowitsch
 */
public class User implements Observer {
	private String firstname;
	private String lastname;
	private int userID;
	private CustomList<Account> accounts;
	private Map<String,CustomList<Transaction>> transactions;
	
	/**
	 * Creates a new User without any Accounts or Transactions.
	 * @param userID The ID within the System.
	 * @param firstname The users first name.
	 * @param lastname The users last name.
	 */
	public User(int userID, String firstname, String lastname) {
		this.userID = userID;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	/**
	 * Adds a DebitCard Account to the List of this Users Accounts.
	 */
	public void addDebitCard() {
		// TODO
	}
	
	/**
	 * Adds a CrebitCard Account to the List of this Users Accounts.
	 */
	public void addCrebitCard() {
		// TODO
	}
	
	/**
	 * Adds a Stocks Account to the List of this Users Accounts.
	 */
	public void addStocks() {
		// TODO
	}
	
	/**
	 * Adds a Cash Account to the List of this Users Accounts.
	 */
	public void addCash() {
		// TODO
	}
	
	/**
	 * 
	 * @param category The Category of the Transaction
	 * @param amount The Amount of Money
	 * @param description The Description of the Transaction
	 */
	public void payOut(PayoutCategory category, float amount, String description) {
		// TODO
	}
	
	/**
	 * 
	 * @param category The Category of the Transaction
	 * @param amount The Amount of Money
	 * @param description The Description of the Transaction
	 */
	public void deposit(DepositCategory category, float amount, String description) {
		// TODO
	}	
	
	/**
	 * Updates the data of the User according to the input of the UserInterface.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns the Firstname.
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Returns the Lastname.
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Returns the userID.
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Returns a CustomList of this Users' Accounts.
	 * @return A CustomList of this Users' Accounts
	 */
	public CustomList<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Returns a Map of all the Transactions by the User.
	 * @return A Map of all the Transactions by the User
	 */
	public Map<String, CustomList<Transaction>> getTransactions() {
		return transactions;
	}

}

