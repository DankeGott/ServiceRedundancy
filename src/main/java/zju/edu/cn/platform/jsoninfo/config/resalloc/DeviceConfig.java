package zju.edu.cn.platform.jsoninfo.config.resalloc;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.button.EdgeIconButton;

/**
 * @author jfqiao
 * @since 2019/12/28
 */
@Getter
@Setter
public class DeviceConfig extends EntityConfig {
    private String name;
    private double reqGeneratedRate;
    private String appName;
    private String accessEdgeName;
    public DeviceConfig() {
        super(EdgeIconButton.DEVICE_BUTTON);
    }
}
