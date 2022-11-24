package com.example.myfinances;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity 
{
	
	ViewFlipper viewFlipper;
	Intent intent;
	
	// SQLiteDatabase
	DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		final RadioGroup radg = findViewById(R.id.radioGroup);
		viewFlipper = findViewById(R.id.viewflipper);
		
		dbHandler = new DatabaseHandler(this);
		//database = openOrCreateDatabase("MyFinances",MODE_PRIVATE,null);
		//database.execSQL("CREATE TABLE IF NOT EXISTS CDs(id INT NOT NULL PRIMARY KEY AUTOINCREMENT, Account_Number INT NOT NULL, Current_Balance INT NOT NULL, Initial_Balance INT NOT NULL, Interest_Rate INT NOT NULL);");
		final Button select = findViewById(R.id.select);
		final Button save_cds = findViewById(R.id.save_cds);
		final Button save_loan = findViewById(R.id.save_loans);
		final Button save_cacc = findViewById(R.id.save_cacc);
		final Button details_cds = findViewById(R.id.details_cds);
		final Button details_loan = findViewById(R.id.details_loans);
		final Button details_cacc = findViewById(R.id.details_cacc);
		
		final EditText acc_no_cds = findViewById(R.id.account_no_cds);
		final EditText curr_bal_cds = findViewById(R.id.curr_bal_cds);
		final EditText init_bal_cds = findViewById(R.id.init_bal_cds);
		final EditText interest_cds = findViewById(R.id.interest_cds);
		final EditText acc_no_loans = findViewById(R.id.account_no_loans);
		final EditText curr_bal_loans = findViewById(R.id.curr_bal_loans);
		final EditText init_bal_loans = findViewById(R.id.init_bal_loans);
		final EditText payment_amount_loans = findViewById(R.id.payment_amount);
		final EditText interest_loans = findViewById(R.id.interest_loans);
		final EditText acc_no_cacc = findViewById(R.id.account_no_cacc);
		final EditText curr_bal_cacc = findViewById(R.id.curr_bal_cacc);
		
		select.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					RadioButton rdb = findViewById(radg.getCheckedRadioButtonId());
					if((rdb.getText()).equals("CDs"))
					{
						viewFlipper.setDisplayedChild(0);
					}
					if((rdb.getText()).equals("Loans"))
					{
						viewFlipper.setDisplayedChild(1);
					}
					if((rdb.getText()).equals("Checkings Account"))
					{
						viewFlipper.setDisplayedChild(2);
					}
				}
		});
		
		save_cds.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View p1)
				{
					try{
						if(dbHandler.addCDs(Integer.parseInt(acc_no_cds.getText().toString()),Double.parseDouble(init_bal_cds.getText().toString()),Double.parseDouble(curr_bal_cds.getText().toString()),Double.parseDouble(interest_cds.getText().toString()))==-1)
						{
							Toast.makeText(getApplicationContext(),"Insertion failed",Toast.LENGTH_SHORT).show();
						} else {
							if(!details_cds.isShown())
								details_cds.setVisibility(View.VISIBLE);
							Toast.makeText(getApplicationContext(),"Success CDs data saved",Toast.LENGTH_SHORT).show();
						}
					}
					catch(Exception e){
						Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
					}
				}
			});
			
		save_loan.setOnClickListener(new View.OnClickListener(){
			
				@Override
				public void onClick(View p1)
				{
					try{
						if(dbHandler.addLoans(Integer.parseInt(acc_no_loans.getText().toString()),Double.parseDouble(init_bal_loans.getText().toString()),Double.parseDouble(curr_bal_loans.getText().toString()),Double.parseDouble(payment_amount_loans.getText().toString()),Double.parseDouble(interest_loans.getText().toString()))==-1)
						{
							Toast.makeText(getApplicationContext(),"Insertion failed",Toast.LENGTH_SHORT).show();
						} else {
							if(!details_loan.isShown())
								details_loan.setVisibility(View.VISIBLE);
							Toast.makeText(getApplicationContext(),dbHandler.getLoansAcc().get(0).get("Payment_Amount")+" Success Loans data saved",Toast.LENGTH_SHORT).show();
						}
					}
					catch(Exception e){
						Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
						}
					}
			});
			
		save_cacc.setOnClickListener(new View.OnClickListener(){
			
				@Override
				public void onClick(View p1)
				{
					try{
						int acc_no = Integer.parseInt(acc_no_cacc.getText().toString());
						double curr_bal = Double.parseDouble(curr_bal_cacc.getText().toString());
						if(dbHandler.addCacc(acc_no, curr_bal)==-1)
						{
							Toast.makeText(getApplicationContext(),"Insertion failed",Toast.LENGTH_SHORT).show();
						}else{
							if(!details_cacc.isShown())
								details_cacc.setVisibility(View.VISIBLE);
							Toast.makeText(getApplicationContext(),"Success Checkings account data saved",Toast.LENGTH_SHORT).show();
						}
					}
					catch(Exception e){
						Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
						
					}
				}
			});
			
		details_cds.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					DetailActivitiy.ACCOUNT = "cds";
					intent = new Intent(MainActivity.this, DetailActivitiy.class);
					startActivity(intent);
				}
		});
		
		details_loan.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					DetailActivitiy.ACCOUNT = "loan";
					intent = new Intent(MainActivity.this, DetailActivitiy.class);
					startActivity(intent);
				}
			});
			
		details_cacc.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					DetailActivitiy.ACCOUNT = "cacc";
					intent = new Intent(MainActivity.this, DetailActivitiy.class);
					startActivity(intent);
				}
			});
    }
}


