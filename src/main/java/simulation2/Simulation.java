package simulation2;


import charts.LineChart;
import configs.SimulationConfig;
import simulation2.enums.ProductType;
import simulation2.processors.SinkConstraintAnalyzer;
import simulation2.models.Sink;

import javax.naming.ConfigurationException;
import java.text.DecimalFormat;

public class Simulation {


    public Simulation() {

    }

    public void run() throws ConfigurationException {
        long startTime = System.nanoTime();

        SimulationTask task = new SimulationTask();
        task.run();

        int simulationCount = SimulationConfig.SIMULATION_COUNT;

        double[] consumerQueueAmount = new double[simulationCount];
        double[] corporateQueueAmount = new double[simulationCount];

        consumerQueueAmount[0] = 0;
        corporateQueueAmount[0] = 0;

        consumerQueueAmount[1] = task.getConsumerQueue().remaining();
        corporateQueueAmount[1] = task.getCorporateQueue().remaining();

        for (int i = 0; i < simulationCount - 2; i++) {
            task = new SimulationTask(task.consumerQueue, task.corporateQueue);
            task.run();
            consumerQueueAmount[i + 2] = task.getConsumerQueue().remaining();
            corporateQueueAmount[i + 2] = task.getCorporateQueue().remaining();
            Sink sink = task.getSink();
            SinkConstraintAnalyzer sinkConstraintAnalyzerConsumer = new SinkConstraintAnalyzer(sink, ProductType.CONSUMER);
            SinkConstraintAnalyzer sinkConstraintAnalyzerCorporate = new SinkConstraintAnalyzer(sink, ProductType.CORPORATE);
            this.printQueueInfo(task,i);
        }

        long endTime = System.nanoTime();

        double duration = (((double) (endTime - startTime)) / 1000000000);

        DecimalFormat f = new DecimalFormat("##.00");

        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("Ran " + simulationCount + " simulations in " + f.format(duration) + " seconds");
        System.out.println("---------------------------");
        System.out.println("---------------------------");

        LineChart ConsumerChart = new LineChart("Consumer queue buildup", "iterations", "In Queue", consumerQueueAmount);
        ConsumerChart.render();

        LineChart corporateChart = new LineChart("Corporate queue buildup", "iterations", "In Queue", corporateQueueAmount);
        corporateChart.render();
    }

    public void printQueueInfo(SimulationTask task, int iteration) {
        System.out.println("------------------------------");
        System.out.println("Simulation " + iteration + " finished");
        System.out.println(task.getCorporateQueue().remaining() + " corporate calls left in queue");
        System.out.println(task.getConsumerQueue().remaining() + " consumer calls left in queue");
        System.out.println(task.getConsumerQueue().remaining() + task.getCorporateQueue().remaining() + " total calls left in queue");
        System.out.println("------------------------------");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ConfigurationException {

        Simulation sim = new Simulation(

        );

        sim.run();
    }
}
