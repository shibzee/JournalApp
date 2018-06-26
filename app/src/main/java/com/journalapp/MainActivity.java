package com.journalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
    }
    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (getSupportActionBar() != null) {
            setSupportActionBar(toolbar);
        }
    }
}
