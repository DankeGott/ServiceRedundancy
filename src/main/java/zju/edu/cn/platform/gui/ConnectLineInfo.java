package zju.edu.cn.platform.gui;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.button.EdgeIconButton;
import zju.edu.cn.platform.jsoninfo.config.burstload.BurstLoadLinkConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.LinkConfig;
import zju.edu.cn.platform.jsoninfo.config.resalloc.WirelessLinkConfig;
import zju.edu.cn.platform.util.PlatformUtils;

import java.awt.*;

@Getter
@Setter
public class ConnectLineInfo {
//    private int PROBLEM_TYPE = PlatformUtils.RESOURCE_ALLOC_AND_SCHEDULING_PROBLEM;

    private IconLabel startJLabel, endJLabel;
    private Color lineColor;
    private LinkConfig linkConfig;
    private WirelessLinkConfig wirelessLinkConfig;
    // --------------variables for burst load problem-----------------
    private BurstLoadLinkConfig burstLoadLinkConfig;
    private static int LINK_IDX = 0;

    public ConnectLineInfo(IconLabel startJLabel, IconLabel endJLabel,
                           Color lineColor) {
        super();
        this.startJLabel = startJLabel;
        this.endJLabel = endJLabel;
        this.lineColor = lineColor;
    }


    @Override
    public String toString() {
        return "ConnectLineInfo{" +
                "startJLabel=" + startJLabel +
                ", endJLabel=" + endJLabel +
                ", lineColor=" + lineColor +
                '}';
    }

    /**
     * 计算点距离当前直线的距离
     * 一般式: ax +by + c = 0 其中
     *
     * @param x
     * @param y
     * @return
     */
    public int dotLineDistance(int x, int y) {
        int x1 = startJLabel.getX(), y1 = startJLabel.getY(), x2 = endJLabel.getX(), y2 = endJLabel.getY();
        if (x < Math.min(x1, x2) - 50 || x > Math.max(x1, x2) + 50 || y < Math.min(y1, y2) - 50 || y > Math.max(y1, y2) + 50) {
            return 9999;
        } else {
            return (int) (Math.abs((y2 - y1) * x + (x1 - x2) * y + (x2 * y1 - x1 * y2)) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x1 - x2, 2)));
        }
    }

    public void setCommonLinkConfig() {
        // 边缘服务器与移动设备之间的连接
        if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.RESOURCE_ALLOC_AND_SCHEDULING_PROBLEM) {
            if (startJLabel.getIconType() == EdgeIconButton.DEVICE_BUTTON ||
                    endJLabel.getIconType() == EdgeIconButton.DEVICE_BUTTON) {
                IconLabel device, edge;
                if (startJLabel.getIconType() == EdgeIconButton.DEVICE_BUTTON) {
                    device = startJLabel;
                    edge = endJLabel;
                } else {
                    device = endJLabel;
                    edge = startJLabel;
                }
                wirelessLinkConfig = new WirelessLinkConfig();
                wirelessLinkConfig.setDeviceName(device.getDeviceConfig().getName());
                wirelessLinkConfig.setEdgeName(edge.getEdgeConfig().getName());
                wirelessLinkConfig.setTransmissionRate(0);
            } else {
                // 服务器之间的连接
                linkConfig = new LinkConfig();
                linkConfig.setStartEdgeName(startJLabel.getEdgeConfig().getName());
                linkConfig.setEndEdgeName(endJLabel.getEdgeConfig().getName());
                linkConfig.setUpLink(0);
                linkConfig.setDownLink(0);
            }
        } else if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.BURST_LOAD_EVACUATION) {
            burstLoadLinkConfig = new BurstLoadLinkConfig();
            burstLoadLinkConfig.setStartEdgeName(startJLabel.getBurstLoadEdgeConfig().getName());
            burstLoadLinkConfig.setEndEdgeName(endJLabel.getBurstLoadEdgeConfig().getName());
            burstLoadLinkConfig.setName(String.valueOf(LINK_IDX++));
            burstLoadLinkConfig.setParallelWidth(0);
            burstLoadLinkConfig.setTransRate(0);
        }

    }
}
