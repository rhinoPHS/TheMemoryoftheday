package com.skapp.lj.thememoryoftheday;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    //ActionMenuItem Id
    private  static final  int ID_HEART = 1;
    private  static final  int ID_QUESTION = 2;
    private  static final  int ID_EM = 3;
    private  static final  int ID_COMMA = 4;
    private  static final  int ID_PERIOD = 5;
    private  static final  int ID_STAR = 6;
    private  static final  int ID_SD = 7;


    TextView txtV_year = null;
    TextView txtV_month = null;
    TextView txtV_day = null;
    TextView txtV_week = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize =2;
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
//        layout.setBackground(new BitmapDrawable(getResources(),
//                BitmapFactory.decodeResource(getResources(),R.drawable.hangi,options)));


        txtV_year = (TextView) findViewById(R.id.txtV_year);
        txtV_month = (TextView) findViewById(R.id.txtV_month);
        txtV_day = (TextView) findViewById(R.id.txtV_day);
        txtV_week = (TextView) findViewById(R.id.txtV_week);

        Calendar today = Calendar.getInstance();

        int year = today.get(Calendar.YEAR);
        String txt_year = Integer.toString(year);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        int day_of_week = today.get(Calendar.DAY_OF_WEEK);

        txtV_year.setText(txt_year.substring(2));
        txtV_month.setText(Integer.toString(month));
        txtV_day.setText(Integer.toString(day));
        txtV_week.setText(day_of_week(day_of_week));




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

    public static class LineEditText extends EditText {
        // we need this constructor for LayoutInflater
        public LineEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            mRect = new Rect();
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(ContextCompat.getColor(context, R.color.shape_date));
            mPaint.setStrokeWidth(5);

        }

        private Rect mRect;
        private Paint mPaint;

        @Override
        protected void onDraw(Canvas canvas) {

            int height = getHeight();
            int line_height = getLineHeight();
            int count = height / line_height;


            if (getLineCount() > count)
                count = getLineCount();

            Rect r = mRect;
            Paint paint = mPaint;
            int baseline = getLineBounds(0, r);

            for (int i = 0; i < count; i++) {

                canvas.drawLine(r.left, baseline + 10, r.right, baseline + 10, paint);
                canvas.drawLine(baseline, 0, baseline, getHeight(), paint);

                baseline += getLineHeight();

                super.onDraw(canvas);
            }

        }
    }
}
