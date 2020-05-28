package analysis;

import configs.AnalysisConfig;
import configs.BusinessConstraintsConfig;
import configs.SimulationConfig;

import java.text.DecimalFormat;

public class SimulationAnalysis {

    protected SinkAnalysisAggregator consumer;
    protected SinkAnalysisAggregator corporate;

    public SimulationAnalysis(SinkAnalysisAggregator consumer, SinkAnalysisAggregator corporate) {
        this.consumer = consumer;
        this.corporate = corporate;
    }

    public void execute() {


        if (AnalysisConfig.PLOT_QUEUE_TIMES) {
            consumer.plotAvgMinutelyQueueTimesWithConfidence(0.99, true);
            corporate.plotAvgMinutelyQueueTimesWithConfidence(0.99, true);
        }


        if (AnalysisConfig.PLOT_ARRIVALS) {
            consumer.plotAvgMinutelyArrivals();
            corporate.plotAvgMinutelyArrivals();
        }

        if (AnalysisConfig.PLOT_SERVICE_TIMES) {
            consumer.plotAvgServiceTimeProbabilities();
            corporate.plotAvgServiceTimeProbabilities();
        }


        if (AnalysisConfig.ANALYZE_BUSINESS_CONSTRAINTS)
            this.analyzeBusinessConstraints();

    }

    public void analyzeBusinessConstraints() {

        double confidence = AnalysisConfig.MIN_BUSINESS_CONSTRAINT_CONFIDENCE;

        String confidenceString = confidence * 100 + "% ";
        double[] fiveMinuteProbability = consumer.avgProbabilityQueueTimeLessThanWithConfidence(BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_1 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);
        double[] tenMinuteProbability = consumer.avgProbabilityQueueTimeLessThanWithConfidence(BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_2 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);

        System.out.println("");
        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("CONSUMER QUEUE TIME PROBABILITIES");
        System.out.println("--------------------------");
        System.out.println("sample mean " + BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_1 + " min: " + new DecimalFormat("##.00").format(fiveMinuteProbability[2] * 100) + " % - " + (fiveMinuteProbability[2] >= BusinessConstraintsConfig.CONSUMER_PERCENTAGE_CONSTRAINT_1 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "conf " + BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_1 + " min: " + new DecimalFormat("##.00").format(fiveMinuteProbability[0] * 100) + " % - " + (fiveMinuteProbability[0] >= BusinessConstraintsConfig.CONSUMER_PERCENTAGE_CONSTRAINT_1 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");

        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("sample mean " + BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_2 + " min: " + new DecimalFormat("##.00").format(tenMinuteProbability[2] * 100) + " % - " + (tenMinuteProbability[2] >= BusinessConstraintsConfig.CONSUMER_PERCENTAGE_CONSTRAINT_2 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "conf " + BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_2 + " min: " + new DecimalFormat("##.00").format(tenMinuteProbability[0] * 100) + " % - " + (tenMinuteProbability[0] >= BusinessConstraintsConfig.CONSUMER_PERCENTAGE_CONSTRAINT_2 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");
        System.out.println("");
        System.out.println("");

        double[] threeMinuteProbability = corporate.avgProbabilityQueueTimeLessThanWithConfidence(BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_1 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);
        double[] sevenMinuteProbability = corporate.avgProbabilityQueueTimeLessThanWithConfidence(BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_2 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);

        System.out.println("--------------------------");
        System.out.println("CORPORATE QUEUE TIME PROBABILITIES");
        System.out.println("--------------------------");
        System.out.println("sample mean " + BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_1 + " min: " + new DecimalFormat("##.00").format(threeMinuteProbability[2] * 100) + " % - " + (threeMinuteProbability[2] >= BusinessConstraintsConfig.CORPORATE_PERCENTAGE_CONSTRAINT_1 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "conf " + BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_1 + " min: " + new DecimalFormat("##.00").format(threeMinuteProbability[0] * 100) + " % - " + (threeMinuteProbability[0] >= BusinessConstraintsConfig.CORPORATE_PERCENTAGE_CONSTRAINT_1 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");

        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("sample mean " + BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_2 + " min: " + new DecimalFormat("##.00").format(sevenMinuteProbability[2] * 100) + " % - " + (sevenMinuteProbability[2] >= BusinessConstraintsConfig.CORPORATE_PERCENTAGE_CONSTRAINT_2 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "conf " + BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_2 + " min: " + new DecimalFormat("##.00").format(sevenMinuteProbability[0] * 100) + " % - " + (sevenMinuteProbability[0] >= BusinessConstraintsConfig.CORPORATE_PERCENTAGE_CONSTRAINT_2 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");
        System.out.println("");
        System.out.println("");
    }
}
