package configs;

import contracts.Distribution;
import factories.*;
import models.ArrivalRate;

public class SimulationConfig {

    public static final int SIMULATION_COUNT = 1000;

    public static final CallFactory CALL_FACTORY = new CallFactory(
            new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT),
            new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)
    );

    public static final ArrivalRate CONSUMER_ARRIVAL_RATE = (new SinusoidArrivalRateInSecondsFactory(
            ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
    )).build();

    public static final ArrivalRate CORPORATE_ARRIVAL_RATE = (new RangeArrivalRateInSecondsFactory(
            ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE
    )).build();


    public static final Distribution CONSUMER_SERVICE_DISTRIBUTION = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT)).getDistribution();
    public static final Distribution CORPORATE_SERVICE_DISTRIBUTION = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)).getDistribution();
}
