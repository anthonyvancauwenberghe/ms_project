package abstracts;

import contracts.IQueue;
import contracts.IStrategy;
import models.Machine;
import models.Product;

public abstract class AbstractStrategy implements IStrategy {
    protected IQueue consumerQueue;
    protected IQueue corporateQueue;

    @Override
    public void setQueues(IQueue consumerQueue, IQueue corporateQueue) {
        this.consumerQueue = consumerQueue;
        this.corporateQueue = corporateQueue;
    }

    public int productInConsumerQueue() {
        return consumerQueue.getQueue().size();
    }

    public boolean corporateAgentsBusy() {
        return this.availableCorporateAgents() == 0;
    }

    public boolean consumerAgentsBusy() {
        return this.availableConsumerAgents() == 0;
    }

    public int totalConsumerAgents() {
        int count = 0;

        for (Machine machine : this.consumerQueue.getMachines()) {
            if (machine.isOperational())
                count++;
        }
        return count;
    }

    public int totalCorporateAgents() {
        int count = 0;

        for (Machine machine : this.corporateQueue.getMachines()) {
            if (machine.isOperational())
                count++;
        }
        return count;
    }

    public int availableConsumerAgents() {
        int count = 0;

        for (Machine machine : this.consumerQueue.getMachines()) {
            if (machine.isOperational() && machine.isIdle())
                count++;
        }
        return count;
    }

    public int availableCorporateAgents() {
        int count = 0;

        for (Machine machine : this.corporateQueue.getMachines()) {
            if (machine.isOperational() && machine.isIdle())
                count++;
        }
        return count;
    }

    protected double getMaxConsumerQueueTime(double currentTime) {
        double max = -1;

        for (Product product : this.consumerQueue.getQueue()) {
            double queueTime = currentTime - product.getArrivalTimeForAnalysis(product.getArrivalTime()) ;

            if (queueTime > max)
                max = queueTime;
        }
        return max;
    }
}
