
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

/*        ArrivalRate consumerArrivalRate = (new SinusoidArrivalRateInSecondsFactory(
                ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
        )).build();

        ArrivalRateToArrivalTimesFactory factory = new ArrivalRateToArrivalTimesFactory(consumerArrivalRate);
        factory.build();*/



/*        Simulation simulation = new Simulation(
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
        )).render();*/

    }


}
