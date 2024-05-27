package com.ncku.hw9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class MainActivity6 extends AppCompatActivity implements SensorEventListener, View.OnLongClickListener {
    SensorManager sensorManager;
    Sensor sensor;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        ImageView grow_tree = findViewById(R.id.grow_tree);
        Button button = findViewById(R.id.button14);
        ImageView water = findViewById(R.id.water);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        button.setOnLongClickListener(MainActivity6.this);
        water.setVisibility(View.INVISIBLE);
        Button button11 = findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity6.this, MainActivity2.class));
                finish();
            }
        });
        Button button13 = findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity6.this, MainActivity5.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(MainActivity6.this, sensor, Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(MainActivity6.this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        ImageView water = findViewById(R.id.water);

        x=(-(x/19.6f)+0.5f);
        y=(0.5f+(y/19.6f));
        if((Math.abs(x)+Math.abs(y))>=5){
            water.setVisibility(View.VISIBLE);
        }
        else if((Math.abs(x)+Math.abs(y))<1){
            water.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onLongClick(View view) {
        CheckBox checkBox;
        checkBox = findViewById(R.id.checkBox);
        ImageView grow_tree = findViewById(R.id.grow_tree);
        ImageView grow_tree2 = findViewById(R.id.grow_tree_2);
        ImageView grow_tree3 = findViewById(R.id.grow_tree_3);
        ImageView grow_tree4 = findViewById(R.id.grow_tree_4);
        ImageView grow_tree5 = findViewById(R.id.grow_tree_5);
        grow_tree.setVisibility(View.INVISIBLE);
        grow_tree2.setVisibility(View.INVISIBLE);
        grow_tree3.setVisibility(View.INVISIBLE);
        grow_tree4.setVisibility(View.INVISIBLE);
        grow_tree5.setVisibility(View.INVISIBLE);
        i+=1;
        if(checkBox.isChecked()){
            i+=1;
        }
        switch (i) {
            case 1:
                grow_tree.setVisibility(View.VISIBLE);
                break;
            case 2:
                grow_tree2.setVisibility(View.VISIBLE);
                break;
            case 3:
                grow_tree3.setVisibility(View.VISIBLE);
                break;
            case 4:
                grow_tree4.setVisibility(View.VISIBLE);
                break;
            case 5:
                grow_tree5.setVisibility(View.VISIBLE);
                break;
            default:
                grow_tree5.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }
}