package factories;

import models.Call;
import enums.CallType;
import contracts.ICallFactory;

public class CallFactory implements ICallFactory {

    protected ServiceTimeFactory consumerServiceTime;
    protected ServiceTimeFactory corporateServiceTime;

    public CallFactory(ServiceTimeFactory consumerServiceTime, ServiceTimeFactory corporateServiceTime) {
        this.consumerServiceTime = consumerServiceTime;
        this.corporateServiceTime = corporateServiceTime;
    }

    public Call build(CallType type) {
        switch (type) {
            case CORPORATE:
                return new Call(this.corporateServiceTime.build(), CallType.CORPORATE);
            case CONSUMER:
                return new Call(this.consumerServiceTime.build(), CallType.CONSUMER);
            default:
                throw new RuntimeException("not a valid call type");
        }
    }
}
