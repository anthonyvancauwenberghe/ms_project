package analysis;

import charts.HistogramChart;
import configs.SimulationConfig;
import contracts.Aggregate;
import org.jfree.chart.axis.NumberTickUnit;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SinkAnalysisAggregator {
    protected ArrayList<SinkAnalysis> sinkAnalyses = new ArrayList<>();

    protected String productType;

    public SinkAnalysisAggregator(String productType) {
        this.productType = productType;
    }

    public void add(SinkAnalysis analysis) {
        this.sinkAnalyses.add(analysis);
    }

    public int count() {
        return this.sinkAnalyses.size();
    }


    protected double[] aggregatePerHour(Aggregate<SinkAnalysis, double[]> aggr) {
        double[] aggregated = new double[24];

        for (SinkAnalysis analysis : this.sinkAnalyses) {
            double[] data = aggr.group(analysis);
            for (int h = 0; h < 24; h++) {
                double value = data[h];
                if (Double.isNaN(value)) {
                    value = 0;
                }
                aggregated[h] += (value / (double) this.count());
            }
        }

        return aggregated;
    }

    protected double[] aggregatePerMinute(Aggregate<SinkAnalysis, double[]> aggr) {
        double[] aggregated = new double[24 * 60];

        for (SinkAnalysis analysis : this.sinkAnalyses) {
            double[] data = aggr.group(analysis);
            for (int h = 0; h < 24; h++) {
                for (int m = 0; m < 60; m++) {
                    double value = data[60 * h + m];
                    if (Double.isNaN(value)) {
                        value = 0;
                    }

                    aggregated[60 * h + m] += (value / (double) this.count());
                }
            }
        }

        return aggregated;
    }

    public double avgProbabilityQueueTimeLessThan(double duration, double minTime, double maxTime) {
        double probability = 0;
        for (SinkAnalysis analysis : this.sinkAnalyses) {
            probability += analysis.probabilityOfQueueTimeLessThan(duration);
        }

        return probability / ((double) this.count());
    }

    public double avgProbabilityQueueTimeLessThan(double duration) {
        return this.avgProbabilityQueueTimeLessThan(duration,0, SimulationConfig.SIMULATION_RUNTIME);
    }


    public double[] avgQueueTimesPerHour() {
        return this.aggregatePerHour(a -> a.avgQueueTimePerHour());
    }

    public double[] avgQueueTimesPerMinute() {
        return this.aggregatePerMinute(a -> a.avgQueueTimePerMinute());
    }

    public double[] avgArrivalsPerHour() {
        return this.aggregatePerHour(a -> a.arrivalsPerHour());
    }

    public double[] avgArrivalsPerMinute() {
        return this.aggregatePerMinute(a -> a.arrivalsPerMinute());
    }

    public void plotAvgHourlyQueueTimes() {
        HistogramChart chart = new HistogramChart(this.productType + " Queue times ( " + this.count() + " simulation days)", "hour", "queue time (min)");
        chart.addSeries(this.productType + " queue times", this.avgQueueTimesPerHour());

        NumberTickUnit tickUnit = new NumberTickUnit(1){
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##").format(value) + "h";
            }
        };

        chart.getDomainAxis().setTickUnit(tickUnit);
        chart.render();
    }

    public void plotAvgMinutelyQueueTimes() {
        HistogramChart chart = new HistogramChart(this.productType + " Queue times ( " + this.count() + " simulation days)", "hour", "queue time (min)");
        chart.addSeries(this.productType + " queue times", this.avgQueueTimesPerMinute());
        chart.getRangeAxis().setRange(0,5);

        NumberTickUnit tickUnit = new NumberTickUnit(60){
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##").format(value /60) + "h";
            }
        };

        chart.getDomainAxis().setTickUnit(tickUnit);
        chart.getDomainAxis().setRange(0,24*60);

        chart.render();
    }

    public void plotAvgHourlyArrivals() {
        HistogramChart chart = new HistogramChart(this.productType + " Arrivals ( " + this.count() + " simulation days)", "hour", "arrivals");
        chart.addSeries(this.productType + " arrivals", this.avgArrivalsPerHour());

        chart.getRangeAxis().setRange(0,60);

        NumberTickUnit tickUnit = new NumberTickUnit(1){
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##").format(value ) + "h";
            }
        };

        chart.getDomainAxis().setTickUnit(tickUnit);

        chart.render();
    }

    public void plotAvgMinutelyArrivals() {
        HistogramChart chart = new HistogramChart(this.productType + " Arrivals ( " + this.count() + " simulation days)", "hour", "arrivals");
        chart.addSeries(this.productType + " arrivals", this.avgArrivalsPerMinute());

        NumberTickUnit tickUnit = new NumberTickUnit(60){
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##").format(value /60) + "h";
            }
        };

        chart.getDomainAxis().setTickUnit(tickUnit);

        chart.getDomainAxis().setRange(0,24*60);
        chart.render();
    }

    public void plotQueueTimeProbabilities(double startHour, double endHour) {
        HistogramChart chart = new HistogramChart(this.productType + " Queue times ( " + this.count() + " simulation days)", "minute", "Probability");

        double[] probabilities= new double[10];

        for (int i = 1; i <= 10; i++) {
            probabilities[i-1] = this.avgProbabilityQueueTimeLessThan(((double) i) * 60.0,startHour*3600,endHour*3600);
        }

        chart.addSeries("Queue time probabilities", probabilities,+0.5);

        chart.getRangeAxis().setRange(0,1.0);

        NumberTickUnit tickUnit = new NumberTickUnit(0.1){
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##.00").format(value * 100) + " %";
            }
        };

        chart.getRangeAxis().setTickUnit(tickUnit);

        chart.render();
    }

    public void plotQueueTimeProbabilities(){
        this.plotQueueTimeProbabilities(0,24);
    }

}
