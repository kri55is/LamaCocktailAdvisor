package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Emilie on 15/05/2015.
 */
public class CocktailDataBaseHandler extends SQLiteOpenHelper{
    private static CocktailDataBaseHandler cocktailDBInstance = null;

    public static synchronized CocktailDataBaseHandler getInstance(Context context){

        if (cocktailDBInstance == null) {
            cocktailDBInstance = new CocktailDataBaseHandler(context.getApplicationContext());
        }
        return cocktailDBInstance;
    }


    private static final String TAG = "Cocktail";

    private static final int COCKTAIL_DATABASE_VERSION = 2;

    //VERSION 1
    private static final String DATABASE_COCKTAIL_NAME = "cocktailDB.sqlite";
    public static final String TABLE_COCKTAILS = "cocktails";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COCKTAILNAME = "cocktailname";
    public static final String COLUMN_AVERAGEGRADE= "averagegrade";
    public static final String COLUMN_GRADESAMOUNT= "gradesamount";


    public CocktailDataBaseHandler(Context context) {
        super(context, DATABASE_COCKTAIL_NAME, null, COCKTAIL_DATABASE_VERSION);
        Log.v(TAG, "Database constructor");
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_COCKTAILS_TABLE = "CREATE TABLE " +
                TABLE_COCKTAILS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COCKTAILNAME + " TEXT,"
                + COLUMN_AVERAGEGRADE + " REAL,"
                + COLUMN_GRADESAMOUNT + " INTEGER"+ ")";
        db.execSQL(CREATE_COCKTAILS_TABLE);
        Log.v(TAG, "Database cocktail created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO in better version : compare tables, not drop all table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COCKTAILS);
        onCreate(db);

        Log.v(TAG, "onUpgrade: cocktail Database has been upgraded");
    }

    public int getCocktailCount(){

        String query = "Select * FROM " + TABLE_COCKTAILS;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        return count;

    }

    public int addCocktailInDB (String name){
		/*SQLiteDatabase db = getWritableDatabase();

		if(db.isReadOnly()){
			Log.v(TAG, "bd in ReadOnly");
			return;
		}*/
        int id;
        SQLiteDatabase db ;
        if (!isCocktailInDB(name)){
            ContentValues values = new ContentValues();
            values.put (COLUMN_COCKTAILNAME, name);
            values.put (COLUMN_AVERAGEGRADE, 0.0f);
            values.put (COLUMN_GRADESAMOUNT, 0);

            try{
                db = getWritableDatabase();
                id = (int)db.insertOrThrow(TABLE_COCKTAILS, null, values);
                db.close();
            }
            catch(SQLException e){
                Log.w(TAG, e);
                return -1;
            }

            Log.v(TAG, "Database cocktail added");
        }
        else{
            Log.v(TAG, "cocktail already in db");
            id = getCocktailId(name);
        }
        return id;
    }

    public int addCocktailInDB (Cocktail cocktail)
    {
        int id;
        SQLiteDatabase db = getWritableDatabase();

        String name= cocktail.getName();

        if (!isCocktailInDB(name)){
            ContentValues values = new ContentValues();
            values.put (COLUMN_COCKTAILNAME, name);
            values.put (COLUMN_AVERAGEGRADE, cocktail.getAverageGrade());
            values.put (COLUMN_GRADESAMOUNT, cocktail.getGradesAmount());

            try{
                db = getWritableDatabase();
                id = (int)db.insertOrThrow(TABLE_COCKTAILS, null, values);
                db.close();
            }
            catch(SQLException e){
                Log.w(TAG, e);
                return -1;
            }

            Log.v(TAG, "Database cocktail added");
        }
        else{
            Log.v(TAG, "cocktail already in db");
            id = getCocktailId(name);
        }

        return id;
    }

    public Cocktail findCocktailFromId(int id) {
        //WARNING id in database starts at 1, not 0!!!
        String query = "Select * FROM " + TABLE_COCKTAILS + " WHERE " + COLUMN_ID + " = " + id ;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int totalCocktail = cursor.getCount();
        if (totalCocktail == 1) {
            try {
                cursor.moveToFirst();

                Cocktail cocktail = new Cocktail();

                cocktail.setId(Integer.parseInt(cursor.getString(0)));
                cocktail.setName(cursor.getString(1));
                cocktail.setAverageGrade(Float.parseFloat(cursor.getString(2)));
                cocktail.setGradesAmount(Integer.parseInt(cursor.getString(3)));

                cursor.close();
                db.close();
                return cocktail;
            }
            catch (Exception e) {
                Log.w(TAG, e);
                cursor.close();
                db.close();
                return null;
            }
        } else
        {
            if (totalCocktail == 0){
                Log.v(TAG, "findCocktailFromId: No cocktail to find");
                return null;
            }
            else {
                Log.v(TAG, "ERROR: several cocktails found with id " + id);

                cursor.close();
                db.close();
                return null;
            }
        }
    }

    public Cocktail findCocktailFromName(String cocktailName)
    {
        String query = "Select * FROM " + TABLE_COCKTAILS + " WHERE " + COLUMN_COCKTAILNAME + " = \"" + cocktailName +  "\"";
        SQLiteDatabase db = getReadableDatabase();

        Cocktail cocktail = new Cocktail();

        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        if (count == 1){
            if (cursor.moveToFirst())
            {
                //cursor.moveToFirst();
                cocktail.setId(Integer.parseInt(cursor.getString(0)));
                cocktail.setName(cursor.getString(1));
                cocktail.setAverageGrade(Float.parseFloat(cursor.getString(2)));
                cocktail.setGradesAmount(Integer.parseInt(cursor.getString(3)));

                Log.v(TAG, "Database cocktail found");
            }
            else
            {
                Log.v(TAG, "Database no cocktail found");
            }

            cursor.close();
            db.close();
            return cocktail;
        }
        else{
            if (count > 1){
                Log.e(TAG, "ERROR : Multiple cocktails found");
            }

            if ( count == 0 ){
                Log.e(TAG, "ERROR : NO cocktail found");
            }
        }
        //TODO if count > 1 ou count =0 on renvoit un cocktail empty...
        return cocktail;
    }

    public int getCocktailId(String name){
        int cocktailId = -1;
        Cocktail cocktail = findCocktailFromName(name);
        cocktailId = cocktail.getId();
        return cocktailId;
    }

    public boolean isCocktailInDB(String cocktailName)
    {
        boolean result = false;
        String query = "Select * FROM " + TABLE_COCKTAILS + " WHERE " + COLUMN_COCKTAILNAME + " = \"" + cocktailName +  "\"";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int numberFoundCocktails = cursor.getCount();

        if (numberFoundCocktails > 0)
        {
            result = true;

            Log.v(TAG, "com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail already in DB");
        }
        else
        {

            Log.v(TAG, "com.example.lamacocktailadvisor.lamacocktailadvisor.Cocktail not found in DB");
        }

        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteCocktail(String cocktailName)
    {
        String query = "Select * FROM " + TABLE_COCKTAILS + "WHERE " + COLUMN_COCKTAILNAME + " = \"" + cocktailName +  "\"";
        SQLiteDatabase db = getWritableDatabase();

        boolean found = false;

        Cursor cursor = db.rawQuery(query, null);

        Cocktail cocktail = new Cocktail();
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            cocktail.setId(Integer.parseInt(cursor.getString(0)));

            db.delete(TABLE_COCKTAILS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(cocktail.getId()) });

            cursor.close();
            found = true;
            Log.v(TAG, "Database cocktail found");
        }
        else
        {
            cocktail = null;

            Log.v(TAG, "Database no cocktail found");
        }
        db.close();
        return found;
    }

    public void deleteAllCocktailsInDB()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_COCKTAILS, null, null);
        /*String query = "Select * FROM " + TABLE_COCKTAILS ;
        SQLiteDatabase db = getWritableDatabase();

        int count = 0;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            count++;
            db.delete(TABLE_COCKTAILS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(cursor.getString(0)) });
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        Log.v(TAG, "deleteAllCocktailsInDB: " + count + " cocktails deleted from database");*/
    }

    public float addCocktailRating(String cocktailName, float rating){
        float average = -1;
        int gradesAmount = 0;

        Log.v(TAG, "Add rating" + rating + " to " + cocktailName);
        String query = "Select * FROM " + TABLE_COCKTAILS + " WHERE " + COLUMN_COCKTAILNAME + " = \"" + cocktailName +  "\"";
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        if (count == 1){
            if (cursor.moveToFirst())
            {
                String name = cursor.getString(1);
                average = Float.parseFloat(cursor.getString(2));
                Log.v(TAG, "old average was : " + average);
                gradesAmount = Integer.parseInt(cursor.getString(3));

                average = (average * gradesAmount + rating)/(gradesAmount + 1 );
                gradesAmount++;

                ContentValues values = new ContentValues();
                values.put (COLUMN_AVERAGEGRADE, average);
                values.put(COLUMN_GRADESAMOUNT, gradesAmount);

                try{
                    int nb_rows = db.update(TABLE_COCKTAILS, values, COLUMN_COCKTAILNAME + " = \"" + cocktailName + "\"", null);
                    Log.v(TAG, "Grade added");
                }
                catch(SQLException e){
                    Log.w(TAG, e);
                }

            }
            else
            {
                Log.v(TAG, "Database no cocktail found");
            }

            cursor.close();
            db.close();
        }
        else{
            if (count > 1){
                Log.e(TAG, "ERROR : Multiple cocktails found");
            }

            if ( count == 0 ){
                Log.e(TAG, "ERROR : NO cocktail found");
            }
        }
        return average;
    }

    public void printCocktailsDB(){
        String query = "Select * FROM " + TABLE_COCKTAILS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int totalCocktail = cursor.getCount();
        Log.v(TAG, "printCocktailsDB:  " + totalCocktail + " cocktails in the database");
        if (totalCocktail < 1){
            Log.v(TAG, "printCocktailsDB: No cocktail to print");
        }
        else{
            try{
                cursor.moveToFirst();
                for (int i = 0; i < totalCocktail; i++) {

                    Cocktail cocktail = new Cocktail();

                    cocktail.setId(Integer.parseInt(cursor.getString(0)));
                    cocktail.setName(cursor.getString(1));
                    cocktail.setAverageGrade(Float.parseFloat(cursor.getString(2)));
                    cocktail.setGradesAmount(Integer.parseInt(cursor.getString(3)));

                    cocktail.printCocktailInfo();
                    cursor.moveToNext();

                }
            }
            catch(Exception e){
                Log.w(TAG, e);
            }
        }

        cursor.close();
        db.close();
    }
}
