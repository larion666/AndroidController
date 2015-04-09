package com.example.larion.controller;

/**
 * Created by Larion on 09.04.2015.
 */
public interface OnVariablesChanged {
    public void onChange(float pitch, float roll,float yaw, float throttle);
    public void setOnVariablesChangedListener(OnVariablesChanged listener);
    public void awakeOnReleaseListener(float pitch,float roll,float yaw,float throttle);
}
