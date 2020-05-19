import charts.LineChart;
import factories.RangeArrivalRateFactory;
import factories.SinusoidArrivalRateFactory;
import factories.LeftTruncatedServiceTimeFactory;
import models.ArrivalRate;
import models.ServiceTime;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrivalRate consumerArrivalRate = (new SinusoidArrivalRateFactory(
                Config.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                Config.CONSUMER_ARRIVAL_RATE_PERIOD,
                Config.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                Config.CONSUMER_ARRIVAL_LOWEST_HOUR
        )).build();

        ArrivalRate corporateArrivalRate = (new RangeArrivalRateFactory(
                Config.CORPORATE_AVG_ARRIVAL_RATE_RANGE)
        ).build();


        ServiceTime consumerServiceTime = (new LeftTruncatedServiceTimeFactory(Config.CONSUMER_SERVICE_TIME_MEAN,Config.CONSUMER_SERVICE_TIME_STD,Config.CONSUMER_SERVICE_TIME_TRUNC_LEFT)).build();
        ServiceTime corporateServiceTime = (new LeftTruncatedServiceTimeFactory(Config.CORPORATE_SERVICE_TIME_MEAN,Config.CORPORATE_SERVICE_TIME_STD,Config.CORPORATE_SERVICE_TIME_TRUNC_LEFT)).build();

        LineChart consumerServiceTimeChart = new LineChart("Consumer Service times", "time (seconds)","Probability", consumerServiceTime.getProbabilities(500));
        consumerServiceTimeChart.render();

        LineChart corporateServiceTimeChart = new LineChart("Corporate Service times", "time (seconds)","Probability", corporateServiceTime.getProbabilities(500));
        corporateServiceTimeChart.render();

        LineChart corporateChart = new LineChart("corporate arrival times", "time (minutes)","Rate of arrivals", corporateArrivalRate.getData());
        corporateChart.render();

        LineChart consumerChart = new LineChart("consumer arrival times", "time (minutes)","Rate of arrivals", consumerArrivalRate.getData());
        consumerChart.render();
    }



}
