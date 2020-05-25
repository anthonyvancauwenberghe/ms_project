package models;

import enums.ProductType;

import java.util.ArrayList;

/**
 * Product that is send trough the system
 *
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Product {
    /**
     * Stamps for the products
     */
    protected ArrayList<Double> times = new ArrayList<>();
    protected ArrayList<String> events = new ArrayList<>();
    protected ArrayList<String> stations = new ArrayList<>();

    protected final ProductType type;

    private double productionTime = -1;

    /**
     * Constructor for the product
     * Pass the product type as param
     */
    public Product(ProductType type) {
        this.type = type;
    }

    public void setProductionTime(double time) {
        if (productionTime != -1)
            throw new RuntimeException("Can only set production time when it's not initialized yet");

        this.productionTime = time;
    }

    public double getProductionTime() {
        if (this.productionTime == -1)
            this.productionTime = this.type().getServiceTimeDistribution().sample();

        return this.productionTime;
    }

    public ProductType type() {
        return this.type;
    }

    public void stamp(double time, String event, String station) {
        times.add(time);
        events.add(event);
        stations.add(station);
    }

    public ArrayList<Double> getTimes() {
        return times;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public ArrayList<String> getStations() {
        return stations;
    }

    public double[] getTimesAsArray() {
        times.trimToSize();
        double[] tmp = new double[times.size()];
        for (int i = 0; i < times.size(); i++) {
            tmp[i] = (times.get(i)).doubleValue();
        }
        return tmp;
    }

    public String[] getEventsAsArray() {
        String[] tmp = new String[events.size()];
        tmp = events.toArray(tmp);
        return tmp;
    }

    public String[] getStationsAsArray() {
        String[] tmp = new String[stations.size()];
        tmp = stations.toArray(tmp);
        return tmp;
    }

    public ProductType getType() {
        return type;
    }
}