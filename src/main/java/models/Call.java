package models;

import enums.CallType;

public class Call {

    protected double totalCallDuration;

    protected int timeInCall = 0;

    protected int queueTime = 0;

    protected CallType type;

    public Call(double callDuration, CallType type) {
        this.totalCallDuration = callDuration;
        this.type = type;
    }

    public double getCallDurationMinutes() {
        return totalCallDuration / 60;
    }

    public void incrementQueueTime() {
        queueTime++;
    }

    public void incrementTimeInCall() {
        this.timeInCall++;
    }

    public boolean isFinished() {
        return timeInCall >= totalCallDuration;
    }

    public boolean isCorporate(){
        return this.isOfType(CallType.CORPORATE);
    }

    public boolean isConsumer(){
        return this.isOfType(CallType.CONSUMER);
    }

    protected boolean isOfType(CallType type){
        return this.type.equals(type);
    }

}
