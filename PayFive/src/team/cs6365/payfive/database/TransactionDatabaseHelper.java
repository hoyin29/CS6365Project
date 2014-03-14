package team.cs6365.payfive.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionDatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "transaction.db";
    
    public static final String SQL_CREATE_TRANSACTION_TABLE = 
    		"CREATE TABLE " +  TransactionDatabaseContract.TABLE_NAME + " (" + 
    		TransactionDatabaseContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		TransactionDatabaseContract.COLUMN_NAME_RECIPIENT + " TEXT NOT NULL, " +  
    		TransactionDatabaseContract.COLUMN_NAME_SENDER + " TEXT NOT NULL, " + 
    		TransactionDatabaseContract.COLUMN_NAME_TYPE + " INTEGER NOT NULL, " +
    		TransactionDatabaseContract.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
    		TransactionDatabaseContract.COLUMN_NAME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, " + 
    		TransactionDatabaseContract.COLUMN_NAME_AMOUNT + " REAL NOT NULL, " +
    		TransactionDatabaseContract.COLUMN_NAME_ITEM + " BLOB NOT NULL)";
    
    public static final String SQL_DROP_TRANSACTION_TABLE = 
    		 "DROP TABLE IF EXISTS " + TransactionDatabaseContract.TABLE_NAME;
    
    public TransactionDatabaseHelper(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) 
    {
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        db.execSQL(SQL_DROP_TRANSACTION_TABLE);
        onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
