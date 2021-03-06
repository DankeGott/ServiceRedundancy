package zju.edu.cn.platform.statistics;

import zju.edu.cn.platform.statistics.DrawChart.DelayFreqBar;
import zju.edu.cn.platform.statistics.DrawChart.ServerNumTaskBar;
import zju.edu.cn.platform.statistics.DrawChart.ServerNumTaskPie;
import zju.edu.cn.platform.statistics.DrawChart.ServerReceiveNumTaskBar;
import zju.edu.cn.platform.util.PlatformUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawStatisticGraph {
    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        showStatistics(PlatformUtils.PATH + "/result/requests/requests_1.txt");
    }

    public static void showStatistics(String resultPath) throws IOException {
        List<RequestData> requestData = new ArrayList<>();
        File f = new File(resultPath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] strs = line.split(",");
            RequestData rq = new RequestData(
                    Integer.parseInt(strs[0]),
                    strs[1],
                    strs[2],
                    strs[3],
                    Double.parseDouble(strs[4])
            );
            requestData.add(rq);
        }

        JFrame frame = new JFrame("EdgeSim数据统计");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2,2,10,10));
        frame.add(new ServerNumTaskBar(requestData).getChartPanel());
        frame.add(new ServerReceiveNumTaskBar(requestData).getChartPanel());
        frame.add(new DelayFreqBar(requestData).getChartPanel());
        frame.add(new ServerNumTaskPie(requestData).getChartPanel());
        frame.setBounds(50, 50, 1280, 900);
        frame.setVisible(true);
    }
}
