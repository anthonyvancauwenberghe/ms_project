package contracts;

public interface ITInterval {

    public int degreesOfFreedom();

    public double sampleStd();

    public double sampleMean();

    public double criticalValue();

    public double upperBound();

    public double lowerBound();

    public int sampleSize();

    public double confidenceLevel();

}
