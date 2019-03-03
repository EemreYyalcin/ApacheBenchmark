package com.benchmark.apache;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

public class DrawGraph extends ApplicationFrame {

    public DrawGraph(String title, XYSeries series) {
        super(title);
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Time(sn)",
                title,
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 470));
        setContentPane(chartPanel);

    }


    public static void main(final String[] args) {

        XYSeries xySeries = new XYSeries("DATA");
        xySeries.add(1,4,true);
        xySeries.add(2,41,true);
        xySeries.add(35,44,true);
        xySeries.add(43,54,true);
        xySeries.add(76,64,true);
        xySeries.add(33,44,true);

        DrawGraph demo = new DrawGraph("XY Series Demo", xySeries);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        DrawGraph demo2 = new DrawGraph("XY Series Demo", xySeries);
        demo2.pack();
        RefineryUtilities.centerFrameOnScreen(demo2);
        demo2.setVisible(true);

    }




}
