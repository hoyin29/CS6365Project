package team.cs6365.payfive.database;

import android.provider.BaseColumns;

public class TransactionDatabaseContract implements BaseColumns
{
    public static final String TABLE_NAME = "transaction";
    public static final String COLUMN_NAME_RECIPIENT = "recipient";
    public static final String COLUMN_NAME_SENDER = "sender";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_AMOUNT = "amount";
    public static final String COLUMN_NAME_ITEM = "item";
    
    public TransactionDatabaseContract()
    {
    	
    }
}
