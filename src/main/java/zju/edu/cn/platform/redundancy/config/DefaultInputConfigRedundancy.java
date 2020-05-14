package zju.edu.cn.platform.redundancy.config;

/**
 * @ClassName: DefaultInputConfigRedundancy
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-03-01 19:58
 * @Version: 1.0
 */
public class DefaultInputConfigRedundancy {
    private DefaultInputConfigRedundancy() {
    }

    public static DefaultInputConfigRedundancy defaultInputConfigRedundancy = new DefaultInputConfigRedundancy();

    // edge config
    private int edgeNum = 10;
    private int appCapacity = 3;


    // service config
    private int serviceNum = 9;
    private int redundancyNum = 3;

    // mobile device config
    private int mobileDeviceNum = 10;
    private int requestNum = 1;

    //general config
    private double wirelessLatency = 0.02;
    private double sbsLatency = 0.001;
    private double backboneDelay = 0.1;

}
