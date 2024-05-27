package com.ncku.hw9;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    GoogleMap mMap;
    @Override
    public void onMapReady(@androidx.annotation.NonNull GoogleMap googleMap) {
        mMap = googleMap;
        reQuery();
    }

    LocationManager lm;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LatLng tree_hb;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity2.this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MainActivity2.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity2.this);
        db = openOrCreateDatabase("tree", Context.MODE_PRIVATE, null);
        reQuery();
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity6.class));
                finish();
            }
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity5.class));
                finish();
            }
        });

        //mMap.addMarker(db);
    }

    Cursor cursor;
    void reQuery(){

        cursor = db.rawQuery("SELECT * FROM test", null);
        if(cursor.moveToFirst()) {
            do {
                int ID = Integer.parseInt(cursor.getString(1));
                String species = cursor.getString(2);
                double lat = Double.parseDouble(cursor.getString(3));
                double lng = Double.parseDouble(cursor.getString(4));
                boolean collect = cursor.getInt(5) == 1;
                targetPoint = new LatLng(lat, lng);

                if(mMap!=null) {
                    if(collect){
                        mMap.addMarker(new MarkerOptions().position(targetPoint).title(species).icon(BitmapDescriptorFactory.fromResource(R.drawable.star)));
                    } else{
                        int iconResourceId = R.drawable.unknown_tree_icon;
                        mMap.addMarker(new MarkerOptions().position(targetPoint).title(Integer.toString(ID)).icon(BitmapDescriptorFactory.fromResource(iconResourceId)));
                    }

                }
                //mMap.addMarker();
                //textView.append(species + "   " + lat + "   " + lng + "   (Location)" + collect + "\n");
            }while (cursor.moveToNext());
        }
    }


    LatLng currPoint;
    LatLng targetPoint;
    List<Address> ad2;
    double lat;
    double lng;
    int select_ID;
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double current_lat = location.getLatitude();
        double current_lng = location.getLongitude();

        EditText editText = findViewById(R.id.edit);
        String target = editText.getText().toString();
        Button button = findViewById(R.id.button);

        if(location!=null && currPoint ==null){
            currPoint = new LatLng(location.getLatitude(), location.getLongitude());
            if(mMap!=null){
//                mMap.clear();
                int iconResourceId = R.drawable.current_pos_icon;
                mMap.addMarker(new MarkerOptions().position(currPoint).title("目前位置").icon(BitmapDescriptorFactory.fromResource(iconResourceId)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currPoint));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            }
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText choose_ID = findViewById(R.id.edit);
                cursor = db.rawQuery("SELECT * FROM test", null);

                if (cursor.moveToFirst()) {
                    // 移动到id的位置
                    select_ID = Integer.parseInt(choose_ID.getText().toString());
                    cursor.moveToPosition(select_ID);
                    String species = cursor.getString(2);
                    lat = cursor.getDouble(3);
                    lng = cursor.getDouble(4);
                    boolean collect = cursor.getInt(5) == 1;
                    //textView.setText(species);
                    cursor.close();
                    if (lm != null) {
                        lm.removeUpdates(MainActivity2.this);
                    }
                    Intent it = new Intent(MainActivity2.this, MainActivity3.class);
                    it.putExtra("緯度", lat);
                    it.putExtra("經度", lng);
                    it.putExtra("ID", select_ID);
                    startActivity(it);
                    finish();
                }


            }
        });
        if (lm != null) {
            lm.removeUpdates(MainActivity2.this);
        }
//        double distance = getDistance(current_lat,current_lng,lat,lng);
//        DecimalFormat df = new DecimalFormat("0");
//        TextView textView = findViewById(R.id.distance);
//        textView.setText("距離："+df.format(distance)+"m");
//
//        double x_dir = Math.toRadians(lng-current_lng);
//        double y_dir = Math.toRadians(lat-current_lat);
//
//        double angle = (Math.atan(x_dir/y_dir))*180/Math.PI;
//        if(y_dir<0) angle += 180;
//        if(x_dir<0 && y_dir>0) angle += 360;
//        ImageView imageView = findViewById(R.id.imageView6);
//        imageView.setRotation((float) angle-90);
//
//        if(distance<500){
//            if (lm != null) {
//                lm.removeUpdates(MainActivity2.this);
//            }
//            startActivity(new Intent(MainActivity2.this, MainActivity3.class));
//            finish();
//        }
    }





    private static final double EARTH_RADIUS = 6371000; // 平均半径,单位：m；不是赤道半径。赤道为6378左右
    public static double getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);

        double acos = Math.acos(cos); // 反余弦值

        return EARTH_RADIUS * acos; // 最终结果
    }
}