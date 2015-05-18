package com.example.lamacocktailadvisor.lamacocktailadvisor; /**
 * Created by Emilie on 15/05/2015.
 */
import android.util.Log;

public class Lama {

    public int mId;
    public String mName;
    public int mFavoriteCocktail;

    private static final String TAG = "LamaClass";

    public Lama(){}

    public Lama(int id, String name, int favoriteCocktail){
        this.mId = id;
        this.mName= name;
        this.mFavoriteCocktail = favoriteCocktail;
    }

    public Lama(int id, String name){
        this.mId = id;
        this.mName= name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getFavoriteCocktail() {
        return mFavoriteCocktail;
    }

    public void setFavoriteCocktail(int mFavoriteCocktail) {
        this.mFavoriteCocktail = mFavoriteCocktail;
    }

    public void printLamaInfo(){
        Log.v(TAG, "lama " + mId + " " + mName + " "
                + mFavoriteCocktail);
    }


}
