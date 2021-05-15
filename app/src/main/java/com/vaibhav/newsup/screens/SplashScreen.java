package com.vaibhav.newsup.screens;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vaibhav.newsup.MainActivity;
import com.vaibhav.newsup.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TypeWriter tw = findViewById(R.id.nameee);
        tw.setText("");
        tw.setCharacterDelay(50);
//        tw.animateText("Made with ❤️ by\nVaibhav Jain\nAbhishek  Garain\nAshish Kirti Singh\nPoorva Waman Khandare");
        tw.animateText("Made by Vaibhav Jain");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);

//        Intent i = new Intent(SplashScreen.this,login.class);
//        startActivity(i);
    }
}
