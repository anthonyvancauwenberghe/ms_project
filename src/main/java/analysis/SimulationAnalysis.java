package analysis;

import configs.AnalysisConfig;
import configs.BusinessConstraintsConfig;
import configs.SimulationConfig;
import contracts.Distribution;
import factories.ConsumerArrivalTimeFactory;
import statistics.ChiSquaredTest;

import java.text.DecimalFormat;

public class SimulationAnalysis {

    protected SinkAnalysisAggregator consumer;
    protected SinkAnalysisAggregator corporate;

    public SimulationAnalysis(SinkAnalysisAggregator consumer, SinkAnalysisAggregator corporate) {
        this.consumer = consumer;
        this.corporate = corporate;
    }

    public void execute() {
        this.compareConsumerArrivalRateDistributions(consumer.countArrivalsPerMinute(),"consumer");
        this.compareConsumerArrivalRateDistributions(corporate.countArrivalsPerMinute(), "corporate");

        this.compareServiceTimeDistributions(consumer.avgServiceTimeFrequencies(), SimulationConfig.CONSUMER_SERVICE_DISTRIBUTION, "Consumer");
        this.compareServiceTimeDistributions(corporate.avgServiceTimeFrequencies(),SimulationConfig.CORPORATE_SERVICE_DISTRIBUTION, "Corporate");

        if (AnalysisConfig.ANALYZE_BUSINESS_CONSTRAINTS)
            this.analyzeBusinessConstraints();

        if (AnalysisConfig.PLOT_QUEUE_TIMES) {
            consumer.plotAvgMinutelyQueueTimesWithConfidence(0.99, false);
            corporate.plotAvgMinutelyQueueTimesWithConfidence(0.99, false);
        }

        if (AnalysisConfig.PLOT_ARRIVALS) {
            consumer.plotAvgMinutelyArrivals();
            corporate.plotAvgMinutelyArrivals();
        }

        if (AnalysisConfig.PLOT_SERVICE_TIMES) {
            consumer.plotAvgServiceTimeProbabilities();
            corporate.plotAvgServiceTimeProbabilities();
        }
    }

    public void compareServiceTimeDistributions(double[] frequencies, Distribution<Double> distribution, String type) {
        int total = 0;
        for (int i = 0; i < 1000; i++) {
            total += frequencies[i];
        }

        double[] expected = new double[frequencies.length];
        for (int i = 0; i < total; i++) {
            Double sample = distribution.sample();
            int rounded = (int) (sample - (sample % 1));
            expected[rounded]++;
        }


        for (int i = 0; i < 1000; i++) {
            double expectedValue = expected[i];
            double observedValue = frequencies[i];
            if (expectedValue < 1)
                expected[i] = 1;
            if (observedValue < 1)
                frequencies[i] = 1;
        }

        ChiSquaredTest test = new ChiSquaredTest();

        System.out.println(type +" Service Time Distribution check Chi Square: " + 1 /test.chiSquare(expected, frequencies));
    }

    public void compareConsumerArrivalRateDistributions(double[] observed, String type){
        ConsumerArrivalTimeFactory factory = (ConsumerArrivalTimeFactory) SimulationConfig.CONSUMER_ARRIVAL_RATE;

        double[] expectedRates = new double[24*60];

       for(int i=0; i<SimulationConfig.SIMULATION_COUNT; i++){
           double[] rates = factory.sampleArrivalRates();
           for(int t=0; t<rates.length; t++) {
               double arrivalTime = rates[t];
               int minute = (int) ((arrivalTime - (arrivalTime %60))/60);
               expectedRates[minute]++;
           }

       }

        for (int i = 0; i < expectedRates.length; i++) {
            double expectedValue = expectedRates[i];
            if (expectedValue < 1)
                expectedRates[i] = 1;
        }

        ChiSquaredTest test = new ChiSquaredTest();

        System.out.println(type + " Arrival Rate Distribution check Chi Square: " + 1 /test.chiSquare(expectedRates, observed));
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
