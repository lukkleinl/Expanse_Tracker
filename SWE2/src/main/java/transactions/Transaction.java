package transactions;
import java.util.Date;

public abstract class Transaction {
	private String description;
	private float amount;
	private Date creationDate;
	
	public Transaction(Date creationDate, float amount, String description) {
		this.description = description;
		this.amount = amount;
		this.creationDate = creationDate;
	}
	
	public String getDescription() {
		return description;
	}
	public float getAmount() {
		return amount;
	}
	public Date getCreationDate() {
		return creationDate;
	}
}
