package com.skapp.lj.thememoryoftheday;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skapp.lj.thememoryoftheday.cal.OneDayView;
import com.skapp.lj.thememoryoftheday.cal.OneMonthView;
import com.skapp.lj.thememoryoftheday.calLogConfig.HLog;
import com.skapp.lj.thememoryoftheday.calLogConfig.MConfig;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;

import static com.skapp.lj.thememoryoftheday.R.string.month;


public class Main2Activity extends FragmentActivity {

    private static final String TAG = MConfig.TAG;
    private static final String NAME = "MainActivity";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());

   // private TextView thisMonthTv;
    private TextView txtYear;
    private TextView txtMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button addButton = (Button) findViewById(R.id.main_add_bt);
        Button monthButton = (Button) findViewById(R.id.main_monthly_bt);
        Button weekButton = (Button) findViewById(R.id.main_weekly_bt);
        Button dayButton = (Button) findViewById(R.id.main_daily_bt);
        //thisMonthTv = (TextView) findViewById(R.id.this_month_tv);
        txtYear = (TextView)findViewById(R.id.txtV_year);
        txtMonth = (TextView)findViewById(R.id.txtV_month);

        MonthlyFragment mf = (MonthlyFragment) getSupportFragmentManager().findFragmentById(R.id.monthly);


        mf.setOnMonthChangeListener(new MonthlyFragment.OnMonthChangeListener() {

            @Override
            public void onChange(int year, int month) {
                HLog.d(TAG, CLASS, "onChange " + year + "." + month);
               // thisMonthTv.setText(year + "." + (month + 1));
                txtYear.setText(Integer.toString(year).substring(2));
                txtMonth.setText(Integer.toString(month+1));
            }

            @Override
            public void onDayClick(OneDayView dayView) {
                int month =  dayView.get(Calendar.MONTH) + 1;
                int day = dayView.get(Calendar.DAY_OF_MONTH);
                Toast.makeText(Main2Activity.this, "Click  " + month + "/" + day, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newbase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newbase));
    }

}

