package zju.edu.cn.platform.gui.button;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.position.EdgeGUIPositionConfig;
import zju.edu.cn.platform.util.PlatformUtils;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class EdgeIconButton {
    public static final int DEVICE_BUTTON = 0;
    public static final int EDGE_SERVER_BUTTON = 1;
    public static final int CLOUD_SERVER_BUTTON = 2;
    public static final int LINE_BUTTON = 3;
    public static final int IMPORT_CONFIG_BUTTON = 4;
    public static final int EXPORT_CONFIG_BUTTON = 5;
    public static final int START_BUTTON = 6;
    public static final int CONFIRM_CONFIG_BUTTON = 7;
    public static final int STATISTICS_BUTTON = 8;
    public static final int BURST_LOAD_ALG_BUTTON = 9; // burst load 算法选择按钮
    public static final int BURST_LOAD_ALG_EXEC_BUTTON = 10; // burst load 算法运行按钮

    private int type;
    private String imageName;
    private String name;
    private JButton button;

    public EdgeIconButton(int type, String name, String imageName) {
        this.type = type;
        this.name = name;
        this.imageName = imageName;
        Image image = Toolkit.getDefaultToolkit().getImage(PlatformUtils.getPathInResources(imageName));
        ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(EdgeGUIPositionConfig.MENU_ICON_WIDTH,
                EdgeGUIPositionConfig.MENU_ICON_HEIGHT, Image.SCALE_DEFAULT));
        button = new JButton("", imageIcon);
    }
}
