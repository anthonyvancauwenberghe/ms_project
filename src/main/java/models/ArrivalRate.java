package models;

public class ArrivalRate {
    protected double[] data;

    public ArrivalRate(double[] data) {
        this.data = data;
    }

    public double getArrivalRate(int hour, int minute, int second) {
        if (this.data.length < 24 * 60 * 60)
            throw new RuntimeException("The arrival rate resolution is not defined in seconds");

        return this.data[hour * 3600 + minute * 60 + second];
    }

    public double getArrivalRate(int hour, int minute) {
        return this.data[hour * 60 + minute];
    }

    public double[] getData() {
        return this.data;
    }
}
