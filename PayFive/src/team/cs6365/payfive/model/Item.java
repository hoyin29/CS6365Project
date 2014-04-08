package team.cs6365.payfive.model;

<<<<<<< HEAD
import java.io.Serializable;
=======
import java.io.ByteArrayOutputStream;

import team.cs6365.payfive.util.ImageConversion;

import android.graphics.Bitmap;
>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09

/* model object for menu item */
public class Item implements Serializable{
	private String name, category, description;
	private double price;
<<<<<<< HEAD
	private String thumbnail;
=======
	// private Bitmap thumbnail;
	private byte[] thumbnailBytes;
>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09
	private boolean visible;

	public Item() {
		this("", 0.0, "", "", "");
	}
<<<<<<< HEAD
	
	public Item(String name, double price, String category, String description, String thumbnail) {
		this(name, price, category, description, thumbnail, false);
	}
	
	public Item(String name, double price, String category, String description, String thumbnail, boolean visible) {
=======

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
>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09
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

<<<<<<< HEAD
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

=======
>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09
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
