package zju.edu.cn.platform.redundancy.log;

import zju.edu.cn.platform.redundancy.gui.EdgeGUI;

public class Logger {
    public static EdgeGUI edgeGUI;

    public static void log(String info) {
        if (edgeGUI != null) {
            edgeGUI.getTextArea().append(info + "\n");
        }
        System.out.println(info);
    }
}
