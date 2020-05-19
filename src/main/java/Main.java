import charts.LineChart;
import configs.ArrivalRatesConfig;
import configs.ServiceTimesConfig;
import configs.SimulationConfig;
import factories.RangeArrivalRateFactory;
import factories.SinusoidArrivalRateFactory;
import factories.ServiceTimeFactory;
import models.ArrivalRate;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Simulation simulation = new Simulation(
                SimulationConfig.CONSUMER_ARRIVAL_RATE,
                SimulationConfig.CORPORATE_ARRIVAL_RATE,
                SimulationConfig.CALL_FACTORY
        );

        simulation.run(SimulationConfig.SIMULATION_COUNT);

        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("Avg Consumer Service Time: " + simulation.getConsumer().getTotalAverageServiceTime());
        System.out.println("Avg corporate Service Time: " + simulation.getCorporate().getTotalAverageServiceTime());

        (new LineChart(
                "Avg Consumer Service times",
                "time (hours)",
                "service time (minutes)",
                simulation.getConsumer().getAverageServiceTimePerHour()
        )).render();

        (new LineChart(
                "Avg Consumer Service times (minutes)",
                "time (minutes)",
                "service time (minutes)",
                simulation.getConsumer().getAverageServiceTimesPerMinute()
        )).render();

        (new LineChart(
                "Avg Corporate Service times",
                "time (hours)",
                "service time (minutes)",
                simulation.getCorporate().getAverageServiceTimePerHour()
        )).render();

        (new LineChart(
                "avg corporate calls per hour",
                "time (hour)",
                "Rate of calls",
                simulation.getCorporate().getAverageCallsPerHour()
        )).render();

        (new LineChart(
                "avg consumer calls per minute",
                "time (minute)",
                "Rate of calls",
                simulation.getConsumer().getAverageCallsPerMinute()
        )).render();

        (new LineChart(
                "avg consumer calls per hour",
                "time (hour)",
                "Rate of calls",
                simulation.getConsumer().getAverageCallsPerHour()
        )).render();

        //exploreData();
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
