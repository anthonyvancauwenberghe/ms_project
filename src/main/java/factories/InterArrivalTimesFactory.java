package factories;

public class InterArrivalTimesFactory {
    protected double[] times;

    public InterArrivalTimesFactory(double [] times) {
        this.times = times;
    }

    public double[] build() {

        double[] array = new double[this.times.length];

        for (int i = 0; i < this.times.length; i++) {
            if (i == 0)
                array[i] = this.times[i];
            else
                array[i] = this.times[i] - this.times[i-1];
        }

        return array;
    }
}
