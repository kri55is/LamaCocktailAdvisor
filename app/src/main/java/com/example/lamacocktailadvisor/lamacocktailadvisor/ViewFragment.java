package com.example.lamacocktailadvisor.lamacocktailadvisor;

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
    private CocktailAdapter mAdapter;


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
        CocktailBar cocktailBar = CocktailBar.get(getActivity());
        List<Cocktail> cocktails = cocktailBar.getCocktails(getActivity());

        mAdapter = new CocktailAdapter(cocktails);
        mRecyclerView.setAdapter(mAdapter);

    }


    private class CocktailHolder extends RecyclerView.ViewHolder{

        private Cocktail mCocktail;

        private TextView mNameTextView;
        private TextView mCocktailTextView;
        private RatingBar mRating;
        private TextView mDateTextView;

        public  CocktailHolder(View itemView){
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name_edit_text);
            mCocktailTextView = (TextView) itemView.findViewById(R.id.list_item_cocktail_edit_text);
            mRating = (RatingBar) itemView.findViewById(R.id.list_item_rating_rating_bar);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_edit_text);
        }

        void bindCocktail(Cocktail cocktail){
            mCocktail = cocktail;
            mNameTextView.setText("dummyName");
            mCocktailTextView.setText(mCocktail.getName());
            mRating.setRating(3);
            mDateTextView.setText("today");

        }

    }
    private class CocktailAdapter extends RecyclerView.Adapter<CocktailHolder>{
        private List<Cocktail> mCocktailList;

        public CocktailAdapter(List<Cocktail> cocktailList){
            mCocktailList = cocktailList;
        }

        @Override
        public int getItemCount(){
            return mCocktailList.size();
        }

        @Override
        public CocktailHolder onCreateViewHolder(ViewGroup parent, int viewType){
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new CocktailHolder(view);
        }

        @Override
        public void onBindViewHolder(CocktailHolder holder, int position){
            Cocktail cocktail = mCocktailList.get(position);
            if(cocktail != null) {
                holder.bindCocktail(cocktail);
            }
            else
            {
                Log.e(TAG, "Error cocktail null in onBindViewHolder in ViewFragment");
            }
        }
    }
}
