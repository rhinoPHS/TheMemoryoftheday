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
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skapp.lj.thememoryoftheday.calLogConfig.HLog;
import com.skapp.lj.thememoryoftheday.calLogConfig.MConfig;
import com.skapp.lj.thememoryoftheday.R;

import java.util.Calendar;

import static android.R.attr.shape;
import static com.skapp.lj.thememoryoftheday.cal.WeatherInfo.Weather.CLOUDY;
import static com.skapp.lj.thememoryoftheday.cal.WeatherInfo.Weather.RAINNY;
import static com.skapp.lj.thememoryoftheday.cal.WeatherInfo.Weather.SNOW;
import static com.skapp.lj.thememoryoftheday.cal.WeatherInfo.Weather.SUNSHINE;
import static com.skapp.lj.thememoryoftheday.cal.WeatherInfo.Weather.SUN_CLOUND;

/**
 * View to display a day
 *
 * @author Brownsoo
 */
public class OneDayView extends RelativeLayout {

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

    /**
     * Updates UI upon the value object.
     */
    public void refresh() {

        //HLog.d(TAG, CLASS, "refresh");
        HLog.d(TAG,CLASS,"Integer.toString(one.get(Calendar.YEAR))"+Integer.toString(one.get(Calendar.YEAR)));
        HLog.d(TAG,CLASS,"Integer.toString(Calendar.YEAR)"+Integer.toString(Calendar.YEAR));
        HLog.d(TAG,CLASS,"Integer.toString(one.get(Calendar.Month))"+Integer.toString(one.get(Calendar.MONTH)));
        HLog.d(TAG,CLASS,"Integer.toString(Calendar.Month)"+Integer.toString(Calendar.MONTH));
        HLog.d(TAG,CLASS,"Integer.toString(one.get(Calendar.DAY_OF_MONTH))"+Integer.toString(one.get(Calendar.DAY_OF_MONTH)));
        HLog.d(TAG,CLASS,"Integer.toString(Calendar.DAY_OF_MONTH)"+Integer.toString(Calendar.DAY_OF_MONTH));

        dayTv.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));

        if (one.get(Calendar.YEAR) == Calendar.YEAR &&
                one.get(Calendar.MONTH) == Calendar.MONTH &&
                one.get(Calendar.DAY_OF_MONTH) == Calendar.DAY_OF_MONTH)
        {
            dayTv.setTextColor(ContextCompat.getColor(getContext(), R.color.shape_date));
            dayTv.setTypeface(null, Typeface.BOLD);
        }

        if (one.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayTv.setTextColor(Color.RED);
        } else if (one.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayTv.setTextColor(Color.BLUE);
        } else {
            dayTv.setTextColor(Color.BLACK);
        }


        //msgTv.setText((one.getMessage()==null)?"":one.getMessage());

//        switch(one.weather) {
//        case CLOUDY :
//        case SUN_CLOUND :
//            weatherIv.setImageResource(R.drawable.heart);
//            break;
//        case RAINNY :
//            weatherIv.setImageResource(R.drawable.heart);
//            break;
//        case SNOW :
//            weatherIv.setImageResource(R.drawable.heart);
//            break;
//        case SUNSHINE :
//            weatherIv.setImageResource(R.drawable.heart);
//            break;
//        }

    }

}