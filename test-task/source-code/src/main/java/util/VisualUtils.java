package util;

import model.PointPodr;
import model.PointSum;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class VisualUtils {
    private static final String PLOT_TITLE_PATTERN = "Results of %s";
    private static final String X_AXIS_LABEL = "X-axis";
    private static final String Y_AXIS_LABEL = "Y-axis";
    private static final int PLOT_WIDTH = 1280;
    private static final int PLOT_HEIGHT = 720;

    private static final String ORIGINAL_POINTS_TITLE = "Original";
    private static final String CUMULATIVE_POINTS_TITLE = "Cumulative";

    private static final String OUTPUT_FILE_PATTERN = "%s/%s-graph.jpeg";

    private static final int MAX_PODRS_IN_LEGEND = 20;
    private static final String POOR_LABEL_PATTERN = "DR: (%.2f, %.2f, %.2f)";
    private static final Shape PODR_SHAPE = new Rectangle.Float(-3, -3, 6, 6);

    /**
     * Generates plot in jpeg image for key and result data
     */
    public static void generateGraph(String dirPath, String key, List<PointSum> pointsWithSum, List<PointPodr> podrs) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        addData(dataset, renderer, podrs, pointsWithSum);
        JFreeChart chart = createXYLineChart(dataset, renderer, key, podrs.size() <= MAX_PODRS_IN_LEGEND);
        createGraph(dirPath, key, chart);
    }

    private static void addData(XYSeriesCollection dataset, XYLineAndShapeRenderer renderer, List<PointPodr> podrs, List<PointSum> pointsWithSum) {
        int seriesCount = 0;
        for (PointPodr point : podrs) {
            XYSeries series = new XYSeries(String.format(POOR_LABEL_PATTERN, point.getX(), point.getY(), point.getCSum()));
            series.add(point.getX(), point.getY());
            series.add(point.getX(), point.getCSum());
            dataset.addSeries(series);
            renderer.setSeriesLinesVisible(seriesCount, false);
            renderer.setSeriesShape(seriesCount, PODR_SHAPE);
            renderer.setSeriesPaint(seriesCount++, Color.RED);
        }

        XYSeries original = new XYSeries(ORIGINAL_POINTS_TITLE);
        pointsWithSum.forEach(point -> original.add(point.getX(), point.getY()));
        dataset.addSeries(original);
        renderer.setSeriesPaint(seriesCount++, Color.BLUE);

        XYSeries cumulative = new XYSeries(CUMULATIVE_POINTS_TITLE);
        pointsWithSum.forEach(point -> cumulative.add(point.getX(), point.getCSum()));
        dataset.addSeries(cumulative);
        renderer.setSeriesPaint(seriesCount, Color.GREEN);
    }

    private static JFreeChart createXYLineChart(XYSeriesCollection dataset, XYLineAndShapeRenderer renderer, String key, boolean withLegend) {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                String.format(PLOT_TITLE_PATTERN, key),
                X_AXIS_LABEL,
                Y_AXIS_LABEL,
                dataset,
                PlotOrientation.VERTICAL,
                withLegend,
                true,
                false);
        xylineChart.getXYPlot().setRenderer(renderer);
        return xylineChart;
    }

    private static void createGraph(String dirPath, String key, JFreeChart chart) {
        File XYChart = new File(String.format(OUTPUT_FILE_PATTERN, dirPath, key));
        try {
            ChartUtils.saveChartAsJPEG(XYChart, chart, PLOT_WIDTH, PLOT_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
