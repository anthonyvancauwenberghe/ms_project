package configs;

import contracts.Distribution;
import factories.*;
import models.ArrivalRate;

public class SimulationConfig {

    public static final int SIMULATION_COUNT = 50;

    public static final int SIMULATION_RUNTIME = 24*60*60;

    public static final boolean DEBUG = false;

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


    public static final Distribution<Double> CONSUMER_SERVICE_DISTRIBUTION = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT)).getDistribution();
    public static final Distribution<Double> CORPORATE_SERVICE_DISTRIBUTION = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)).getDistribution();


    public static final double costPerHourCorporateAgent = 60.0;
    public static final double costPerHourConsumerAgent = 35.0;

    public static final int MORNING_CONSUMER_AGENTS = 5;
    public static final int MORNING_CORPORATE_AGENTS = 5;

    public static final int NOON_CONSUMER_AGENTS = 5;
    public static final int NOON_CORPORATE_AGENTS = 5;

    public static final int NIGHT_CONSUMER_AGENTS = 4;
    public static final int NIGHT_CORPORATE_AGENTS = 4;

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
