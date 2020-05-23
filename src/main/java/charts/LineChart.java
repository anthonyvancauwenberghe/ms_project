package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class LineChart extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;

    JFreeChart chart;

    ChartPanel panel;

    protected String XAxxis;

    public LineChart(String title, String XAxis, String YAxxis, double[] data) {
        super(title);

        this.XAxxis = XAxis;

        // Create dataset
        XYDataset dataset = createDataset(data, XAxis);

        // Create chart
        this.chart = ChartFactory.createXYLineChart(
                title,
                XAxis,
                YAxxis,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Create Panel
        this.panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public void updateDataSet(double[] dataset){
        this.createDataset(dataset, this.XAxxis);
        chart.getXYPlot().setDataset(chart.getXYPlot().getDataset());
        this.panel.getChart().fireChartChanged();
    }

    private XYDataset createDataset(double[] data, String Xaxxis) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(Xaxxis);

        for (int i = 0; i < data.length; i++) {
            series.add(i, data[i]);
        }

        dataset.removeAllSeries();

        //Add series to dataset
        dataset.addSeries(series);

        return dataset;
    }

    public void render() {
        this.setSize(800, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
