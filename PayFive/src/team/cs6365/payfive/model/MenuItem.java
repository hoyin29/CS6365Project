package team.cs6365.payfive.model;

import android.graphics.Bitmap;

/* model object for menu item */
public class MenuItem {
	private String name, category, description;
	private double price;
	private Bitmap thumbnail;

	public MenuItem() {
		this.name = "";
		this.category = "";
		this.description = "";
		this.price = 0.0;
		this.thumbnail = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
