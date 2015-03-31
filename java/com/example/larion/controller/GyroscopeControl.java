package com.example.larion.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnTouchListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;


public class GyroscopeControl extends ActionBarActivity {

    SeekBar throttlebar;
    TextView pitchtext;
    TextView rolltext;
    TextView yawtext;
    TextView throttletext;
    Handler handler;
    private Runnable runnable;
    Thread thread;
    float pitch;
    float roll;
    float yaw;
    float pitchtemp=0;
    float rolltemp=0;
    float yawtemp=0;
    float throttle;
    boolean isFirstRun;
    float[] r=new float[9];
    float[] valuesResult=new float[3];
    float[] valuesAccel=new float[3];
    float[] valuesMagnet=new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyroscope_control_activity);
        pitchtext = (TextView) findViewById(R.id.pitch);
        rolltext = (TextView) findViewById(R.id.roll);
        yawtext = (TextView) findViewById(R.id.yaw);
        throttletext = (TextView) findViewById(R.id.throttle);
        throttlebar =(SeekBar) findViewById(R.id.throttleBar);
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        //boolean isFirstRun=true;
                        isFirstRun=true;
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (isFirstRun){
                                    getTempOrientation();
                                }
                                isFirstRun=false;
                                getOrientation();
                                handler.post(new Runnable(){
                                    public void run() {
                                        pitchtext.setText(Float.toString(Math.round(pitch)));
                                        rolltext.setText(Float.toString(Math.round(roll)));
                                        yawtext.setText(Float.toString(Math.round(yaw)));
                                    }
                                });
                                //Log.i("Test","true");
                                handler.postDelayed(this, 100);
                            }
                        };
                        thread = new Thread(runnable);
                        thread.start();
                        //getOrientation(true);
                    }
                    case MotionEvent.ACTION_UP:{
                        handler.removeCallbacks(runnable);
                        Log.i("Stoped","True");
                    }
                }
                return false;
            }
        });
        throttlebar.setOnSeekBarChangeListener (new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
               throttle = progressValue;
               throttletext.setText(Float.toString(throttle));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });
        //sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gyroscope_control_menu, menu);
        return true;
    }
    @Override
    protected void onStop() {
        super.onPause();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.unregisterListener(listener);
    }
    public void getOrientation(){
            //mTimer.schedule();
            SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
            SensorManager.getOrientation(r, valuesResult);
            valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
            valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
            valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
            yaw = valuesResult[0]-yawtemp;
            roll = valuesResult[1]-rolltemp;
            pitch = valuesResult[2]-pitchtemp;
            //pitchtext.setText(Float.toString(pitch));
            //rolltext.setText(Float.toString(roll));
            //yawtext.setText(Float.toString(yaw));
            //Log.i("Changed", "True");
    }
    public void getTempOrientation(){
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);
        valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
        valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
        valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
        yawtemp = valuesResult[0];
        rolltemp = valuesResult[1];
        pitchtemp = valuesResult[2];
        Log.i("pitchtemp", Float.toString(pitchtemp));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i=0; i < 3; i++){
                        valuesAccel[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i=0; i < 3; i++){
                        valuesMagnet[i] = event.values[i];
                    }
                    break;
            }
        }
    };
}
