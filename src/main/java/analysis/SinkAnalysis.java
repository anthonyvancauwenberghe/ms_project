package analysis;

import charts.HistogramChart;
import configs.ServiceTimesConfig;
import configs.SimulationConfig;
import contracts.Aggregate;
import contracts.IQueue;
import enums.ProductType;
import models.Machine;
import models.Product;
import models.Sink;

import java.util.ArrayList;

public class SinkAnalysis {
    protected ArrayList<Product> products;

    protected IQueue queue;

    protected String productType;

    public SinkAnalysis(Sink sink, IQueue endQueue, String productType) {
        this.products = sink.getProducts();
        this.queue = endQueue;
        this.productType = productType;
    }


    public int count() {
        return this.products.size();
    }

    public double avgDailyQueueTime() {
        return this.totalDailyQueueTime() / this.count();
    }

    public double totalDailyQueueTime() {
        double total = 0;
        for (Product product : this.products) {
            total += product.getQueueTime();
        }
        return total;
    }

    public double probabilityOfQueueTimeLessThan(double duration) {
        double count = 0;
        double totalCount = 0;
        for (Product product : this.getProductsWithArrivalBetween(0, SimulationConfig.SIMULATION_RUNTIME)) {
            if (product.getQueueTime() <= duration) {
                count++;
            }
            totalCount++;
        }
        return count / totalCount;
    }

    public double probabilityOfQueueTimeLessThan(double duration, double startTime, double endTime) {
        double count = 0;
        double totalCount = 0;
        for (Product product : this.getProductsWithArrivalBetween(startTime, endTime)) {
            if (product.getQueueTime() <= duration) {
                count++;
            }
            totalCount++;
        }
        return count / totalCount;
    }

    public double avgDailyQueueTime(int hour) {

        double minArrival = hour * 3600;
        double maxArrival = (hour + 1) * 3600;

        return this.averageProductValue(this.getProductsWithArrivalBetween(minArrival, maxArrival), input -> input.getQueueTime());
    }

    public ArrayList<Product> getProductsWithArrivalBetween(double minArrival, double maxArrival) {
        ArrayList<Product> products = new ArrayList<>();

        //add products from sink
        for (Product product : this.products) {
            if (product.getArrivalTimeForAnalysis(product.getArrivalTime()) >= minArrival && product.getArrivalTimeForAnalysis(product.getArrivalTime()) < maxArrival) {
                products.add(product);
            }
        }

        //add products from queue
        for (Product product : this.queue.getQueue()) {
            if (product.getArrivalTimeForAnalysis(product.getArrivalTime()) >= minArrival && product.getArrivalTimeForAnalysis(product.getArrivalTime()) < maxArrival) {
                if (!product.hasQueueTime()) {
                    product.setQueueTime(SimulationConfig.SIMULATION_RUNTIME - product.getArrivalTimeForAnalysis(product.getArrivalTime()));
                }

                products.add(product);
            }
        }

        //add products from busy machines
        for (Machine machine : this.queue.getMachines()) {
            if (machine.isBusy()) {
                Product product = machine.getProduct();
                if (product.getArrivalTimeForAnalysis(product.getArrivalTime()) >= minArrival && product.getArrivalTimeForAnalysis(product.getArrivalTime()) < maxArrival) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    public double avgDailyQueueTime(int hour, int minute) {
        double minArrival = hour * 3600 + minute * 60;
        double maxArrival = hour * 3600 + (minute + 1) * 60;

        return this.averageProductValue(this.getProductsWithArrivalBetween(minArrival, maxArrival), input -> input.getQueueTime());
    }

    public double getAvgProductionTime() {
        return this.averageProductValue(this.getProductsWithArrivalBetween(0, SimulationConfig.SIMULATION_RUNTIME), product -> product.getProductionTime());
    }

    public double[] getAvgProductionTimeProbabilities() {

        ProductType type = ProductType.CONSUMER;
        double[] total = new double[1000];
        for (Product product : this.products) {
            int interval = (int) (product.getProductionTime() - (product.getProductionTime() % 1));
            total[interval]++;
            type = product.type();
        }

        for (int i = 0; i < total.length; i++) {
            double value = total[i];
            if (Double.isNaN(value) || value < 1)
                value = 0;

            if(type.isConsumer()){
                if(i< ServiceTimesConfig.CONSUMER_SERVICE_TIME_TRUNC_LEFT)
                    value = 0;
            }
            if(type.isCorporate()){
                if(i< ServiceTimesConfig.CORPORATE_SERVICE_TIME_TRUNC_LEFT)
                    value = 0;
            }

            total[i] = value / this.products.size();
        }
        return total;

    }


    protected double averageProductValue(ArrayList<Product> products, Aggregate<Product, Double> aggr) {
        double total = 0;
        double count = 0;
        for (Product product : products) {
            total += aggr.group(product);
            count++;
        }
        return (total / 60.0) / count;
    }

    public double avgArrivals(int hour) {
        double minArrival = hour * 3600;
        double maxArrival = (hour + 1) * 3600;

        return this.getProductsWithArrivalBetween(minArrival, maxArrival).size();
    }

    public double avgArrivals(int hour, int minute) {
        double minArrival = hour * 3600 + minute * 60;
        double maxArrival = hour * 3600 + (minute + 1) * 60;

        return this.getProductsWithArrivalBetween(minArrival, maxArrival).size();
    }

    public double[] avgQueueTimePerHour() {
        double[] times = new double[24];
        for (int h = 0; h < 24; h++) {
            times[h] = this.avgDailyQueueTime(h);
        }
        return times;
    }

    public double[] avgQueueTimePerMinute() {
        double[] times = new double[24 * 60];
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                times[60 * h + m] = this.avgDailyQueueTime(h, m);
            }
        }
        return times;
    }

    public double[] arrivalsPerMinute() {
        double[] times = new double[24 * 60];
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                times[60 * h + m] = this.avgArrivals(h, m);
            }
        }
        return times;
    }

    public double[] arrivalsPerHour() {
        double[] arrivals = new double[24];
        for (int h = 0; h < 24; h++) {
            arrivals[h] = this.avgArrivals(h);
        }
        return arrivals;
    }


    public void plotAvgHourlyQueueTimes() {
        HistogramChart chart = new HistogramChart(this.productType + " Queue times", "hour", "queue time (min)");
        chart.addSeries(this.productType + " queue times", this.avgQueueTimePerHour());

        chart.render();
    }

    public void plotAvgMinutelyQueueTimes() {
        HistogramChart chart = new HistogramChart(this.productType + " Queue times", "hour", "queue time (min)");
        chart.addSeries(this.productType + " queue times", this.avgQueueTimePerHour());

        chart.render();
    }

    public void plotArrivalsPerHour() {
        HistogramChart chart = new HistogramChart(this.productType + " Arrivals", "hour", "Arrival Amount");
        chart.addSeries(this.productType + " arrivals", this.arrivalsPerHour());

        chart.render();
    }

}
