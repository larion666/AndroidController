package com.example.larion.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class VirtualJoystick extends ActionBarActivity {
    TextView text1, text2;
    DualJoystick joystick;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtualjoystick_activity);
        joystick = (DualJoystick)findViewById(R.id.view);
        joystick.setOnCoordinateChangedListener(_listenerLeft, _listenerRight);
        //view.setOnCoordinateChangedListener(this);
        //view2 = new Draw(context);
        //view2.setOnCoordinateChangedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.virualjoystick_menu_activity, menu);
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
    public void GoToButtons(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

    private OnCoordinateChanged _listenerLeft = new OnCoordinateChanged() {
        @Override
        public void onChange(float x, float y) {
            final TextView text1 = (TextView) findViewById(R.id.textView2);
            text1.setText(Float.toString(Math.round(x))+" "+Float.toString(Math.round(y)));
        }
    };
    private OnCoordinateChanged _listenerRight = new OnCoordinateChanged() {
        @Override
        public void onChange(float x, float y) {
            final TextView text2 = (TextView) findViewById(R.id.textView3);
            text2.setText(Float.toString(Math.round(x))+" "+Float.toString(Math.round(y)));
        }
    };
}