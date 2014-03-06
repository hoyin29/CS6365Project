package team.cs6365.payfive.database;

import android.provider.BaseColumns;

public class TransactionDatabaseContract implements BaseColumns
{
    public static final String TABLE_NAME = "transaction";
    public static final String COLUMN_NAME_SELLER = "seller";
    public static final String COLUMN_NAME_BUYER = "buyer";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_COST = "cost";
    public static final String COLUMN_NAME_ITEM = "item";
    
    public TransactionDatabaseContract()
    {
    	
    }
}
