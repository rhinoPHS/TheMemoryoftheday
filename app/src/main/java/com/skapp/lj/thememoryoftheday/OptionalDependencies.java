package com.skapp.lj.thememoryoftheday;

import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by a on 2016-11-03.
 */

public class OptionalDependencies {


    private final Context context;


    public OptionalDependencies(Context context) {
        this.context = context;
    }


    public void initialize() {
        Stetho.initializeWithDefaults(context);
    }
}