package simulation.tasks;

import models.Call;
import results.IterationSimulationResult;
import results.StrategyResult;
import simulation.contracts.IWorkerGroupProcessor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class SimulationStrategyTask implements Callable<StrategyResult> {

    protected IterationSimulationResult consumerCalls;
    protected IterationSimulationResult corporateCalls;

    protected int iteration;

    protected IWorkerGroupProcessor consumerWorkers;
    protected IWorkerGroupProcessor corporateWorkers;

    protected Queue<Call> consumerQueue = new LinkedList<>();
    protected Queue<Call> corporateQueue = new LinkedList<>();


    public SimulationStrategyTask(int iteration, IterationSimulationResult consumerCalls, IterationSimulationResult corporateCalls) {
        this.consumerCalls = consumerCalls;
        this.corporateCalls = corporateCalls;
        this.iteration = iteration;
    }

    public StrategyResult call() {
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                this.addCallsToQueues(h, m);

                for (int s = 0; s < 60; s++) {

                    this.allocateWorkers();
                    this.processWorkers();
                }
            }
        }

        System.out.println("Finished simulation strategy number " + this.iteration + " .");

        return new StrategyResult();
    }

    protected void allocateWorkers() {
        //TODO IMPLEMENT
    }

    protected void processWorkers() {
        Call[] finishedConsumerCalls = this.consumerWorkers.process();
        Call[] finishedCorporateCalls = this.corporateWorkers.process();
    }

    protected void addCallsToQueues(int h, int m) {
        for (Call call : consumerCalls.get(h, m).all()) {
            if (call.isConsumer())
                consumerQueue.add(call);
            if (call.isCorporate())
                corporateQueue.add(call);
            else
                throw new RuntimeException("call type not supported");
        }
    }

}
