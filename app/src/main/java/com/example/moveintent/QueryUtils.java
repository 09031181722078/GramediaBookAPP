package com.example.moveintent;

import android.text.TextUtils;
import android.util.Log;

import com.example.moveintent.Earthquake;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** Waktu gempa */
    private long mTimeInMilliseconds;

    /**
     * Private Constructor artinya tidak ada yang bole membuat instance dari kelas ini
     * ini adalah kelas utility  shg berisi variabel static method stactic dimana kita bisa memenggil method ini scr langsung
     * tanpa harus memiliki instance objec dari kelas
     */
    private QueryUtils() {
    }

    /**
     * Kembalikan daftar objek {@link Earthquake} yang sudah terbuat dari
     * parsing respon JSON yang sudah ada.
     */
    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        // Jika string JSON kosong atau nol, segera kembalilah.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Buat ArrayList kosong yang dapat kita masuki gempa ke
        List<Earthquake> earthquakes = new ArrayList<>();

        // Coba parse string respons JSON. Jika ada masalah dengan pemformatan JSON
        // objek exception JSONException akan dimunculkan.
        // Tangkap perkecualiannya agar aplikasi tidak mengalami crash, dan cetak pesan error-nya ke log.
        try {

            // Buat JSONObject dari string respons JSON
            JSONArray baseJsonResponse = new JSONArray(earthquakeJSON);

            for (int i = 0; i < baseJsonResponse.length(); i++) {

                JSONObject terserah = baseJsonResponse.getJSONObject(i);

                JSONArray earthquakeArray = terserah.getJSONArray("formats");

                for (int j = 0; j < earthquakeArray.length(); j++) {

                    JSONObject x = earthquakeArray.getJSONObject(j);

                    String location = x.getString("name");
                    int date=x.getInt("basePrice");
                    String url =x.getString("href");

                    Earthquake earthquake = new Earthquake(location, date,url);

                    // Tambahkan {@link Earthquake} baru ke daftar gempa.
                    earthquakes.add(earthquake);
                }



                // Ekstrak JSONArray yang berhubungan dengan kunci bernama "features",
                // yang mewakili daftar fitur (atau gempa).


                //long timeInMilliseconds = 1454124312220L;
                //Date dateObject = new Date(timeInMilliseconds);

                //SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
                //String dateToDisplay = dateFormatter.format(dateObject);

                // Ekstrak nilai untuk kunci bernama "url"

                // Buat objek {@link Earthquake} baru dengan magnitudo, lokasi, waktu,
                // dan url dari response JSON.ß


            }
        } catch (JSONException e) {
            // Jika error dimunculkan saat mengeksekusi pernyataan apapun di dalam blok "try",
            // tangkap perkecualiannya di sini, agar aplikasi tidak crash. Cetak pesan log dengan
            // pesan dari perkecualian.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // kembalikan daftar gempa
        return earthquakes;
    }

    /**
     * Kembalikan objek URL baru dari string URL yang ada.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Buat HTTP request ke URL yang ada dan kembalikan sebuah String sebagai respons.
     */
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

            // Jika request berhasil (kode respons 200),
            // maka baca input stream dan lakukan parse atas respons.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
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
                // Menutup input stream bisa memunculkan IOException, karena itu
                // lambang metode (method signature) makeHttpRequest(URL url) harus menyatakan IOException
                // dapat dimunculkan.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Ubah {@link InputStream} menjadi String yang mengandung seluruh
     * respon JSON dari server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Query dataset USGS dan kembalikan daftar objek {@link Earthquake}.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        Log.i(LOG_TAG,"TEST fetchEarthquakeData() PADA QUERYUTILS DIPANGGIL");


        //UNTUK WAKTU LOADING DATA
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Buat objek URLß
        URL url = createUrl(requestUrl);

        // Buat HTTP request ke URL dan terima kembali respons JSON
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Ekstrak field yang relevan dari respons JSON dan buatlah daftar {@link Earthquake}
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Kembalikan daftar {@link Earthquake}
        return earthquakes;
    }


}