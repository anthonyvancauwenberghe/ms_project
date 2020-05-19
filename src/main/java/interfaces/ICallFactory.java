package interfaces;

import abstracts.Call;
import enums.CallType;

public interface ICallFactory {
    public Call build(CallType type);
}
