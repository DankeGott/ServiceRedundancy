package zju.edu.cn.platform.gui.config.burstload;

import lombok.Getter;

@Getter
public class DefaultInputConfigBurstLoad {

    private DefaultInputConfigBurstLoad() {
    }

    public static DefaultInputConfigBurstLoad defaultInputConfigBurstLoad = new DefaultInputConfigBurstLoad();

    // Edge Config
    private double freqLow = 1000;
    private double freqHigh = 5000;
    private double ramLow = 100;
    private double ramHigh = 500;
    private double storageLow = 100;
    private double storageHigh = 500;
    private int numEdges = 10;

    // Link Config
    private int bandWidthLow = 2;
    private int bandWidthHigh = 3;
    private int transTimeLow = 2;
    private int transTimeHigh = 5;
    private int degreeLow = 2;
    private int degreeHigh = 3;

    // Task Config
    private double packetSizeLow = 10;
    private double packetSizeHigh = 50;
    private int execTimeLow = 2;
    private int execTimeHigh = 5;
    private int numTasks = 100;
}
