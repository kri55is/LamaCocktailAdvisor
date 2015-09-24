package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity
        extends Activity  {

    private static final String TAG= "MyMainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AddActivity.class);
        startActivity(intent);

        setContentView(R.layout.activity_fragment);
    }

}