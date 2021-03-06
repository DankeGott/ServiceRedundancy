package zju.edu.cn.platform.redundancy.gui;

import lombok.Data;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;

import javax.swing.*;
import java.awt.*;

/**
 * @ClassName: GeneralConfigGUI
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-01-08 22:44
 * @Version: 1.0
 */
@Data
public class GeneralConfigGUI extends JPanel {

    private JFrame generalConfig;

    private JTextFieldWithUnit wirelessAccessField;

    private JTextFieldWithUnit betweenSBSField;

    private JTextFieldWithUnit backboneDelayField;

    private EdgeGUI parentObject;

    private final int labelTextGap=10;
    private final int labelPanelPadding=10;

    public GeneralConfigGUI(EdgeGUI parentObject) {
        this.parentObject = parentObject;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setBounds(EdgeGUIPositionConfig.GENERAL_CONFIG_X, EdgeGUIPositionConfig.GENERAL_CONFIG_Y,
                EdgeGUIPositionConfig.GENERAL_CONFIG_WIDTH, EdgeGUIPositionConfig.GENERAL_CONFIG_HEIGHT);

        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.WHITE);

        final int labelWidth=(int)(EdgeGUIPositionConfig.GENERAL_CONFIG_WIDTH/2-labelPanelPadding-labelTextGap-10)/2,
                labelHeight=(int)(EdgeGUIPositionConfig.GENERAL_CONFIG_HEIGHT/10);
        final int textFieldWidth=labelWidth + 10, textFieldHeight=labelHeight;

        JLabel tag = new JLabel("General Config");
        tag.setBounds(labelPanelPadding,10,textFieldWidth,textFieldHeight);

        JLabel lblServiceNumber = new JLabel("<html><body>"+"Wireless"+"<br>"+" Access:"+"<body></html>");
        lblServiceNumber.setBounds(labelPanelPadding,10+labelHeight*2,textFieldWidth,textFieldHeight);

        JLabel lblNewLabel = new JLabel("Between SBSs:");
        lblNewLabel.setBounds(labelPanelPadding*2+textFieldWidth+labelWidth,10+labelHeight*2,textFieldWidth,textFieldHeight);

        JLabel backbone = new JLabel("<html><body>"+"Backbone"+"<br>"+" Delay:"+"<body></html>");
        backbone.setBounds(labelPanelPadding,10+labelHeight*4,textFieldWidth,textFieldHeight);

        wirelessAccessField = new JTextFieldWithUnit("/s");
        wirelessAccessField.setBounds(labelWidth,10+labelHeight*2,labelWidth +labelTextGap ,labelHeight, "");
        betweenSBSField = new JTextFieldWithUnit("/s");
        betweenSBSField.setBounds(labelPanelPadding+labelWidth*2+textFieldWidth+labelTextGap,10+labelHeight*2,labelWidth,labelHeight,"");
        backboneDelayField = new JTextFieldWithUnit("/s");
        backboneDelayField.setBounds(labelWidth,10+labelHeight*4,labelWidth+labelTextGap,labelHeight,"");

        this.add(tag);
        this.add(lblServiceNumber);
        this.add(wirelessAccessField);
//        JLabel waf = new JLabel("/s");
//        waf.setBounds(labelWidth + labelWidth +labelTextGap + 5,10+labelHeight*2,5 ,labelHeight);
        this.add(wirelessAccessField.getUnitLabel());
//        this.add(waf);
        this.add(lblNewLabel);
        this.add(betweenSBSField);
        this.add(betweenSBSField.getUnitLabel());
        this.add(backbone);
        this.add(backboneDelayField);
        this.add(backboneDelayField.getUnitLabel());

    }
}
