package com.ncku.hw9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.Provider;
import java.text.DecimalFormat;

public class MainActivity3 extends AppCompatActivity implements LocationListener{
    double lat;
    double lng;
    double current_lat;
    double current_lng;
    int select_ID;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity3.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity3.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) MainActivity3.this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) MainActivity3.this);
        TextView tv = findViewById(R.id.textView3);
        Intent it = getIntent();
        lat = it.getDoubleExtra("緯度", 0);
        lng = it.getDoubleExtra("經度", 0);
        select_ID = it.getIntExtra("ID", 0);

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        ImageView iv = findViewById(R.id.ivvvv);
        TextView tv = findViewById(R.id.textView3);
        current_lat = location.getLatitude();
        current_lng = location.getLongitude();
        double distance = getDistance(current_lat,current_lng,lat,lng);

        DecimalFormat df = new DecimalFormat("0");
        tv.setText("距離："+df.format(distance)+"m");

        double x_dir = Math.toRadians(lng-current_lng);
        double y_dir = Math.toRadians(lat-current_lat);

        double angle = (Math.atan(x_dir/y_dir))*180/Math.PI;
        if(y_dir<0) angle += 180;
        if(x_dir<0 && y_dir>0) angle += 360;
        iv.setRotation((float) angle-90);
        if(distance<2){
            if (lm != null) {
                lm.removeUpdates(MainActivity3.this);
            }
            Intent it = new Intent(MainActivity3.this, MainActivity4.class);
            it.putExtra("ID", select_ID);
            startActivity(it);
            finish();
        }
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