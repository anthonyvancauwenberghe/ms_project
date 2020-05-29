package configs;

import contracts.Distribution;
import contracts.IArrivalRateFactory;
import contracts.IStrategy;
import factories.*;
import strategies.CorporateTakeoverStrategy;

public class SimulationConfig {

    /**
     * Determines how many simulations days will be run
     */
    public static final int SIMULATION_COUNT = 50;

    /**
     * Determines if the queue will be warmed to reach a steady state first
     */
    public static final boolean WARMUP_QUEUE = false;

    /**
     * Determines the maximum amount of iterations of queue warming.
     * If more than 200 iterations are necessary we can be quite certain
     * that the schedule is wrong and the queue will build up forever
     * thus never reaching a steady state
     */
    public static final int MAX_QUEUE_WARM_UP_ITERATIONS = 200;

    /**
     * The time a simulation will run.
     * DO NOT TOUCH
     */
    public static final int SIMULATION_RUNTIME = 24*60*60;

    /**
     * Determines if debug logs will be printed
     */
    public static final boolean DEBUG = false;

    /**
     * Determines the strategy of when corporate workers will help out consumers
     */
    public static final IStrategy strategy = new CorporateTakeoverStrategy();

    /**
     * Determines the factory for the consumer arrival rates
     */
    public static final IArrivalRateFactory CONSUMER_ARRIVAL_RATE = (new ConsumerArrivalTimeFactory(
            ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
    ));

    /**
     * Determines the factory for the corporate arrival rates
     */
    public static final IArrivalRateFactory CORPORATE_ARRIVAL_RATE = (new CorporateArrivalTimeFactory(
            ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE,
            SimulationConfig.SIMULATION_RUNTIME
    ));

    /**
     * Determines the factory for the consumer service times
     */
    public static final Distribution<Double> CONSUMER_SERVICE_DISTRIBUTION = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT)).getDistribution();

    /**
     * Determines the factory for the corporate arrival rates
     */
    public static final Distribution<Double> CORPORATE_SERVICE_DISTRIBUTION = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)).getDistribution();

    /**
     * The cost per hour per consumer agent
     */
    public static final double costPerHourConsumerAgent = 35.0;

    /**
     * The cost per hour per corporate agent
     */
    public static final double costPerHourCorporateAgent = 60.0;

    /**
     * The morning shift hours
     * 6am-2pm
     */
    public static final boolean[] MORNING_SHIFT = {
            false, //0-1
            false, //1-2
            false, //2-3
            false, //3-4
            false, //4-5
            false, //5-6
            true, //6-7
            true, //7-8
            true, //8-9
            true, //9-10
            true, //10-11
            true, //11-12
            true, //12-13
            true, //13-14
            false, //14-15
            false, //15-16
            false, //16-17
            false, //17-18
            false, //18-19
            false, //19-20
            false, //20-21
            false, //21-22
            false, //22-23
            false, //23-24
    };

    /**
     * The noon shift hours
     *  2pm-10pm
     */
    public static final boolean[] NOON_SHIFT = {
            false, //0-1
            false, //1-2
            false, //2-3
            false, //3-4
            false, //4-5
            false, //5-6
            false, //6-7
            false, //7-8
            false, //8-9
            false, //9-10
            false, //10-11
            false, //11-12
            false, //12-13
            false, //13-14
            true, //14-15
            true, //15-16
            true, //16-17
            true, //17-18
            true, //18-19
            true, //19-20
            true, //20-21
            true, //21-22
            false, //22-23
            false, //23-24
    };

    /**
     * The Night shift hours
     * 10pm-6am
     */
    public static final boolean[] NIGHT_SHIFT = {
            true, //0-1
            true, //1-2
            true, //2-3
            true, //3-4
            true, //4-5
            true, //5-6
            false, //6-7
            false, //7-8
            false, //8-9
            false, //9-10
            false, //10-11
            false, //11-12
            false, //12-13
            false, //13-14
            false, //14-15
            false, //15-16
            false, //16-17
            false, //17-18
            false, //18-19
            false, //19-20
            false, //20-21
            false, //21-22
            true, //22-23
            true, //23-24
    };
}
