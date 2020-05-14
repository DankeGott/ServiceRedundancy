package zju.edu.cn.platform.jsoninfo.config.burstload;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.button.EdgeIconButton;
import zju.edu.cn.platform.jsoninfo.config.resalloc.EntityConfig;

@Getter
@Setter
public class BurstLoadEdgeConfig extends EntityConfig {

    private String name;
    private double edgeCpuCycle;

    public BurstLoadEdgeConfig() {
        super(EdgeIconButton.EDGE_SERVER_BUTTON);
    }

    @Override
    public String toString() {
        return "BurstLoadEdgeConfig{" +
                "name='" + name + '\'' +
                ", edgeCpuCycle=" + edgeCpuCycle +
                '}';
    }
}
