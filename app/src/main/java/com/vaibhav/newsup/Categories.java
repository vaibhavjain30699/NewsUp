package com.vaibhav.newsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vaibhav.newsup.network.AppInterface;

public class Categories extends AppCompatActivity {

    private AppInterface appInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

    }
}
