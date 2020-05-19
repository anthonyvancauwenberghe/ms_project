import interfaces.ICallFactory;
import models.ArrivalRate;
import results.IterationSimulationResult;
import results.TotalSimulationResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Simulation {

    protected ArrivalRate consumerArrivalRate;
    protected ArrivalRate corporateArrivalRate;

    protected ICallFactory callFactory;

    protected TotalSimulationResult consumer;
    protected TotalSimulationResult corporate;

    protected boolean executed = false;

    public Simulation(ArrivalRate consumerArrivalRate, ArrivalRate corporateArrivalRate, ICallFactory callFactory) {
        this.consumerArrivalRate = consumerArrivalRate;
        this.corporateArrivalRate = corporateArrivalRate;
        this.callFactory = callFactory;
    }

    public void run(int amount) {
        if (this.executed)
            throw new RuntimeException("Simulation already executed");


        long startTime = System.nanoTime();

        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("found " + threads + " threads. Booting up threadpool..");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);

        List<Future<IterationSimulationResult[]>> results = new ArrayList<>();

        LinkedList<IterationSimulationResult> consumerSimulations = new LinkedList<>();
        LinkedList<IterationSimulationResult> corporateSimulations = new LinkedList<>();

        for (int i = 0; i < amount; i++) {
            Callable<IterationSimulationResult[]> task = new SimulationTask(i, this.consumerArrivalRate, this.corporateArrivalRate, callFactory);
            Future<IterationSimulationResult[]> result = executor.submit(task);
            results.add(result);
        }

        for (Future<IterationSimulationResult[]> future : results) {
            try {
                consumerSimulations.add(future.get()[0]);
                corporateSimulations.add(future.get()[1]);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //shut down the executor service now
        executor.shutdown();

        this.consumer = new TotalSimulationResult(consumerSimulations);
        this.corporate = new TotalSimulationResult(corporateSimulations);
        this.executed = true;

        long endTime = System.nanoTime();

        double duration = (((double)(endTime - startTime)) / 1000000000);

        DecimalFormat f = new DecimalFormat("##.00");

        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("Ran " + amount + " simulations in " + f.format(duration) + " seconds");
    }

    public TotalSimulationResult getConsumer() {
        if (!this.executed)
            throw new RuntimeException("Cannot retrieve results run the simulation first.");

        return consumer;
    }

    public TotalSimulationResult getCorporate() {
        if (!this.executed)
            throw new RuntimeException("Cannot retrieve results run the simulation first.");

        return corporate;
    }
}
