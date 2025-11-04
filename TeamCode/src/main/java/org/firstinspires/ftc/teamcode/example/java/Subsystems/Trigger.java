package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Trigger {

    public enum TriggerStates {
        RESTING,
        RETURNING,
        SHOOTING
    }

    TriggerStates triggerState = TriggerStates.RESTING;
    Servo trigger;
    Timer timer;
    public static double triggerResting = 0.5;
    public static double triggerShooting = 0.2;
    public static long shootTime = 200;
    public boolean readyToFire = true;

    public void initiate(HardwareMap hardwareMap) {
        trigger = hardwareMap.servo.get("trig");
        timer = new Timer();
    }

    public void shoot() {
        if (triggerState == TriggerStates.RESTING) {
            timer.setWait(shootTime);
            triggerState = TriggerStates.SHOOTING;
        }

    }

    public void update() {
        switch (triggerState) {
            case RESTING:
                readyToFire = true;
                trigger.setPosition(triggerResting);
                break;
            case RETURNING:
                readyToFire = false;
                trigger.setPosition(triggerResting);
                break;
            case SHOOTING:
                readyToFire = false;
                trigger.setPosition(triggerShooting);
                break;
        }
        if (timer.isWaitComplete() && timer.timePassed() < (2 * shootTime)) {
            triggerState = TriggerStates.RETURNING;
        } else if (timer.timePassed() > (shootTime * 2)) {
            triggerState = TriggerStates.RESTING;
        }
    }
    public void status(Telemetry telemetry){
        telemetry.addData("Trigger state",triggerState);
    }

}
