import org.junit.jupiter.api.Test;

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
