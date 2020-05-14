package zju.edu.cn.platform.gui.config.resalloc;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.position.ConfigPanelCalculator;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.position.PosConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;
import zju.edu.cn.platform.jsoninfo.config.resalloc.EdgeConfig;
import zju.edu.cn.platform.jsoninfo.generator.EdgeGenerator;
import zju.edu.cn.platform.jsoninfo.generator.LinkGenerator;
import zju.edu.cn.platform.jsoninfo.generator.PositionGenerator;

import java.awt.*;
import java.util.List;

import javax.swing.*;

@Getter
@Setter
public class EdgeConfigGUI extends JPanel {

    private JTextFieldWithUnit edgeNumField;
    private JTextFieldWithUnit bandwidthMeanField;
    private JTextFieldWithUnit bandwidthDevField;
    private JTextFieldWithUnit priceMeanField;
    private JTextFieldWithUnit priceDevField;
    private JTextFieldWithUnit computingPowerMeanField;
    private JTextFieldWithUnit computingPowerDevField;

    private final int labelLeftPadding = 10;
    private final int labelTextGap = 10;
    private final int textRightPadding = 5;

    private EdgeGenerator edgeGenerator;

    private LinkGenerator linkGenerator;

    private ConfigPanelCalculator configPanelCalculator;

    private EdgeGUI parentGui;

    public void setEdgeGenerator(PositionGenerator positionGenerator) {
        this.edgeGenerator = new EdgeGenerator(
                Integer.parseInt(edgeNumField.getText()),
                Double.parseDouble(computingPowerMeanField.getText()),
                Double.parseDouble(computingPowerDevField.getText()),
                Double.parseDouble(priceMeanField.getText()),
                Double.parseDouble(priceDevField.getText()),
                positionGenerator
        );
    }

    public void setLinkGenerator(List<EdgeConfig> edgeConfigs) {
        this.linkGenerator = new LinkGenerator(edgeConfigs, Double.parseDouble(bandwidthMeanField.getText()),
                Double.parseDouble(bandwidthDevField.getText()));
    }

    /**
     * Create the application.
     */
    public EdgeConfigGUI(EdgeGUI parentEdgeGUI) {
        this.parentGui = parentEdgeGUI;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setBounds(EdgeGUIPositionConfig.EDGE_CONFIG_X, EdgeGUIPositionConfig.EDGE_CONFIG_Y,
                EdgeGUIPositionConfig.EDGE_CONFIG_WIDTH, EdgeGUIPositionConfig.EDGE_CONFIG_HEIGHT);
        configPanelCalculator = new ConfigPanelCalculator(this.getWidth(), this.getHeight(),
                7, -1, false, true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(null);

        JLabel titleLabel = new JLabel("<html><body><p align='center'><b>Edge Config</b></p></body></html>",SwingConstants.CENTER);
        PosConfig posConfig = configPanelCalculator.getTitlePos();
        titleLabel.setBounds(posConfig.getX(), posConfig.getY(), posConfig.getWidth(), posConfig.getHeight());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        int configIndex = 0;
        JLabel lblEdgeNumber = new JLabel("Edge Number:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblEdgeNumber, configIndex++);
        this.add(lblEdgeNumber);

        JLabel lblBandwidthMean = new JLabel("Bandwidth Mean:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblBandwidthMean, configIndex++);
        this.add(lblBandwidthMean);

        JLabel lblBandwidthDev = new JLabel("Bandwidth Dev:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblBandwidthDev, configIndex++);
        this.add(lblBandwidthDev);

        JLabel lblPriceMean = new JLabel("Price Mean:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblPriceMean, configIndex++);
        this.add(lblPriceMean);

        JLabel lblPriceDev = new JLabel("Price Dev:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblPriceDev, configIndex++);
        this.add(lblPriceDev);

        JLabel lblPowerMean = new JLabel("Power Mean:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblPowerMean, configIndex++);
        this.add(lblPowerMean);

        JLabel lblPowerDev = new JLabel("Power Dev:");
        ConfigPanelCalculator.setLabelPos(configPanelCalculator, lblPowerDev, configIndex);
        this.add(lblPowerDev);

        configIndex = 0;
        edgeNumField = new JTextFieldWithUnit(null, String.valueOf(DefaultInputConfig.defaultInputConfig.getEdgeNum()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, edgeNumField, configIndex++);

        bandwidthMeanField = new JTextFieldWithUnit("MB/s",
                String.valueOf(DefaultInputConfig.defaultInputConfig.getBandwidthMean()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, bandwidthMeanField, configIndex++);

        this.add(bandwidthMeanField.getUnitLabel());

        bandwidthDevField = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfig.defaultInputConfig.getBandwidthVar()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, bandwidthDevField, configIndex++);

        priceMeanField = new JTextFieldWithUnit("$/MIPS",
                String.valueOf(DefaultInputConfig.defaultInputConfig.getPriceMean()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, priceMeanField, configIndex++);
        this.add(priceMeanField.getUnitLabel());

        priceDevField = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfig.defaultInputConfig.getPriceVar()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, priceDevField, configIndex++);

        computingPowerMeanField = new JTextFieldWithUnit("MIPS",
                String.valueOf(DefaultInputConfig.defaultInputConfig.getComputingPowerMean()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, computingPowerMeanField, configIndex++);
        this.add(computingPowerMeanField.getUnitLabel());

        computingPowerDevField = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfig.defaultInputConfig.getComputingPowerVar()));
        ConfigPanelCalculator.setInputPos(configPanelCalculator, computingPowerDevField, configIndex);

        this.add(edgeNumField);
        this.add(bandwidthMeanField);
        this.add(bandwidthDevField);
        this.add(priceMeanField);
        this.add(priceDevField);
        this.add(computingPowerMeanField);
        this.add(computingPowerDevField);
    }
}