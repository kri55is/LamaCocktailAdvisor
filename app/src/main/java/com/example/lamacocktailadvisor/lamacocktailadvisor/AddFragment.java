package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Emilie on 14/07/2015.
 */
public class AddFragment extends Fragment {

    private static final String TAG= "AddFragment";

    EditText mEtxtMyInputCocktail = null;
    Button mBtnDeleteAllDB = null;
    Button mBtnPrintAllDB = null;
    EditText mEtxtMyInputLama = null;
    RatingBar mRatingBarCocktail = null;
    Button mBtnEnter = null;

    String mCocktail = null;
    String mLama = null;

    CocktailDataBaseHandler mDBCocktail = null;
    LamaDataBaseHandler mDBLama = null;
    SessionDataBaseHandler mDBSession= null;

    @Override
    public void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "***onCreate***");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){

        Log.v(TAG, "***onCreateView***");

        View v = inflater.inflate(R.layout.fragment_add, parent, false);

        /*Input for cocktails*/
        mEtxtMyInputCocktail = (EditText) v.findViewById(R.id.list_item_cocktail_edit_text);
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
        mEtxtMyInputLama = (EditText) v.findViewById(R.id.editTextLama);
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
        mRatingBarCocktail = (RatingBar) v.findViewById(R.id.ratingBar1);
        Log.v(TAG, " Input for rating stars done. ");

        //Button Enter
        mBtnEnter = (Button) v.findViewById(R.id.enter);
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EnterInputs();
            }
        });

        //Button deleteAllDB
        mBtnDeleteAllDB = (Button) v.findViewById(R.id.deleteAll);
        mBtnDeleteAllDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DeleteAll();
            }
        });
        Log.v(TAG, " Listeners prepared done. ");

        //Button printCocktails
        mBtnPrintAllDB = (Button) v.findViewById(R.id.printCocktails);
        mBtnPrintAllDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintAllCocktails();
            }
        });

                mDBCocktail = CocktailDataBaseHandler.getInstance(getActivity());
        mDBLama = LamaDataBaseHandler.getInstance(getActivity());
        mDBSession = SessionDataBaseHandler.getInstance(getActivity());

        //setContentView(mLayout);
        // Log.v(TAG, " set content done. ");
        return v;
    }


    public void EnterInputs(){

        Log.v(TAG, " Button Enter pushed. ");
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
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        }
        else{
            Log.v(TAG, "Enter " + myCocktail + " " + myLama + " " + myRating);

            //add cocktail entry in cocktail table is new entry
            int cocktailId = mDBCocktail.addCocktailInDB(myCocktail);
            mDBCocktail.printCocktailsDB();
            Toast.makeText(getActivity(), "Cocktail " + cocktailId + " " + myCocktail, Toast.LENGTH_SHORT).show();

            //change cocktail grade and amount of grades
            float average = mDBCocktail.addCocktailRating(myCocktail, myRating);
            mDBCocktail.printCocktailsDB();
            Toast.makeText(getActivity(), "new average for " + myCocktail + " : " + average, Toast.LENGTH_SHORT).show();

            //add lama entry in lama table if new lama
            int lamaId = mDBLama.addLamaInDB(myLama);
            mDBLama.printLamasDB();
            Toast.makeText(getActivity(), "Lama " + lamaId + " " + myLama, Toast.LENGTH_SHORT).show();

            //add entry in session with cocktail, lama and grade + check if lama's favorite cocktail changed
            mDBSession.addEntryInSession(1 /*session num*/, cocktailId, lamaId, myRating);


        }
    }

    private void DeleteAll(){
        Log.v(TAG, " Button Delete All clicked. ");

        mDBCocktail.deleteAllCocktailsInDB();
        mDBLama.deleteAllLamasInDB();
        mDBSession.deleteAllEntryInSessionsInDB();

        mDBCocktail.printCocktailsDB();
        mDBLama.printLamasDB();

        Toast.makeText(getActivity(), "Deleted All entries", Toast.LENGTH_LONG).show();

    }

    private void PrintAllCocktails(){
        Log.v(TAG, " Button Print Cocktails clicked. ");

        mDBCocktail.printCocktailsDB();
        mDBLama.printLamasDB();
    }

    private void resetInput(){
        mEtxtMyInputCocktail.setText("");
        mEtxtMyInputLama.setText("");
        mRatingBarCocktail.setRating(2.5f);
    }
}
