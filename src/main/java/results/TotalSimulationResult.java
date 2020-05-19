package results;

import java.util.List;

public class TotalSimulationResult {

    List<IterationSimulationResult> simulations;

    public TotalSimulationResult(List<IterationSimulationResult> simulations) {
        this.simulations = simulations;
    }

    public double[] getAverageServiceTimePerHour() {
        double[] serviceTimes = new double[24];
        for (int i = 0; i < simulations.size(); i++) {
            double[] simulationAverages = this.simulations.get(i).getAverageServiceTimePerHour();
            for (int h = 0; h < 24; h++) {
                serviceTimes[h] += (simulationAverages[h] / simulations.size());
            }
        }
        return serviceTimes;
    }

    public double[] getAverageCallsPerHour() {
        double[] calls = new double[24];
        for (int i = 0; i < simulations.size(); i++) {
            double[] simulationAverages = this.simulations.get(i).getAverageCallsPerHour();
            for (int h = 0; h < 24; h++) {
                calls[h] += (simulationAverages[h] / simulations.size());
            }
        }
        return calls;
    }

    public double getTotalAverageServiceTime() {
        double[] serviceTime = this.getAverageServiceTimePerHour();
        double totalAvgServiceTime = 0;

        for (int i = 0; i < serviceTime.length; i++) {
            totalAvgServiceTime += serviceTime[i];
        }
        return totalAvgServiceTime / serviceTime.length;
    }

}
