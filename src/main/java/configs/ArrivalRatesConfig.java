package configs;

public class ArrivalRatesConfig {
    public static final double CONSUMER_AVG_MINUTE_ARRIVAL_RATE = 0.1;
    public static final double CONSUMER_ARRIVAL_RATE_PERIOD = 24;
    public static final double CONSUMER_ARRIVAL_LOWEST_HOUR = 3;
    public static final double CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE = 0.2;

    public static final double[] CORPORATE_AVG_ARRIVAL_RATE_RANGE = {
            0.2, //0-1
            0.2, //1-2
            0.2, //2-3
            0.2, //3-4
            0.2, //4-5
            0.2, //5-6
            0.2, //6-7
            0.2, //7-8
            1.0, //8-9
            1.0, //9-10
            1.0, //10-11
            1.0, //11-12
            1.0, //12-13
            1.0, //13-14
            1.0, //14-15
            1.0, //15-16
            1.0, //16-17
            1.0, //17-18
            0.2, //18-19
            0.2, //19-20
            0.2, //20-21
            0.2, //21-22
            0.2, //22-23
            0.2, //23-24
    };
}
