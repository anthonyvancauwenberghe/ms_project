import charts.HistogramChart;
import charts.LineChart;
import configs.ArrivalRatesConfig;
import configs.ServiceTimesConfig;
import factories.RangeArrivalRateInSecondsFactory;
import factories.ServiceTimeFactory;
import factories.SinusoidArrivalRateInSecondsFactory;
import models.ArrivalRate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import statistics.NormalDistribution;

public class PlotData {

    public static void main(String[] args) {
        plotHistogram();

        //plotCorporateArrivals();
        //plotConsumerArrivals();

        //plotConsumerServiceTime();
        //plotCorporateServiceTime();
    }



    public static void plotCorporateArrivals() {
        ArrivalRate corporateArrivalRate = (new RangeArrivalRateInSecondsFactory(
                ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE)
        ).build();

        LineChart corporateChart = new LineChart("corporate arrival times", "time (seconds)", "Rate of arrivals", corporateArrivalRate.getRates());
        corporateChart.render();
    }

    public static void plotConsumerArrivals() {
        ArrivalRate consumerArrivalRate = (new SinusoidArrivalRateInSecondsFactory(
                ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
        )).build();

        double[] rates = consumerArrivalRate.getRates();

        LineChart consumerChart = new LineChart("consumer arrival times", "time (seconds)", "Rate of arrivals", rates);
        consumerChart.render();
    }

    public static void plotConsumerServiceTime() {
        ServiceTimeFactory consumerServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT));
        LineChart consumerServiceTimeChart = new LineChart("Consumer Service time density", "time (seconds)", "Probability", consumerServiceTime.probabilities(500));
        consumerServiceTimeChart.render();
    }

    public static void plotCorporateServiceTime() {
        ServiceTimeFactory corporateServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT));
        LineChart corporateServiceTimeChart = new LineChart("Corporate Service time density", "time (seconds)", "Probability", corporateServiceTime.probabilities(500));
        corporateServiceTimeChart.render();
    }

    public static void plotHistogram(){
        HistogramChart chart = new HistogramChart("test","x","y");
        chart.addSeries("testData",new double[]{1.0,5.6,8.0,1.0,5.6,8.0,1.0,5.6,8.0,1.0,5.6,8.0});
        chart.addSeries("testData2",new double[]{3.0,9.0,10,3.0,9.0,10,3.0,9.0,10,3.0,9.0,10,8.0});

        chart.render();
    }

}
