package abstracts;

public abstract class Call {
    protected double callDuration;

    public Call(double callDuration) {
        this.callDuration = callDuration;
    }

    public double getCallDuration() {
        return callDuration / 60;
    }
}
