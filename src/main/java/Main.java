import charts.LineChart;
import configs.ArrivalRatesConfig;
import configs.ServiceTimesConfig;
import factories.CallFactory;
import factories.RangeArrivalRateFactory;
import factories.SinusoidArrivalRateFactory;
import factories.ServiceTimeFactory;
import interfaces.ICallFactory;
import models.ArrivalRate;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        exploreData();

        ArrivalRate consumerArrivalRate = (new SinusoidArrivalRateFactory(
                ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
        )).build();

        ArrivalRate corporateArrivalRate = (new RangeArrivalRateFactory(
                ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE
        )).build();

        ICallFactory callFactory = new CallFactory(
                new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT),
                new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)
        );

        Simulation simulation = new Simulation(consumerArrivalRate, corporateArrivalRate, callFactory);
        simulation.run(10000);

        double[] avgConsumerServicesTimes = simulation.getConsumer().getAverageServiceTimePerHour();
        double[] avgCorporateServicesTimes = simulation.getCorporate().getAverageServiceTimePerHour();

        double[] avgConsumerCallsPerHour = simulation.getConsumer().getAverageCallsPerHour();
        double[] avgCorporateCallsPerHour = simulation.getCorporate().getAverageCallsPerHour();

        double avgConsumerServiceTime = simulation.getConsumer().getTotalAverageServiceTime();
        double avgCorporateServiceTime = simulation.getCorporate().getTotalAverageServiceTime();

        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("Avg Consumer Service Time: " + avgConsumerServiceTime);
        System.out.println("Avg corporate Service Time: " + avgCorporateServiceTime);

        LineChart consumerServiceTimeChart = new LineChart("Avg Consumer Service times", "time (hours)", "service time (minutes)", avgConsumerServicesTimes);
        consumerServiceTimeChart.render();

        LineChart corporateServiceTimeChart = new LineChart("Avg Corporate Service times", "time (hours)", "service time (minutes)", avgCorporateServicesTimes);
        corporateServiceTimeChart.render();

        LineChart corporateChart = new LineChart("avg corporate calls per hour", "time (hour)", "Rate of calls", avgCorporateCallsPerHour);
        corporateChart.render();

        LineChart consumerChart = new LineChart("avg consumer calls per hour", "time (hour)", "Rate of calls", avgConsumerCallsPerHour);
        consumerChart.render();
    }

    public static void exploreData() {
        ArrivalRate consumerArrivalRate = (new SinusoidArrivalRateFactory(
                ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
        )).build();

        ArrivalRate corporateArrivalRate = (new RangeArrivalRateFactory(
                ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE)
        ).build();

        ServiceTimeFactory consumerServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT));
        ServiceTimeFactory corporateServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT));

        LineChart consumerServiceTimeChart = new LineChart("Consumer Service time density", "time (seconds)", "Probability", consumerServiceTime.probabilities(500));
        consumerServiceTimeChart.render();

        LineChart corporateServiceTimeChart = new LineChart("Corporate Service time density", "time (seconds)", "Probability", corporateServiceTime.probabilities(500));
        corporateServiceTimeChart.render();

        LineChart corporateChart = new LineChart("corporate arrival times", "time (minutes)", "Rate of arrivals", corporateArrivalRate.getRates());
        corporateChart.render();

        LineChart consumerChart = new LineChart("consumer arrival times", "time (minutes)", "Rate of arrivals", consumerArrivalRate.getRates());
        consumerChart.render();
    }


}
