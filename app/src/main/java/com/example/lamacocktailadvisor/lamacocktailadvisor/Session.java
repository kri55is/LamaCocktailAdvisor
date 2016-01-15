package com.example.lamacocktailadvisor.lamacocktailadvisor; /**
 * Created by Emilie on 15/05/2015.
 */

import android.util.Log;

public class Session {

    private int mId;
    private int mSessionNumber;
    private int mCocktailId;
    private int mLamaId;
    private float mGrade;

    private static final String TAG = "Session";

    public Session(){
        this.mId = -1;
        this.mSessionNumber = -1;
        this.mCocktailId = -1;
        this.mLamaId = -1;
        this.mGrade = -1;
    }
    public Session(int id, int sessionNumber,  int cocktailId, int lamaId, int grade){
        this.mId = id;
        this.mSessionNumber = sessionNumber;
        this.mCocktailId = cocktailId;
        this.mLamaId = lamaId;
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

    public int getCocktailId() {
        return mCocktailId;
    }

    public void setCocktailId(int mCocktailId) {
        this.mCocktailId = mCocktailId;
    }

    public int getLamaId() {
        return mLamaId;
    }

    public void setLamaId(int mLamaId) {
        this.mLamaId = mLamaId;
    }

    public float getGrade() {
        return mGrade;
    }

    public void setGrade(float mGrade) {
        this.mGrade = mGrade;
    }

    public void printSessionInfo(){
        Log.v(TAG, "session id:" + mId + ", session nb: " + mSessionNumber + ", cocktailId: "
                + mCocktailId + ", lamaId: " + mLamaId + ", grade: " + mGrade);
    }

}
