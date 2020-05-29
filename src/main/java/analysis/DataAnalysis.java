package analysis;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class DataAnalysis {

/*    public void testDistributions(){

        public double chiSquare(final double[] expected, final long[] observed){

            if (expected.length < 2) {
                throw new DimensionMismatchException(expected.length, 2);
            }
            if (expected.length != observed.length) {
                throw new DimensionMismatchException(expected.length, observed.length);
            }
            MathArrays.checkPositive(expected);
            MathArrays.checkNonNegative(observed);

            double sumExpected = 0d;
            double sumObserved = 0d;
            for (int i = 0; i < observed.length; i++) {
                sumExpected += expected[i];
                sumObserved += observed[i];
            }
            double ratio = 1.0d;
            boolean rescale = false;
            if (FastMath.abs(sumExpected - sumObserved) > 10E-6) {
                ratio = sumObserved / sumExpected;
                rescale = true;
            }
            double sumSq = 0.0d;
            for (int i = 0; i < observed.length; i++) {
                if (rescale) {
                    final double dev = observed[i] - ratio * expected[i];
                    sumSq += dev * dev / (ratio * expected[i]);
                } else {
                    final double dev = observed[i] - expected[i];
                    sumSq += dev * dev / expected[i];
                }
            }
            return sumSq;

        }
    }*/
}
