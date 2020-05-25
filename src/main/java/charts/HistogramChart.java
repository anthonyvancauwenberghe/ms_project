package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;

public class HistogramChart {

    protected DefaultTableXYDataset dataset = new DefaultTableXYDataset();
    protected JFreeChart chart;

    protected String title;
    protected String xAxxis;
    protected String yAxxis;

    public HistogramChart(String title, String xAxxis, String yAxxis) {
        this.title = title;
        this.xAxxis = xAxxis;
        this.yAxxis = yAxxis;
        this.chart = ChartFactory.createHistogram(this.title, this.xAxxis, this.yAxxis, dataset, PlotOrientation.VERTICAL, true, true, true);
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void addSeries(String name, double[] series, double offset) {
        XYSeries serie = new XYSeries(name, true, false);

        for (int i = 0; i < series.length; i++) {
            double value = series[i];
            if (!Double.isNaN(value))
                serie.add(i + offset, value);
        }

        dataset.addSeries(serie);
    }

    public void addSeries(String name, double[] series) {
        this.addSeries(name, series, +0.5);
    }

    public NumberAxis getRangeAxis(){
        return (NumberAxis) this.chart.getXYPlot().getRangeAxis();
    }

    public NumberAxis getDomainAxis(){
        return (NumberAxis) this.chart.getXYPlot().getDomainAxis();
    }

    public void render() {


        ChartFrame frame = new ChartFrame(this.title, chart);
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
