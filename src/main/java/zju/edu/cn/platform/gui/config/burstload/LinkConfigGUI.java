package zju.edu.cn.platform.gui.config.burstload;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeServerBean;
import zju.edu.cn.platform.gui.config.resalloc.DefaultInputConfig;
import zju.edu.cn.platform.gui.position.ConfigPanelCalculator;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.position.PosConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;
import zju.edu.cn.platform.jsoninfo.generator.BurstLoadLinkGenerator;

import javax.swing.*;
import java.awt.*;

import java.util.List;

@Getter
@Setter
public class LinkConfigGUI extends JPanel {

    //    private JTextField textFieldLinkBandWidthLow;
//    private JTextField textFieldLinkBandWidthHigh;
//    private JTextField textFieldLinkTransTimeLow;
//    private JTextField textFieldLinkTransTimeHigh;
    private JTextFieldWithUnit textFieldLinkBandWidthLow;
    private JTextFieldWithUnit textFieldLinkBandWidthHigh;
    private JTextFieldWithUnit textFieldLinkTransTimeLow;
    private JTextFieldWithUnit textFieldLinkTransTimeHigh;

    private JTextField textFieldEdgeDegreeLow;
    private JTextField textFieldEdgeDegreeHigh;

//    private JTextField textFieldNumLinks;

    private final int labelLeftPadding = 10;
    private final int labelTextGap = 10;
    private final int textRightPadding = 5;

    private ConfigPanelCalculator configPanelCalculator;

    private BurstLoadLinkGenerator burstLoadLinkGenerator;

    private EdgeGUI edgeGUI;

    public LinkConfigGUI(EdgeGUI edgeGUI) {
        this.edgeGUI = edgeGUI;
        init();
    }

    private void init() {
        this.setBounds(EdgeGUIPositionConfig.SERVICE_CONFIG_X, EdgeGUIPositionConfig.SERVICE_CONFIG_Y,
                EdgeGUIPositionConfig.SERVICE_CONFIG_WIDTH, EdgeGUIPositionConfig.SERVICE_CONFIG_HEIGHT);
        configPanelCalculator = new ConfigPanelCalculator(this.getWidth(), this.getHeight(),
                6, -1, false, true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(null);

        int configIndex = 0;
        JLabel titleLabel = new JLabel("Link Config", SwingConstants.CENTER);
        PosConfig posConfig = configPanelCalculator.getTitlePos();
        titleLabel.setBounds(posConfig.getX(), posConfig.getY(), posConfig.getWidth(), posConfig.getHeight());
        ;
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        JLabel labelBandWidthLow = new JLabel("BandWidth Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelBandWidthLow, configIndex++);
        this.add(labelBandWidthLow);

        JLabel labelBandWidthHigh = new JLabel("BandWidth High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelBandWidthHigh, configIndex++);
        this.add(labelBandWidthHigh);

        JLabel labelTransTimeLow = new JLabel("Trans Time Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelTransTimeLow, configIndex++);
        this.add(labelTransTimeLow);

        JLabel labelTransTimeHigh = new JLabel("Trans Time High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelTransTimeHigh, configIndex++);
        this.add(labelTransTimeHigh);

        JLabel labelDegreeLow = new JLabel("Degree Low: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelDegreeLow, configIndex++);
        this.add(labelDegreeLow);

        JLabel labelDegreeHigh = new JLabel("Degree High: ");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, labelDegreeHigh, configIndex++);
        this.add(labelDegreeHigh);

//        JLabel labelNumTasks = new JLabel("Num Tasks: ");
//        ConfigPanelCalculator.setLabelPos(configPanelCalculator,labelNumTasks,configIndex++);
//        this.add(labelNumTasks);

        configIndex = 0;

//        textFieldLinkBandWidthLow=new JTextFieldWithUnit("(rec 2)#Task/T");
        textFieldLinkBandWidthLow = new JTextFieldWithUnit("#Task/T",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getBandWidthLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldLinkBandWidthLow, configIndex++);

//        textFieldLinkBandWidthHigh=new JTextFieldWithUnit("(rec 3)#Task/T");
        textFieldLinkBandWidthHigh = new JTextFieldWithUnit("#Task/T",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getBandWidthHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldLinkBandWidthHigh, configIndex++);

//        textFieldLinkTransTimeLow=new JTextFieldWithUnit("(rec 2)T");
        textFieldLinkTransTimeLow = new JTextFieldWithUnit("T",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getTransTimeLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldLinkTransTimeLow, configIndex++);

//        textFieldLinkTransTimeHigh=new JTextFieldWithUnit("(rec 5)T");
        textFieldLinkTransTimeHigh = new JTextFieldWithUnit("T",
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getTransTimeHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldLinkTransTimeHigh, configIndex++);

//        textFieldEdgeDegreeLow = new JTextField("2");
        textFieldEdgeDegreeLow = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getDegreeLow()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldEdgeDegreeLow, configIndex++);

//        textFieldEdgeDegreeHigh = new JTextField("3");
        textFieldEdgeDegreeHigh = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfigBurstLoad.defaultInputConfigBurstLoad.getDegreeHigh()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldEdgeDegreeHigh, configIndex++);

        this.add(textFieldLinkBandWidthLow.getUnitLabel());
        this.add(textFieldLinkBandWidthHigh.getUnitLabel());
        this.add(textFieldLinkTransTimeLow.getUnitLabel());
        this.add(textFieldLinkTransTimeHigh.getUnitLabel());

        this.add(textFieldLinkBandWidthLow);
        this.add(textFieldLinkBandWidthHigh);
        this.add(textFieldLinkTransTimeLow);
        this.add(textFieldLinkTransTimeHigh);
        this.add(textFieldEdgeDegreeLow);
        this.add(textFieldEdgeDegreeHigh);

//        textFieldNumLinks = new JTextField();
//        ConfigPanelCalculator.setInputPos(configPanelCalculator, textFieldNumLinks, configIndex++);
//        this.add(textFieldNumLinks);
    }

    public void setLinkGenerator(List<EdgeServerBean> edgeServerBeans) {
        this.burstLoadLinkGenerator = new BurstLoadLinkGenerator(
                new int[]{Integer.parseInt(textFieldLinkBandWidthLow.getText()), Integer.parseInt(textFieldLinkBandWidthHigh.getText())},
                edgeServerBeans,
                new int[]{Integer.parseInt(textFieldEdgeDegreeLow.getText()), Integer.parseInt(textFieldEdgeDegreeHigh.getText())},
                new int[]{Integer.parseInt(textFieldLinkTransTimeLow.getText()), Integer.parseInt(textFieldLinkTransTimeHigh.getText())}
        );
    }
}
