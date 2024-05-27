package com.ncku.hw9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;
    CountDownTimer countDownTimer = null;
    TextView remain_cut;
    SQLiteDatabase db;


    int times=10;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ImageView iv = findViewById(R.id.tree);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        AlertDialog.Builder bdr = new AlertDialog.Builder(MainActivity4.this);
        TextView mTextField = findViewById(R.id.tvvv);
        remain_cut = findViewById(R.id.tvvvvvvvvv);
        remain_cut.setText("剩餘次數:"+times);
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText("時間倒數: " + millisUntilFinished / 1000 +"秒");
            }
            public void onFinish() {
                mTextField.setText("失敗ㄌQQ");
                bdr.setMessage("失敗ㄌQQ").setTitle("").setNegativeButton("好", null).show();
//                startActivity(new Intent(MainActivity4.this, MainActivity2.class));
                finish();

            }
        }.start();

        db = openOrCreateDatabase("tree", Context.MODE_PRIVATE, null);
        Intent it = getIntent();
        select_ID = it.getIntExtra("ID", 0);

        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this, MainActivity6.class));
                finish();
            }
        });
        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this, MainActivity5.class));
                finish();
            }
        });
    }
    int select_ID;

//    void cancelTimer() {
//        if(countDownTimer!=null)
//            countDownTimer.cancel();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(MainActivity4.this, sensor, Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(MainActivity4.this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ImageView iv = findViewById(R.id.tree);
        TextView tv = findViewById(R.id.tvvv);
        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        x=(-(x/19.6f)+0.5f);
        y=(0.5f+(y/19.6f));


        if((Math.abs(x)+Math.abs(y))>=6 && times>=0){
            remain_cut.setText("剩餘次數:"+times);
            times-=1;
        }
        if(times==0){
            countDownTimer.cancel();

            ContentValues values = new ContentValues();
            values.put("collect", 1); // 使用1表示true
            db.update("test", values, "tree_id=?", new String[]{String.valueOf(select_ID)});

            sensorManager.unregisterListener(MainActivity4.this);
            startActivity(new Intent(MainActivity4.this, MainActivity5.class));
            finish();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}