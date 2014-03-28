package team.cs6365.payfive.database;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Serializer;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
	
	public void addTransaction(String recipient, String sender, boolean type, String description, String date, double amount, List<Item> items) 
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
				columns[0] + "='" + item.getRecipient().getName() + "' AND " + 
				columns[1] + "='" + item.getSender().getName() + "' AND " + 
				columns[2] + "=" + item.getSendType() + " AND " + 
				columns[3] + "='" + item.getDesc() + "' AND " + 
				columns[4] + "='" + item.getDate() + "' AND " + 
				columns[5] + "=" + item.getAmount(), null);
	}
	
	public Transaction getTransaction(String recipient, String sender, boolean type, String description, String date, double amount)
	{
		Transaction t = new Transaction();
		
		Cursor cur = db.query(TransactionDatabaseContract.TABLE_NAME, 
				columns,
				columns[0] + "='" + recipient + "' AND " + 
				columns[1] + "='" + sender + "' AND " + 
				columns[2] + "=" + type + " AND " + 
				columns[3] + "='" + description + "' AND " + 
				columns[4] + "='" + date + "' AND " + 
				columns[5] + "=" + amount, 
				null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			t = cursorToTransaction(cur);	
		}
		
		cur.close();
		return t;
	}
	
	public List<Transaction> getAllTransaction()
	{
		List<Transaction> ts = new ArrayList<Transaction>();
		
		Cursor cur = db.query(TransactionDatabaseContract.TABLE_NAME, 
				columns, null, null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			while(!cur.isAfterLast())
			{
				ts.add(cursorToTransaction(cur));
				cur.moveToNext();
			}
		}
		
		cur.close();
		return ts;
	}
	
	public Transaction cursorToTransaction(Cursor cur)
	{
		Transaction t = new Transaction(cur.getLong(0),
				(List<Item>)Serializer.deserialize(cur.getBlob(7)),
				new User(cur.getString(1)),
				new User(cur.getString(2)),	
				cur.getDouble(6),
				cur.getString(4),
				cur.getString(5),
				cur.getInt(3) == 1 ? true : false);
		return t;
	}
}
