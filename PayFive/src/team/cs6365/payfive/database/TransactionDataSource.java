package team.cs6365.payfive.database;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.model.MenuItem;
import team.cs6365.payfive.model.Serializer;
import team.cs6365.payfive.model.Transaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

public class TransactionDataSource 
{
	private SQLiteDatabase db;
	private TransactionDatabaseHelper dbHelper;
	private String[] columns = {TransactionDatabaseContract.COLUMN_NAME_RECIPIENT,
			TransactionDatabaseContract.COLUMN_NAME_SENDER,
			TransactionDatabaseContract.COLUMN_NAME_TYPE,
			TransactionDatabaseContract.COLUMN_NAME_DESCRIPTION,
			TransactionDatabaseContract.COLUMN_NAME_DATE,
			TransactionDatabaseContract.COLUMN_NAME_AMOUNT,
			TransactionDatabaseContract.COLUMN_NAME_ITEM};
	
	public TransactionDataSource(Context context)
	{
		dbHelper = new TransactionDatabaseHelper(context);
	}
	
	public void open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() 
	{
		dbHelper.close();
	}
	
	public void addTransaction(String recipient, String sender, boolean type, String description, String date, double amount, List<MenuItem> items) 
	{
		ContentValues row = new ContentValues();
		row.put(columns[0], recipient);
		row.put(columns[1], sender);
		
		if(type)
			row.put(columns[2], 1);
		else 
			row.put(columns[2], 0);
		
		row.put(columns[3], description);
		row.put(columns[4], date);
		row.put(columns[5], amount);
		row.put(columns[6], Serializer.serialize(items));  // blob??
	
		db.insert(TransactionDatabaseContract.TABLE_NAME, null, row);
	}
	
	public void deleteTransaction(Transaction item)
	{
		db.delete(TransactionDatabaseContract.TABLE_NAME, 
				columns[0] + "='" + item.getRecipient() + "' AND " + 
				columns[1] + "='" + item.getSender() + "' AND " + 
				columns[2] + "=" + item.getSendType() + " AND " + 
				columns[3] + "='" + item.getDesc() + "' AND " + 
				columns[4] + "='" + item.getDate() + "' AND " + 
				columns[5] + "=" + item.getAmount(), null);
	}
	
	public Transaction getTransaction(String name, String category)
	{
		Transaction mi = new Transaction();
		
		Cursor cur = db.query(TransactionDatabaseContract.TABLE_NAME, 
				columns,
				TransactionDatabaseContract.COLUMN_NAME_NAME + "='" + name + "' AND " + 
				TransactionDatabaseContract.COLUMN_NAME_CATEGORY + "='" + category + "'", 
				null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			mi = cursorToTransaction(cur);	
		}
		
		cur.close();
		return mi;
	}
	
	public List<Transaction> getAllTransaction()
	{
		List<Transaction> mis = new ArrayList<Transaction>();
		
		Cursor cur = db.query(TransactionDatabaseContract.TABLE_NAME, 
				columns, null, null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			while(!cur.isAfterLast())
			{
				mis.add(cursorToTransaction(cur));
				cur.moveToNext();
			}
		}
		
		cur.close();
		return mis;
	}
	
	public Transaction cursorToTransaction(Cursor cur)
	{
		Transaction mi = new Transaction(cur.getString(1),
				cur.getString(2),	
				cur.getString(3),
				cur.getDouble(4),
				null);
		
		byte[] img = cur.getBlob(5);
		mi.setThumbnail(BitmapFactory.decodeByteArray(img, 0, img.length));
		return mi;
	}
}
