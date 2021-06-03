package com.kheileang.numberinburmese.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.kheileang.numberinburmese.MainActivity;
import com.kheileang.numberinburmese.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Kitkat translucent statusbar
        // set ActionBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setTranslucentStatus();
        showScreen();
    }

    private void setTranslucentStatus() {
        final int bits = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS : 0;

        final Window win = getWindow();
        final WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= bits;
        win.setAttributes(winParams);
    }

    private void showScreen(){
        // use handler here to post Runnable to Message Queue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 1000);
    }
}