package team.cs6365.payfive.model;

import java.util.List;

public class Transaction {
	private long id;
	private List<MenuItem> items;
	private User seller;
	private List<User> buyers;
	private double cost;
	private String date, title;
	
	public Transaction() {
		this(0, null, null, null, 0.0, null, null);
	}
	
	public Transaction(long id, List<MenuItem> items, User seller, List<User> buyers, double cost, String date, String title) {
		this.id = id;
		this.items = items;
		this.seller = seller;
		this.buyers = buyers;
		this.cost = cost;
		this.date = date;
		this.title = title;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public void setItems(List<MenuItem> items) {
		this.items = items;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public List<User> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<User> buyers) {
		this.buyers = buyers;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
