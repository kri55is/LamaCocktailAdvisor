package com.example.lamacocktailadvisor.lamacocktailadvisor; /**
 * Created by Emilie on 15/05/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LamaDataBaseHandler extends SQLiteOpenHelper{


    private static LamaDataBaseHandler lamaDBInstance = null;

    public static synchronized LamaDataBaseHandler getInstance(Context context){

        if (lamaDBInstance == null) {
            lamaDBInstance = new LamaDataBaseHandler(context.getApplicationContext());
        }
        return lamaDBInstance;
    }

    private static final String TAG = "LamaDataBaseHandler";


    //VERSION 1
    private static final int LAMA_DATABASE_VERSION = 1;
    private static final String DATABASE_LAMA_NAME = "lamaDB.sqlite";
    public static final String TABLE_LAMAS = "lamas";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LAMANAME = "lamaname";
    public static final String COLUMN_FAVORITECOCKTAIL= "favoritecocktail";


    public LamaDataBaseHandler(Context context) {
        super(context, DATABASE_LAMA_NAME, null, LAMA_DATABASE_VERSION);
        Log.v(TAG, "Database constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_LAMA_TABLE = "CREATE TABLE" +
                TABLE_LAMAS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LAMANAME + " TEXT,"
                + COLUMN_FAVORITECOCKTAIL + " TEXT" + ")";
        db.execSQL(CREATE_LAMA_TABLE);
        Log.v(TAG, "Database lamas created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO in better version : compare tables, not drop all table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAMAS);
        onCreate(db);

        Log.v(TAG, "onUpgrade: com.example.lamacocktailadvisor.lamacocktailadvisor.Lama Database has been upgraded");
    }



    public void addLamaInDB (String name){
        SQLiteDatabase db = getWritableDatabase();

        if(db.isReadOnly()){
            Log.v(TAG, "bd in ReadOnly");
            return;
        }
        if (!isLamaInDB(name)){
            ContentValues values = new ContentValues();
            values.put (COLUMN_LAMANAME, name);

            try{
                db.insertOrThrow(TABLE_LAMAS, null, values);
            }
            catch(SQLException e){
                Log.w(TAG, e);
            }

            Log.v(TAG, "Database lama added");
        }
        else{
            Log.v(TAG, "lama already in db");
        }
        db.close();
    }

    public boolean isLamaInDB(String lamaName)
    {
        boolean result = false;
        String query = "Select * FROM " + TABLE_LAMAS + " WHERE " + COLUMN_LAMANAME + " = \"" + lamaName +  "\"";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int numberFoundLamas = cursor.getCount();

        if (numberFoundLamas > 0)
        {
            result = true;

            Log.v(TAG, "com.example.lamacocktailadvisor.lamacocktailadvisor.Lama already in DB");
        }
        else
        {

            Log.v(TAG, "com.example.lamacocktailadvisor.lamacocktailadvisor.Lama not found in DB");
        }

        db.close();
        return result;
    }

    public boolean deleteLama(String lamaName)
    {
        String query = "Select * FROM " + TABLE_LAMAS + "WHERE " + COLUMN_LAMANAME + " = \"" + lamaName +  "\"";
        SQLiteDatabase db = getWritableDatabase();

        boolean found = false;

        Cursor cursor = db.rawQuery(query, null);

        Lama lama = new Lama();
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            lama.setId(Integer.parseInt(cursor.getString(0)));

            db.delete(TABLE_LAMAS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(lama.getId()) });

            cursor.close();
            found = true;
            Log.v(TAG, "Database lama found");
        }
        else
        {
            lama = null;

            Log.v(TAG, "Database no lama found");
        }
        db.close();
        return found;
    }

    public void deleteAllLamasInDB()
    {
        String query = "Select * FROM " + TABLE_LAMAS ;
        SQLiteDatabase db = getWritableDatabase();

        int count = 0;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            count++;
            db.delete(TABLE_LAMAS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(cursor.getString(0)) });
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        Log.v(TAG, "deleteAllLamasInDB: " + count + " lamas deleted from database");
    }

    public void printLamasDB(){
        String query = "Select * FROM " + TABLE_LAMAS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int totalCocktail = cursor.getCount();
        Log.v(TAG, "printCocktailsDB:  " + totalCocktail + " cocktails in the database");
        if (totalCocktail < 1){
            Log.v(TAG, "printCocktailsDB: No lama to print");
        }
        else{
            try{
                cursor.moveToFirst();
                for (int i = 0; i < totalCocktail; i++) {

                    Lama lama = new Lama();

                    lama.setId(Integer.parseInt(cursor.getString(0)));
                    lama.setName(cursor.getString(1));
                    lama.setFavoriteCocktail(Integer.parseInt(cursor.getString(2)));

                    lama.printLamaInfo();
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

    public Lama findLamaFromName(String lamaName)
    {
        String query = "Select * FROM " + TABLE_LAMAS + "WHERE " + COLUMN_LAMANAME + " = \"" + lamaName +  "\"";
        SQLiteDatabase db = getReadableDatabase();

        Lama lama= new Lama();

        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        if (count == 1){
            if (cursor.moveToFirst())
            {
                //cursor.moveToFirst();
                lama.setId(Integer.parseInt(cursor.getString(0)));
                lama.setName(cursor.getString(1));
                lama.setFavoriteCocktail(Integer.parseInt(cursor.getString(2)));

                cursor.close();

                Log.v(TAG, "Database lama found");
            }
            else
            {
                Log.v(TAG, "Database no lama found");
            }

            db.close();
            return lama;
        }
        else{
            if (count > 1){
                Log.e(TAG, "ERROR : Multiple lamas found");
            }

            if ( count == 0 ){
                Log.e(TAG, "ERROR : NO lama found");
            }
        }
        //TODO if count > 1 ou count =0 on renvoit un lama empty...
        return lama;
    }

    public int getLamaId(String name){
        int lamaId = -1;
        Lama lama = findLamaFromName(name);
        lamaId = lama.getId();
        return lamaId;
    }
}