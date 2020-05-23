package contracts;

public interface Distribution<T> {

    public T sample();

    public T[] sample(int size);
}
