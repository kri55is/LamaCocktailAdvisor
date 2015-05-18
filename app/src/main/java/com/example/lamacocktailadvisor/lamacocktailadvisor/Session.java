package com.example.lamacocktailadvisor.lamacocktailadvisor; /**
 * Created by Emilie on 15/05/2015.
 */

import android.util.Log;

public class Session {

    public int mId;
    public int mSessonNumber;
    public int mCocktail;
    public int mLama;
    public float mGrade;

    private static final String TAG = "SessonsClass";

    public Session(){}
    public Session(int id, int sessonNumber,  int cocktail, int lama, int grade){
        this.mId = id;
        this.mSessonNumber = sessonNumber;
        this.mCocktail = cocktail;
        this.mLama = lama;
        this.mGrade = grade;
    }

    public Session(int id, int sessonNumber){
        this.mId = id;
        this.mSessonNumber = sessonNumber;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getSessonNumber() {
        return mSessonNumber;
    }

    public void setSessonNumber(int mSessonNumber) {
        this.mSessonNumber = mSessonNumber;
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
        Log.v(TAG, "session " + mId + " " + mSessonNumber + " "
                + mCocktail + " " + mLama + " " + mGrade);
    }

}
