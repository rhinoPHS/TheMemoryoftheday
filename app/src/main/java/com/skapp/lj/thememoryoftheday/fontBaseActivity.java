package com.skapp.lj.thememoryoftheday;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by a on 2016-10-24.
 */
class fontBaseActivity extends AppCompatActivity {
    @Override
    protected  void attachBaseContext(Context newbase)
    {
        super.attachBaseContext(TypekitContextWrapper.wrap(newbase));
    }
}
