package zju.edu.cn.platform.gui.button;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.MainGUI;
import zju.edu.cn.platform.jsoninfo.config.util.BurstLoadAlgUtil;
import zju.edu.cn.platform.util.PlatformUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

@Getter
@Setter
public class AlgExecButton extends EdgeIconButton {

    private EdgeGUI edgeGUI;
    private BurstLoadAlgUtil burstLoadAlgUtil;
    private final Integer semaphore = 0;

    public AlgExecButton(EdgeGUI edgeGUI) {
        super(EdgeIconButton.BURST_LOAD_ALG_EXEC_BUTTON, "", "alg_exec_icon.png");
        this.edgeGUI = edgeGUI;
        setAlgExecButton();
    }

    /**
     * 点击该按钮后，运行算法并等待运行结束，算法运行结束后加载输出的数据
     */
    private void setAlgExecButton() {
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                burstLoadAlgUtil = new BurstLoadAlgUtil();
                // 运行算法并加载输出数据
                burstLoadAlgUtil.setAlgPathStr(edgeGUI.getAlgButton().getPathAlgFile());
                if (burstLoadAlgUtil.getAlgPathStr() == null || burstLoadAlgUtil.getAlgPathStr().equals("")) {
                    System.out.println("请先选择算法脚本");
                    JDialog jDialog = displayTipWindow("请先选择算法脚本文件!");
                    return;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int retVal = 0;
                            synchronized (semaphore) {
                                retVal = burstLoadAlgUtil.runAlg();
                                System.out.println("alg exec button, ret val = " + retVal);
                            }
                            // 算法运行中出现错误，显示提示窗口
                            if (retVal != 0) {
                                JOptionPane.showMessageDialog(null,
                                        "算法运行过程中出现错误，请确保算法能够正确运行！！", "错误",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                burstLoadAlgUtil.loadStatisticInfo();
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                JDialog jDialog2 = displayTipWindow("运行算法中，请等待...");
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        synchronized (semaphore) {
                            jDialog2.dispatchEvent(new WindowEvent(jDialog2, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }

    /**
     * 显示提示窗口
     */
    private JDialog displayTipWindow(String stringContent) {
        JDialog jDialog = new JDialog();
        jDialog.setTitle("提示");

        JLabel labelContent = new JLabel();
        labelContent.setText(stringContent);
        labelContent.setBounds(10, 10, 80, 80);
        labelContent.paintImmediately(0, 0, labelContent.getWidth(), labelContent.getHeight());
        jDialog.add(labelContent);
        jDialog.setResizable(false);
        jDialog.setBounds(edgeGUI.getFrame().getWidth() / 2 - 200,
                edgeGUI.getFrame().getHeight() / 2 - 200, 100, 100);
        jDialog.setVisible(true);
        return jDialog;
    }
}
