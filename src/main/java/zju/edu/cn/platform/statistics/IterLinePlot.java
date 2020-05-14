package zju.edu.cn.platform.statistics;

import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import java.awt.*;

/**
 * 算法迭代折线图
 */
@Getter
@Setter
public class IterLinePlot {

    private String[] tmStrs;
    private double[][] delayDoubles;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public void createChart() {
        CategoryDataset categoryDataset = DatasetUtilities.createCategoryDataset(new String[]{"算法迭代"},
                tmStrs, delayDoubles);
        chart = ChartFactory.createLineChart("算法的迭代情况(非迭代类算法显示的为直线)", "时间",
                "最大延迟", categoryDataset, PlotOrientation.VERTICAL,
                true, false, false);
        chart.setTitle(new TextTitle(chart.getTitle().getText(),new Font("宋体",1,13)));
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundAlpha(0.5f);
        plot.setForegroundAlpha(0.5f);
        CategoryAxis domainAxis=plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("宋体",1,12));
        domainAxis.setTickLabelFont(new Font("宋体",0,10));
        NumberAxis numberAxis=(NumberAxis)plot.getRangeAxis();
        numberAxis.setTickLabelFont(new Font("宋体",0,12));
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setLabelFont(new Font("宋体",1,10));
        chart.getLegend().setItemFont(new Font("宋体",0,12));
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        chartPanel = new ChartPanel(chart, true);
    }

}
