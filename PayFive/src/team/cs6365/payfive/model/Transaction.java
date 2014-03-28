package team.cs6365.payfive.model;

import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private List<Item> items;
	private User recipient;
	private User sender;
	private double amount;
	private String date, desc;
	private boolean sendType = true; // true for send, false for receive of
										// payment

	public Transaction() {
		this(0, null, null, null, 0.0, null, null, true);
	}

	public Transaction(long id, List<Item> items, User recipient,
			User sender, double amount, String date, String desc,
			boolean sendType) {
		this.id = id;
		this.items = items;
		this.recipient = recipient;
		this.sender = sender;
		this.amount = amount;
		this.date = date;
		this.desc = desc;
		this.sendType = sendType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return desc;
	}

	public void setTitle(String title) {
		this.desc = title;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public boolean isSendType() {
		return sendType;
	}

	public int getSendType() {
		return sendType ? 1 : 0; 
	}
	
	public void setSendType(boolean sendType) {
		this.sendType = sendType;
	}

}
