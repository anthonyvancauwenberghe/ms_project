package simulation2;


import charts.LineChart;
import configs.SimulationConfig;

import javax.naming.ConfigurationException;
import java.text.DecimalFormat;

public class Simulation {

    public void run(){
        long startTime = System.nanoTime();

        int simulationCount = SimulationConfig.SIMULATION_COUNT;

        double[] consumerQueueAmount = new double[simulationCount];
        double[] corporateQueueAmount = new double[simulationCount];

        SimulationTask prevTask = null;
        for (int i = 0; i < simulationCount; i++) {
            SimulationTask task = new SimulationTask();

            if (prevTask != null)
                task.transferQueues(prevTask.consumerQueue, prevTask.corporateQueue);

            task.run();
            consumerQueueAmount[i] = task.getConsumerQueue().remaining();
            corporateQueueAmount[i] = task.getCorporateQueue().remaining();

            //  SinkConstraintAnalyzer sinkConstraintAnalyzerConsumer = new SinkConstraintAnalyzer(task.getConsumerSink(), ProductType.CONSUMER);
            //  SinkConstraintAnalyzer sinkConstraintAnalyzerCorporate = new SinkConstraintAnalyzer(task.getCorporateSink(), ProductType.CORPORATE);
            this.printQueueInfo(task, i);
            prevTask = task;
        }

        long endTime = System.nanoTime();

        this.printSimulationInfo(startTime, endTime, simulationCount, consumerQueueAmount, corporateQueueAmount);
    }

    public void printSimulationInfo(long startTime, long endTime, int simulationCount, double[] consumerQueueAmount, double[] corporateQueueAmount){
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
    public static void main(String[] args){

        Simulation sim = new Simulation(

        );

        sim.run();
    }
}
