package simulation2.models;

import simulation2.contracts.ProductAcceptor;

public class ConsumerAgent extends Machine {
    public ConsumerAgent(Queue q, ProductAcceptor s, CEventList e, String n) {
        super(q, s, e, n);
    }

    @Override
    public boolean giveProduct(Product p) {
        if (p.type().isConsumer())
            return super.giveProduct(p);

        return false;
    }
}
