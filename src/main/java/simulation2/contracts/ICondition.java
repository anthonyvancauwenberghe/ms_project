package simulation2.contracts;

import simulation2.models.Product;

import java.util.List;

public interface ICondition {

    public boolean evaluate(List<Product> product);
}
