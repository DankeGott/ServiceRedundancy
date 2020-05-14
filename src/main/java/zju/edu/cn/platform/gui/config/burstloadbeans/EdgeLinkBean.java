package zju.edu.cn.platform.gui.config.burstloadbeans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EdgeLinkBean {

    private String name;
    private int travelTime;
    private int maxCapacity;
    private String startEdge;
    private String endEdge;
}
