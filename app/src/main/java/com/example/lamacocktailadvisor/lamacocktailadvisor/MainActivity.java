package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity
        extends FragmentActivity {

    private static final String TAG= "MyMainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null){
            fragment = new AddFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }

        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, " **** onCreateOptionsMenu ****. ");
        getMenuInflater().inflate(R.menu.menu_main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_viewCocktails) {
            Toast.makeText(this, "View cocktail menu clicked", Toast.LENGTH_SHORT).show();
            return true;
        } if (id == R.id.action_addCocktails) {
            Toast.makeText(this, "Add cocktail menu clicked", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}