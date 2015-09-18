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

public class SessionDataBaseHandler extends SQLiteOpenHelper{
    private static SessionDataBaseHandler sessionDBInstance = null;

    public static synchronized SessionDataBaseHandler getInstance(Context context){
        if (sessionDBInstance == null){
            sessionDBInstance = new SessionDataBaseHandler((context.getApplicationContext()));
        }
        return sessionDBInstance;
    }

    private static final String TAG = "Session";

    //VERSION 1
    private static final String DATABASE_SESSION_NAME = "sessionDB.sqlite";
    private static final int 	SESSION_DATABASE_VERSION = 1;

    public static final String TABLE_SESSIONS = "sessions";

    public static final String COLUMN_SESSIONNUMBER = "sessionnumber";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COCKTAILID = "cocktailid";
    public static final String COLUMN_LAMAID = "lamaid";
    public static final String COLUMN_GRADE = "grade";

    public SessionDataBaseHandler(Context context) {
        super(context, DATABASE_SESSION_NAME, null, SESSION_DATABASE_VERSION);
        Log.v(TAG, "Database constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_SESSION_TABLE = "CREATE TABLE " +
                TABLE_SESSIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_SESSIONNUMBER + " INTEGER,"
                + COLUMN_COCKTAILID + " INTEGER,"
                + COLUMN_LAMAID + " INTEGER,"
                + COLUMN_GRADE + " REAL" + ")";
        db.execSQL(CREATE_SESSION_TABLE);
        Log.v(TAG, "Database session created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO in better version : compare tables, not drop all table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        onCreate(db);

        Log.v(TAG, "onUpgrade: com.example.lamacocktailadvisor.lamacocktailadvisor.Session Database has been upgraded");
    }


    public void addEntryInSession (int sessionNum, int cocktailId, int lamaId, float rating ){

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SESSIONNUMBER, sessionNum);
            values.put(COLUMN_COCKTAILID, cocktailId);
            values.put(COLUMN_LAMAID, lamaId);
            values.put(COLUMN_GRADE, rating);

            try {
                SQLiteDatabase db = getWritableDatabase();
                db.insertOrThrow(TABLE_SESSIONS, null, values);
                db.close();
            } catch (SQLException e) {
                Log.e(TAG,"SQL Error in addEntryInSession", e);
            }

            Log.v(TAG, "Database entry added");

            //TODO check if lama's favorite cocktail changed
        }
        catch(Throwable t){
            Log.e(TAG, "Error in addEntryInSession. ", t);
        }
    }


    public void deleteAllEntryInSessionsInDB()
    {
        String query = "Select * FROM " + TABLE_SESSIONS ;
        SQLiteDatabase db = getWritableDatabase();

        int count = 0;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++)
        {
            count++;
            db.delete(TABLE_SESSIONS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(cursor.getString(0)) });
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        Log.v(TAG, "deleteAllEntryInSessionsInDB: " + count + " entries deleted from database");
    }

    public void printEntriesInSessionsInDB(){
        String query = "Select * FROM " + TABLE_SESSIONS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int totalEntries = cursor.getCount();
        Log.v(TAG, "printEntriesInSessionsInDB:  " + totalEntries + " entries in the database");
        if (totalEntries < 1){
            Log.v(TAG, "printEntriesInSessionsInDB: No entry to print");
        }
        else{
            try{
                cursor.moveToFirst();
                for (int i = 0; i < totalEntries; i++) {

                    Session session= new Session();

                    session.setId(Integer.parseInt(cursor.getString(1)));
                    session.setSessionNumber(Integer.parseInt(cursor.getString(1)));
                    session.setCocktail(Integer.parseInt(cursor.getString(2)));
                    session.setLama(Integer.parseInt(cursor.getString(3)));
                    session.setGrade(Integer.parseInt(cursor.getString(4)));

                    session.printSessionInfo();
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