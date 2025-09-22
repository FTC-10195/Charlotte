package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Config
public class FlyWheel {
    public enum States {
        RESTING,
        SPINNING
    }

    States currentState = States.RESTING;
    DcMotor flyMotor;
    Timer timer = new Timer();

    public static double maxPower = 1;
    public static double powerChangeSensitivity = .005;
    public static long spinChargeUpTime = 500;
    double power = 0;

    public void initiate(HardwareMap hardwareMap) {
        flyMotor = hardwareMap.dcMotor.get("fly");
    }

    public void setState(States currentState) {
        this.currentState = currentState;
    }

    public States getState() {
        return currentState;
    }
    public void modifyPower(double p){
        power += (p * powerChangeSensitivity);
    }
    public void spin(){
        if (currentState == States.RESTING){
            timer.setWait(spinChargeUpTime);
            setState(States.SPINNING);
        }
    }
    public boolean readyToFire = false;

    public void update() {
        switch (currentState) {
            case RESTING:
                power = 0;
                break;
            case SPINNING:
                if (timer.isWaitComplete()){
                    readyToFire = true;
                }
                break;
        }
        flyMotor.setPower(power * maxPower);
    }
}
