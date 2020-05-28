package charts;

import configs.AnalysisConfig;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class HistogramChart {

    protected final int width = AnalysisConfig.PLOT_WIDTH;
    protected final int height = AnalysisConfig.PLOT_HEIGHT;

    protected DefaultTableXYDataset dataset = new DefaultTableXYDataset();
    protected JFreeChart chart;

    protected int countseries = 0;

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
        DefaultTableXYDataset dataset;

        if (this.countseries != 0)
            dataset = new DefaultTableXYDataset();
        else {
            dataset = this.dataset;
        }

        XYSeries serie = new XYSeries(name, true, false);

        for (int i = 0; i < series.length; i++) {
            double value = series[i];
            if (!Double.isNaN(value))
                serie.add(i + offset, value);
        }

        dataset.addSeries(serie);
        if (countseries > 0)
            this.chart.getXYPlot().setDataset(this.countseries, dataset);
        countseries++;

    }

    public void addSeries(String name, double[] series) {
        this.addSeries(name, series, +0.5);
    }

    public NumberAxis getRangeAxis() {
        return (NumberAxis) this.chart.getXYPlot().getRangeAxis();
    }

    public NumberAxis getDomainAxis() {
        return (NumberAxis) this.chart.getXYPlot().getDomainAxis();
    }

    public void render() {

        if (AnalysisConfig.EXPORT_TO_SVG)
            this.exportChartAsSVG();

        ChartFrame frame = new ChartFrame(this.title, chart);
        frame.pack();
        frame.setSize(this.width, this.height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void exportChartAsSVG() {
        String directory = "src/main/resources/charts/svg/";
        String svgPath = directory + this.title + ".svg";
        svgPath = svgPath.replaceAll(" ", "_").toLowerCase();


        SVGGraphics2D g2 = new SVGGraphics2D(this.width, this.height);
        Rectangle r = new Rectangle(0, 0, 1000, 600);
        chart.draw(g2, r);
        File f = new File(svgPath);
        try {
            SVGUtils.writeToSVG(f, g2.getSVGElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
