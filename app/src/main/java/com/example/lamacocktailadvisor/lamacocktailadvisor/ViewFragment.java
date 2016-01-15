package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Emilie on 14/07/2015.
 */
public class ViewFragment extends Fragment {

    private static final String TAG= "ViewFragment";
    private RecyclerView mRecyclerView;
    private SessionAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        Log.v(TAG, "***onCreateView***");

        View v = inflater.inflate(R.layout.fragment_view, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.cocktail_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
        }

    void updateUI(){
        SessionBook sessionBook = SessionBook.get(getActivity());
        List<Session> sessions = sessionBook.getSessions(getActivity());
        mAdapter = new SessionAdapter(sessions);
        mRecyclerView.setAdapter(mAdapter);

    }


    private class SessionHolder extends RecyclerView.ViewHolder{

        private Session mSession;

        private TextView mNameTextView;
        private TextView mCocktailTextView;
        private RatingBar mRating;
        private TextView mDateTextView;

        public SessionHolder(View itemView){
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name_edit_text);
            mCocktailTextView = (TextView) itemView.findViewById(R.id.list_item_cocktail_edit_text);
            mRating = (RatingBar) itemView.findViewById(R.id.list_item_rating_rating_bar);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_edit_text);
        }

        void bindSession(Session session){
            mSession = session;
            Context ctx= getContext();
            LamaDataBaseHandler DBLama = LamaDataBaseHandler.getInstance(ctx);
            CocktailDataBaseHandler DBCocktail = CocktailDataBaseHandler.getInstance(ctx);

            Lama lama = DBLama.findLamaFromId(mSession.getLamaId());
            String lamaName = "";
            if(lama != null){
                lamaName = lama.getName();
            }

            Cocktail cocktail = DBCocktail.findCocktailFromId(mSession.getCocktailId());
            String cocktailName = "";
            if(cocktail != null){
                cocktailName = cocktail.getName();
            }

            mNameTextView.setText(lamaName);
            mCocktailTextView.setText(cocktailName);
            mRating.setRating(mSession.getGrade());
            mDateTextView.setText("today");

        }

    }
    private class SessionAdapter extends RecyclerView.Adapter<SessionHolder>{
        private List<Session> mSessionList;

        public SessionAdapter(List<Session> sessionList){
            mSessionList = sessionList;
        }

        @Override
        public int getItemCount(){
            return mSessionList.size();
        }

        @Override
        public SessionHolder onCreateViewHolder(ViewGroup parent, int viewType){
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new SessionHolder(view);
        }

        @Override
        public void onBindViewHolder(SessionHolder holder, int position){
            Session session = mSessionList.get(position);
            if(session != null) {
                holder.bindSession(session);
            }
            else
            {
                Log.e(TAG, "Error cocktail null in onBindViewHolder in ViewFragment");
            }
        }
    }
}
