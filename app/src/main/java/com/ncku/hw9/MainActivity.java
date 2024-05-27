package com.ncku.hw9;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = openOrCreateDatabase("tree", Context.MODE_PRIVATE, null);
        String createTable ="CREATE TABLE IF NOT EXISTS test"+"(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "tree_id VARCHAR(8), species VARCHAR(32), lat VARCHAR(32), lng VARCHAR(32), collect VARCHAR(1))";
        db.execSQL(createTable);
        add(0,"大葉山欖", 22.9991684, 120.2219696, false);
        add(1,"大葉桉", 22.99954987, 120.2208862, false);
        add(2,"毛柿", 22.99824142, 120.2210388, false);
        add(3,"水黃皮", 22.99701309, 120.2219086, false);
        add(4,"台灣欒樹", 22.99701309, 120.2217331, false);
        add(5,"光蠟樹", 22.99794388, 120.2219162, false);
        add(6,"芒果樹", 22.99785423, 120.221283, false);
        add(7,"金龜樹", 22.99882507, 120.2214661, false);
        add(8,"雨豆樹", 22.99797058, 120.2219086, false);
        add(9,"洋紫荊", 22.99901009, 120.2208786, false);
        add(10,"美人樹", 22.99822235, 120.2213593, false);
        add(11,"苦揀", 22.99874687, 120.2216187, false);
        add(12,"茄苳", 22.99719048, 120.2204208, false);
        add(13,"香頻婆", 22.99756622, 120.221138, false);
        add(14,"桃花心木", 22.99764824, 120.220871, false);
        add(15,"涼傘樹", 22.99752045, 120.2205429, false);
        add(16,"菩提樹", 22.99837685, 120.2205582, false);
        add(17,"黑板樹", 22.99958229, 120.2205505, false);
        add(18,"圓柏", 22.99886322, 120.2215881, false);
        add(19,"椰子樹", 22.99832726, 120.2209091, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(e.getAction()==MotionEvent.ACTION_DOWN){
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
            //finish();
        }
        return true;
    }

    void add(int tree_id, String species, double lat, double lng, boolean collect){
        ContentValues contentValues =new ContentValues(5);
        contentValues.put("tree_id", tree_id);
        contentValues.put("species", species);
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);
        contentValues.put("collect", collect);
        db.insert("test",null,contentValues);

    }

}

