package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity
        extends Activity {

    private static final String TAG= "MyMainActivity";

    RelativeLayout mLayout = null;
    TextView mTxtMyWelcome = null;
    EditText mEtxtMyInputCocktail = null;
    Button mBtnAddCocktail = null;
    Button mBtnSaveDB = null;
    Button mBtnDeleteAllDB = null;
    EditText mEtxtMyInputLama = null;
    RatingBar mRatingBarCocktail = null;
    Button mBtnEnter = null;

    String mCocktail = null;
    String mLama = null;

    CocktailDataBaseHandler mDBCocktail = null;
    LamaDataBaseHandler mDBLama = null;
    SessionDataBaseHandler mDBSession= null;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "***onCreate***");
        mLayout = (RelativeLayout)RelativeLayout.inflate(this, R.layout.activity_main, null);


        /*Input for cocktails*/
        mEtxtMyInputCocktail = (EditText) mLayout.findViewById(R.id.editTextCocktail);
        mEtxtMyInputCocktail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCocktail = s.toString();
               // mEtxtMyInputCocktail.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.v(TAG, " Input for cocktails done. ");

        //Input for lamas
        mEtxtMyInputLama = (EditText) mLayout.findViewById(R.id.editTextLama);
        mEtxtMyInputLama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLama = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.v(TAG, " Input for lamas done. ");

        //rating stars
        mRatingBarCocktail = (RatingBar) mLayout.findViewById(R.id.ratingBar1);
        Log.v(TAG, " Input for rating stars done. ");

        //Button Enter
        mBtnEnter = (Button) mLayout.findViewById(R.id.enter);
        Log.v(TAG, " Listeners prepared done. ");

        //DataBase

        mDBCocktail = CocktailDataBaseHandler.getInstance(this);
        mDBLama = LamaDataBaseHandler.getInstance(this);
        mDBSession = SessionDataBaseHandler.getInstance(this);

        setContentView(mLayout);
        Log.v(TAG, " set content done. ");
    }

    public void EnterInputs(View view){

        String myCocktail = mCocktail;
        String myLama = mLama;
        Float myRating = mRatingBarCocktail.getRating();

        //clear input
        resetInput();

        //if an info is missing,error message
        if(myCocktail.isEmpty() || myLama.isEmpty()){
            String msg = "";
            if (myCocktail.isEmpty() && myLama.isEmpty()){
                msg = "You must specify a lama and a cocktail.";
            }
            else{
                if (myCocktail.isEmpty())
                    msg = "You must specify a cocktail.";
                else
                    msg = "You must specify a lama.";
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }
        else{
            Log.v(TAG, "Enter " + myCocktail + " " + myLama + " " + myRating);

            //add cocktail entry in cocktail table is new entry
            int cocktailId = mDBCocktail.addCocktailInDB(myCocktail);
            mDBCocktail.printCocktailsDB();
            Toast.makeText(this, "Cocktail " + myCocktail + " added.", Toast.LENGTH_LONG).show();

            //add lama entry in lama table if new lama
/*            mDBLama.addLamaInDB(myLama);
            mDBLama.printLamasDB();*/

            //add entry in sesson with cocktail, lama and grade + check if lama's favorite cocktail changed
            //mDBSession.addEntryInSesson(1 /*sesson num*/, myCocktailId, myLamaId, myRating);

            //change cocktail grade and amount of grades
            //mDBCocktail.addCocktailRating(myCocktail, myRating);

        }
    }

    private void resetInput(){
        mEtxtMyInputCocktail.setText("");
        mEtxtMyInputLama.setText("");
        mRatingBarCocktail.setRating(2.5f);
    }
}