package team.cs6365.payfive.database;

import team.cs6365.payfive.model.MenuItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

public class MenuItemDataSource 
{
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	private String[] columns = {DatabaseContract.COLUMN_NAME_NAME,
			DatabaseContract.COLUMN_NAME_PRICE,
			DatabaseContract.COLUMN_NAME_CATEGORY,
			DatabaseContract.COLUMN_NAME_DESCRIPTION,
			DatabaseContract.COLUMN_NAME_THUMBNAIL};
	
	public MenuItemDataSource(Context context)
	{
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() 
	{
		dbHelper.close();
	}
	
	public void addMenuItem(String name, String category, String description, double price, byte[] thumbnail) 
	{
		ContentValues row = new ContentValues();
		row.put(columns[0], name);
		row.put(columns[1], category);
		row.put(columns[2], description);
		row.put(columns[3], price);
		row.put(columns[4], thumbnail);
		db.insert(DatabaseContract.TABLE_NAME, null, row);
	}
	
	public void deleteMenuItem(MenuItem item)
	{
		db.delete(DatabaseContract.TABLE_NAME, 
				DatabaseContract.COLUMN_NAME_NAME + "='" + item.getName() + "' AND " + 
				DatabaseContract.COLUMN_NAME_CATEGORY + "='" + item.getCategory() + "'",
				null);
	}
	
	public MenuItem getMenuItem(String name, String category)
	{
		MenuItem mi = null;
		Cursor cur = db.query(DatabaseContract.TABLE_NAME, 
				columns,
				DatabaseContract.COLUMN_NAME_NAME + "='" + name + "' AND " + 
				DatabaseContract.COLUMN_NAME_CATEGORY + "='" + category + "'", 
				null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			mi = cursorToMenuItem(cur);	
		}
		cur.close();
		return mi;
	}
	
	public MenuItem cursorToMenuItem(Cursor cur)
	{
		MenuItem mi = new MenuItem(cur.getString(1),
				cur.getString(2),	
				cur.getString(3),
				cur.getDouble(4),
				null);
		
		byte[] img = cur.getBlob(5);
		mi.setThumbnail(BitmapFactory.decodeByteArray(img, 0, img.length));
		return mi;
	}
}
