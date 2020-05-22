package simulation.abstracts;

import models.Call;
import simulation.contracts.IWorkerGroupProcessor;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class AbstractWorkerGroupProcessor implements IWorkerGroupProcessor {
    protected int idle;
    protected int busy = 0;

    protected ArrayList<Call> calls = new ArrayList<>();

    public AbstractWorkerGroupProcessor(int workers) {
        this.idle = workers;
    }

    @Override
    public boolean available() {
        return idle > 0;
    }

    @Override
    public Call[] process() {
        HashSet<Call> finishedCalls = new HashSet<>();

        for (Call call : this.calls) {
            call.incrementTimeInCall();

            if (call.isFinished())
                finishedCalls.add(call);
        }

        this.calls.removeAll(finishedCalls);

        return finishedCalls.toArray((new Call[finishedCalls.size()]));
    }

    @Override
    public boolean accept(Call call) {

        if (this.available()) {
            idle--;
            busy++;
            calls.add(call);
            return true;
        }
        return false;
    }


}
