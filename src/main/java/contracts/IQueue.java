package contracts;

import models.Machine;
import models.Product;

import java.util.ArrayList;

public interface IQueue {

    public Machine add(Product product);

    public Product assign(Machine machine);

    public Product ask(Machine machine);

    public ArrayList<Product> getQueue();

    public ArrayList<Machine> getMachines();

    public int count();

}
