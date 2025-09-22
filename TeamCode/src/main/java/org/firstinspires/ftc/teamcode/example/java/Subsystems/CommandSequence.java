package org.firstinspires.ftc.teamcode.example.java.Subsystems;

import java.util.List;

public class CommandSequence {
    public boolean parallel(boolean[] commands){
        boolean ready = true;
        for (int i = 0; i< commands.length; i++){
            if (!commands[i]){
                ready = false;
            }
        }
        return ready;
    }
    public boolean sequential(List<Boolean> commands){
        boolean ready = true;
        for (int i = 0; i< commands.size(); i++){
            if (!commands.get(i)){
                ready = false;
                return ready;
            }
        }
        return ready;

    }
}
