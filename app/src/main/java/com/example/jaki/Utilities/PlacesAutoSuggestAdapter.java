package com.example.jaki.Utilities;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;

public class PlacesAutoSuggestAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> results;
    int resource;
    Context context;
    PlacesFetcher placeApi = new PlacesFetcher();

    public PlacesAutoSuggestAdapter(Context context, int resId){
        super(context, resId);
        this.context = context;
        this.resource = resId;
    }

    @Override
    public int getCount(){
        return results.size();
    }

    @Override
    public String getItem(int pos){
        return results.get(pos);
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null){
                    results = placeApi.autoComplete(constraint.toString() );
                    filterResults.values = results;
                    filterResults.count = results.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                if (results != null && results.size() > 0){
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

}
