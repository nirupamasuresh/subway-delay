package com.predict.nirupama.predict.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.predict.nirupama.predict.activity.MainActivity;
import com.predict.nirupama.predict.R;

import java.util.Map;

public class CustomSpinnerAdapter extends ArrayAdapter {
    private static final String TAG = "CustomSpinnerAdapter";
    Map<Integer, MainActivity.Route> routeMap;

    public CustomSpinnerAdapter(MainActivity context, Map<Integer, MainActivity.Route> map) {
        super(context, 0);
        Log.i(TAG, "Created Adapter");
        routeMap = map;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView img = convertView.findViewById(R.id.imageView_train);
        Log.i(TAG, "Getting routemap for position: " + routeMap.get(position));
        MainActivity.Route currentRoute = routeMap.get(position);
        if(currentRoute != null){
            img.setImageResource(currentRoute.getImage());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return routeMap.size();
    }
}
