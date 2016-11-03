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

import com.skapp.lj.thememoryoftheday.cal.OneDayView;
import com.skapp.lj.thememoryoftheday.calLogConfig.HLog;
import com.skapp.lj.thememoryoftheday.calLogConfig.MConfig;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;


public class Main2Activity extends FragmentActivity {
    final static int ACT_MainActivity = 0;

    private static final String TAG = MConfig.TAG;
    private static final String NAME = "MainActivity";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());

    private TextView txtYear;
    private TextView txtMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Calendar today = Calendar.getInstance();
        final int today_year = today.get(Calendar.YEAR);
        final int today_month = today.get(Calendar.MONTH);
        final int today_date = today.get(Calendar.DATE);

        Button addButton = (Button) findViewById(R.id.main_add_bt);
        Button monthButton = (Button) findViewById(R.id.main_monthly_bt);
        Button weekButton = (Button) findViewById(R.id.main_weekly_bt);
        Button dayButton = (Button) findViewById(R.id.main_daily_bt);

        txtYear = (TextView)findViewById(R.id.txtV_year);
        txtMonth = (TextView)findViewById(R.id.txtV_month);

       final MonthlyFragment mf = (MonthlyFragment) getSupportFragmentManager().findFragmentById(R.id.monthly);

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

                int year = dayView.get(Calendar.YEAR);
                int month =  dayView.get(Calendar.MONTH) + 1;
                int day = dayView.get(Calendar.DAY_OF_MONTH);
                int day_of_week = dayView.get(Calendar.DAY_OF_WEEK);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("day",day);
                intent.putExtra("day_of_week",day_of_week);
                startActivityForResult(intent,ACT_MainActivity);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("year",today.get(Calendar.YEAR));
                intent.putExtra("month",today.get(Calendar.MONTH)+1);
                intent.putExtra("day",today.get(Calendar.DATE));
                intent.putExtra("day_of_week",today.get(Calendar.DAY_OF_WEEK));
                startActivityForResult(intent,ACT_MainActivity);
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

