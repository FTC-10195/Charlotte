package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Sequence {
    public enum Colors{
        RED,
        GREEN,
        BLUE
    }
    public int selectedID = 0;
    Colors[] colorSequence  = {Colors.RED,Colors.RED,Colors.RED}; // Default sequence
    Colors selectedColor = colorSequence[selectedID];
    public void colorCycle(){
       switch (selectedColor){
           case RED:
               selectedColor = Colors.GREEN;
               break;
           case GREEN:
               selectedColor = Colors.BLUE;
               break;
           case BLUE:
               selectedColor = Colors.RED;
               break;
       }
    }
    public void idCycle(){
        selectedID += 1;
        if (selectedID > 2){
            selectedID = 0;
        }
    }
    public void status(Telemetry telemetry){
        telemetry.addData("First (0)",colorSequence[0]);
        telemetry.addData("Second (1)",colorSequence[1]);
        telemetry.addData("Third (2)",colorSequence[2]);
        telemetry.addData("selectedId",selectedID);
        telemetry.addData("selectedColor",selectedColor);
    }
}
