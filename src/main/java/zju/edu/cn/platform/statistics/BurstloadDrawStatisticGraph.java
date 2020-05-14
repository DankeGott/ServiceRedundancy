package zju.edu.cn.platform.statistics;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BurstloadDrawStatisticGraph extends JFrame {

    private IterLinePlot iterLinePlot;
    private StackBarGraph stackBarGraph;
    private String pathDirStr;

    public BurstloadDrawStatisticGraph(String pathDirStr) throws IOException {
        super("统计图");
        this.pathDirStr = pathDirStr;
        loadStatisticInfo();
        init();
    }

    /**
     * 加载统计数据
     */
    private void loadStatisticInfo() throws IOException {
        String iterFilePathStr = Paths.get(pathDirStr, "iter_info.txt").toString();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(iterFilePathStr)));
        iterLinePlot = new IterLinePlot();
        List<String> listTms = new ArrayList<>();
        String lineVal = null;
        while ((lineVal = bufferedReader.readLine()) != null && !lineVal.trim().equals("")) {
            listTms.add(lineVal.trim());
        }
        bufferedReader.close();
        double[][] iterDoubles = new double[1][];
        String[] tmStrs = new String[listTms.size()];
        iterDoubles[0] = new double[listTms.size()];
        for (int i = 0; i < listTms.size(); ++i) {
            iterDoubles[0][i] = Double.parseDouble(listTms.get(i));
            tmStrs[i] = String.valueOf(i);
        }
        iterLinePlot.setTmStrs(tmStrs);
        iterLinePlot.setDelayDoubles(iterDoubles);
        iterLinePlot.createChart();

        stackBarGraph = new StackBarGraph();
        stackBarGraph.createChart(pathDirStr);
    }

    private void init() {
        this.setLayout(new GridLayout(2, 1, 10, 10));
        JScrollPane scrollPane1 = new JScrollPane(iterLinePlot.getChartPanel());
        this.add(scrollPane1);
        JScrollPane scrollPane2 = new JScrollPane(stackBarGraph.getChartPanel());
        this.add(scrollPane2);
        this.setBounds(50, 50, 800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
