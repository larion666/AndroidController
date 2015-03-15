package com.example.larion.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Larion on 10.03.2015.
 */


public class Draw extends View implements OnReleaseListener {
    private Paint p;
    private Draw releaselistener;
    private float x;
    private float y;
    private float offsetX=0;
    private float offsetY=0;
    private float previousX;
    private float previousY;
    private float centerX;
    private float centerY;
    private int circleRadius=60;
    private float circleBigX;
    private float circleBigY;
    private int circleBigRadius=130;
    private OnCoordinateChanged coordinatesChangedListener;
    private OnReleaseListener releaseListener;
    public Draw(Context context) {
        super(context);
        initJoystick();
    }

    public Draw(Context context, AttributeSet attrs) {
        super(context, attrs);
        initJoystick();
    }

    public Draw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initJoystick();
    }

    public void onDraw(Canvas canvas) {
        p.setColor(Color.GRAY);
        //Log.i ("Coordinates",String.valueOf(x));
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(circleBigX, circleBigY, circleBigRadius, p);
        p.setColor(Color.LTGRAY);
        if(isInsideCircle()) {
            canvas.drawCircle(x-offsetX, y+offsetY, circleRadius, p);
            previousX=x;
            previousY=y;
        }else{
            canvas.drawCircle(previousX, previousY, circleRadius, p);
        }
    }
    public void setOnCoordinateChangedListener(OnCoordinateChanged listener)
    {
        this.coordinatesChangedListener = listener;
    }
    public void awakeCoordinatesListener()
    {
        if(coordinatesChangedListener != null)
        {
            coordinatesChangedListener.onChange(getX(),getY());
        }
    }
    public void setOnReleaseListener(OnReleaseListener listener)
    {
        this.releaseListener = listener;
    }
    public void awakeOnReleaseListener()
    {
        if(releaseListener != null)
        {
            releaseListener.onRelease();
        }
    }
    public boolean onTouchEvent(MotionEvent ev){
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:{
                awakeOnReleaseListener();
                offsetX=0;
                offsetY=0;
            }
            break;
            case MotionEvent.ACTION_MOVE:{
                x=ev.getX();
                y=ev.getY();
                if (isInsideSmallCircle(x,y)) {
                    if (isInsideCircle()) {
                        awakeCoordinatesListener();
                        invalidate();
                    }
                }
            }
            break;
            case MotionEvent.ACTION_DOWN:{
                float a;
                float b;
                a=ev.getX();
                b=ev.getY();
                if (isInsideSmallCircle(a,b)) {
                    offsetX=a-centerX;
                    offsetY=centerY-b;
                    awakeCoordinatesListener();
                    Log.i ("offsetX",String.valueOf(offsetX));
                    Log.i ("offsetY",String.valueOf(offsetY));
                }
            }
            break;
        }
        return true;
    }
    public boolean isInsideCircle(){
        return(Math.pow((x - circleBigX-offsetX),2) + Math.pow((y - circleBigY+offsetY),2) < Math.pow(circleBigRadius,2));

    }
    public boolean  isInsideSmallCircle(float pointX, float pointY){
        return(Math.pow((pointX - previousX),2) + Math.pow((pointY - previousY),2) < Math.pow(circleRadius,2));
    }
    public float getX(){
        if(x - centerX==0) {
            //Log.i ("Released",String.valueOf(width));
            return Math.round(x - centerX);
        }else {
            return Math.round(x - centerX - offsetX);
        }
    }
    public float getY(){
        if(centerY-y==0) {
            //Log.i ("Released",String.valueOf(width));
            return Math.round(centerY-y);
        }else {
            return Math.round(centerY-y-offsetY);
        }
        //Log.i ("Released",String.valueOf(height));

    }
    public void onRelease(){
        Log.i ("Released","True");
        x=circleBigX;
        y=circleBigY;
        awakeCoordinatesListener();
        invalidate();
    }
    public void initJoystick(){
        p = new Paint();
        releaselistener = (Draw)findViewById(R.id.view);
        releaselistener.setOnReleaseListener(this);
        setMinimumHeight(100);
        setMinimumWidth(100);
    }
    public void setParameters(float cX,float cY){
        x=previousX=centerX=circleBigX=cX;
        y=previousY=centerY=circleBigY=cY;
    }
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(160, 160);
    }*/

}