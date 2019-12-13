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
package com.example.moveintent;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FourthActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    /** TextView yang ditampilkan jika daftar kosong */
    private TextView mEmptyStateTextView;

    /**
     * Nilai konstanta untuk ID loader gempa. Pilih angka integral manapun.
     * Ini hanya akan tampak hasilnya jika menggunakan beberapa loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String LOG_TAG = FourthActivity.class.getName();

    /** URL untuk data gempa dari dataset USGS */
    private static final String USGS_REQUEST_URL =
            "https://www.gramedia.com/api/products/";

    /** Adapter untuk daftar gempa */
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG,"TEST EARTHQUAKE ACTIVITY DIPANGGIL");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //TIGA BARIS DI BAWAH INI AWALNYA DILETAKKAN DI BAWAH PENGATURAN NO INTERNET CONNECTION DI BAWAH INI
        //SEHINGGA APPLIKASI CRASH KARENA
        //mEmptyStateTextView TIDAK DITEMUKAN TEMPATNYA(?)
        //TIDAK BISA MENEMUKAN TEMPAT TEXTVIEWNYA(?) sejenis itulah

        // Cari referensi pada {@link ListView} pada layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Ambil referensi ke LoaderManager untuk berinteraksi dengan loader.
            LoaderManager loaderManager = getLoaderManager();

            // Inisialisasi loader. Masukkan konstanta int ID yang dinyatakan di atas dan masukkan
            // null untuk bundle-nya. Masukkan aktivitas ini untuk parameter LoaderCallbacks (yang valid
            // karena aktivitas ini mengimplementasikan antarmuka LoaderCallbacks).
            Log.i(LOG_TAG, "TEST INITLOADER() DIPANGGIL");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        // Buat adapter baru yang mengambil daftar kosong gempa sebagai inputnyaåå
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Atur adapter di {@link ListView}
        // agar daftar dapat diisi di antarmuka pengguna
        earthquakeListView.setAdapter(mAdapter);

        // Atur item click listener di ListView, yang mengirimkan intent ke perambah web
        // untuk membuka situs web dengan lebih banyak informasi mengenai gempa tertentu.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Temukan gempa terkini yang diklik di
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Ubah String URL menjadi objek URI (untuk memasuki konstruktor Intent)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Buat intent baru untuk melihat URI gempa
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Kirimkan intent agar membuka aktivitas baru
                startActivity(websiteIntent);
            }
        });

    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG,"TEST ONCREATELOAER() DIPANGGIL");
        // Buat loader baru dengan parameter context dan URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.i(LOG_TAG,"TEST ONLOADFINISHED() DIPANGGIL");

        // Sembunyikan indikator loading sebab datanya sudah dimuat
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Atur teks status kosong agar menampilkan "Tidak ditemukan gempa."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Bersihkan adapter dari data gempa sebelumnya
        mAdapter.clear();

        // Jika terdapat daftar yang valid dari {@link Earthquake}s, maka tambahkan ke adapter
        // Ini akan memicu pembaruan ListView.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i(LOG_TAG,"TEST ONLOADERRESET() DIPANGGIL");

        // Loader direset, jadi kita dapat bersihkan data.
        mAdapter.clear();
    }


}
