package transactions;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Transaction {
  private String description;
  private float amount;
  private Date creationDate;
  private int ID;
  private static AtomicInteger nextId = new AtomicInteger();
  private int id;

  public Transaction(Date creationDate, float amount, String description) {
    id = nextId.incrementAndGet();
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

  public int getID()  { return id;}
}
