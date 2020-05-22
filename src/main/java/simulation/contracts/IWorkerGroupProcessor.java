package simulation.contracts;

import models.Call;

public interface IWorkerGroupProcessor {

    public boolean accept(Call call);

    public boolean available();

    public Call[] process();

}
