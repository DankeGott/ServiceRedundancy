package zju.edu.cn.platform.statistics;


import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class StackBarGraph {

    private JFreeChart jFreeChart;
    private double[][] data;
    private ChartPanel chartPanel;

    public void createChart(String pathDirStr) throws IOException {
        String dataFilePathStr = Paths.get(pathDirStr, "tasks_tm_series.txt").toString();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(dataFilePathStr)));
        String stringLine = null;
        List<List<Double>> listsData = new ArrayList<>();
        // 记录每个任务在4个阶段的时长
        listsData.add(new ArrayList<>());
        listsData.add(new ArrayList<>());
        listsData.add(new ArrayList<>());
        listsData.add(new ArrayList<>());
        while ((stringLine = bufferedReader.readLine()) != null && !stringLine.trim().equals("")) {
            double t1 = Double.parseDouble(stringLine.split(" ")[0]),
                    t2 = Double.parseDouble(stringLine.split(" ")[1]),
                    t3 = Double.parseDouble(stringLine.split(" ")[2]),
                    t4 = Double.parseDouble(stringLine.split(" ")[3]);
            listsData.get(0).add(t1);
            listsData.get(1).add(t2);
            listsData.get(2).add(t3);
            listsData.get(3).add(t4);
        }
        bufferedReader.close();
        data = new double[4][];
        data[0] = new double[listsData.get(0).size()];
        data[1] = new double[listsData.get(1).size()];
        data[2] = new double[listsData.get(2).size()];
        data[3] = new double[listsData.get(3).size()];

        for (int i = 0; i < listsData.get(0).size(); ++i) {
            data[0][i] = listsData.get(0).get(i);
            data[1][i] = listsData.get(1).get(i);
            data[2][i] = listsData.get(2).get(i);
            data[3][i] = listsData.get(3).get(i);
        }

        CategoryDataset categoryDataset = DatasetUtilities.createCategoryDataset("Stage",
                "Task", data);
        jFreeChart = ChartFactory.createStackedBarChart("任务阶段时间柱状图(等待在起始服务器，迁移，等待执行，执行)",
                "",
                "时长", categoryDataset, PlotOrientation.HORIZONTAL, true,
                true, false);
        jFreeChart.setTitle(new TextTitle(jFreeChart.getTitle().getText(), new Font("黑体", 0, 20)));
        StandardChartTheme chartTheme = new StandardChartTheme("CN");
        chartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
        chartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
        chartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
        ChartFactory.setChartTheme(chartTheme);
        CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
        categoryPlot.getRenderer().setSeriesPaint(0, new Color(0xD02522));
        categoryPlot.getRenderer().setSeriesPaint(1, new Color(0xC7AE3F));
        categoryPlot.getRenderer().setSeriesPaint(2, new Color(0x11DA38));
        categoryPlot.getRenderer().setSeriesPaint(3, new Color(0x5571DA));
        categoryPlot.getDomainAxis().setTickLabelFont(new Font("宋体", 0, 10));
        categoryPlot.getRangeAxis().setLabelFont(new Font("宋体", 0, 10));
        chartPanel = new ChartPanel(jFreeChart, true);
    }
}
