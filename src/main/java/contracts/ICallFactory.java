package contracts;

import models.Call;
import enums.CallType;

public interface ICallFactory {
    public Call build(CallType type);
}
