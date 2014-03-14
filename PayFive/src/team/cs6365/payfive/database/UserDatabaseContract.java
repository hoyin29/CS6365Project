package team.cs6365.payfive.database;

import android.provider.BaseColumns;

public abstract class UserDatabaseContract implements BaseColumns {
	
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PAYPALID = "paypalid";
    
    private UserDatabaseContract()
    {
    	
    }
}
