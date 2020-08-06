package com.example.android.quakereportreal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public final class QueryUtils {

    public static final String LOG_TAG = "QueryUtils";
    private QueryUtils() {
    }

    public  static ArrayList<Earthquake> fetchEarthquakeData(String requestUrl){
        Log.i(LOG_TAG, "fetchEarthquakeData()");
        URL url = createUrlObject(requestUrl);
        String JSONdata = null;
            try {
        JSONdata = makeHttpRequest(url);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception in fetchEarthquakeData()", e); }

        ArrayList<Earthquake> earthquakes = parseJSON(JSONdata);
    return earthquakes;
    }

    private  static URL createUrlObject(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private  static String makeHttpRequest(URL url) throws  IOException{
        String jsonResponse = "";
        if (url ==null) {return jsonResponse;}
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readDataFromStream(inputStream); }
            else {Log.e(LOG_TAG, "Failure with HTTP request");}
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException in makeHtpRequest method");
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

    private  static String readDataFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = reader.readLine();
            while (line!=null) {
             output.append(line);
             line = reader.readLine();
            }
        }
    return output.toString();
    }


    private  static ArrayList<Earthquake> parseJSON(String JSONdata) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject jsonGlobalObject = new JSONObject(JSONdata);
            JSONArray jsonArray= jsonGlobalObject.getJSONArray("features");
            for (int i = 0; i < jsonArray.length(); i++) {
               JSONObject jsonPropertiesObject= jsonArray.optJSONObject(i)
                      .getJSONObject("properties");

                String place = jsonPropertiesObject.optString("place");
                double magnitude = jsonPropertiesObject.optDouble("mag");
                long timeInMilliseconds = jsonPropertiesObject.optLong("time");
                String URL = jsonPropertiesObject.optString("url");

               earthquakes.add(new Earthquake(place, magnitude, timeInMilliseconds, URL));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

}