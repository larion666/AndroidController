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
    OnVariablesChanged variablesChangedListener;
    int pitchTemp=0;
    int rollTemp=0;
    int pitch=0;
    int roll=0;
    int yaw=0;
    int throttle=0;
    int isArm=1;
    float pid_P_Coef=1.04f;
    float pid_I_Coef=0.002f;
    float  pid_D_Coef=0.35f;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtualjoystick_activity);
        joystick = (DualJoystick)findViewById(R.id.view);
        joystick.setOnCoordinateChangedListener(_listenerLeft, _listenerRight);
        variablesChangedListener= new SendDataToQuadcopter();
        variablesChangedListener.setOnVariablesChangedListener(variablesChangedListener);
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
            pitch=roundVariableIfPositive(Math.round(x));
            roll=roundVariableIfPositive(Math.round(y));
            if(pitch!=pitchTemp||roll!=rollTemp){
                variablesChangedListener.awakeOnReleaseListener(pitch,roll,yaw,throttle,isArm,pid_P_Coef,pid_I_Coef,pid_D_Coef);
                Log.i("Pitch value",String.valueOf(pitch));
                Log.i("Roll value",String.valueOf(roll));
            }

            text1.setText(Float.toString(Math.round(x))+" "+Float.toString(Math.round(y)));
            pitchTemp=pitch;
            rollTemp=roll;
        }
    };
    private OnCoordinateChanged _listenerRight = new OnCoordinateChanged() {
        @Override
        public void onChange(float x, float y) {
            final TextView text2 = (TextView) findViewById(R.id.textView3);
            text2.setText(Float.toString(Math.round(x))+" "+Float.toString(Math.round(y)));
        }
    };
    public int roundVariableIfPositive(float var){
        return (int)(var - 0) * (15) / (130);
    }
}