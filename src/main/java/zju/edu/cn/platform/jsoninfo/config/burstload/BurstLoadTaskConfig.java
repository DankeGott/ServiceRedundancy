package zju.edu.cn.platform.jsoninfo.config.burstload;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.button.EdgeIconButton;
import zju.edu.cn.platform.jsoninfo.config.resalloc.EntityConfig;

@Getter
@Setter
public class BurstLoadTaskConfig extends EntityConfig {

    private int taskSize;
    private int execTime;

    /**
     * the type set for this class is nonsense
     */
    public BurstLoadTaskConfig() {
        super(EdgeIconButton.DEVICE_BUTTON);
    }

    @Override
    public String toString() {
        return "BurstLoadTaskConfig{" +
                "taskSize=" + taskSize +
                ", execTime=" + execTime +
                '}';
    }
}
