package zju.edu.cn.platform.gui.textfield;

import lombok.Getter;
import zju.edu.cn.platform.util.FontUtil;

import javax.swing.*;

/**
 * JTextField with unit in the end.
 *
 * @author jfqiao
 * @since 2020/02/21
 */
public class JTextFieldWithUnit extends JTextField {

    private String unit;
    @Getter
    private JLabel unitLabel;

    public JTextFieldWithUnit(String unit) {
        super();
        this.unit = unit;
    }

    public JTextFieldWithUnit(String unit, String placeholder) {
        super();
        this.unit = unit;
        if (placeholder != null) {
            this.addFocusListener(new JTextFieldHintListener(this, placeholder));
        }

    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (unit != null) {
            unitLabel = new JLabel(unit);
            int charLen = FontUtil.getStringLength(null, unit) + 10;
            unitLabel.setBounds(x + width - charLen, y, charLen, height);
        }
    }

    public void setBounds(int x, int y, int width, int height, String s) {
        this.setBounds(x, y, width, height);
        if (unit != null) {
//            int charLen = FontUtil.getStringLength(null, unit) + 10;
            unitLabel.setBounds(x + width, y, 14 , height);
        }
    }
}
