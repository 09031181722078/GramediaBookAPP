package com.example.moveintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_move_profil = (Button) findViewById(R.id.btn_move_profil);
        Button btn_move_justjava = (Button) findViewById(R.id.btn_move_justjava);
        Button btn_move_galeri = (Button) findViewById(R.id.btn_move_galeri);

        btn_move_profil.setOnClickListener(this);
        btn_move_justjava.setOnClickListener(this);
        btn_move_galeri.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_move_profil:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.btn_move_justjava:
                startActivity(new Intent(this, ThirdActivity.class));
                break;
            case R.id.btn_move_galeri:
                startActivity(new Intent(this, FourthActivity.class));
                break;
        }
    }
}
