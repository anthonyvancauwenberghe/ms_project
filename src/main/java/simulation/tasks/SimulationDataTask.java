package simulation.tasks;

import enums.CallType;
import contracts.ICallFactory;
import models.ArrivalRate;
import results.MinutelyCallResult;
import results.IterationSimulationResult;
import java.util.concurrent.Callable;

public class SimulationDataTask implements Callable<IterationSimulationResult[]> {

    protected ArrivalRate consumerArrivalRate;
    protected ArrivalRate corporateArrivalRate;

    protected ICallFactory callFactory;

    protected int iteration;

    public SimulationDataTask(int iteration, ArrivalRate consumerArrivalRate, ArrivalRate corporateArrivalRate, ICallFactory callFactory) {
        this.iteration = iteration;
        this.consumerArrivalRate = consumerArrivalRate;
        this.corporateArrivalRate = corporateArrivalRate;
        this.callFactory = callFactory;
    }

    public IterationSimulationResult[] call() {
        IterationSimulationResult consumerCallSimulation = new IterationSimulationResult();
        IterationSimulationResult corporateCallSimulation = new IterationSimulationResult();

        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                int consumerArrivals = this.consumerArrivalRate.getArrivalRateSample(h, m);
                int corporateArrivals = this.corporateArrivalRate.getArrivalRateSample(h, m);

                MinutelyCallResult consumerCalls = new MinutelyCallResult();
                MinutelyCallResult corporateCalls = new MinutelyCallResult();

                for (int calls = 0; calls < consumerArrivals; calls++) {
                    consumerCalls.add(this.callFactory.build(CallType.CONSUMER));
                }

                for (int calls = 0; calls < corporateArrivals; calls++) {
                    corporateCalls.add(this.callFactory.build(CallType.CORPORATE));
                }

                consumerCallSimulation.add(h, m, consumerCalls);
                corporateCallSimulation.add(h, m, corporateCalls);

            }
        }

        System.out.println("Finished simulation number " + this.iteration + " .");

        return new IterationSimulationResult[]{
                consumerCallSimulation,
                corporateCallSimulation
        };
    }
}
