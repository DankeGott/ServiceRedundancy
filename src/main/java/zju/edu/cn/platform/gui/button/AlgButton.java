package zju.edu.cn.platform.gui.button;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.EdgeGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 主界面上用于选择算法的按钮
 */
@Getter
@Setter
public class AlgButton extends EdgeIconButton {

    private EdgeGUI edgeGUI;
    private String pathAlgDir;
    private String pathAlgFile;

    public AlgButton(EdgeGUI edgeGUI) {
        super(EdgeIconButton.BURST_LOAD_ALG_BUTTON, "", "alg_icon.png");
        this.edgeGUI = edgeGUI;
        this.setAlgButton();
    }

    /**
     * 设置算法选择按钮
     */
    private void setAlgButton() {
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser(".");
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int retVal = jFileChooser.showDialog(edgeGUI.getFrame(), "选择python脚本编写的算法");
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    pathAlgFile = jFileChooser.getSelectedFile().getAbsolutePath();
                    pathAlgDir = Paths.get(pathAlgFile).getParent().toString();
                }
            }
        });
    }

}
