import configs.DefaultSimConfig;
import configs.SimulationConfig;
import contracts.ISimulationConfig;

import java.text.DecimalFormat;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ISimulationConfig config = new DefaultSimConfig();

        long startTime = System.nanoTime();

        for (int i = 0; i < SimulationConfig.SIMULATION_COUNT; i++) {
            Simulator sim = new Simulator(config);
            sim.run();
            config = new DefaultSimConfig(sim.getProcessor().getQueues());

            System.out.println("Finished sim iteration: " + i);
        }

        long endTime = System.nanoTime();
        double duration = (((double) (endTime - startTime)) / 1000000000);

        DecimalFormat f = new DecimalFormat("##.0000");
        System.out.println("Ran " + SimulationConfig.SIMULATION_COUNT +" simulations in " + f.format(duration) + " seconds");

    }


}
