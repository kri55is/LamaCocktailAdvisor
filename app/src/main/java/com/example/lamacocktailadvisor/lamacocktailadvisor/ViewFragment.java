package com.example.lamacocktailadvisor.lamacocktailadvisor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public TextView mTitleTextView;

        public  CocktailHolder(View itemView){
            super(itemView);

            mTitleTextView = (TextView) itemView;
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
                View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                return new CocktailHolder(view);
            }

            @Override
            public void onBindViewHolder(CocktailHolder holder, int position){
                Cocktail cocktail = mCocktailList.get(position);
                if(cocktail != null) {
                    holder.mTitleTextView.setText(cocktail.getName());
                }
                else
                {
                    Log.e(TAG, "Error cocktail null in onBindViewHolder in ViewFragment");
                }
            }
        }
}
