package contracts;

public interface Aggregate<I,O> {
    public O group(I input);
}
