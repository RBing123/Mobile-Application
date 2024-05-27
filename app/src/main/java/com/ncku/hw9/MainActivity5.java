package com.ncku.hw9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity5 extends AppCompatActivity{
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        db = openOrCreateDatabase("tree", Context.MODE_PRIVATE, null);
        reQuery();

        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity5.this, MainActivity2.class));
                finish();
            }
        });
        Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity5.this, MainActivity6.class));
                finish();
            }
        });
    }
    Cursor cursor;
    void reQuery(){
        ImageView tree1 = findViewById(R.id.tree1);
        ImageView tree1b = findViewById(R.id.tree1b);
        ImageView tree2 = findViewById(R.id.tree2);
        ImageView tree2b = findViewById(R.id.tree2b);
        boolean collect = true;
        cursor = db.rawQuery("SELECT * FROM test", null);
        if(cursor.moveToFirst()) {
            do {
                collect = cursor.getInt(5) == 1;
                int ID = Integer.parseInt(cursor.getString(1));
                String species = cursor.getString(2);

                if(collect && ID == 1){
                    tree1b.setVisibility(View.INVISIBLE);
                    tree1.setVisibility(View.VISIBLE);
                }
                if(collect && ID == 2){
                    tree2b.setVisibility(View.INVISIBLE);
                    tree2.setVisibility(View.VISIBLE);
                }

            }while (cursor.moveToNext());
        }
    }
}
