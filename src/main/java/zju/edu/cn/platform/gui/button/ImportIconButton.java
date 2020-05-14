package zju.edu.cn.platform.gui.button;

import com.alibaba.fastjson.JSONObject;
import zju.edu.cn.platform.gui.ConnectLineInfo;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.gui.IconLabel;
import zju.edu.cn.platform.gui.LineShape;
import zju.edu.cn.platform.gui.config.burstload.BurstLoadConfig;
import zju.edu.cn.platform.jsoninfo.config.*;
import zju.edu.cn.platform.jsoninfo.generator.BurstLoadDefaultLayoutGenerator;
import zju.edu.cn.platform.util.PlatformUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportIconButton extends EdgeIconButton {

    private EdgeGUI edgeGUI;

    public ImportIconButton(EdgeGUI edgeGUI) {
        super(EdgeIconButton.IMPORT_CONFIG_BUTTON, "", "file.png");
        this.edgeGUI = edgeGUI;
        setImportAction();
    }

    private void setImportAction() {
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int retVal = jFileChooser.showOpenDialog(jFileChooser);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    String configFilePathString = jFileChooser.getSelectedFile().getAbsolutePath();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br;
                    try {
                        br = new BufferedReader(new FileReader(configFilePathString));
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.RESOURCE_ALLOC_AND_SCHEDULING_PROBLEM) {
                            edgeGUI.setJsonConfig(JSONObject.parseObject(sb.toString(), JsonConfig.class));
                            edgeGUI.convertJsonConfig();
                        } else if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.BURST_LOAD_EVACUATION) {
                            edgeGUI.setJsonConfig(JSONObject.parseObject(sb.toString(), JsonConfig.class));
                            edgeGUI.convertJsonConfig();
                        }
                        edgeGUI.addLegends();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("选择配置文件的路径为: [" + configFilePathString + "]");
                }
            }
        });
    }
}
