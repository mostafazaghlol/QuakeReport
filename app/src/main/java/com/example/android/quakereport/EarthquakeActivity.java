/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<EarthquakeData>> {
    //use for the textview .
    TextView textEmpty;
    //use for prograss bar
    ProgressBar p1;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    /**
     * Url to grab top 10 earthquakes in the world
     **/
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    //use for the adapter
    private customArrayAdaptor adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        /*checking internet connection*/
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        //set The textview id to a certain id.
        textEmpty = (TextView) findViewById(R.id.textToEmptyState);
        //set the Prograss bar to a certain id.
        p1 = (ProgressBar) findViewById(R.id.loading_spinner);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        //fill the adaptor with empty list.
        adapter = new customArrayAdaptor(this, new ArrayList<EarthquakeData>());

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            Log.i(LOG_TAG, "Here i am after the initialization of the loader");
            // Set the list to a new view if the adapter is empty.
            earthquakeListView.setEmptyView(textEmpty);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);
        } else {
            /* Otherwise, display error
             *First, hide loading indicator so error message will be visible
            */
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            textEmpty.setText(R.string.no_internet_connection);
        }

    }
    /*
     * Return  an object of  Loader<ArrayList<EarthquakeData>>
     */
    @Override
    public Loader<ArrayList<EarthquakeData>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "Here I return a new object of Earthquake Loader class");
        return new EarthquakeLoader(this, USGS_URL);
    }
    /*
     * here i can update the UI
     */
    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeData>> loader, ArrayList<EarthquakeData> earthquakeData) {
        // Clear the adapter of previous earthquake data
        adapter.clear();
        Log.i(LOG_TAG, "Updating the UI");
        textEmpty.setText(R.string.emptyStateMessage);
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakeData != null && !earthquakeData.isEmpty()) {
            p1.setVisibility(View.GONE);
            adapter.addAll(earthquakeData);
        } else {
            adapter.clear();
            textEmpty.setText(R.string.emptyStateMessage);
        }
    }
    /*
     * Clear Memory .
     */
    @Override
    public void onLoaderReset(Loader<ArrayList<EarthquakeData>> loader) {
        Log.i(LOG_TAG, "Clear the list");
        adapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

