package zju.edu.cn.platform.jsoninfo.config.resalloc;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.button.EdgeIconButton;
import zju.edu.cn.platform.gui.button.PrintIconButton;

@Getter
@Setter
public class CloudConfig extends EdgeConfig {
    private String name = "cloud-server";

    public CloudConfig() {
        setWidth(PrintIconButton.ICON_CONFIG.get(EdgeIconButton.CLOUD_SERVER_BUTTON).getIconWidth());
        setHeight(PrintIconButton.ICON_CONFIG.get(EdgeIconButton.CLOUD_SERVER_BUTTON).getIconHeight());
        setType(EdgeIconButton.CLOUD_SERVER_BUTTON);
    }
}
