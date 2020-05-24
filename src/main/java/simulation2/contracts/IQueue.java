package simulation2.contracts;

import simulation2.models.Machine;
import simulation2.models.Product;

public interface IQueue {

    public Machine add(Product product);

    public Product assign(Machine machine);

    public Product ask(Machine machine);

}
