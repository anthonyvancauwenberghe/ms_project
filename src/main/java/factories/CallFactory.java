package factories;

import abstracts.Call;
import enums.CallType;
import interfaces.ICallFactory;
import models.ConsumerCall;
import models.CorporateCall;

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
                return this.createCorporateCall();
            case CONSUMER:
                return this.createConsumerCall();
            default:
                throw new RuntimeException("not a valid call type");
        }
    }

    protected ConsumerCall createConsumerCall() {
        return new ConsumerCall(this.consumerServiceTime.build());
    }

    protected CorporateCall createCorporateCall() {
        return new CorporateCall(this.corporateServiceTime.build());
    }
}
