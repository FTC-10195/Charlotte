package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlyWheel {
    public enum States {
        RESTING,
        SPINNING
    }

    States flyState = States.RESTING;
    DcMotor flyMotor; //Right dominant

    public void initiate(HardwareMap hardwareMap) {
        flyMotor = hardwareMap.dcMotor.get("fly");
    }

    public void setState(States state) {
        flyState = state;
    }

    public States getState() {
        return flyState;
    }

    public void update() {
        switch (flyState) {
            case RESTING:
                flyMotor.setPower(0);
                break;
            case SPINNING:
                flyMotor.setPower(1);
                break;
        }

    }
}
