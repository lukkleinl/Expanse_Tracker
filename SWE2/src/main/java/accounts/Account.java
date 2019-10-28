package accounts;

public abstract class Account {
	private String account_number;
	private String name;
	private float balance;
	
	public String getAccount_number() {
		return account_number;
	}
	public String getName() {
		return name;
	}
	public float getBalance() {
		return balance;
	}
}
