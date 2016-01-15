package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 21/09/2015.
 */
public class CocktailBar {
    private static CocktailBar sCocktailBar;
    private List<Cocktail> mCocktailList;

    private CocktailBar(Context context){

        Log.v("CocktailBar", "Create CocktailBar");
        mCocktailList = new ArrayList<>();
            }

    static CocktailBar get(Context context){
        if (sCocktailBar == null){
            sCocktailBar = new CocktailBar(context);
        }
        return sCocktailBar;
    }

    public Cocktail getCocktail(int id){
        for (Cocktail cocktail : mCocktailList){
            if(cocktail.getId() == id){
                return cocktail;
            }
        }
        return null;
    }

    public List<Cocktail> getCocktails(Context context){
        //delete things in the list, might be old
        mCocktailList.clear();

        //get cocktails count in database
        CocktailDataBaseHandler db = CocktailDataBaseHandler.getInstance(context);
        int count = db.getCocktailCount();
        db.printCocktailsDB();
        //get the cocktails
        //WARNING id in database starts at 1, not 0!!!
        for (int i = 1; i <= count; i++){
            Cocktail cocktail = db.findCocktailFromId(i);
            if(cocktail != null)
            {
                mCocktailList.add(cocktail);
            }
        }

        return mCocktailList;
    }
}
