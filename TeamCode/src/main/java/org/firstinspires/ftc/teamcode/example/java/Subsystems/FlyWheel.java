package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class FlyWheel {
    public enum States {
        RESTING,
        PREPARING,
        SPINNING
    }

    States currentState = States.RESTING;
    DcMotor flyMotor;

    public static double maxPower = 1;
    public static double powerChangeSensitivity = .0005;
    public static long spinChargeUpTime = 500;
    public static double targetVelocity = 100; //Ticks Per Second, test number for now

    public void initiate(HardwareMap hardwareMap) {
        flyMotor = hardwareMap.dcMotor.get("fly");
        flyMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flyMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void setState(States currentState) {
        this.currentState = currentState;
    }

    public States getState() {
        return currentState;
    }
    public void modifyTargetVelocity(double p){
   /*     power += (p * powerChangeSensitivity);
        if (power < 0 && p < 0){
            power = 0;
        }
        if (power > 1){
            power = 1;
        }

    */
    }
    public void spin(){
        setState(States.PREPARING);
    }
    public boolean readyToFire = false;
    double previousPosition = 0;
    long previousTime = System.currentTimeMillis();
    public static double velocityTolerance = 50;
    public static double kP = .0006;
    public static double kI = 0;
    public static double kD = 0;
    PIDFController pidfController = new PIDFController(kP,kI,kD);
    double velocity = 0;
    public void update() {
        flyMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pidfController.setKP(kP);
        pidfController.setKI(kI);
        pidfController.setKD(kD);
        velocity = (flyMotor.getCurrentPosition() - previousPosition)/ ((System.currentTimeMillis() - previousTime)/1000.0000);
        double power = 0;
        switch (currentState) {
            case RESTING:
                readyToFire = false;
                break;
            case PREPARING:
                if (targetVelocity - velocity < velocityTolerance){
                    readyToFire = true;
                }
              //  power = pidfController.calculate(velocity, targetVelocity);
                power = maxPower;
                break;
            case SPINNING:
             //   power = pidfController.calculate(velocity, targetVelocity);
                power =  maxPower;
                break;
        }
        if (power > Math.abs(maxPower)){
            power = Math.signum(power) * maxPower;
        }
        flyMotor.setPower(power);
        previousPosition = flyMotor.getCurrentPosition();
        previousTime = System.currentTimeMillis();

    }
    public void status(Telemetry telemetry){
        telemetry.addData("FlyVelocity t/s", velocity);
        telemetry.addData("FlyVelocity error",targetVelocity - velocity);
        telemetry.addData("FlyState",currentState);
        telemetry.addData("FlyPos",flyMotor.getCurrentPosition());
        telemetry.addData("flywheel motor power", flyMotor.getPower());
    }

}
