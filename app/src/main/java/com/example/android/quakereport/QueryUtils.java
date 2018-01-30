package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
    /**fetch the data **/
    protected static ArrayList<EarthquakeData> fetchdata(String urlsite){
        Log.e(LOG_TAG,"TEST : fetchdata ");

        URL url=creatUrl(urlsite);
        String jsonresponse =null;
        try{
            jsonresponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Error with make http request");
        }
        return extractEarthquakes(jsonresponse);
    }



    /**
     * Returns new URL object from the given string URL.
     */
    private static URL creatUrl(String urlSite){
         URL url= null;

         try{
             url=new URL(urlSite);
         } catch (MalformedURLException e) {
             Log.e(LOG_TAG,"Error with url",e);
         }
         return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /* To convert input stream to string value*/
    private static String readFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder output=new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line=bufferedReader.readLine();
            }

        }
        return output.toString();
    }

    /**
     * Return a list of {@link EarthquakeData} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<EarthquakeData> extractEarthquakes(String JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakeData> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.


        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonobject=new JSONObject(JSON_RESPONSE);
            JSONArray FeatureArray=jsonobject.optJSONArray("features");
            for (int i=0;i<FeatureArray.length();i++){
                JSONObject jsonFeatureObject = FeatureArray.getJSONObject(i);
                JSONObject propertiesObject = jsonFeatureObject.getJSONObject("properties");
                double Magnitude=propertiesObject.optDouble("mag");
                String Place=propertiesObject.optString("place");
                long time=propertiesObject.optLong("time");
                String URLsite=propertiesObject.optString("url");
                EarthquakeData ob=new EarthquakeData(Magnitude,Place,time,URLsite);
                earthquakes.add(ob);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}