package zju.edu.cn.platform.jsoninfo.generator;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import zju.edu.cn.platform.gui.ConnectLineInfo;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.LineShape;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeLinkBean;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeServerBean;
import zju.edu.cn.platform.gui.config.burstloadbeans.TaskBean;
import zju.edu.cn.platform.log.Logger;
import zju.edu.cn.platform.util.PlatformUtils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

/**
 * this class load the information from json file located in `jsoninfo/test/edge_num_tasks.json` and
 * `jsoninfo/test/link_cap_status.json`, the data will be used for simulation
 */
public class BurstLoadSimulationInfo {

    private JPanel jPanelDraw;
    private EdgeGUI edgeGUI;
    private List<List<Double>> numTaskEdgeTime; // 二维列表，每个列表对应一个edge server, 每个列表的长度相同，等于最大时长
    private List<List<Double>> capLinkTime;
    private StringBuilder stringBuilderMigrateInfo;
    private int numEdges, numTasks;
    private boolean finishTimeGet;
    private int finishTime;
    private List<JLabel> jLabelList4Edges;
    private List<JLabel> jLabelList4Links;
    private JLabel labelTimeTick;


    public final static Object SYNC_SIMU_OBJ = new Object();

    public BurstLoadSimulationInfo(JPanel jPanelDraw) {
        this.jPanelDraw = jPanelDraw;
    }

    public BurstLoadSimulationInfo(JPanel jPanelDraw, EdgeGUI edgeGUI) {
        this.jPanelDraw = jPanelDraw;
        this.edgeGUI = edgeGUI;
    }

    /**
     * Load simulation needed information from json files
     */
    public void loadSimInfo() throws IOException {
        File fileEdgeTask = new File(Paths.get(edgeGUI.getAlgExecButton().
                getBurstLoadAlgUtil().getStringEdgeNumTasksPath()).toString());
        File fileLinkCap = new File(Paths.get(edgeGUI.getAlgExecButton().getBurstLoadAlgUtil().
                getStringLinkCapStatusPath()).toString());
        File migrateInfo = new File(Paths.get(edgeGUI.getAlgButton().getPathAlgDir(), "tasks_migrate_info.txt").toString());
        int ch = 0;
        BufferedReader reader = new BufferedReader(new FileReader(fileEdgeTask));
        StringBuilder stringBuilder = new StringBuilder();
        while ((ch = reader.read()) != -1) {
            stringBuilder.append((char) ch);
        }
        reader.close();
        numTaskEdgeTime = JSONObject.parseObject(stringBuilder.toString(), List.class);
        reader = new BufferedReader(new FileReader(fileLinkCap));
        stringBuilder = new StringBuilder();
        while ((ch = reader.read()) != -1) {
            stringBuilder.append((char) ch);
        }
        reader.close();
        capLinkTime = JSONObject.parseObject(stringBuilder.toString(), List.class);

        // 读取任务迁移信息
        reader = new BufferedReader(new FileReader(migrateInfo));
        String stringLine = null;
        stringBuilderMigrateInfo = new StringBuilder();
        while ((stringLine = reader.readLine()) != null && !stringLine.trim().equals("")) {
            String[] vals = stringLine.split(" ");
            stringBuilderMigrateInfo.append(String.format("task %s at time %s, migrate %s --> %s",
                    vals[0], vals[1], vals[2], vals[3]));
            stringBuilderMigrateInfo.append("\n");
        }
    }

    /**
     * 算法的第idx步状态
     *
     * @param idx 算法执行到时间步idx
     */
    private void simulationOneStep(int idx) {
        Timer timer = new Timer(idx * 400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Simulation , tick=" + idx);
                labelTimeTick.setText("Current Tick: " + idx);
                labelTimeTick.paintImmediately(labelTimeTick.getVisibleRect());
                boolean allzero = true;
                for (int j = 0; j < EdgeGUI.connectLineInfos.size(); ++j) {
                    if (Integer.parseInt(String.valueOf(capLinkTime.get(j).get(idx))) > 0) {
                        allzero = false;
                        new LineShape(EdgeGUI.connectLineInfos.get(j).getStartJLabel(),
                                EdgeGUI.connectLineInfos.get(j).getEndJLabel()).
                                display(jPanelDraw.getGraphics(), Color.WHITE);
                        new LineShape(EdgeGUI.connectLineInfos.get(j).getStartJLabel(),
                                EdgeGUI.connectLineInfos.get(j).getEndJLabel()).
                                display(jPanelDraw.getGraphics(), Color.GREEN);
                    } else {
                        new LineShape(EdgeGUI.connectLineInfos.get(j).getStartJLabel(),
                                EdgeGUI.connectLineInfos.get(j).getEndJLabel()).
                                display(jPanelDraw.getGraphics(), Color.BLACK);
                    }
                    jLabelList4Links.get(j).setText("<html><body><span>bandwidth usage=" +
                            capLinkTime.get(j).get(idx) + "</span></body></html>");
                    jLabelList4Links.get(j).paintImmediately(jLabelList4Links.get(j).getVisibleRect());
                }
                for (int j = 0; j < EdgeGUI.addedLabel.size(); ++j) {
                    EdgeGUI.addedLabel.get(j).repaint();
                    if (Integer.parseInt(String.valueOf(numTaskEdgeTime.get(j).get(idx))) > 0) {
                        EdgeGUI.addedLabel.get(j).setBackground(Color.GREEN);
                        allzero = false;
                    } else {
                        EdgeGUI.addedLabel.get(j).setBackground(Color.WHITE);
                    }
                    jLabelList4Edges.get(j).setText("<html><body><span>edge id=" +
                            EdgeGUI.addedLabel.get(j).getBurstLoadEdgeConfig().getName() + "&emsp;number of task on edge=" +
                            numTaskEdgeTime.get(j).get(idx) + "</span></body></html>");
                    jLabelList4Edges.get(j).paintImmediately(jLabelList4Edges.get(j).getVisibleRect());
                    if (j == 0) {
                        System.out.println("location=" + jLabelList4Edges.get(j).getLocation());
                    }
                }
                if (allzero && !finishTimeGet) {
                    finishTime = idx;
                    finishTimeGet = true;
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * 仿真结束之后清除界面上的标注
     */
    private void clearSimulationInfo(int t) {
        Timer timer = new Timer(t * 400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                edgeGUI.getBurstLoadConfig().getResultGUI().getTextFieldDelay().
                        setText(String.valueOf(numTaskEdgeTime.get(0).size()));
                jPanelDraw.remove(labelTimeTick);
                for (JLabel label : jLabelList4Edges) {
                    jPanelDraw.remove(label);
                }
                for (JLabel label : jLabelList4Links) {
                    jPanelDraw.remove(label);
                }
                jPanelDraw.revalidate();
                jPanelDraw.repaint();
                SwingUtilities.invokeLater(() -> {
                    for (ConnectLineInfo connectLineInfo : EdgeGUI.connectLineInfos) {
                        new LineShape(connectLineInfo.getStartJLabel(), connectLineInfo.getEndJLabel()).
                                display(jPanelDraw.getGraphics(), Color.BLACK);
                    }
                    for (JLabel label : EdgeGUI.addedLabel) {
                        label.repaint();
                    }
                });
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * using the loaded data to simulation
     */
    public void startSimulation() {
        assert PlatformUtils.PROBLEM_TYPE == PlatformUtils.BURST_LOAD_EVACUATION;

        Logger.edgeGUI = edgeGUI;
        final int maxTime = numTaskEdgeTime.get(0).size();
        labelTimeTick = new JLabel();
        labelTimeTick.setBounds(20, 0, 100, 60);
        jPanelDraw.add(labelTimeTick);
        labelTimeTick.setText("Current Tick: " + 0);
        // add hint labels for edge servers and links
        jLabelList4Edges = new ArrayList<>();
        jLabelList4Links = new ArrayList<>();
        for (int i = 0; i < EdgeGUI.connectLineInfos.size(); ++i) {
            JLabel label = new JLabel("<html><body><span>bandwidth usage=" + 0 + "</span></body></html>");
            jLabelList4Links.add(label);
            label.setBounds((EdgeGUI.connectLineInfos.get(i).getStartJLabel().getX() +
                            EdgeGUI.connectLineInfos.get(i).getEndJLabel().getX()) / 2 + 50,
                    (EdgeGUI.connectLineInfos.get(i).getStartJLabel().getY() +
                            EdgeGUI.connectLineInfos.get(i).getEndJLabel().getY()) / 2 - 50, 80, 80);
            jPanelDraw.add(label);
        }
        for (int i = 0; i < EdgeGUI.addedLabel.size(); ++i) {
            JLabel label = new JLabel("<html><body><span>number of task on edge=" + 0 + "</span></body></html>");
            jLabelList4Edges.add(label);
            label.setBounds(EdgeGUI.addedLabel.get(i).getX() + 50,
                    EdgeGUI.addedLabel.get(i).getY() - 50, 80, 80);
            jPanelDraw.add(label);
        }
        jPanelDraw.revalidate();

        // 输出任务迁移信息
        finishTime = 0;
        finishTimeGet = false;
        Logger.log(stringBuilderMigrateInfo.toString());
        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (SYNC_SIMU_OBJ) {
                    for (int i = 0; i < maxTime; ++i) {
                        simulationOneStep(i);
                    }
                }
                clearSimulationInfo(maxTime);
            }
        });
    }
}
