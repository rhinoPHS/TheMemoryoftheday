package com.skapp.lj.thememoryoftheday;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by a on 2016-10-24.
 */

public class CustomStartApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"hlM.ttf"))
                .addBold(Typekit.createFromAsset(this,"hlEB.ttf"));

    }
}
