package conditions;

import contracts.ICondition;
import models.Product;

import java.util.List;

public class SomeCondition implements ICondition {

    @Override
    public boolean evaluate(List<Product> product) {
        return false;
    }
}
