package com.example.larion.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * Created by Larion on 13.03.2015.
 */
public class DualJoystick extends RelativeLayout {
    private Draw leftJ;
    private Draw rightJ;
    private View pad;
    public DualJoystick(Context context) {
        super(context);
        leftJ = new Draw(context);
        rightJ = new Draw(context);
        init();
    }

    public DualJoystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        leftJ = new Draw(context, attrs);
        rightJ = new Draw(context, attrs);
        init();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean l = leftJ.onTouchEvent(ev);
        boolean r = rightJ.onTouchEvent(ev);
        return l || r;
    }
    public void init(){
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, 400);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(400, 400);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        leftJ.setParameters(200,200,true);
        rightJ.setParameters(200,200,false);
        layout.removeView(leftJ);
        layout.removeView(rightJ);
        layout.addView(leftJ, params);
        Log.i("Added", "first joystick");
        layout.addView(rightJ, params2);
        Log.i ("Added","second joystick");
        invalidate();
    }
    public void setOnCoordinateChangedListener(OnCoordinateChanged left, OnCoordinateChanged right) {
        leftJ.setOnCoordinateChangedListener(left);
        rightJ.setOnCoordinateChangedListener(right);
    }
}
