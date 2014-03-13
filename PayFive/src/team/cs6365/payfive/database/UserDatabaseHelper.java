package team.cs6365.payfive.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "user.db";

	public static final String SQL_CREATE_USER_TABLE = 
			"CREATE TABLE " +  UserDatabaseContract.TABLE_NAME + " (" + 
					UserDatabaseContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					UserDatabaseContract.COLUMN_NAME_NAME + " TEXT NOT NULL, " +  
					UserDatabaseContract.COLUMN_NAME_PAYPALID + " TEXT NOT NULL)";

	public static final String SQL_DROP_USER_TABLE = 
			"DROP TABLE IF EXISTS " + UserDatabaseContract.TABLE_NAME;

	public UserDatabaseHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(SQL_CREATE_USER_TABLE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL(SQL_DROP_USER_TABLE);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		onUpgrade(db, oldVersion, newVersion);
	}
}
