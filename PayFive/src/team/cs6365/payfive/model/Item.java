package team.cs6365.payfive.model;

import java.io.Serializable;

/* model object for menu item */
public class Item implements Serializable {
	private String name, category, description;
	private double price;
	private String thumbnail = "";
	// private Bitmap thumbnail;
	private boolean visible;

	public Item() {
		this("", 0.0, "", "", "");
	}

	public Item(String name, double price, String category, String description,
			String thumbnail) {
		this(name, price, category, description, thumbnail, false);
	}

	public Item(String name, double price, String category, String description,
			String thumbnail, boolean visible) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.visible = visible;
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
