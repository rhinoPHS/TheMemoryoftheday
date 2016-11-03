package com.skapp.lj.thememoryoftheday;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skapp.lj.thememoryoftheday.db.DiaryDBHelper;
import com.skapp.lj.thememoryoftheday.quickAction.ActionItem;
import com.skapp.lj.thememoryoftheday.quickAction.QuickAction;
import com.tsengvn.typekit.TypekitContextWrapper;

import static android.os.Build.ID;
import static com.skapp.lj.thememoryoftheday.R.drawable.comma;
import static com.skapp.lj.thememoryoftheday.R.drawable.em;
import static com.skapp.lj.thememoryoftheday.R.id.body;

public class MainActivity extends AppCompatActivity {

    DiaryDBHelper mHelper;

    //derfault
    private static final int date_default = 0;

    //title Id
    private static final int ID_HEART = 1;
    private static final int ID_QUESTION = 2;
    private static final int ID_EM = 3;
    private static final int ID_COMMA = 4;
    private static final int ID_PERIOD = 5;
    private static final int ID_STAR = 6;
    private static final int ID_SD = 7;

    TextView txtV_year = null;
    TextView txtV_month = null;
    TextView txtV_day = null;
    TextView txtV_week = null;
    Button imageBtn_txt_title = null;
    Button btn_save_update = null;
    Button btn_cancle_delete = null;
    EditText mBodyText;
    String curDate;

    int title_backgroud;

    BitmapFactory.Options options = new BitmapFactory.Options();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DiaryDBHelper(this);


//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize =2;
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
//        layout.setBackground(new BitmapDrawable(getResources(),
//                BitmapFactory.decodeResource(getResources(),R.drawable.hangi,options)));


        btn_save_update = (Button) findViewById(R.id.save_update);
        btn_cancle_delete = (Button) findViewById(R.id.cancle_delete);

        txtV_year = (TextView) findViewById(R.id.txtV_year);
        txtV_month = (TextView) findViewById(R.id.txtV_month);
        txtV_day = (TextView) findViewById(R.id.txtV_day);
        txtV_week = (TextView) findViewById(R.id.txtV_week);

        imageBtn_txt_title = (Button) findViewById(R.id.txt_title);
        mBodyText = (EditText) findViewById(body);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year", date_default);
        String txt_year = Integer.toString(year);
        int month = intent.getIntExtra("month", date_default);
        int day = intent.getIntExtra("day", date_default);
        int day_of_week = intent.getIntExtra("day_of_week", date_default);

        txtV_year.setText(txt_year.substring(2));
        txtV_month.setText(Integer.toString(month));
        txtV_day.setText(Integer.toString(day));
        txtV_week.setText(day_of_week(day_of_week));

        curDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(day);

        // if diary already exists
//        if(true)
//        {
//            btn_save_update.setId(R.id.update);
//            btn_save_update.setText("수정");
//            btn_cancle_delete.setId(R.id.delete);
//            btn_cancle_delete.setText("삭제");
              getdata();
//        }
//        else
//        {
//            btn_save_update.setId(R.id.save);
//            btn_save_update.setText("저장");
//            btn_cancle_delete.setId(R.id.cancle);
//            btn_cancle_delete.setText("취소");
//        }

//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
//        layout.setBackground(new BitmapDrawable(getResources(),
//                BitmapFactory.decodeResource(getResources(),R.drawable.hangi,options)));

        options.inSampleSize = 2;

        ActionItem addHeart = new ActionItem();
        //addHeart.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart));
        addHeart.setIcon(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.heart, options)));

        ActionItem addQuestion = new ActionItem();
        addQuestion.setIcon(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.question, options)));

        ActionItem addEM = new ActionItem();
        addEM.setIcon(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), em, options)));

        ActionItem addComma = new ActionItem();
        addComma.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.comma));

        ActionItem addPeriod = new ActionItem();
        addPeriod.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.period));

        ActionItem addStrat = new ActionItem();
        addStrat.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star));

        ActionItem addSD = new ActionItem();
        addSD.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sd));


        final QuickAction mQuickAction = new QuickAction(MainActivity.this);

        mQuickAction.addActionItem(addHeart);
        mQuickAction.addActionItem(addQuestion);
        mQuickAction.addActionItem(addEM);
        mQuickAction.addActionItem(addComma);
        mQuickAction.addActionItem(addPeriod);
        mQuickAction.addActionItem(addStrat);
        mQuickAction.addActionItem(addSD);


        Bitmap period = BitmapFactory.decodeResource(getResources(), R.drawable.period);
        final Bitmap periodDouble = Bitmap.createScaledBitmap(period, period.getWidth() * 2, period.getHeight() * 2, true);

        // setup the action item click listener


        mQuickAction
                .setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
                    public void onItemClick(int pos) {

                        if (pos == 0) { // Add item selected
                            imageBtn_txt_title.setBackground(new BitmapDrawable(getResources(),
                                    BitmapFactory.decodeResource(getResources(), R.drawable.heart, options)));
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.heart;

                        } else if (pos == 1) { // Accept item selected
                            imageBtn_txt_title.setBackground(new BitmapDrawable(getResources(),
                                    BitmapFactory.decodeResource(getResources(), R.drawable.question, options)));
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.question;
                        } else if (pos == 2) { // Upload item selected
//                            imageBtn_txt_title.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.em));
                            imageBtn_txt_title.setBackground(new BitmapDrawable(getResources(),
                                    BitmapFactory.decodeResource(getResources(), em, options)));
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.em;
                        } else if (pos == 3) {
                            imageBtn_txt_title.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.comma));
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.comma;
                        } else if (pos == 4) {
                            imageBtn_txt_title.setBackground(new BitmapDrawable(getResources(), periodDouble));
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.period;
                        } else if (pos == 5) {
                            imageBtn_txt_title.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star));
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.star;
                        } else if (pos == 6) {
//                            imageBtn_txt_title.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sd));
                            imageBtn_txt_title.setBackgroundResource(R.drawable.sd);
                            imageBtn_txt_title.setText("");
                            title_backgroud = R.drawable.sd;
                        }
                    }
                });


        imageBtn_txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuickAction.show(view);
            }
        });

        new OptionalDependencies(this).initialize();


    }

    private void getdata() {
        int title_number = 0;
        String body = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("diary", new String[]{"title", "body"}, "date" + "=" + curDate, null,
                null, null, null, null);
        cursor.moveToNext();
        title_number = cursor.getInt(0);
        body = cursor.getString(1);


        getTitleimage(title_number);
        mBodyText.setText(body);
    }

    public void getTitleimage(int a) {
        if (a == ID_HEART) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.heart);
            imageBtn_txt_title.setText("");
        } else if (a == ID_QUESTION) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.question);
            imageBtn_txt_title.setText("");
        } else if (a == ID_EM) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.em);
            imageBtn_txt_title.setText("");
        } else if (a == ID_COMMA) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.comma);
            imageBtn_txt_title.setText("");
        } else if (a == ID_PERIOD) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.period);
            imageBtn_txt_title.setText("");
        } else if (a == ID_STAR) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.star);
            imageBtn_txt_title.setText("");
        } else if (a == ID_SD) {
            imageBtn_txt_title.setBackgroundResource(R.drawable.sd);
            imageBtn_txt_title.setText("");
        }
    }

    public String day_of_week(int day_of_week) {
        String dayOfWeek = null;

        if (day_of_week == 1)
            dayOfWeek = "일";
        else if (day_of_week == 2)
            dayOfWeek = "월";
        else if (day_of_week == 3)
            dayOfWeek = "화";
        else if (day_of_week == 4)
            dayOfWeek = "수";
        else if (day_of_week == 5)
            dayOfWeek = "목";
        else if (day_of_week == 6)
            dayOfWeek = "금";
        else if (day_of_week == 7)
            dayOfWeek = "토";

        return dayOfWeek;
    }

    @Override
    protected void attachBaseContext(Context newbase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newbase));
    }

    public void dbQuery(View v) {
        int titleNumber = 0;
        String body = null;
        if (v.getId() != R.id.cancle && v.getId() != R.id.cancle_delete) {
            titleNumber = getTitleNumber();
            body = mBodyText.getText().toString();
        }
        SQLiteDatabase db;
        ContentValues row;
        switch (v.getId()) {
            case R.id.save:
            case R.id.save_update:
                db = mHelper.getWritableDatabase();
                row = new ContentValues();
                row.put("title", titleNumber);
                row.put("body", body);
                row.put("date", curDate);
                db.insert("diary", null, row);
                mHelper.close();
                Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.delete:
                db = mHelper.getWritableDatabase();
                db.delete("diary", "date" + "=" + curDate, null);
                mHelper.close();
                Toast.makeText(this, "삭제완료", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.update:
                db = mHelper.getWritableDatabase();
                row = new ContentValues();
                row.put("title", titleNumber);
                row.put("body", body);
                row.put("date", curDate);
                db.update("diary", row, "date" + "=" + curDate, null);
                mHelper.close();
                Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.cancle:
            case R.id.cancle_delete:
                onBackPressed();

        }
    }

    public int getTitleNumber() {
        int titleNumber = 0;

        if (title_backgroud == R.drawable.heart) {
            titleNumber = ID_HEART;
        } else if (title_backgroud == R.drawable.question) {
            titleNumber = ID_QUESTION;
        } else if (title_backgroud == R.drawable.em) {
            titleNumber = ID_EM;
        } else if (title_backgroud == R.drawable.comma) {
            titleNumber = comma;
        } else if (title_backgroud == R.drawable.period) {
            titleNumber = ID_PERIOD;
        } else if (title_backgroud == R.drawable.star) {
            titleNumber = ID_STAR;
        } else if (title_backgroud == R.drawable.sd) {
            titleNumber = ID_SD;
        }
        return titleNumber;

    }


}
