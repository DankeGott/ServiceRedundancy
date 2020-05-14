package zju.edu.cn.platform.gui.config.burstload;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.position.ConfigPanelCalculator;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.position.PosConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;
import zju.edu.cn.platform.jsoninfo.generator.BurstLoadTaskGenerator;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class TaskConfigGUI extends JPanel {

    //    private JTextField textFieldPacketSizeLow;
//    private JTextField textFieldPacketSizeHigh;
//    private JTextField textFieldExecTimeLow;
//    private JTextField textFieldExecTimeHigh;
    private JTextFieldWithUnit textFieldPacketSizeLow;
    private JTextFieldWithUnit textFieldPacketSizeHigh;
    private JTextFieldWithUnit textFieldExecTimeLow;
    private JTextFieldWithUnit textFieldExecTimeHigh;

    private JTextField textFieldNumTasks;

    private final int labelLeftPadding = 10;
    private final int labelTextGap = 10;
    private final int textRightPadding = 5;

    private BurstLoadTaskGenerator taskGenerator;
    private ConfigPanelCalculator configPanelCalculator;

    private EdgeGUI edgeGUI;

    public TaskConfigGUI(EdgeGUI edgeGUI) {
        this.edgeGUI = edgeGUI;
        this.init();
    }

    private void init() {
        this.setBounds(EdgeGUIPositionConfig.DEVICE_CONFIG_X, EdgeGUIPositionConfig.DEVICE_CONFIG_Y,
                EdgeGUIPositionConfig.DEVICE_CONFIG_WIDTH, EdgeGUIPositionConfig.DEVICE_CONFIG_HEIGHT);
//        configPanelCalculator = new ConfigPanelCalculator(this.getWidth(), this.getHeight(), 3,
//                -1, false, true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(null);

        JLabel titleLabel = new JLabel("Task Config");
//        PosConfig posConfig = configPanelCalculator.getTitlePos();
//        titleLabel.setBounds(posConfig.getX(), posConfig.getY(), posConfig.getWidth(), posConfig.getHeight());
        titleLabel.setBounds(0, 0, this.getWidth(), 20);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        JPanel panelLeft = new JPanel(null);
        panelLeft.setBounds(0, 0,
                EdgeGUIPositionConfig.DEVICE_CONFIG_WIDTH / 2, EdgeGUIPositionConfig.DEVICE_CONFIG_HEIGHT);
        ConfigPanelCalculator configPanelCalculatorLeft = new ConfigPanelCalculator(panelLeft.getWidth(),
                panelLeft.getHeight(),
                3, -1, false, true);
        panelLeft.setBackground(SystemColor.white);
        int configIndex = 0;

        JLabel labelPacketSizeLow = new JLabel("Packetsize Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculatorLeft, labelPacketSizeLow, configIndex++);
        panelLeft.add(labelPacketSizeLow);

        JLabel labelPacketSizeHigh = new JLabel("Packetsize High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculatorLeft, labelPacketSizeHigh, configIndex++);
        panelLeft.add(labelPacketSizeHigh);

        JLabel labelExecTimeLow = new JLabel("ExecTime Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculatorLeft, labelExecTimeLow, configIndex++);
        panelLeft.add(labelExecTimeLow);

        configIndex = 0;
//        textFieldPacketSizeLow = new JTextFieldWithUnit("(rec 10)MB");
        textFieldPacketSizeLow = new JTextFieldWithUnit("MB",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getPacketSizeLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculatorLeft, textFieldPacketSizeLow, configIndex++);
        panelLeft.add(textFieldPacketSizeLow.getUnitLabel());
        panelLeft.add(textFieldPacketSizeLow);

//        textFieldPacketSizeHigh = new JTextFieldWithUnit("(rec 50)MB");
        textFieldPacketSizeHigh = new JTextFieldWithUnit("MB",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getPacketSizeHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculatorLeft, textFieldPacketSizeHigh, configIndex++);
        panelLeft.add(textFieldPacketSizeHigh.getUnitLabel());
        panelLeft.add(textFieldPacketSizeHigh);

//        textFieldExecTimeLow = new JTextFieldWithUnit("(rec 2)T");
        textFieldExecTimeLow = new JTextFieldWithUnit("T",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getExecTimeLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculatorLeft, textFieldExecTimeLow, configIndex++);
        panelLeft.add(textFieldExecTimeLow.getUnitLabel());
        panelLeft.add(textFieldExecTimeLow);

        JPanel panelRight = new JPanel(null);
        panelRight.setBounds(panelLeft.getWidth(), 0, EdgeGUIPositionConfig.DEVICE_CONFIG_WIDTH / 2,
                EdgeGUIPositionConfig.DEVICE_CONFIG_HEIGHT);
        panelRight.setBackground(SystemColor.white);
        ConfigPanelCalculator configPanelCalculatorRight = new ConfigPanelCalculator(panelRight.getWidth(),
                panelRight.getHeight(), 3,
                -1, false, true);

        configIndex = 0;
        JLabel labelExecTimeHigh = new JLabel("ExecTime High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculatorRight, labelExecTimeHigh, configIndex++);
        panelRight.add(labelExecTimeHigh);

        JLabel labelNumTask = new JLabel("Num Task: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculatorRight, labelNumTask, configIndex++);
        panelRight.add(labelNumTask);

        configIndex = 0;

//        textFieldExecTimeHigh = new JTextFieldWithUnit("(rec 5)T");
        textFieldExecTimeHigh = new JTextFieldWithUnit("T",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getExecTimeHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculatorRight, textFieldExecTimeHigh, configIndex++);
        panelRight.add(textFieldExecTimeHigh.getUnitLabel());
        panelRight.add(textFieldExecTimeHigh);

//        textFieldNumTasks = new JTextField("100");
        textFieldNumTasks = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getNumTasks()));
        ConfigPanelCalculator.setInputPos(configPanelCalculatorRight, textFieldNumTasks, configIndex++);
        panelRight.add(textFieldNumTasks);

        this.add(panelLeft);
        this.add(panelRight);
    }

    public void setTaskGenerator() {
        this.taskGenerator = new BurstLoadTaskGenerator(
                Integer.parseInt(textFieldNumTasks.getText()),
                new int[]{Integer.parseInt(textFieldPacketSizeLow.getText()), Integer.parseInt(textFieldPacketSizeHigh.getText())},
                new int[]{Integer.parseInt(textFieldExecTimeLow.getText()), Integer.parseInt(textFieldExecTimeHigh.getText())}
        );
    }
}
