package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Turret {
    public enum States{
        RESTING,
        RESETING,
        TRACKING
    }
    public enum Mode{
        MANUAL,
        SEQUENCE
    }
    States currentState = States.RESTING;
    Mode currentMode = Mode.MANUAL;
    Servo turret;
    Timer timer = new Timer();
    public static long resetTime = 300;
    public static long trackTime = 300;
    public static double restPosition = 0.5;
    public double targetPos = 0.5;
    public static double kP = .005; //Rotate sensitivity FOR CV
    public static double manualRotateSensitiviy = .005; //Rotate sensitivity FOR MANUAL
    public void initiate(HardwareMap hardwareMap){
        turret = hardwareMap.servo.get("turret");
    }
    public void setState(States newState){
        currentState = newState;
    }
    public States getState(){
        return currentState;
    }
    public void setMode(Mode newMode){
        currentMode = newMode;
    }
    public Mode getMode(){
        return currentMode;
    }

    public void reset(){
        if (timer.isWaitComplete() && currentState == States.RESTING){
            timer.setWait(resetTime);
            currentState = States.RESETING;
        }
    }
    public void track(){
        if (timer.isWaitComplete() && currentState == States.RESTING){
            timer.setWait(trackTime);
            currentState = States.TRACKING;
        }
    }
    //Manually rotate
    public void rotate(double input){
        targetPos = targetPos + (input * manualRotateSensitiviy);
        if (targetPos > 1){
            targetPos = 1;
        }
        if (targetPos < 0){
            targetPos = 0;
        }
    }
    public void update(){
        switch (currentState){
            case RESTING:
                break;
            case RESETING:
                turret.setPosition(restPosition);
                if (timer.isWaitComplete()){
                    currentState = States.RESTING;
                }
                break;
            case TRACKING:
                //ONLY STOP TRACKING AUTOMATICALLY IF ON SEQUENCE MODE!!!
                if (timer.isWaitComplete() && currentMode == Mode.SEQUENCE){
                    currentState = States.RESTING;
                }
                turret.setPosition(targetPos);
                break;
        }
    }
    public void status (Telemetry telemetry){
        telemetry.addData("Turret",currentState);
        telemetry.addData("turretTargetPos",targetPos);
    }
}
