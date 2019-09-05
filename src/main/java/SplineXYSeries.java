import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;


class SplineXYSeries extends ApplicationFrame {

    private static List<Double> arrayX = new ArrayList<Double>() {
        {
            this.add(-1.6);
            this.add(-1.2);
            this.add(-0.8);
            this.add(-0.4);
            this.add(0.0);
            this.add(0.4);
            this.add(0.8);
            this.add(1.2);
            this.add(1.6);
            this.add(2.0);
        }
    };

    SplineXYSeries(final String title) {
        super(title);
        final org.jfree.data.xy.XYSeries series = new org.jfree.data.xy.XYSeries("Function");
        CubicalSplineMethod currentTask = new CubicalSplineMethod();
        List<Double> coef = currentTask.calculateSpline();
        System.out.print(coef);
        for(int i = 0; i < 101; ++i)
        {
            Double x = arrayX.get(0)+(arrayX.get(arrayX.size()-1)-arrayX.get(0))*i/100.0;
            Double y = currentTask.getSpline(x,coef);
            series.add(x,y);
        }
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Series CubicalSplineMethod",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
    public static void main(final String[] args) {

        final SplineXYSeries firstWindow = new SplineXYSeries("XY Series CubicalSplineMethod");
        firstWindow.pack();
        RefineryUtilities.centerFrameOnScreen(firstWindow);
        firstWindow.setVisible(true);
    }

}
