package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.util.Log;

/**
 * Created by Emilie on 15/05/2015.
 */
public class Cocktail {
    public int mId;
    public String mName;
    public float mAverageGrade;
    public int mGradeAmount;
    //mListIngredients


    private static final String TAG = "Cocktail";

    public Cocktail(int id, String name, float averageGrade, int gradeAmount){

        //check input
        if(id < 0) {
            Log.v(TAG, "warning in com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail " +
                    "declaration, given id is negative.");
            id = -1;
        }
        if(name.isEmpty()) {
            Log.v(TAG, "warning in com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail " +
                    "declaration, given name is empty.");
            name = "";
        }
        if(averageGrade < 0) {
            Log.v(TAG, "warning in com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail " +
                    "declaration, given average grade is negative.");
            averageGrade = 0;
        }
        if(gradeAmount < 0) {
            Log.v(TAG, "warning in com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail " +
                    "declaration, given gradeAmount is negative.");
            gradeAmount = 0;
        }

        mId = id;
        mName = name;
        mAverageGrade = averageGrade;
        mGradeAmount = gradeAmount;

        Log.v(TAG, "cocktail" + name.toString() + "created");
    }

    public Cocktail(String name){
        if(name.isEmpty()) {
            Log.v(TAG, "warning in com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail " +
                    "declaration, given name is empty.");
            name = "";
        }
        mName = name;

        mId = -1;
        mAverageGrade = 0;
        mGradeAmount = 0;
        Log.v(TAG, "cocktail created");
    }

    public Cocktail(){
        mId = -1;
        mName = "";
        mAverageGrade = 0;
        mGradeAmount = 0;
    }

    /**
     * @return the id
     */
    public int getId() {
        return mId;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * @return the averageGrade
     */
    public float getAverageGrade() {
        return mAverageGrade;
    }

    /**
     * @param averageGrade the averageGrade to set
     */
    public void setAverageGrade(float averageGrade) {
        mAverageGrade = averageGrade;
    };

    /**
     * @return the gradeAmount
     */
    public int getGradesAmount() {
        return mGradeAmount;
    }

    /**
     * @param gradeAmount the gradeAmount to set
     */
    public void setGradesAmount(int gradeAmount) {
        mGradeAmount = gradeAmount;
    }

    public void printCocktailInfo(){

        Log.v(TAG, "cocktail id:" + mId + ", name: " + mName + ", average: "
                + mAverageGrade + ", nb grades: " + mGradeAmount);
    }
}
