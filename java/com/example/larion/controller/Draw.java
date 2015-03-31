package com.example.larion.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Larion on 10.03.2015.
 */


public class Draw extends View implements OnReleaseListener {
    private Paint p;
    private Draw releaselistener;
    private float x;
    private float y;
    private float tempX;
    private float tempY;
    private float a;
    private float b;
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
    private boolean autoReturnToCenter;
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
        //canvas.drawColor(Color.WHITE);
        canvas.drawCircle(circleBigX, circleBigY, circleBigRadius, p);
        p.setColor(Color.LTGRAY);
        if(isInsideCircle(x,y)) {
            canvas.drawCircle(x, y, circleRadius, p);
            previousX=x;
            previousY=y;
        }else{
            canvas.drawCircle(previousX, previousY, circleRadius, p);
            //canvas.drawCircle(previousX, previousY, circleRadius, p2);
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
                if(autoReturnToCenter) {
                    awakeOnReleaseListener();
                }
                offsetX = 0;
                offsetY = 0;
                invalidate();
            }
            break;
            case MotionEvent.ACTION_MOVE:{
                tempX=ev.getX();
                tempY=ev.getY();
                if (isInsideCircle(tempX, tempY)){
                    if (isInsideSmallCircle(tempX, tempY)) {
                        x=tempX-offsetX;
                        y=tempY+offsetY;
                        awakeCoordinatesListener();
                        invalidate();
                        }
                }else {
                    offsetX=0;
                    offsetY=0;
                    x=circleBigX-(circleBigRadius*(float)Math.sin(getAngle(tempX, tempY)));
                    //if(x<0){x = Math.abs(x);}else{x=-(x);}
                    //Log.i ("C",String.valueOf(tempX));
                    y=circleBigY-(circleBigRadius*(float)Math.cos(getAngle(tempX, tempY)));
                    //if(y<0){x = Math.abs(x);}else{y=-(y);}
                    //Log.i ("D",String.valueOf(tempY));
                    awakeCoordinatesListener();
                    invalidate();
                }
            }
            break;
            case MotionEvent.ACTION_DOWN:{
                a=ev.getX();
                b=ev.getY();
                //Log.i ("C",String.valueOf(tempX));
                //Log.i ("D",String.valueOf(tempY));
                if(isInsideCircle(a,b)) {
                    if (isInsideSmallCircle(a, b)) {
                        Log.i ("PressedDown","True");
                        offsetX = a - previousX;
                        offsetY = previousY - b;
                        awakeCoordinatesListener();
                        //Log.i ("offsetX",String.valueOf(offsetX));
                        //Log.i ("offsetY",String.valueOf(offsetY));
                    }
                }
            }
            break;
        }
        return true;
    }
    public boolean isInsideCircle(float pointX, float pointY){
        return(Math.pow((pointX - circleBigX-offsetX),2) + Math.pow((pointY - circleBigY+offsetY),2) < Math.pow(circleBigRadius,2));

    }
    public boolean  isInsideSmallCircle(float pointX, float pointY){
        return(Math.pow((pointX - previousX),2) + Math.pow((pointY - previousY),2) < Math.pow(circleRadius,2));
    }
    public float getX(){
        return Math.round(x - centerX);
    }
    public float getY(){
        return Math.round(centerY-y);
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
    public void setParameters(float cX,float cY, boolean autoReturn){
        x=previousX=centerX=circleBigX=cX;
        y=previousY=centerY=circleBigY=cY;
        autoReturnToCenter=autoReturn;
    }
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(160, 160);
    }*/
    public float getAngle(float pointX, float pointY){
        float angleInDegrees;
        float deltaX;
        float deltaY;
        deltaX=circleBigX-pointX;
        deltaY=circleBigY-pointY;
        angleInDegrees=(float)/*Math.toDegrees(*/Math.atan2(deltaX,deltaY);
        /*if(angleInDegrees > 0){
            angleInDegrees -= 360;
        }*/
        //angleInDegrees=Math.abs(angleInDegrees);
        return angleInDegrees;
    }
}