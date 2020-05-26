import charts.HistogramChart;
import charts.LineChart;
import configs.ArrivalRatesConfig;
import configs.ServiceTimesConfig;
import factories.ServiceTimeFactory;
import models.ArrivalRate;
import org.jfree.chart.axis.NumberTickUnit;

import java.text.DecimalFormat;

public class PlotData {

    public static void main(String[] args) {
       //plotHistogram();

        //plotCorporateArrivals();
        //plotConsumerArrivals();

        plotConsumerServiceTime();
        plotCorporateServiceTime();
    }



/*    public static void plotCorporateArrivals() {
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
    }*/

    public static void plotConsumerServiceTime() {
        ServiceTimeFactory consumerServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT));
        HistogramChart consumerServiceTimeChart = new HistogramChart("Consumer Service time density", "time (seconds)", "Probability");
        consumerServiceTimeChart.addSeries("Consumer Service time",consumerServiceTime.probabilities(200));

        consumerServiceTimeChart.getRangeAxis().setRange(0, 0.07);

        NumberTickUnit tickUnit = new NumberTickUnit(0.01) {
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##.00").format(value * 100) + " %";
            }
        };

        consumerServiceTimeChart.getRangeAxis().setTickUnit(tickUnit);

        consumerServiceTimeChart.render();
    }

    public static void plotCorporateServiceTime() {
        ServiceTimeFactory corporateServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT));
        HistogramChart corporateServiceTimeChart = new HistogramChart("Corporate Service time density", "time (seconds)", "Probability");
        corporateServiceTimeChart.addSeries("Corporate Service Time",corporateServiceTime.probabilities(500));

        corporateServiceTimeChart.getRangeAxis().setRange(0, 0.035);

        NumberTickUnit tickUnit = new NumberTickUnit(0.01) {
            @Override
            public String valueToString(double value) {
                return new DecimalFormat("##.00").format(value * 100) + " %";
            }
        };

        corporateServiceTimeChart.getRangeAxis().setTickUnit(tickUnit);

        corporateServiceTimeChart.render();
    }

}
