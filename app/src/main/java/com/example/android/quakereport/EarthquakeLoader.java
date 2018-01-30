package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.quakereport.EarthquakeData;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mostafa on 1/5/18.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeData>> {
    private String mUrl;
    private static final String LOG_TAG=EarthquakeLoader.class.getName();

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.mUrl=url;
    }

    @Override
    public ArrayList<EarthquakeData> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        final ArrayList<EarthquakeData> earthquakes = QueryUtils.fetchdata(mUrl);
        Log.i(LOG_TAG,"Here i return you with the List of earthq");
        return earthquakes;
    }
}
