package com.example.moveintent;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /** Tag ke pesan log */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"TEST ONSTARLOADING() PADA EARTHQUAKELOADER DIPANGGIL");
        forceLoad();
    }

    /**
     * Hal ini ada di background thread.
     */

    @Override
    public List<Earthquake> loadInBackground() {
        Log.i(LOG_TAG,"TEST LOADINBACKGROUND() PADA EARTHQUAKELOADER DIPANGGIL");
        if (mUrl == null) {
            return null;
        }

        // Lakukan network request, uraikan respons, dan ekstrak daftar gempa.
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
