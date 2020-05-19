import enums.CallType;
import interfaces.ICallFactory;
import models.ArrivalRate;
import models.CallGroup;
import models.CallSimulationGroup;
import java.util.concurrent.Callable;

public class SimulationTask implements Callable<CallSimulationGroup[]> {

    protected ArrivalRate consumerArrivalRate;
    protected ArrivalRate corporateArrivalRate;

    protected ICallFactory callFactory;

    protected int iteration;

    public SimulationTask(int iteration, ArrivalRate consumerArrivalRate, ArrivalRate corporateArrivalRate, ICallFactory callFactory) {
        this.iteration = iteration;
        this.consumerArrivalRate = consumerArrivalRate;
        this.corporateArrivalRate = corporateArrivalRate;
        this.callFactory = callFactory;
    }

    public CallSimulationGroup[] call() {
        CallSimulationGroup consumerCallSimulation = new CallSimulationGroup();
        CallSimulationGroup corporateCallSimulation = new CallSimulationGroup();

        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                int consumerArrivals = this.consumerArrivalRate.getArrivalRateSample(h, m);
                int corporateArrivals = this.corporateArrivalRate.getArrivalRateSample(h, m);

                CallGroup consumerCalls = new CallGroup();
                CallGroup corporateCalls = new CallGroup();

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

        return new CallSimulationGroup[]{
                consumerCallSimulation,
                corporateCallSimulation
        };
    }
}
