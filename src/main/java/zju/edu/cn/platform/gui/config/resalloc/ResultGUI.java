package zju.edu.cn.platform.gui.config.resalloc;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.gui.textfield.JTextFieldWithUnit;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ResultGUI extends JPanel {

    private static final int PADDING = 10;

    private JLabel lblCost;
    private JTextFieldWithUnit costField;
    private JTextFieldWithUnit delayField;
    private JLabel lblDelay;

    private EdgeGUI parentObject;

    public ResultGUI(EdgeGUI parentObject) {
        this.parentObject = parentObject;
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setBounds(EdgeGUIPositionConfig.RESULT_X, EdgeGUIPositionConfig.RESULT_Y,
                EdgeGUIPositionConfig.RESULT_WIDTH, EdgeGUIPositionConfig.RESULT_HEIGHT);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.WHITE);

        lblCost = new JLabel("Cost:");
//        lblCost.setPreferredSize(new Dimension(60, 50));
        lblCost.setBounds(10, 10, 100, 100);
        this.add(lblCost);

        lblCost.setHorizontalAlignment(SwingConstants.CENTER);

        costField = new JTextFieldWithUnit("$");
        costField.setBounds(120, 40, 80, 40);
        this.add(costField.getUnitLabel());

//        JLabel costUnit = new JLabel("$");
//        costUnit.setBounds(185, 40, 20, 40);
//        this.add(costUnit);

        this.add(costField);

        lblDelay = new JLabel("Delay:");
//        lblDelay.setPreferredSize(new Dimension(60, 20));
        lblDelay.setBounds(220, 10, 100, 100);
        this.add(lblDelay);
        lblDelay.setHorizontalAlignment(SwingConstants.CENTER);

        delayField = new JTextFieldWithUnit("s");
        delayField.setBounds(330, 40, 80, 40);
        this.add(delayField.getUnitLabel());


//        JLabel delayUnit = new JLabel("s");
//        delayUnit.setBounds(395, 40, 15, 40);
//        this.add(delayUnit);

        this.add(delayField);
    }
}
