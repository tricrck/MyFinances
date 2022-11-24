package com.example.myfinances;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;

public // Database connection helper class
class DatabaseHandler extends SQLiteOpenHelper
{  
    private static final int DATABASE_VERSION = 1;  
    private static final String DATABASE_NAME = "MyFinances";  
    private static final String TABLE_CDs = "CDs"; 
	private static final String TABLE_LOANS = "Loans";
	private static final String TABLE_CHECKINGS_ACC = "Cacc";

    public DatabaseHandler(Context context)
	{  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }

    // Creating Tables  
    @Override  
    public void onCreate(SQLiteDatabase db)
	{  
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CDs + "(id INT NOT NULL PRIMARY KEY, Account_Number INT NOT NULL, Current_Balance DOUBLE NOT NULL, Initial_Balance DOUBLE NOT NULL, Interest_Rate DOUBLE NOT NULL );");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LOANS + "(id INT NOT NULL PRIMARY KEY, Account_Number INT NOT NULL, Current_Balance DOUBLE NOT NULL, Initial_Balance DOUBLE NOT NULL, Interest_Rate DOUBLE NOT NULL, Payment_Amount DOUBLE NOT NULL );"); 
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CHECKINGS_ACC + "(id INT NOT NULL PRIMARY KEY, Account_Number INT NOT NULL, Current_Balance DOUBLE NOT NULL );");
    }  

    // Upgrading database  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{  
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CDs + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKINGS_ACC+";");
        // Create tables again  
        onCreate(db);  
    }  

    // code to add the new cds
    long addCDs(int acc_no, double initial_bal, double curr_bal, double interest)
	{  
        SQLiteDatabase db = this.getWritableDatabase();  
        ContentValues values = new ContentValues();  
        values.put("Account_Number", acc_no);
		values.put("Current_Balance", curr_bal);
		values.put("Initial_Balance", initial_bal);
		values.put("Interest_Rate", interest);
		long out = 0;
        // Inserting Row  
		// It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
			// Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            out = db.insertOrThrow(TABLE_CDs, null, values);
            db.execSQL("INSERT INTO TABLE_LOANS('Account Number','Current Balance','Initial Balance','Interest Rate') VALUES("+
					   acc_no+","+curr_bal+","+initial_bal+","+interest+");");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // "Error while trying to add post to database"
			out = -1;
        } finally {
            db.endTransaction();
        }
			
        return out; //db.insert(TABLE_CDs, null, values);   
    }

	// code to add the new cds
    long addLoans(int acc_no, double initial_bal, double curr_bal, double payment_amount, double interest)
	{  
        SQLiteDatabase db = this.getWritableDatabase();  
        /*
		ContentValues values = new ContentValues();  
        values.put("Account_Number", acc_no);
		values.put("Current_Balance", curr_bal);
		values.put("Initial_Balance", initial_bal);
		values.put("Interest_Rate", interest);
		values.put("Payment_Amount", payment_amount);
		*/
        // Inserting Row  
        long out = 0;
        // Inserting Row  
		// It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
			// Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            // out = db.insertOrThrow(TABLE_LOANS, null, values);
			db.execSQL("INSERT INTO TABLE_LOANS(Account Number,Current Balance,Initial Balance,Interest Rate,Payment Amount) VALUES("+
			acc_no+","+curr_bal+","+initial_bal+","+interest+","+payment_amount+");");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // "Error while trying to add post to database"
			out = -1;
        } finally {
            db.endTransaction();
        }

        return out;
	}

	// code to add the new cds
    long addCacc(int acc_no, double curr_bal)
	{  
        SQLiteDatabase db = this.getWritableDatabase();  
        ContentValues values = new ContentValues();  
        values.put("Account_Number", acc_no);
		values.put("Current_Balance", curr_bal);
        // Inserting Row  

        long out = 0;
        // Inserting Row  
		// It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
			// Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            // out = db.insertOrThrow(TABLE_CHECKINGS_ACC, null, values);
            db.execSQL("INSERT INTO TABLE_LOANS(Account Number,Current Balance) VALUES("+
					   acc_no+","+curr_bal+");");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // "Error while trying to add post to database"
			out = -1;
        } finally {
            db.endTransaction();
        }

        return out;
	}

	@SuppressLint("Range")
	public ArrayList<HashMap<String, String>> getCheckingAcc()
	{
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT Account_Number,Current_Balance FROM " + TABLE_CHECKINGS_ACC + ";";
        Cursor cursor = db.rawQuery(query, null);
		try{
        	while (cursor.moveToNext())
			{
            	HashMap<String,String> user = new HashMap<>();
            	user.put("Account Number", cursor.getString(cursor.getColumnIndex("Account_Number")));
            	user.put("Current Balance", cursor.getString(cursor.getColumnIndex("Current_Balance")));
            	userList.add(user);
        	}
		} catch (Exception e) {
            
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
		}
        return  userList;
    }

	@SuppressLint("Range")
	public ArrayList<HashMap<String, String>> getLoansAcc()
	{
		SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT Account_Number,Current_Balance,Initial_Balance,Payment_Amount,Interest_Rate FROM " + TABLE_LOANS + ";";
        Cursor cursor = db.rawQuery(query, null);
		try{
        	while (cursor.moveToNext())
			{
            	HashMap<String,String> user = new HashMap<>();
            	user.put("Account Number", cursor.getString(cursor.getColumnIndex("Account_Number")));
            	user.put("Current Balance", cursor.getString(cursor.getColumnIndex("Current_Balance")));
				user.put("Initial Balance", cursor.getString(cursor.getColumnIndex("Initial_Balance")));
				user.put("Payment_Amount", cursor.getString(cursor.getColumnIndex("Payment_Amount")));
				user.put("Interest Rate", cursor.getString(cursor.getColumnIndex("Interest_Rate")));
            	userList.add(user);
        	}
		} catch (Exception e) {

		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
        return  userList;
	}

	@SuppressLint("Range")
	public ArrayList<HashMap<String, String>> getCDsAcc()
	{
		SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT Account_Number,Current_Balance,Initial_Balance,Interest_Rate FROM " + TABLE_CDs + ";";
        Cursor cursor = db.rawQuery(query, null);
		try{
        	while (cursor.moveToNext())
			{
            	HashMap<String,String> user = new HashMap<>();
            	user.put("Account Number", cursor.getString(cursor.getColumnIndex("Account_Number")));
            	user.put("Current Balance", cursor.getString(cursor.getColumnIndex("Current_Balance")));
				user.put("Initial Balance", cursor.getString(cursor.getColumnIndex("Initial_Balance")));
				user.put("Interest Rate", cursor.getString(cursor.getColumnIndex("Interest_Rate")));
            	userList.add(user);
        	}
		} catch (Exception e) {

		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
        return  userList;
	}  
	
	 // Delete CDs User Details
	 public void DeleteCDsUser(int userid){
	 SQLiteDatabase db = this.getWritableDatabase();
	 db.delete(TABLE_CDs, "id = ?",new String[]{String.valueOf(userid)});
	 db.close();
	 }
	 
	// Delete Loans User Details
	public void DeleteLoansUser(int userid){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LOANS, "id = ?",new String[]{String.valueOf(userid)});
		db.close();
	}
	
	// Delete Checkings account User Details
	public void DeleteCaccUser(int userid){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CHECKINGS_ACC, "id = ?",new String[]{String.valueOf(userid)});
		db.close();
	}
}  
