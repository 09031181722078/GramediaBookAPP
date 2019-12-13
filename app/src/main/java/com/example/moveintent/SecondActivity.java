package com.example.moveintent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of words
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Rp.99.000", "Sukses Tanpa Modal", R.drawable.satubuku));
        words.add(new Word("Rp.200.000", "Insider GUIDE to Jakarta", R.drawable.duabuku));
        words.add(new Word("Rp.154.000", "Sweet And Yummy", R.drawable.tigabuku));
        words.add(new Word("Rp.149.000", "Cake From My Kitchen", R.drawable.empatbuku));
        words.add(new Word("Rp.55.000", "UKM Jaman Now", R.drawable.limabuku));
        words.add(new Word("Rp.178.000", "Serba Serbi Baking", R.drawable.enambuku));
        words.add(new Word("Rp.52.800", "Cara Dahsyat Belajar Bisnis", R.drawable.tujuhbuku));
        words.add(new Word("Rp.89.800", "The King Of Property", R.drawable.delapanbuku));
        words.add(new Word("Rp.115.000", "The Alibaba Way", R.drawable.sembilanbuku));
        words.add(new Word("Rp.95.000", "Ngopi Yuk!", R.drawable.sepuluhbuku));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter
        // knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
    }

}
