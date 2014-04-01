package team.cs6365.payfive.model;

import java.io.ByteArrayOutputStream;

import team.cs6365.payfive.util.ImageConversion;

import android.graphics.Bitmap;

/* model object for menu item */
public class Item {
	private String name, category, description;
	private double price;
	// private Bitmap thumbnail;
	private byte[] thumbnailBytes;
	private boolean visible;

	public Item() {
		this("", 0.0, "", "", null);
	}

	public Item(String name, double price, String category, String description,
			byte[] thumbnailBytes) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.thumbnailBytes = thumbnailBytes;
		this.visible = false;
	}

	public Item(String name, double price, String category, String description,
			Bitmap thumbnail, boolean visible) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.thumbnailBytes = thumbnailBytes;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public byte[] getThumbnailBytes() {
		return thumbnailBytes;
	}

	public void setThumbnailBytes(byte[] thumbnailBytes) {
		this.thumbnailBytes = thumbnailBytes;
	}

	public void setThumbnailBytes(Bitmap bmp) {
		this.thumbnailBytes = ImageConversion.compressBitmap(bmp);

	}

}
