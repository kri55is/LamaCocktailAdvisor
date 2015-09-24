package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Emilie on 22/09/2015.
 */
public class ViewActivity extends  SingleFragmentActivity {
    private static final String TAG= "ViewActivity";

    @Override
    protected Fragment createFragment(){
        return new ViewFragment();
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
            Toast.makeText(this, "ViewActivity cocktail menu clicked", Toast.LENGTH_SHORT).show();

            return true;
        }
            if (id == R.id.action_addCocktails) {
                Toast.makeText(this, "Add cocktail menu clicked", Toast.LENGTH_SHORT).show();
                Log.v(TAG, " **** AddActivity cocktail menu clicked ****. ");

                Intent intent = new Intent();
                intent.setClass(ViewActivity.this, AddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
