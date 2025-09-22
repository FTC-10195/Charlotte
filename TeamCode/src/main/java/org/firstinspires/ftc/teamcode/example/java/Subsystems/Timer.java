package org.firstinspires.ftc.teamcode.example.java.Subsystems;

public class Timer {
   private long initTime = System.currentTimeMillis();
   private long waitTime = 0;
    private boolean waitComplete = true;
    public void setWait(long newWaitTime){
        initTime = System.currentTimeMillis();
        waitTime = newWaitTime;
    }
    public boolean isWaitComplete(){
        waitComplete = System.currentTimeMillis() - initTime > waitTime;
        return waitComplete;
    }
    public long timePassed(){
        return System.currentTimeMillis() - initTime;
    }
    public long getWaitTime(){
        return waitTime;
    }
}
