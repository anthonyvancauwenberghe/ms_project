import configs.ArrivalRatesConfig;
import configs.SimulationConfig;
import contracts.Distribution;
import factories.SinusoidArrivalRateInSecondsFactory;
import models.ArrivalRate;
import org.junit.jupiter.api.Test;
import statistics.NormalDistribution;
import statistics.PoissonDistribution;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimulation {

    @Test
    void testConvertSecondsToHourRange() {

        double time = 7800.521;

        double value = time % 3600;

        int result = (int) (time-value)/3600;

        assertEquals(2,result);

    }


}
