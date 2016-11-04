/*
* Copyright (C) 2015 Hansoo Lab.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.skapp.lj.thememoryoftheday.cal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skapp.lj.thememoryoftheday.R;
import com.skapp.lj.thememoryoftheday.calLogConfig.HLog;
import com.skapp.lj.thememoryoftheday.calLogConfig.MConfig;
import com.skapp.lj.thememoryoftheday.db.DiaryDBHelper;

import java.util.Calendar;

/**
 * View to display a day
 *
 * @author Brownsoo
 */
public class OneDayView extends RelativeLayout {

    private DiaryDBHelper mHelper;
    private SQLiteDatabase db;

    private static final String TAG = MConfig.TAG;
    private static final String NAME = "OneDayView";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());


    /**
     * number text field
     */
    private TextView dayTv;
    /** message text field*/
    //  private TextView msgTv;
    /**
     * Weather icon
     */
    private ImageView weatherIv;
    /**
     * Value object for a day info
     */
    private OneDayData one;

    /**
     * OneDayView constructor
     *
     * @param context context
     */
    public OneDayView(Context context) {
        super(context);
        init(context);

    }

    /**
     * OneDayView constructor for xml
     *
     * @param context context
     * @param attrs   AttributeSet
     */
    public OneDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        View v = View.inflate(context, R.layout.cal_onday, this);

        dayTv = (TextView) v.findViewById(R.id.onday_dayTv);
        weatherIv = (ImageView) v.findViewById(R.id.onday_weatherIv);
        //msgTv = (TextView) v.findViewById(R.id.onday_msgTv);
        one = new OneDayData();

        mHelper = new DiaryDBHelper(getContext());
    }

    /**
     * Set the day to display
     *
     * @param year  4 digits of year
     * @param month Calendar.JANUARY ~ Calendar.DECEMBER
     * @param day   day of month
     */
    public void setDay(int year, int month, int day) {
        this.one.cal.set(year, month, day);
    }

    /**
     * Set the day to display
     *
     * @param cal Calendar instance
     */
    public void setDay(Calendar cal) {
        this.one.setDay((Calendar) cal.clone());
    }


    /**
     * Set the day to display
     *
     * @param one OneDayData instance
     */
    public void setDay(OneDayData one) {
        this.one = one;
    }

    /**
     * Get the day to display
     *
     * @return OneDayData instance
     */
    public OneDayData getDay() {
        return one;
    }

    /**
     * Set the message to display
     *
     * @param msg message
     */
    public void setMessage(String msg) {
        one.setMessage(msg);
    }

    /**
     * Get the message
     *
     * @return message
     */
    public CharSequence getMessage() {
        return one.getMessage();
    }

    /**
     * Same function with {@link Calendar#get(int)}<br>
     * <br>
     * Returns the value of the given field after computing the field values by
     * calling {@code complete()} first.
     *
     * @param field Calendar.YEAR or Calendar.MONTH or Calendar.DAY_OF_MONTH
     * @throws IllegalArgumentException       if the fields are not set, the time is not set, and the
     *                                        time cannot be computed from the current field values.
     * @throws ArrayIndexOutOfBoundsException if the field is not inside the range of possible fields.
     *                                        The range is starting at 0 up to {@code FIELD_COUNT}.
     */
    public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        return one.get(field);
    }

    /**
     * Set weather
     *
     * @param weather Weather instance
     */
    public void setWeather(WeatherInfo.Weather weather) {
        this.one.setWeather(weather);
    }
    public WeatherInfo.Weather getWeather()
    {
        return this.one.getWeather();
    }

    /**
     * Updates UI upon the value object.
     */
    public void refresh() {

        int year = one.get(Calendar.YEAR);
        int month = one.get(Calendar.MONTH);
        int date = one.get(Calendar.DATE);


        String curDate = Integer.toString(year) + Integer.toString(month + 1) + Integer.toString(date);

        db = mHelper.getReadableDatabase();

        Cursor cursor;

        cursor = db.query("diary", new String[]{"title", "date"}, null, null, null, null, null);

          try {
              while (cursor.moveToNext()) {

                  int titleNumber = cursor.getInt(0);
                  String day = cursor.getString(1);

                  HLog.d(TAG, CLASS, "refresh   /   " + curDate + "  /  " + day + "  /  " + titleNumber);

                  if (curDate.equals(day)) {
                      switch (titleNumber) {
                          case 1:
                              weatherIv.setImageResource(R.drawable.heart);
                              setWeather(WeatherInfo.Weather.HEART);
                              break;
                          case 2:
                              weatherIv.setImageResource(R.drawable.question);
                              setWeather(WeatherInfo.Weather.QUESTION);
                              break;
                          case 3:
                              weatherIv.setImageResource(R.drawable.em);
                              setWeather(WeatherInfo.Weather.EM);
                              break;
                          case 4:
                              weatherIv.setImageResource(R.drawable.comma);
                              setWeather(WeatherInfo.Weather.COMMA);
                              break;
                          case 5:
                              weatherIv.setImageResource(R.drawable.period);
                              setWeather(WeatherInfo.Weather.PERIOD);
                              break;
                          case 6:
                              weatherIv.setImageResource(R.drawable.star);
                              setWeather(WeatherInfo.Weather.STAR);
                              break;
                          case 7:
                              weatherIv.setImageResource(R.drawable.sd);
                              setWeather(WeatherInfo.Weather.SD);
                              break;
                      }
                  }
              }
          }catch (Exception e){e.printStackTrace();}
        finally {
              cursor.close();
              mHelper.close();
          }



        Calendar today = Calendar.getInstance();
        //HLog.d(TAG, CLASS, "refresh");
        dayTv.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));

        if (one.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayTv.setTextColor(Color.RED);
        } else if (one.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayTv.setTextColor(Color.BLUE);
        } else {
            dayTv.setTextColor(Color.BLACK);
        }

        if (year == (today.get(Calendar.YEAR)) && month == (today.get(Calendar.MONTH)) && date == (today.get(Calendar.DATE))) {
            dayTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.shape_date));
        }


        //msgTv.setText((one.getMessage()==null)?"":one.getMessage());
        //one.weather


    }

}