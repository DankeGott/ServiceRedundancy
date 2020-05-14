package zju.edu.cn.platform.gui.button;

import lombok.SneakyThrows;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.jsoninfo.ParaBuilder;
import zju.edu.cn.platform.jsoninfo.config.util.ResAllocConfigUtil;
import zju.edu.cn.platform.jsoninfo.generator.BurstLoadSimulationInfo;
import zju.edu.cn.platform.log.Logger;
import zju.edu.cn.platform.test.OPCA_CATS;
import zju.edu.cn.platform.util.PlatformUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartupIconButton extends EdgeIconButton {

    private static final String BOOT_START_ICON_PATH = "boot_start.png";
    private static final String BOOT_END_ICON_PATH = "boot_end.png";

    private static final ImageIcon BOOT_START_ICON = PlatformUtils.getImageIconByPath(BOOT_START_ICON_PATH);

    private static final ImageIcon BOOT_END_ICON = PlatformUtils.getImageIconByPath(BOOT_END_ICON_PATH);

    private EdgeGUI edgeGUI;

    public StartupIconButton(EdgeGUI edgeGUI) {
        super(EdgeIconButton.START_BUTTON, "", BOOT_END_ICON_PATH);
        this.edgeGUI = edgeGUI;
        setActionListener();
    }

    private void setActionListener() {
        getButton().addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.RESOURCE_ALLOC_AND_SCHEDULING_PROBLEM) {
                    Logger.edgeGUI = edgeGUI;
                    if (edgeGUI.getJsonConfig() == null)
                        return;
                    getButton().setIcon(BOOT_START_ICON);
                    getButton().repaint();
                    ParaBuilder pb = ResAllocConfigUtil.convertJsonConfigToParaBuilder(edgeGUI.getJsonConfig());
                    try {
                        OPCA_CATS.startSim(pb, "1");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    edgeGUI.getResourceAllocConfig().getResultGUI().getCostField().setText(String.format("%.2f", pb.calculateCt()));
                    edgeGUI.getResourceAllocConfig().getResultGUI().getDelayField().setText(String.format("%.4f", pb.calculateDt()));
                    getButton().setIcon(BOOT_END_ICON);
                } else if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.BURST_LOAD_EVACUATION) {
                    BurstLoadSimulationInfo burstLoadSimulationInfo = new BurstLoadSimulationInfo(edgeGUI.getDrawJPanel(), edgeGUI);
                    burstLoadSimulationInfo.loadSimInfo();
                    burstLoadSimulationInfo.startSimulation();
                    getButton().setIcon(BOOT_START_ICON);
                    Thread thread = new Thread(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            Thread.sleep(5000);
                            System.out.println("StartupIconButton wait...");
                            synchronized (BurstLoadSimulationInfo.SYNC_SIMU_OBJ) {
                            }
                            System.out.println("StartupIconButton get notify");
                            StartupIconButton.this.getButton().setIcon(BOOT_END_ICON);
                        }
                    });
                    thread.start();
                }
            }
        });
    }
}
