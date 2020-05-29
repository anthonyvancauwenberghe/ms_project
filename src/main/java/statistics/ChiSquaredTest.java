package statistics;


public class ChiSquaredTest {

    /*
     * chi squared based on the frequencies of the observed vs distribution
     */
    public double chiSquare(double[] distributionData, double[] observeddData) {
        double totalDistribution = 0.0;
        double totalObserved = 0.0;

        this.checkConstraints(distributionData, observeddData);

        for (int i = 0; i < observeddData.length; i++) {
            totalDistribution += observeddData[i];
            totalObserved += observeddData[i];
        }

        double ratio = 1.0;
        boolean redo = false;
        if (Math.abs(totalDistribution - totalObserved) > 0.0000001) {
            ratio = totalObserved / totalDistribution;
            redo = true;
        }

        return this.calculate(distributionData, observeddData, redo, ratio);

    }

    private double calculate(double[] distributionData, double[] observeddData, boolean redo, double ratio) {
        double chi = 0.0d;
        double inter;
        for (int i = 0; i < observeddData.length; i++) {
            if (redo) {
                inter = observeddData[i] - ratio * distributionData[i];
                chi += inter * inter / (ratio * distributionData[i]);
            } else {
                inter = observeddData[i] - distributionData[i];
                chi += inter * inter / distributionData[i];
            }
        }
        return chi;
    }

    private void checkConstraints(double[] distributionData, double[] observeddData) {

        if (distributionData.length != observeddData.length ||
                distributionData.length < 2

        ) {
            throw new RuntimeException("arrays must be equal size and array lengt  > 2");
        }

    }
}
