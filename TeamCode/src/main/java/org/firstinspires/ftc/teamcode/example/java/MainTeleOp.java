package org.firstinspires.ftc.teamcode.example.java;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.example.java.Subsystems.Sequence;
import org.firstinspires.ftc.teamcode.example.java.Subsystems.Turret;

@TeleOp
public class MainTeleOp extends LinearOpMode {
public enum States{
    RESTING,
    TRACKING,
    READY,
    FIRING
}
    @Override
    public void runOpMode() throws InterruptedException {
        Turret turret = new Turret();
        turret.initiate(hardwareMap);
        Sequence sequence = new Sequence();
        States state = States.RESTING;
        waitForStart();


        Gamepad previousGamepad1 = new Gamepad();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            boolean LB = (gamepad1.left_bumper && !previousGamepad1.left_bumper);
            boolean RB = (gamepad1.right_bumper && !previousGamepad1.right_bumper);
            boolean RT = (gamepad1.right_trigger >= .1 && previousGamepad1.right_trigger < .1);
            boolean LT = (gamepad1.left_trigger >= .1 && previousGamepad1.left_trigger < .1);
            previousGamepad1.copy(gamepad1);
            States newState = state;
            if (LT){
                turret.reset();
            }
            if (RT){
                switch (state){
                    case RESTING:
                        newState = States.TRACKING;
                        turret.track();
                        break;
                    case READY:
                        newState = States.FIRING;
                        break;
                }
            }
            if (LB){
                sequence.idCycle();
            }
            if (RB){
                sequence.colorCycle();
            }
            state = newState;
            switch (state){
                case READY:
                    //FlyWheel off
                    //Trigger Back
                    break;
                case TRACKING:
                    //FlyWheel Spinning
                    //Trigger Back
                    //Check if all subsystems are ready
                    break;
            }

            turret.update();
            sequence.status(telemetry);
            telemetry.update();
        }
    }
}
