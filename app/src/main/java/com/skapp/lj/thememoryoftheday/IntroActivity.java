package com.skapp.lj.thememoryoftheday;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class IntroActivity extends AppCompatActivity {

    private Handler handler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        ImageView introImage = (ImageView) findViewById(R.id.imageView);
        introImage.setBackground(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.intro2, options)));

        init();
        handler.postDelayed(runnable, 1000);
    }

    public void init() {
        handler = new Handler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
