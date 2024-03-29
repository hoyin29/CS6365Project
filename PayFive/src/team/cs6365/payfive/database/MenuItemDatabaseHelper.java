package team.cs6365.payfive.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MenuItemDatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "menuitem.db";
    
    public static final String SQL_CREATE_MENUITEM_TABLE = 
    		"CREATE TABLE " +  MenuItemDatabaseContract.TABLE_NAME + " (" + 
    		MenuItemDatabaseContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		MenuItemDatabaseContract.COLUMN_NAME_NAME + " TEXT NOT NULL, " +  
    		MenuItemDatabaseContract.COLUMN_NAME_PRICE + " REAL NOT NULL, " +
    		MenuItemDatabaseContract.COLUMN_NAME_CATEGORY + " TEXT NOT NULL, " + 
    		MenuItemDatabaseContract.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " + 
    		MenuItemDatabaseContract.COLUMN_NAME_THUMBNAIL + " TEXT NOT NULL, " + 
    		MenuItemDatabaseContract.COLUMN_NAME_VISIBLE + " INTEGER NOT NULL)";
    
    public static final String SQL_DROP_MENUITEM_TABLE = 
    		 "DROP TABLE IF EXISTS " + MenuItemDatabaseContract.TABLE_NAME;
    
    public MenuItemDatabaseHelper(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) 
    {
        db.execSQL(SQL_CREATE_MENUITEM_TABLE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        db.execSQL(SQL_DROP_MENUITEM_TABLE);
        onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
