package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Turret {
    public enum States{
        RESTING,
        RESETING,
        TRACKING
    }
    States currentState = States.RESTING;
    Servo turret;
    public static double resetTime = 300;
    public static double trackTime = 300;
    public static double restPosition = 0.5;
    public static double targetPos = 0.5;
    long timeSnapshot = System.currentTimeMillis();
    public void initiate(HardwareMap hardwareMap){
        turret = hardwareMap.servo.get("turret");
    }
    public void setState(States newState){
        currentState = newState;
    }
    public void reset(){
        if (System.currentTimeMillis() - timeSnapshot > resetTime && currentState == States.RESTING){
            timeSnapshot = System.currentTimeMillis();
            currentState = States.RESETING;
        }
    }
    public void track(){
        if (System.currentTimeMillis() - timeSnapshot > trackTime && currentState == States.RESTING){
            timeSnapshot = System.currentTimeMillis();
            currentState = States.TRACKING;
        }
    }
    public void update(){
        switch (currentState){
            case RESTING:
                break;
            case RESETING:
                turret.setPosition(restPosition);
                if (System.currentTimeMillis() - timeSnapshot > resetTime){
                    currentState = States.RESTING;
                }
                break;
            case TRACKING:
                if (System.currentTimeMillis() - timeSnapshot > trackTime){
                    currentState = States.RESTING;
                }
                turret.setPosition(targetPos);
                break;
        }
    }
}
