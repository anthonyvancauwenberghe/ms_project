package simulation2.contracts;

public interface IListener<T> {
    /**
     *	Method that is triggered when the event gets fired
     */
    public void handle(T event);
}
