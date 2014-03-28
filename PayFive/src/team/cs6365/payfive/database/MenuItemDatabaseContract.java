package team.cs6365.payfive.database;

import android.provider.BaseColumns;

public abstract class MenuItemDatabaseContract implements BaseColumns
{
    public static final String TABLE_NAME = "menuitem";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_CATEGORY = "category";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
    public static final String COLUMN_NAME_VISIBLE = "visible";
    
    private MenuItemDatabaseContract()
    {
    	
    }
}