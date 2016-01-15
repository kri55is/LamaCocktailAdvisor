package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilie on 21/09/2015.
 */
public class SessionBook {
    private static SessionBook sSessionBook;
    private List<Session> mSessionList;

    private SessionBook(Context context){

        Log.v("SessionBook", "Create SessionBook");
        mSessionList = new ArrayList<>();
            }

    static SessionBook get(Context context){
        if (sSessionBook == null){
            sSessionBook = new SessionBook(context);
        }
        return sSessionBook;
    }

    public Session getSession(int id){
        for (Session session : mSessionList){
            if(session.getId() == id){
                return session;
            }
        }
        return null;
    }

    public List<Session> getSessions(Context context){
        //delete things in the list, might be old
        mSessionList.clear();

        //get sessions count in database
        SessionDataBaseHandler db = SessionDataBaseHandler.getInstance(context);
        int count = db.getSessionCount();
        //get the sessions
        //WARNING id in database starts at 1, not 0!!!
        for (int i = 1; i <= count; i++){
            Session session= db.findSessionFromId(context, i);
            if(session != null)
            {
                mSessionList.add(session);
            }
        }

        return mSessionList;
    }
}
