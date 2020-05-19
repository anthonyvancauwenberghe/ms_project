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

    public LineChart(String title, String XAxis, String YAxxis, double[] data) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(data, XAxis);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                XAxis,
                YAxxis,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(double[] data, String Xaxxis) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(Xaxxis);

        for (int i = 0; i < data.length; i++) {
            series.add(i, data[i]);
        }

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
