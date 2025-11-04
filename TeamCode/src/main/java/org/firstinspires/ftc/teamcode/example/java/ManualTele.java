package org.firstinspires.ftc.teamcode.example.java;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.example.java.Subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.example.java.Subsystems.Sequence;
import org.firstinspires.ftc.teamcode.example.java.Subsystems.Timer;
import org.firstinspires.ftc.teamcode.example.java.Subsystems.Trigger;
import org.firstinspires.ftc.teamcode.example.java.Subsystems.Turret;

@TeleOp
public class ManualTele extends LinearOpMode {
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
        FlyWheel flyWheel = new FlyWheel();
        flyWheel.initiate(hardwareMap);
        Trigger trigger = new Trigger();
        trigger.initiate(hardwareMap);
        Sequence sequence = new Sequence();
        States state = States.RESTING;
        waitForStart();


        Gamepad previousGamepad1 = new Gamepad();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
         //   boolean LB = (gamepad1.left_bumper && !previousGamepad1.left_bumper);
            boolean RB = (gamepad1.right_bumper && !previousGamepad1.right_bumper);
            boolean RT = (gamepad1.right_trigger >= .1 && previousGamepad1.right_trigger < .1);
            boolean LT = (gamepad1.left_trigger >= .1 && previousGamepad1.left_trigger < .1);
            previousGamepad1.copy(gamepad1);
            States newState = state;

            switch (state){
                case RESTING:
                    flyWheel.setState(FlyWheel.States.RESTING);
                    turret.setState(Turret.States.TRACKING);
                    break;
                case TRACKING:
                    if (flyWheel.readyToFire){
                        newState = States.READY;
                    }
                    break;
                case READY:
                   gamepad1.rumble(50);
                    break;
                case FIRING:
                    if (trigger.readyToFire){
                        newState = States.READY;
                    }
                    break;

            }
            if (LT){
                turret.reset();
                newState = States.RESTING;
            }
            if (RT){
                switch (state){
                    case RESTING:
                        flyWheel.spin();
                        newState = States.TRACKING;
                        break;
                    case READY:
                        newState = States.FIRING;
                        trigger.shoot();
                        break;
                }
            }

            state = newState;
            //Manual controls for turret rotate and flywheel spin

            turret.rotate(gamepad1.left_stick_x);

            flyWheel.update();
            trigger.update();
            turret.update();
            sequence.status(telemetry);
            turret.status(telemetry);
            flyWheel.status(telemetry);
            trigger.status(telemetry);
            telemetry.addData("State",state);
            telemetry.update();
        }
    }
}
