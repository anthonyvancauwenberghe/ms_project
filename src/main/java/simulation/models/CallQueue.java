package simulation.models;

import models.Call;

import java.util.LinkedList;
import java.util.Queue;

public class CallQueue {
    protected Queue<Call> queue = new LinkedList<Call>();

    public void add(Call call){
        this.queue.add(call);
    }

    public Call remove(){
        return queue.remove();
    }



}
