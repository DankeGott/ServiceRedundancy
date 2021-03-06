package zju.edu.cn.platform.gui.config.resalloc;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.ConnectLineInfo;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.IconLabel;
import zju.edu.cn.platform.gui.LineShape;
import zju.edu.cn.platform.gui.button.EdgeIconButton;
import zju.edu.cn.platform.gui.button.PrintIconButton;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.DeviceConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.EdgeConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.LinkConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.WirelessLinkConfig;
import zju.edu.cn.platform.jsoninfo.generator.PositionGenerator;
import zju.edu.cn.platform.jsoninfo.generator.SolutionConfigGenerator;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResourceAllocConfig {
    private ServiceConfigGUI serviceConfigGUI;
    private EdgeConfigGUI edgeConfigGUI;
    private MobileDeviceConfigGUI mobileDeviceConfigGUI;
    private ResultGUI resultGUI;
    private EdgeGUI edgeGUI;
    private PositionGenerator positionGenerator;

    public ResourceAllocConfig(EdgeGUI edgeGUI) {
        this.edgeGUI = edgeGUI;
    }

    public void addConfigPanel() {
        JPanel panelConfigs = new JPanel(null);
        panelConfigs.setBounds(EdgeGUIPositionConfig.CONFIG_PANEL_X, EdgeGUIPositionConfig.CONFIG_PANEL_Y,
                EdgeGUIPositionConfig.CONFIG_PANEL_WIDTH, EdgeGUIPositionConfig.CONFIG_PANEL_HEIGHT);

        serviceConfigGUI = new ServiceConfigGUI(edgeGUI); // 传入自身，获取数据
        edgeConfigGUI = new EdgeConfigGUI(edgeGUI);
        mobileDeviceConfigGUI = new MobileDeviceConfigGUI(edgeGUI);

        panelConfigs.add(edgeConfigGUI);
        panelConfigs.add(serviceConfigGUI);
        panelConfigs.add(mobileDeviceConfigGUI);

        edgeGUI.getFrame().add(panelConfigs);
    }

    public void addResultConfig() {
        resultGUI = new ResultGUI(edgeGUI);
        edgeGUI.getFrame().add(resultGUI);
    }

    public void generateConfig() {
        positionGenerator = new PositionGenerator(edgeGUI.getDrawJPanel());
        edgeConfigGUI.setEdgeGenerator(positionGenerator);
        edgeGUI.getJsonConfig().setEdges(edgeConfigGUI.getEdgeGenerator().generateEdge());

        edgeConfigGUI.setLinkGenerator(edgeGUI.getJsonConfig().getEdges());
        edgeGUI.getJsonConfig().setLinks(edgeConfigGUI.getLinkGenerator().generateLink());

        serviceConfigGUI.setAppGenerator();
        edgeGUI.getJsonConfig().setApps(serviceConfigGUI.getAppGenerator().generateApp());

        mobileDeviceConfigGUI.setDeviceGenerator(edgeGUI.getJsonConfig().getEdges(), edgeGUI.getJsonConfig().getApps(), positionGenerator);
        edgeGUI.getJsonConfig().setDevices(mobileDeviceConfigGUI.getDeviceGenerator().generateDeviceConfigs());
        edgeGUI.getJsonConfig().setWirelessLinkConfigs(mobileDeviceConfigGUI.getDeviceGenerator().generateWirelessConfig(edgeGUI.getJsonConfig().getDevices()));
        edgeGUI.getJsonConfig().setResourceAllocationConfigs(SolutionConfigGenerator.generateResourceAllocConfig(edgeGUI.getJsonConfig().getApps(),
                edgeGUI.getJsonConfig().getEdges()));
        edgeGUI.getJsonConfig().setSchedulingConfigs(SolutionConfigGenerator.generateShedulingConfig(edgeGUI.getJsonConfig().getApps(),
                edgeGUI.getJsonConfig().getEdges()));
        // 生成配置后，需要将配置转化出来。
        convertJsonConfig();
//        addLegends();
    }


    public void convertJsonConfig() {
        edgeGUI.getDrawJPanel().removeAll();
        EdgeGUI.addedLabel = new ArrayList<>();
        EdgeGUI.connectLineInfos = new ArrayList<>();
        for (int i = 0; i < edgeGUI.getJsonConfig().getEdges().size(); i++) {
            EdgeConfig edgeConfig = edgeGUI.getJsonConfig().getEdges().get(i);
            IconLabel iconLabel;
            if (i == edgeGUI.getJsonConfig().getEdges().size() - 1) {
                iconLabel = new IconLabel(EdgeIconButton.CLOUD_SERVER_BUTTON,
                        PrintIconButton.ICON_CONFIG.get(EdgeIconButton.CLOUD_SERVER_BUTTON).getImageIcon());
            } else {
                iconLabel = new IconLabel(EdgeIconButton.EDGE_SERVER_BUTTON,
                        PrintIconButton.ICON_CONFIG.get(EdgeIconButton.EDGE_SERVER_BUTTON).getImageIcon());
            }
            iconLabel.setMouseAdapter(edgeGUI.getPrintIconButtonEdge().new MyMouseAdapter(iconLabel));
            iconLabel.setEdgeConfig(edgeConfig);
            iconLabel.setPos(edgeConfig);
            Font font = new Font(null, Font.PLAIN, 10);
            iconLabel.setFont(font);
            iconLabel.setText(iconLabel.getEntityName());
            iconLabel.setHorizontalTextPosition(JLabel.CENTER);
            iconLabel.setVerticalTextPosition(JLabel.BOTTOM);
            // 加入到画板中
            edgeGUI.getDrawJPanel().add(iconLabel);
            EdgeGUI.addedLabel.add(iconLabel);
        }
        for (int i = 0; i < edgeGUI.getJsonConfig().getDevices().size(); i++) {
            DeviceConfig deviceConfig = edgeGUI.getJsonConfig().getDevices().get(i);
            IconLabel iconLabel = new IconLabel(EdgeIconButton.DEVICE_BUTTON,
                    PrintIconButton.ICON_CONFIG.get(EdgeIconButton.DEVICE_BUTTON).getImageIcon());
            iconLabel.setMouseAdapter(edgeGUI.getPrintIconButtonDevice().new MyMouseAdapter(iconLabel));
            iconLabel.setDeviceConfig(deviceConfig);
            iconLabel.setPos(deviceConfig);
            Font font = new Font(null, Font.PLAIN, 10);
            iconLabel.setText(iconLabel.getEntityName());
            iconLabel.setFont(font);
            iconLabel.setHorizontalTextPosition(JLabel.CENTER);
            iconLabel.setVerticalTextPosition(JLabel.BOTTOM);
            edgeGUI.getDrawJPanel().add(iconLabel);
            EdgeGUI.addedLabel.add(iconLabel);
        }
        edgeGUI.getDrawJPanel().revalidate();
        edgeGUI.getDrawJPanel().repaint();
        for (int i = 0; i < edgeGUI.getJsonConfig().getWirelessLinkConfigs().size(); i++) {
            WirelessLinkConfig wirelessLinkConfig = edgeGUI.getJsonConfig().getWirelessLinkConfigs().get(i);
            IconLabel startLabel = null;
            IconLabel endLabel = null;
            for (int k = 0; k < EdgeGUI.addedLabel.size(); k++) {
                if (EdgeGUI.addedLabel.get(k).getEntityName().equals(wirelessLinkConfig.getDeviceName())) {
                    startLabel = EdgeGUI.addedLabel.get(k);
                }
                if (EdgeGUI.addedLabel.get(k).getEntityName().equals(wirelessLinkConfig.getEdgeName())) {
                    endLabel = EdgeGUI.addedLabel.get(k);
                }
            }
            LineShape lineShape = new LineShape(startLabel, endLabel);
            lineShape.setLineStroke(3);
            lineShape.display(edgeGUI.getDrawJPanel().getGraphics(), Color.BLACK);
            ConnectLineInfo connectLineInfo = new ConnectLineInfo(startLabel, endLabel, Color.BLACK);
            connectLineInfo.setWirelessLinkConfig(wirelessLinkConfig);
            startLabel.getLines().add(connectLineInfo);
            endLabel.getLines().add(connectLineInfo);
            EdgeGUI.connectLineInfos.add(connectLineInfo);
        }
        for (int i = 0; i < edgeGUI.getJsonConfig().getLinks().size(); i++) {
            LinkConfig linkConfig = edgeGUI.getJsonConfig().getLinks().get(i);
            IconLabel startLabel = null;
            IconLabel endLabel = null;
            for (int k = 0; k < EdgeGUI.addedLabel.size(); k++) {
                if (EdgeGUI.addedLabel.get(k).getEntityName().equals(linkConfig.getStartEdgeName())) {
                    startLabel = EdgeGUI.addedLabel.get(k);
                }
                if (EdgeGUI.addedLabel.get(k).getEntityName().equals(linkConfig.getEndEdgeName())) {
                    endLabel = EdgeGUI.addedLabel.get(k);
                }
            }
            LineShape lineShape = new LineShape(startLabel, endLabel);
            lineShape.setLineStroke(3);
            lineShape.display(edgeGUI.getDrawJPanel().getGraphics(), Color.BLACK);
            ConnectLineInfo connectLineInfo = new ConnectLineInfo(startLabel, endLabel, Color.BLACK);
            connectLineInfo.setLinkConfig(linkConfig);
            startLabel.getLines().add(connectLineInfo);
            endLabel.getLines().add(connectLineInfo);
            EdgeGUI.connectLineInfos.add(connectLineInfo);
        }
    }
}
