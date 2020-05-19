package configs;

import factories.CallFactory;
import factories.RangeArrivalRateFactory;
import factories.ServiceTimeFactory;
import factories.SinusoidArrivalRateFactory;
import models.ArrivalRate;

public class SimulationConfig {

    public static int SIMULATION_COUNT = 1000;

    public static CallFactory CALL_FACTORY = new CallFactory(
            new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT),
            new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)
    );

    public static ArrivalRate CONSUMER_ARRIVAL_RATE = (new SinusoidArrivalRateFactory(
            ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
            ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
    )).build();

    public static ArrivalRate CORPORATE_ARRIVAL_RATE = (new RangeArrivalRateFactory(
            ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE
    )).build();
}
