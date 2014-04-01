package team.cs6365.payfive.database;

import java.util.ArrayList;

import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Serializer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MenuItemDataSource {
	private Context ctx;
	private SQLiteDatabase db;
	private MenuItemDatabaseHelper dbHelper;
	private String[] columns = { MenuItemDatabaseContract.COLUMN_NAME_NAME,
			MenuItemDatabaseContract.COLUMN_NAME_PRICE,
			MenuItemDatabaseContract.COLUMN_NAME_CATEGORY,
			MenuItemDatabaseContract.COLUMN_NAME_DESCRIPTION,
			MenuItemDatabaseContract.COLUMN_NAME_THUMBNAIL,
			MenuItemDatabaseContract.COLUMN_NAME_VISIBLE };

	private static final String TAG = "***MENUDS";

	public MenuItemDataSource(Context context) {
		ctx = context;
		dbHelper = new MenuItemDatabaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void drop() {
		if (db == null)
			Log.d(TAG, "null db");
		else
			Log.d(TAG, "good db");

		db.execSQL(MenuItemDatabaseHelper.SQL_DROP_MENUITEM_TABLE);
		Log.d(TAG, "dropped the DB");
	}

	public void create() {
		db.execSQL(MenuItemDatabaseHelper.SQL_CREATE_MENUITEM_TABLE);
		Log.d(TAG, "created the DB");
		Log.d(TAG, "current count: " + getAllMenuItems().size());
	}

	public void addMenuItem(String name, double price, String category,
			String description, byte[] thumbnailBytes) {
		ContentValues row = new ContentValues();
		row.put(columns[0], name);
		row.put(columns[1], price);
		row.put(columns[2], category);
		row.put(columns[3], description);
		row.put(columns[4], thumbnailBytes);
		row.put(columns[5], 0);
		db.insert(MenuItemDatabaseContract.TABLE_NAME, null, row);
	}

	public void deleteMenuItem(Item item) {
		db.delete(
				MenuItemDatabaseContract.TABLE_NAME,
				MenuItemDatabaseContract.COLUMN_NAME_NAME + "='"
						+ item.getName() + "' AND "
						+ MenuItemDatabaseContract.COLUMN_NAME_CATEGORY + "='"
						+ item.getCategory() + "'", null);
	}

	public void updateMenuItem(Item before, Item after) {
		ContentValues row = new ContentValues();
		row.put(columns[0], after.getName());
		row.put(columns[1], after.getPrice());
		row.put(columns[2], after.getCategory());
		row.put(columns[3], after.getDescription());
		row.put(columns[4], after.getThumbnailBytes());
		row.put(columns[5], after.isVisible() ? 1 : 0);
		db.update(
				MenuItemDatabaseContract.TABLE_NAME,
				row,
				MenuItemDatabaseContract.COLUMN_NAME_NAME + "='"
						+ before.getName() + "' AND "
						+ MenuItemDatabaseContract.COLUMN_NAME_PRICE + "="
						+ before.getPrice() + " AND "
						+ MenuItemDatabaseContract.COLUMN_NAME_CATEGORY + "='"
						+ before.getCategory() + "' AND "
						+ MenuItemDatabaseContract.COLUMN_NAME_DESCRIPTION
						+ "='" + before.getDescription() + "'", null);
	}

	public Item getMenuItem(String name, String category) {
		Item mi = new Item();

		Cursor cur = db.query(MenuItemDatabaseContract.TABLE_NAME, columns,
				MenuItemDatabaseContract.COLUMN_NAME_NAME + "='" + name
						+ "' AND "
						+ MenuItemDatabaseContract.COLUMN_NAME_CATEGORY + "='"
						+ category + "'", null, null, null, null);

		if (cur != null && cur.getCount() > 0) {
			cur.moveToFirst();
			mi = cursorToMenuItem(cur);
		}

		cur.close();
		return mi;
	}

	public ArrayList<Item> getAllMenuItems() {
		ArrayList<Item> mis = new ArrayList<Item>();

		Cursor cur = db.query(MenuItemDatabaseContract.TABLE_NAME, columns,
				null, null, null, null, null);

		if (cur != null && cur.getCount() > 0) {
			cur.moveToFirst();
			while (!cur.isAfterLast()) {
				mis.add(cursorToMenuItem(cur));
				cur.moveToNext();
			}
		}

		cur.close();
		return mis;
	}

	public ArrayList<Item> getAllVisibleMenuItemsOnly() {
		ArrayList<Item> mis = new ArrayList<Item>();

		/*
		 * String query = "SELECT * FROM " + MenuItemDatabaseContract.TABLE_NAME
		 * + " WHERE " + MenuItemDatabaseContract.COLUMN_NAME_VISIBLE + "=1";
		 * 
		 * Cursor cur = db.rawQuery(query, null);
		 */

		Cursor cur = db.query(MenuItemDatabaseContract.TABLE_NAME, columns,
				MenuItemDatabaseContract.COLUMN_NAME_VISIBLE + "=1", null,
				null, null, null);

		if (cur != null && cur.getCount() > 0) {
			Log.d(TAG, "has at least one visible item");
			cur.moveToFirst();
			while (!cur.isAfterLast()) {
				mis.add(cursorToMenuItem(cur));
				cur.moveToNext();
			}
		}

		cur.close();
		return mis;
	}

	public Item cursorToMenuItem(Cursor cur) {
		Item mi = new Item(cur.getString(0), cur.getDouble(1),
				cur.getString(2), cur.getString(3), null);

		Log.d(TAG, "-------------------------------");
		Log.d(TAG, "column count: " + cur.getColumnCount());
		Log.d(TAG, "name: " + cur.getString(0));
		Log.d(TAG, "price: " + cur.getDouble(1));
		Log.d(TAG, "category: " + cur.getString(2));
		Log.d(TAG, "description: " + cur.getString(3));

		for (int i = 0; i < cur.getColumnCount(); ++i) {
			Log.d(TAG, "column" + i + " - " + cur.getColumnName(i));
		}

//		byte[] img = cur.getBlob(4);
		// mi.setThumbnail(BitmapFactory.decodeByteArray(img, 0, img.length));
		mi.setThumbnailBytes(cur.getBlob(4));
		mi.setVisible(cur.getInt(5) == 0 ? false : true);

		// Log.d(TAG, "visible: " + cur.getInt(5));

		/*
		 * if(mi.getThumbnail() == null) Log.d(TAG, "bitmap: null"); else
		 * Log.d(TAG, "bitmap: not null");
		 */

		return mi;
	}
}
