package zju.edu.cn.platform.log;

import zju.edu.cn.platform.gui.EdgeGUI;

public class Logger {
    public static EdgeGUI edgeGUI;

    public static void log(String info) {
        if (edgeGUI != null) {
            edgeGUI.getTextArea().append(info + "\n");
        }
        System.out.println(info);
    }
}
