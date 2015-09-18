package com.example.lamacocktailadvisor.lamacocktailadvisor; /**
 * Created by Emilie on 15/05/2015.
 */

import android.util.Log;

public class Session {

    public int mId;
    public int mSessionNumber;
    public int mCocktail;
    public int mLama;
    public float mGrade;

    private static final String TAG = "Session";

    public Session(){}
    public Session(int id, int sessionNumber,  int cocktail, int lama, int grade){
        this.mId = id;
        this.mSessionNumber = sessionNumber;
        this.mCocktail = cocktail;
        this.mLama = lama;
        this.mGrade = grade;
    }

    public Session(int id, int sessionNumber){
        this.mId = id;
        this.mSessionNumber = sessionNumber;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getSessionNumber() {
        return mSessionNumber;
    }

    public void setSessionNumber(int mSessionNumber) {
        this.mSessionNumber = mSessionNumber;
    }

    public int getCocktail() {
        return mCocktail;
    }

    public void setCocktail(int mCocktail) {
        this.mCocktail = mCocktail;
    }

    public int getLama() {
        return mLama;
    }

    public void setLama(int mLama) {
        this.mLama = mLama;
    }

    public float getGrade() {
        return mGrade;
    }

    public void setGrade(float mGrade) {
        this.mGrade = mGrade;
    }

    public void printSessionInfo(){
        Log.v(TAG, "session " + mId + " " + mSessionNumber + " "
                + mCocktail + " " + mLama + " " + mGrade);
    }

}
