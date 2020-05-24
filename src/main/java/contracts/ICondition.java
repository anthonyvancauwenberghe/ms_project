package contracts;

import models.Product;

import java.util.List;

public interface ICondition {

    public boolean evaluate(List<Product> product);
}
