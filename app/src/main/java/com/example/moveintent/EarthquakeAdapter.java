package com.example.moveintent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> earthquake){
        super(context,0,earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String primaryLocation;
        String locationOffset;

        //mengecek apakah ada view yang bisa digunakan kembali,jika tidak maka view akan di inflate
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // mencari posisi pada Earthquake
        Earthquake currentEarthquake = getItem(position);
        /**
         // mencari TextView menggunakan id pada eathhquake_list_item.xml untuk menempatkan magnitudo
         TextView magnitudoView = (TextView) listItemView.findViewById(R.id.magnitude);
         // Mengambil text magnitudo pada objek currentEarthquake
         // sset text tersebut pada textView
         magnitudoView.setText(currentEarthquake.getMagnitudo());//get ini mengembalikan data menggunakan method pada Earthq*/

        // Cari TextView dengan view ID magnitude
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Format magnitudo agar menampilkan 1 angka di belakang koma

        // Tampilkan magnitudo gempa di TextView
        magnitudeView.setText(""+currentEarthquake.getDate());

        /**
         TextView locationView = (TextView) listItemView.findViewById(R.id.location);
         locationView.setText(currentEarthquake.getLocation());
         String originalLocation = currentEarthquake.getLocation(); */

        TextView locationView = (TextView) listItemView.findViewById(R.id.location);
        locationView.setText(currentEarthquake.getLocation());



        //Jika sudah ada 2 String terpisah, bisa kita tampilkan dalam 2 TextViews di tata letak list item.




        // Buat objek Date baru dari waktu gempa dalam satuan milidetik




        /**TextView dateView = (TextView) listItemView.findViewById(R.id.date);
         dateView.setText(currentEarthquake.getDate());*/



        // Return list item layout yang terdiri dari 3 TextView
        // sehingga muncul pada listView
        return listItemView;
    }

    //ini variabel? yg akan digunakan untk memisahkan 1 String jadi 2


    /**
     * Kembalikan string tanggal terformat ("Mar 3, 1984") dari objek Date.
     */



    /**
     * Kembalikan string magnitudo terformat yang menampilkan 1 angka belakang koma ("3.2")
     * dari nilai magnitudo desimal.
     */


    /**
     *  method helper untuk
     */

    /**
     * untuk mengubah color resource ID menjadi nilai warna bilangan bulat sebenarnya, dan
     * mengembalikan hasilnya sebagai return value dari metode helper getMagnitudeColor().
     */
}


