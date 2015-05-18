package com.example.lamacocktailadvisor.lamacocktailadvisor;

/**
 * Created by Emilie on 15/05/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends Activity {


    private static final String TAG = "SplashScreenActivity";
    //TODO add delay to show splash screen
    private final long DELAY = 0/*2000*/;

    LinearLayout mLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "***onCreate***");
        //TODO change splashscreen in xml : square is caca
        mLayout = (LinearLayout)RelativeLayout.inflate(this, R.layout.activity_splash_screen, null);
        setContentView(mLayout);

        try{

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
                    , DELAY);
        }
        catch(Exception e){
            Log.e(TAG, e.toString());
        }
    }

}
