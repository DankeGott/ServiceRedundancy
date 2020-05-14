package zju.edu.cn.platform.gui.config.burstload;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.position.ConfigPanelCalculator;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.position.PosConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;
import zju.edu.cn.platform.jsoninfo.generator.BurstLoadEdgeGenerator;
import zju.edu.cn.platform.jsoninfo.generator.BurstLoadLinkGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@Getter
@Setter
public class EdgeServerConfigGUI extends JPanel {

    //    private JTextField textFieldCpuFreqLow;
//    private JTextField textFieldCpuFreqHigh;
//    private JTextField textFieldRamLow;
//    private JTextField textFieldRamHigh;
//    private JTextField textFieldStorageLow;
//    private JTextField textFieldStorageHigh;
    private JTextFieldWithUnit textFieldCpuFreqLow;
    private JTextFieldWithUnit textFieldCpuFreqHigh;
    private JTextFieldWithUnit textFieldRamLow;
    private JTextFieldWithUnit textFieldRamHigh;
    private JTextFieldWithUnit textFieldStorageLow;
    private JTextFieldWithUnit textFieldStorageHigh;

    private JTextField textFieldNumEdges;

    private final int labelLeftPadding = 10;
    private final int labelTextGap = 10;
    private final int textRightPadding = 5;

    private BurstLoadEdgeGenerator burstLoadEdgeGenerator;

    private BurstLoadLinkGenerator burstLoadLinkGenerator;

    private ConfigPanelCalculator configPanelCalculator;

    private EdgeGUI edgeGUIParent;

    public EdgeServerConfigGUI(EdgeGUI edgeGUIParent) {
        this.edgeGUIParent = edgeGUIParent;
        this.init();
    }

    private void init() {
        this.setBounds(EdgeGUIPositionConfig.EDGE_CONFIG_X, EdgeGUIPositionConfig.EDGE_CONFIG_Y,
                EdgeGUIPositionConfig.EDGE_CONFIG_WIDTH, EdgeGUIPositionConfig.EDGE_CONFIG_HEIGHT);
        configPanelCalculator = new ConfigPanelCalculator(this.getWidth(), this.getHeight(), 7,
                -1, false, true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK
        ));
        this.setLayout(null);

        JLabel titleLabel = new JLabel("Edge Config", SwingConstants.CENTER);
        PosConfig posConfig = configPanelCalculator.getTitlePos();
        titleLabel.setBounds(posConfig.getX(), posConfig.getY(), posConfig.getWidth(), posConfig.getHeight());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        int configIndex = 0;
        JLabel labelCpuFreqLow = new JLabel("Freq Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelCpuFreqLow, configIndex++);
        this.add(labelCpuFreqLow);

        JLabel labelCpuFreqHigh = new JLabel("Freq High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelCpuFreqHigh, configIndex++);
        this.add(labelCpuFreqHigh);

        JLabel labelRamLow = new JLabel("Ram Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelRamLow, configIndex++);
        this.add(labelRamLow);

        JLabel labelRamHigh = new JLabel("Ram High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelRamHigh, configIndex++);
        this.add(labelRamHigh);

        JLabel labelStorageLow = new JLabel("Storage Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelStorageLow, configIndex++);
        this.add(labelStorageLow);

        JLabel labelStorageHigh = new JLabel("Storage High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelStorageHigh, configIndex++);
        this.add(labelStorageHigh);

        JLabel labelNumEdges = new JLabel("Num Edges: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelNumEdges, configIndex++);
        this.add(labelNumEdges);

        configIndex = 0;
//        textFieldCpuFreqLow = new JTextFieldWithUnit("(rec 1000)MIPS");
        textFieldCpuFreqLow = new JTextFieldWithUnit("MIPS",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getFreqLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldCpuFreqLow, configIndex++);
//        textFieldCpuFreqHigh = new JTextFieldWithUnit("(rec 5000)MIPS");
        textFieldCpuFreqHigh = new JTextFieldWithUnit("MIPS",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getFreqHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldCpuFreqHigh, configIndex++);
//        textFieldRamLow = new JTextFieldWithUnit("(rec 100)KB");
        textFieldRamLow = new JTextFieldWithUnit("KB",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getRamLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldRamLow, configIndex++);
//        textFieldRamHigh = new JTextFieldWithUnit("(rec 500)KB");
        textFieldRamHigh = new JTextFieldWithUnit("KB",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getRamHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldRamHigh, configIndex++);
//        textFieldStorageLow = new JTextFieldWithUnit("(rec 100)MB");
        textFieldStorageLow = new JTextFieldWithUnit("MB",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getStorageLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldStorageLow, configIndex++);
//        textFieldStorageHigh = new JTextFieldWithUnit("(rec 500)MB");
        textFieldStorageHigh = new JTextFieldWithUnit("MB",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getStorageHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldStorageHigh, configIndex++);
//        textFieldNumEdges = new JTextField("10");
        textFieldNumEdges = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getNumEdges()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldNumEdges, configIndex++);

        this.add(textFieldCpuFreqLow.getUnitLabel());
        this.add(textFieldCpuFreqHigh.getUnitLabel());
        this.add(textFieldRamLow.getUnitLabel());
        this.add(textFieldRamHigh.getUnitLabel());
        this.add(textFieldStorageLow.getUnitLabel());
        this.add(textFieldStorageHigh.getUnitLabel());

        this.add(textFieldCpuFreqLow);
        this.add(textFieldCpuFreqHigh);
        this.add(textFieldRamLow);
        this.add(textFieldRamHigh);
        this.add(textFieldStorageLow);
        this.add(textFieldStorageHigh);

        this.add(textFieldNumEdges);
    }

    public void setEdgeGenerator() {
        this.burstLoadEdgeGenerator = new BurstLoadEdgeGenerator(Integer.parseInt(textFieldNumEdges.getText()),
                new int[]{Integer.parseInt(textFieldCpuFreqLow.getText()), Integer.parseInt(textFieldCpuFreqHigh.getText())},
                new int[]{Integer.parseInt(textFieldRamLow.getText()), Integer.parseInt(textFieldRamHigh.getText())},
                new int[]{Integer.parseInt(textFieldStorageLow.getText()), Integer.parseInt(textFieldStorageHigh.getText())});
    }

}
