package zju.edu.cn.platform.gui.config.burstload;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.ConnectLineInfo;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.IconLabel;
import zju.edu.cn.platform.gui.LineShape;
import zju.edu.cn.platform.gui.button.EdgeIconButton;
import zju.edu.cn.platform.gui.button.PrintIconButton;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeLinkBean;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeLocBean;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeServerBean;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.jsoninfo.config.burstload.BurstLoadEdgeConfig;
import zju.edu.cn.platform.jsoninfo.config.burstload.BurstLoadLinkConfig;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class BurstLoadConfig {
    private EdgeGUI edgeGUI;
    private EdgeServerConfigGUI edgeServerConfigGUI;
    private LinkConfigGUI linkConfigGUI;
    private TaskConfigGUI taskConfigGUI;
    private ResultGUI resultGUI;

    public BurstLoadConfig(EdgeGUI edgeGUI) {
        this.edgeGUI = edgeGUI;
    }

    /**
     * Add burst load problem configures panel
     */
    public void addConfigPanel() {
        JPanel panelConfig = new JPanel(null);
        panelConfig.setBounds(EdgeGUIPositionConfig.CONFIG_PANEL_X, EdgeGUIPositionConfig.CONFIG_PANEL_Y,
                EdgeGUIPositionConfig.CONFIG_PANEL_WIDTH, EdgeGUIPositionConfig.CONFIG_PANEL_HEIGHT);
        edgeServerConfigGUI = new EdgeServerConfigGUI(edgeGUI);
        linkConfigGUI = new LinkConfigGUI(edgeGUI);
        taskConfigGUI = new TaskConfigGUI(edgeGUI);

        panelConfig.add(edgeServerConfigGUI);
        panelConfig.add(linkConfigGUI);
        panelConfig.add(taskConfigGUI);

        edgeGUI.getFrame().add(panelConfig);
    }

    /**
     * Add the total delay time of burst load problem
     */
    public void addResultPanel() {
        resultGUI = new ResultGUI(edgeGUI);
        edgeGUI.getFrame().add(resultGUI);
    }

    /**
     * generate configure file from the inputs in the configure panel
     */
    public void generateConfig() throws IOException {
        edgeServerConfigGUI.setEdgeGenerator();
        edgeGUI.getJsonConfig().setEdgeServerBeanList(edgeServerConfigGUI.getBurstLoadEdgeGenerator().generateBurstLoadEdgeConfig());

        linkConfigGUI.setLinkGenerator(edgeGUI.getJsonConfig().getEdgeServerBeanList());
        edgeGUI.getJsonConfig().setEdgeLinkBeanList(linkConfigGUI.getBurstLoadLinkGenerator().generateLinkConfigs());

        taskConfigGUI.setTaskGenerator();
        edgeGUI.getJsonConfig().setEdgeLocBeanList(edgeServerConfigGUI.getBurstLoadEdgeGenerator().
                generateEdgeLocs(new int[]{0, edgeGUI.getDrawJPanel().getWidth()}, new int[]{0, edgeGUI.getDrawJPanel().getHeight()}));

        edgeGUI.getJsonConfig().setTaskBeanList(taskConfigGUI.getTaskGenerator().generateTask());
        // convert to display
        convertJsonConfig();
        convertJsonConfigForAlg(); //将配置转换为python算法脚本使用的文本文件格式，并保存在算法所在的目录下
    }

    /**
     * 将json格式的文件转换为python脚本使用的文本文件格式，并将文件保存在python编写的算法所在的目录下
     */
    public void convertJsonConfigForAlg() throws IOException {
        List<EdgeServerBean> edgeServerBeans = edgeGUI.getJsonConfig().getEdgeServerBeanList();
        String parentDir = edgeGUI.getAlgButton().getPathAlgDir();
        File fileInfo = new File(Paths.get(parentDir, "info.txt").toString());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileInfo));
        // 边缘服务器配置，按照编号输出
        bufferedWriter.write("# edge servers info, index from 0");
        bufferedWriter.newLine();
        edgeServerBeans.sort(new Comparator<EdgeServerBean>() {
            @Override
            public int compare(EdgeServerBean edgeServerBean, EdgeServerBean t1) {
                int idx = Integer.parseInt(edgeServerBean.getName().substring(1)),
                        idx2 = Integer.parseInt(t1.getName().substring(1));
                return idx - idx2;
            }
        });
        for (int i = 0; i < edgeServerBeans.size(); ++i) {
            bufferedWriter.write(edgeServerBeans.get(i).getFreq() + " " + edgeServerBeans.get(i).getRam() +
                    " " + edgeServerBeans.get(i).getStorage());
            bufferedWriter.newLine();
        }
        // 链路自身配置信息，按照编号输出
        bufferedWriter.write("# edge links info, travel_time，max task capacity");
        bufferedWriter.newLine();
        List<EdgeLinkBean> edgeLinkBeans = edgeGUI.getJsonConfig().getEdgeLinkBeanList();
        edgeLinkBeans.sort(new Comparator<EdgeLinkBean>() {
            @Override
            public int compare(EdgeLinkBean edgeLinkBean, EdgeLinkBean t1) {
                int idx1 = Integer.parseInt(edgeLinkBean.getName().substring(1)),
                        idx2 = Integer.parseInt(edgeLinkBean.getName().substring(1));
                return idx1 - idx2;
            }
        });
        for (int i = 0; i < edgeLinkBeans.size(); ++i) {
            bufferedWriter.write(edgeLinkBeans.get(i).getTravelTime() + " " + edgeLinkBeans.get(i).getMaxCapacity());
            bufferedWriter.newLine();
        }
        // 网络图的连接
        bufferedWriter.write("# graph info, edge1 edge2 and their link index");
        bufferedWriter.newLine();
        for (int i = 0; i < edgeLinkBeans.size(); ++i) {
            int idx = Integer.parseInt(edgeLinkBeans.get(i).getName().substring(1));
            bufferedWriter.write(edgeLinkBeans.get(i).getStartEdge().substring(1) + " " +
                    edgeLinkBeans.get(i).getEndEdge().substring(1) + " " + idx);
            bufferedWriter.newLine();
        }
        // 随机生成的任务个数
        bufferedWriter.write("# task num");
        bufferedWriter.newLine();
        bufferedWriter.write(edgeGUI.getBurstLoadConfig().getTaskConfigGUI().getTextFieldNumTasks().getText());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * load info from json configure file
     */
    public void convertJsonConfig() {
        edgeGUI.getDrawJPanel().removeAll();
        EdgeGUI.addedLabel = new ArrayList<>();
        EdgeGUI.connectLineInfos = new ArrayList<>();
        edgeGUI.getDrawJPanel().revalidate();
        edgeGUI.getDrawJPanel().repaint();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < edgeGUI.getJsonConfig().getEdgeServerBeanList().size(); ++i) {
                    EdgeServerBean edgeServerBean = edgeGUI.getJsonConfig().getEdgeServerBeanList().get(i);
                    EdgeLocBean locBean = edgeGUI.getJsonConfig().getEdgeLocBeanList().get(i);
                    IconLabel label;
                    label = new IconLabel(EdgeIconButton.EDGE_SERVER_BUTTON,
                            PrintIconButton.ICON_CONFIG.get(EdgeIconButton.EDGE_SERVER_BUTTON).getImageIcon());
                    label.setMouseAdapter(edgeGUI.getPrintIconButtonEdge().new MyMouseAdapter(label));
                    BurstLoadEdgeConfig edgeConfig = new BurstLoadEdgeConfig();
                    edgeConfig.setName(edgeServerBean.getName());
                    edgeConfig.setEdgeCpuCycle(edgeServerBean.getFreq());
                    label.setBurstLoadEdgeConfig(edgeConfig);
                    edgeConfig.setX(locBean.getX());
                    edgeConfig.setY(locBean.getY());
                    edgeConfig.setWidth(PrintIconButton.ICON_CONFIG.get(EdgeIconButton.EDGE_SERVER_BUTTON).getIconWidth());
                    edgeConfig.setHeight(PrintIconButton.ICON_CONFIG.get(EdgeIconButton.EDGE_SERVER_BUTTON).getIconHeight());
                    label.setPos(edgeConfig);
                    Font font = new Font(null, Font.PLAIN, 10);
                    label.setFont(font);
                    label.setHorizontalTextPosition(JLabel.CENTER);
                    label.setVerticalTextPosition(JLabel.BOTTOM);
                    label.setText(label.getEntityName());
                    // add to draw panel
                    edgeGUI.getDrawJPanel().add(label);
                    EdgeGUI.addedLabel.add(label);
                }

                for (int i = 0; i < edgeGUI.getJsonConfig().getEdgeLinkBeanList().size(); ++i) {
                    EdgeLinkBean linkBean = edgeGUI.getJsonConfig().getEdgeLinkBeanList().get(i);
                    IconLabel labelStart = null, labelEnd = null;
                    for (int k = 0; k < EdgeGUI.addedLabel.size(); ++k) {
                        if (EdgeGUI.addedLabel.get(k).getEntityName().equals(linkBean.getStartEdge())) {
                            labelStart = EdgeGUI.addedLabel.get(k);
                        }
                        if (EdgeGUI.addedLabel.get(k).getEntityName().equals(linkBean.getEndEdge())) {
                            labelEnd = EdgeGUI.addedLabel.get(k);
                        }
                    }
                    LineShape lineShape = new LineShape(labelStart, labelEnd);
                    lineShape.setLineStroke(3);
                    ;
                    lineShape.display(edgeGUI.getDrawJPanel().getGraphics(), Color.BLACK);
                    ConnectLineInfo connectLineInfo = new ConnectLineInfo(labelStart, labelEnd, Color.BLACK);

                    BurstLoadLinkConfig burstLoadLinkConfig = new BurstLoadLinkConfig();
                    burstLoadLinkConfig.setName(linkBean.getName());
                    burstLoadLinkConfig.setStartEdgeName(linkBean.getStartEdge());
                    burstLoadLinkConfig.setEndEdgeName(linkBean.getEndEdge());
                    burstLoadLinkConfig.setParallelWidth(linkBean.getMaxCapacity());
                    burstLoadLinkConfig.setTransRate(linkBean.getTravelTime());

                    connectLineInfo.setBurstLoadLinkConfig(burstLoadLinkConfig);
                    labelStart.getLines().add(connectLineInfo);
                    labelEnd.getLines().add(connectLineInfo);
                    EdgeGUI.connectLineInfos.add(connectLineInfo);

                }
            }
        });
    }
}
