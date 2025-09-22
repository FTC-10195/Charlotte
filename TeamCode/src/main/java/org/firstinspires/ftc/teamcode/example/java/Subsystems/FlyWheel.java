package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class FlyWheel {
    public enum States {
        RESTING,
        SPINNING
    }

    States currentState = States.RESTING;
    DcMotor flyMotor;
    Timer timer;

    public static double maxPower = 1;
    public static double powerChangeSensitivity = .005;
    public static long spinChargeUpTime = 500;
    double power = 1;

    public void initiate(HardwareMap hardwareMap) {
        flyMotor = hardwareMap.dcMotor.get("fly");
        timer = new Timer();
    }

    public void setState(States currentState) {
        this.currentState = currentState;
    }

    public States getState() {
        return currentState;
    }
    public void modifyPower(double p){
        power += (p * powerChangeSensitivity);
        if (power < 0){
            power = 0;
        }
        if (power > 1){
            power = 1;
        }
    }
    public void spin(){
        if (currentState == States.RESTING){
            timer.setWait(spinChargeUpTime);
        }
        setState(States.SPINNING);
    }
    public boolean readyToFire = false;

    public void update() {
        switch (currentState) {
            case RESTING:
                flyMotor.setPower(0);
                readyToFire = false;
                break;
            case SPINNING:
                if (timer.isWaitComplete()){
                    readyToFire = true;
                }
                flyMotor.setPower(power * maxPower);
                break;
        }

    }
    public void status(Telemetry telemetry){
        telemetry.addData("FlyWheel",getState());
        telemetry.addData("flywheel power", power);
        telemetry.addData("flywheel motor power", flyMotor.getPower());
        telemetry.addData("flywheel timer time", timer.timePassed());
        telemetry.addData("flywheel timer status", timer.getWaitTime());
    }

}
