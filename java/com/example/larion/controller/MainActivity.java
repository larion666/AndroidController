package com.example.larion.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    TextView pitch_text;
    TextView roll_text;
    TextView yaw_text;
    TextView throttle_text;
    Button roll_right;
    Button pitch_increase;
    Button pitch_decrease;
    Button roll_left;
    Button yaw_right;
    Button yaw_left;
    Button throttle_increase;
    Button throttle_decrease;
    Button Go_to_activity_2;
    OnVariablesChanged variablesChangedListener;
    float pitch=0;
    float roll=0;
    float yaw=0;
    int throttle=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pitch_text = (TextView) findViewById(R.id.pitch_text);
        roll_text = (TextView) findViewById(R.id.roll_text);
        yaw_text = (TextView) findViewById(R.id.yaw_text);
        throttle_text = (TextView) findViewById(R.id.throttle_text);
        pitch_increase = (Button) findViewById(R.id.pitch_increase);
        pitch_decrease = (Button) findViewById(R.id.pitch_decrease);
        roll_right = (Button) findViewById(R.id.roll_right);
        roll_left = (Button) findViewById(R.id.roll_left);
        yaw_right = (Button) findViewById(R.id.yaw_right);
        yaw_left = (Button) findViewById(R.id.yaw_left);
        throttle_increase = (Button) findViewById(R.id.throttle_increase);
        throttle_decrease = (Button) findViewById(R.id.throttle_decrease);
        Go_to_activity_2 = (Button) findViewById(R.id.Go_to_activity_2);
        variablesChangedListener= new SendDataToQuadcopter();
        variablesChangedListener.setOnVariablesChangedListener(variablesChangedListener);
        View.OnTouchListener pitch_increase_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        pitch=15;
                        Log.i("pitch", Float.toString(pitch));
                        pitch_text.setText(String.valueOf(pitch));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        pitch=0;
                        Log.i("pitch", Float.toString(pitch));
                        pitch_text.setText(String.valueOf(pitch));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                }
                return false;
            }
        };
        View.OnTouchListener pitch_decrease_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        pitch=-15;
                        pitch_text.setText(String.valueOf(pitch));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        pitch=0;
                        pitch_text.setText(String.valueOf(pitch));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                }
                pitch_text.setText(String.valueOf(pitch));
                return false;
            }
        };
        View.OnTouchListener roll_right_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        roll=-15;
                        roll_text.setText(String.valueOf(roll));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        roll=0;
                        roll_text.setText(String.valueOf(roll));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                }
                return false;
            }
        };
        View.OnTouchListener roll_left_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        roll=15;
                        roll_text.setText(String.valueOf(roll));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        roll=0;
                        roll_text.setText(String.valueOf(roll));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                }
                pitch_text.setText(String.valueOf(pitch));
                return false;
            }
        };
        View.OnTouchListener yaw_right_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        yaw=15;
                        yaw_text.setText(String.valueOf(yaw));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        yaw=0;
                        yaw_text.setText(String.valueOf(yaw));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                }

                return false;
            }
        };
        View.OnTouchListener yaw_left_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        yaw=-15;
                        yaw_text.setText(String.valueOf(yaw));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        yaw=0;
                        yaw_text.setText(String.valueOf(yaw));
                        variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        break;
                    }
                }
                pitch_text.setText(String.valueOf(pitch));
                return false;
            }
        };
        View.OnTouchListener throttle_increase_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        if(throttle+10<=100) {
                            throttle += 10;
                            throttle_text.setText(String.valueOf(throttle));
                            variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        }
                    }
                    case MotionEvent.ACTION_UP:{

                    }
                }

                return false;
            }
        };
        View.OnTouchListener throttle_decrease_listener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                switch (action & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:{
                        if(throttle-10>=0) {
                            throttle -= 10;
                            throttle_text.setText(String.valueOf(throttle));
                            variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle);
                        }
                    }
                    case MotionEvent.ACTION_UP:{

                    }
                }
                return false;
            }
        };
        pitch_increase.setOnTouchListener(pitch_increase_listener);
        pitch_decrease.setOnTouchListener(pitch_decrease_listener);
        roll_right.setOnTouchListener(roll_right_listener);
        roll_left.setOnTouchListener(roll_left_listener);
        yaw_right.setOnTouchListener(yaw_right_listener);
        yaw_left.setOnTouchListener(yaw_left_listener);
        throttle_increase.setOnTouchListener(throttle_increase_listener);
        throttle_decrease.setOnTouchListener(throttle_decrease_listener);
        //myTextView.setText(String.valueOf(counter));
    }
    public void GoToVirtualJoystick(View view){
        Intent intent = new Intent(this, VirtualJoystick.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };
    public void GoToGyroscope(View view){
        Intent intent = new Intent(this, GyroscopeControl.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

}
