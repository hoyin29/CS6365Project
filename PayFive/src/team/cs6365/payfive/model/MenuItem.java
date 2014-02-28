package team.cs6365.payfive.model;

import android.graphics.Bitmap;

/* model object for menu item */
public class MenuItem {
	private String name = "";
	private double price = 0.0;
	private Bitmap thumbnail = null;

	public MenuItem() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

}
