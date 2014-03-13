package team.cs6365.payfive.database;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {
	
	private SQLiteDatabase db;
	private UserDatabaseHelper dbHelper;
	private String[] columns = {UserDatabaseContract.COLUMN_NAME_NAME,
			UserDatabaseContract.COLUMN_NAME_PAYPALID};
	
	public UserDataSource(Context context)
	{
		dbHelper = new UserDatabaseHelper(context);
	}
	
	public void open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() 
	{
		dbHelper.close();
	}
	
	public void addUser(String name, String paypalId) 
	{
		ContentValues row = new ContentValues();
		row.put(columns[0], name);
		row.put(columns[1], paypalId);
		db.insert(UserDatabaseContract.TABLE_NAME, null, row);
	}
	
	public void deleteUser(User user)
	{
		db.delete(UserDatabaseContract.TABLE_NAME, 
				UserDatabaseContract.COLUMN_NAME_NAME + "='" + user.getName() + "' AND " + 
				UserDatabaseContract.COLUMN_NAME_PAYPALID + "='" + user.getPaypalId(), null);
	}
	
	public User getUser(String name, String paypalId)
	{
		User u = new User();
		
		Cursor cur = db.query(UserDatabaseContract.TABLE_NAME, 
				columns,
				UserDatabaseContract.COLUMN_NAME_NAME + "='" + name + "' AND " + 
				UserDatabaseContract.COLUMN_NAME_PAYPALID + "='" + paypalId + "'", 
				null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			u = cursorToUser(cur);	
		}
		
		cur.close();
		return u;
	}
	
	public List<User> getAllUser()
	{
		List<User> us = new ArrayList<User>();
		
		Cursor cur = db.query(UserDatabaseContract.TABLE_NAME, 
				columns, null, null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			while(!cur.isAfterLast())
			{
				us.add(cursorToUser(cur));
				cur.moveToNext();
			}
		}
		
		cur.close();
		return us;
	}
	
	public User cursorToUser(Cursor cur)
	{
		User u = new User(cur.getString(1),
				cur.getString(2));
		
		return u;
	}
}
