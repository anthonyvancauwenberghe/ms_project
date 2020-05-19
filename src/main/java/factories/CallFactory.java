package factories;

import abstracts.Call;
import configs.ServiceTimesConfig;
import enums.CallType;
import interfaces.ICallFactory;
import models.ConsumerCall;
import models.CorporateCall;

public class CallFactory implements ICallFactory {

    protected ServiceTimeFactory consumerServiceTime;
    protected ServiceTimeFactory corporateServiceTime;

    public CallFactory() {
        this.consumerServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CONSUMER_SERVICE_TIME_MEAN, ServiceTimesConfig.CONSUMER_SERVICE_TIME_STD, ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT));
        this.corporateServiceTime = (new ServiceTimeFactory(ServiceTimesConfig.CORPORATE_SERVICE_TIME_MEAN, ServiceTimesConfig.CORPORATE_SERVICE_TIME_STD, ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT));
    }


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
