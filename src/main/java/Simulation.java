import abstracts.Call;
import configs.ArrivalRatesConfig;
import configs.ServiceTimesConfig;
import enums.CallType;
import factories.CallFactory;
import factories.RangeArrivalRateFactory;
import factories.ServiceTimeFactory;
import factories.SinusoidArrivalRateFactory;
import interfaces.IArrivalRateFactory;
import interfaces.ICallFactory;
import models.ArrivalRate;
import models.CallGroup;
import models.CallSimulationGroup;
import models.SimulationResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Simulation {

    protected ArrivalRate consumerArrivalRate;
    protected ArrivalRate corporateArrivalRate;

    protected ICallFactory callFactory;

    protected SimulationResult consumer;
    protected SimulationResult corporate;

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

        List<Future<CallSimulationGroup[]>> results = new ArrayList<>();

        LinkedList<CallSimulationGroup> consumerSimulations = new LinkedList<>();
        LinkedList<CallSimulationGroup> corporateSimulations = new LinkedList<>();

        for (int i = 0; i < amount; i++) {
            Callable<CallSimulationGroup[]> task = new SimulationTask(i, this.consumerArrivalRate, this.corporateArrivalRate, callFactory);
            Future<CallSimulationGroup[]> result = executor.submit(task);
            results.add(result);
        }

        for (Future<CallSimulationGroup[]> future : results) {
            try {
                consumerSimulations.add(future.get()[0]);
                corporateSimulations.add(future.get()[1]);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //shut down the executor service now
        executor.shutdown();

        this.consumer = new SimulationResult(consumerSimulations);
        this.corporate = new SimulationResult(corporateSimulations);
        this.executed = true;

        long endTime = System.nanoTime();

        double duration = (((double)(endTime - startTime)) / 1000000000);

        DecimalFormat f = new DecimalFormat("##.00");

        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("Ran " + amount + " simulations in " + f.format(duration) + " seconds");
    }

    public SimulationResult getConsumer() {
        if (!this.executed)
            throw new RuntimeException("Cannot retrieve results run the simulation first.");

        return consumer;
    }

    public SimulationResult getCorporate() {
        if (!this.executed)
            throw new RuntimeException("Cannot retrieve results run the simulation first.");

        return corporate;
    }
}
