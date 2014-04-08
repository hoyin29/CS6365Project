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
import android.util.Log;

public class TransactionDataSource 
{
	private static final String TAG = "***TRANSDS";
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
	
	public void drop() 
	{
		if(db == null) 
			Log.d(TAG, "null db");
		else
			Log.d(TAG, "good db");
		
		db.execSQL(TransactionDatabaseHelper.SQL_DROP_TRANSACTION_TABLE);
		Log.d(TAG, "dropped the DB");
	}
	
	public void create() 
	{
		db.execSQL(TransactionDatabaseHelper.SQL_CREATE_TRANSACTION_TABLE);
		Log.d(TAG, "created the DB");
		Log.d(TAG, "current count: " + getAllTransactions().size());
	}
	
	public void open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() 
	{
		dbHelper.close();
	}
	
	public void addTransaction(Transaction t) {
		addTransaction(t.getRecipient().getName(),
				t.getSender().getName(),
				t.getSendType(),
				t.getDesc(),
				t.getDate(),
				t.getAmount(),
				t.getItems());
	}
	
	public void addTransaction(String recipient, String sender, int type, String description, String date, double amount, List<Item> items) 
	{
		ContentValues row = new ContentValues();
		row.put(columns[0], recipient);
		row.put(columns[1], sender);
		row.put(columns[2], type);
		row.put(columns[3], description);
		//row.put(columns[4], date); // db generate timestamp
		row.put(columns[5], formatPrice(amount));
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
				columns[5] + "=" + formatPrice(item.getAmount()), null);
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
				columns[5] + "=" + formatPrice(amount), 
				null, null, null, null);
		
		if(cur != null && cur.getCount() > 0)
		{
			cur.moveToFirst();
			t = cursorToTransaction(cur);	
		}
		
		cur.close();
		return t;
	}
	
	public List<Transaction> getAllTransactions()
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
		Log.d(TAG, "column count: " + cur.getColumnCount());
		/*
		for(int i = 0; i < cur.getColumnCount(); ++i) {
			Log.d(TAG, "column" + i + " - " + cur.getColumnName(i));
		}
		*/
		Transaction t = new Transaction(0,
				(List<Item>)Serializer.deserialize(cur.getBlob(6)),
				new User(cur.getString(0)),
				new User(cur.getString(1)),	
				cur.getDouble(5),
				cur.getString(4),
				cur.getString(3),
				cur.getInt(2) == 1 ? true : false);
		
		Log.d(TAG, "date: " + t.getDesc());
		Log.d(TAG, "date: " + t.getDate());
		Log.d(TAG, "item: " + t.getItems().get(0).getName());
		
		return t;
	}
	
	protected double formatPrice(double d) {
		return Math.round(d * 100.0) / 100.0;
	}
}
