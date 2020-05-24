package simulation2.conditions;

import simulation2.contracts.ICondition;
import simulation2.models.Product;

import java.util.List;

public class SomeCondition implements ICondition {

    @Override
    public boolean evaluate(List<Product> product) {
        return false;
    }
}
