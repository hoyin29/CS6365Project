package team.cs6365.payfive.model;

import java.util.ArrayList;

public class Cart {

	private ArrayList<Item> cart;
	private int balance;

	public Cart() {
		this(new ArrayList<Item>());
	}

	public Cart(ArrayList<Item> cart) {
		this.cart = cart;
		balance = 0;
	}

	public int size() {
		return cart.size();
	}

	public boolean isEmpty() {
		return cart.isEmpty();
	}

	public ArrayList<Item> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Item> cart) {
		this.cart = cart;
	}
}
