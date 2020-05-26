package utils;

import contracts.ITimeLoop;

import java.util.LinkedList;
import java.util.List;

public class TimeLoop<I> {

    public List<I> seconds(ITimeLoop<I> formula){
        LinkedList<I> list = new LinkedList<>();
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                for (int s = 0; s < 60; s++) {
                    int time = h * 3600 + m * 60 + s;
                    list.addLast(formula.seconds(time, h,m,s));

                }
            }
        }

        return list;
    }

/*    public List<I> minutes(ITimeLoop<I> formula){
        LinkedList<I> list = new LinkedList<>();
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                    int time = h * 60 + m ;
                    list.addLast(formula.minutes(time, h, m));
            }
        }

        return list;
    }

    public I minutes(int time, int h, int m);

    public I hours(int time, int h);

    public List<I> hours(ITimeLoop<I> formula){
        LinkedList<I> list = new LinkedList<>();
        for (int h = 0; h < 24; h++) {
            list.addLast(formula.hours(h, h));
        }

        return list;
    }*/

}
