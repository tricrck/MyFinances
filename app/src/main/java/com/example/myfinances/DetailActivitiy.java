package com.example.myfinances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;


public class DetailActivitiy extends Activity {
    Intent intent;

	public static String ACCOUNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<HashMap<String, String>> data = null;
		ListView lv = findViewById(R.id.user_list);
		
		if(ACCOUNT.equals("cds")) {
			data = db.getCDsAcc();
			ListAdapter adapter = new SimpleAdapter(DetailActivitiy.this, data, R.layout.list_row_cds,new String[]{"Account Number", "Current Balance","Initial Balance","Interest Rate"}, new int[]{R.id.acc_no_cds_lst, R.id.curr_bal_cds_lst,R.id.init_bal_cds_lst,R.id.intrest_rt_cds_lst});
			lv.setAdapter(adapter);
		}
		
		if(ACCOUNT.equals("loan")) {
			data = db.getLoansAcc();
			ListAdapter adapter = new SimpleAdapter(DetailActivitiy.this, data, R.layout.list_row_loan,new String[]{"Account Number", "Current Balance","Initial Balance","Interest Rate","Payment Amount"}, new int[]{R.id.acc_no_loan_lst, R.id.curr_bal_loan_lst,R.id.init_bal_loan_lst,R.id.intrest_rt_loan_lst,R.id.pay_amnt_loan_lst});
			lv.setAdapter(adapter);
		}
		
		if(ACCOUNT.equals("cacc")) {
			data = db.getCheckingAcc();
			ListAdapter adapter = new SimpleAdapter(DetailActivitiy.this, data, R.layout.list_row_cacc,new String[]{"Account Number", "Current Balance"}, new int[]{R.id.acc_no_cacc_lst, R.id.curr_bal_cacc_lst});
			lv.setAdapter(adapter);
		}
		
        Button back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					intent = new Intent(DetailActivitiy.this,MainActivity.class);
					startActivity(intent);
				}
			});
    }
}
