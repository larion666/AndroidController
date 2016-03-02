package com.example.larion.controller;

/**
 * Created by Larion on 09.04.2015.
 */
public interface OnVariablesChanged {
    public void onChange(float pitch, float roll,float yaw, float throttle, int isArm,float pid_P_Coef,float pid_I_Coef,float pid_D_Coef);
    public void setOnVariablesChangedListener(OnVariablesChanged listener);
    public void awakeOnReleaseListener(float pitch,float roll,float yaw,float throttle, int isArm,float pid_P_Coef,float pid_I_Coef,float pid_D_Coef);
}
