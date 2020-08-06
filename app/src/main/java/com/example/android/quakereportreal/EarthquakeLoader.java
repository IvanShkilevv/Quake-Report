package com.example.android.quakereportreal;

import android.content.Context;

import android.content.AsyncTaskLoader;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader <List<Earthquake>> {
    private  String url;
    public static final String LOG_TAG = "CHECKING LOADER, EarLod";

    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
        Log.i(LOG_TAG, "constructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i(LOG_TAG, "onStartLoading()");
    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground()");
        ArrayList<Earthquake> earthquakesResult= QueryUtils.fetchEarthquakeData(url);
        return earthquakesResult;
    }


}
