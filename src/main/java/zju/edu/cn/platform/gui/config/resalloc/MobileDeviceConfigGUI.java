package zju.edu.cn.platform.gui.config.resalloc;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;
import zju.edu.cn.platform.jsoninfo.config.resalloc.AppConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.EdgeConfig;
import zju.edu.cn.platform.jsoninfo.generator.DeviceGenerator;
import zju.edu.cn.platform.jsoninfo.generator.PositionGenerator;

import java.awt.*;
import java.util.List;

import javax.swing.*;

@Getter
@Setter
public class MobileDeviceConfigGUI extends JPanel {

    private JLabel titleLabel;

    private JLabel labelTransmissionRateMean;
    private JLabel labelTransmissionRateVar;
    private JLabel labelReqGeneratedMean;
    private JLabel labelReqGeneratedVar;

    private JTextFieldWithUnit textFieldTransmissionRateMean;
    private JTextFieldWithUnit textFieldTransmissionRateVar;
    private JTextFieldWithUnit textFieldReqGeneratedMean;
    private JTextFieldWithUnit textFieldReqGeneratedVar;

    private final int labelTextGap = 10;
    private final int labelPanelPadding = 10;

    private DeviceGenerator deviceGenerator;

    private EdgeGUI parentGui;

    public void setDeviceGenerator(List<EdgeConfig> edgeConfigs, List<AppConfig> appConfigs,
                                   PositionGenerator positionGenerator) {
        this.deviceGenerator = new DeviceGenerator(
                edgeConfigs,
                appConfigs,
                Double.parseDouble(textFieldTransmissionRateMean.getText()),
                Double.parseDouble(textFieldTransmissionRateVar.getText()),
                Double.parseDouble(textFieldReqGeneratedMean.getText()),
                Double.parseDouble(textFieldReqGeneratedVar.getText()),
                positionGenerator
        );
    }

    /**
     * Create the application.
     */
    public MobileDeviceConfigGUI(EdgeGUI parentEdgeGUI) {
        this.parentGui = parentEdgeGUI;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setBounds(EdgeGUIPositionConfig.DEVICE_CONFIG_X, EdgeGUIPositionConfig.DEVICE_CONFIG_Y,
                EdgeGUIPositionConfig.DEVICE_CONFIG_WIDTH, EdgeGUIPositionConfig.DEVICE_CONFIG_HEIGHT);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.WHITE);

        int oneLabelHeight = (int) ((this.getHeight() - 2 * labelPanelPadding) / 4.5);
        int labelWidth = (this.getWidth() - 3 * labelPanelPadding) / 4;

        JLabel titleLabel = new JLabel("<html><body><p><b>Device Config</b></p></body></html>");
        titleLabel.setBounds(labelPanelPadding, labelPanelPadding, this.getWidth() - 2 * labelPanelPadding, (int) (oneLabelHeight * 0.75 + labelPanelPadding));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        labelTransmissionRateMean = new JLabel("<html><body><p align=\"center\">Transmission<br/>Rate Mean:</p></body></html>");
        labelTransmissionRateMean.setBounds(labelPanelPadding, labelPanelPadding + oneLabelHeight,
                labelWidth, (int) (oneLabelHeight * 1.5));
        labelTransmissionRateVar = new JLabel("<html><body><p align=\"center\">Transmission<br/>Rate Dev:</p></body></html>");
        labelTransmissionRateVar.setBounds(labelPanelPadding, labelPanelPadding + 3 * oneLabelHeight,
                labelWidth, labelTransmissionRateMean.getHeight());
        labelReqGeneratedMean = new JLabel("<html><body><p align=\"center\">Req Generated<br/>Rate Mean:</p></body></html>");
        labelReqGeneratedMean.setBounds(2 * labelPanelPadding + 2 * labelWidth, labelTransmissionRateMean.getY(),
                labelWidth, labelTransmissionRateMean.getHeight());
        labelReqGeneratedVar = new JLabel("<html><body><p align=\"center\">Req Generated<br/>Rate Dev:</p></body></html>");
        labelReqGeneratedVar.setBounds(labelReqGeneratedMean.getX(), labelTransmissionRateVar.getY(),
                labelWidth, labelTransmissionRateMean.getHeight());

        textFieldTransmissionRateMean = new JTextFieldWithUnit("MB/s",
                String.valueOf(DefaultInputConfig.defaultInputConfig.getTransmissionRateMean()));
        textFieldTransmissionRateMean.setBounds(labelTransmissionRateMean.getX() + labelWidth, labelTransmissionRateMean.getY(),
                labelWidth, labelTransmissionRateMean.getHeight());
        this.add(textFieldTransmissionRateMean.getUnitLabel());

        textFieldTransmissionRateVar = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfig.defaultInputConfig.getTransmissionRateVar()));
        textFieldTransmissionRateVar.setBounds(labelTransmissionRateVar.getX() + labelWidth, labelTransmissionRateVar.getY(),
                labelWidth, labelTransmissionRateMean.getHeight());

        textFieldReqGeneratedMean = new JTextFieldWithUnit("QPS",
                String.valueOf(DefaultInputConfig.defaultInputConfig.getReqGeneratedMean()));
        textFieldReqGeneratedMean.setBounds(labelReqGeneratedMean.getX() + labelWidth, labelReqGeneratedMean.getY(),
                labelWidth, labelReqGeneratedMean.getHeight());
        this.add(textFieldReqGeneratedMean.getUnitLabel());

        textFieldReqGeneratedVar = new JTextFieldWithUnit(null,
                String.valueOf(DefaultInputConfig.defaultInputConfig.getReqGeneratedVar()));
        textFieldReqGeneratedVar.setBounds(labelReqGeneratedVar.getX() + labelWidth, labelReqGeneratedVar.getY(),
                labelWidth, labelReqGeneratedVar.getHeight());

        this.add(labelTransmissionRateMean);
        this.add(textFieldTransmissionRateMean);
        this.add(labelReqGeneratedMean);
        this.add(textFieldReqGeneratedMean);
        this.add(labelTransmissionRateVar);
        this.add(textFieldTransmissionRateVar);
        this.add(labelReqGeneratedVar);
        this.add(textFieldReqGeneratedVar);
    }

}