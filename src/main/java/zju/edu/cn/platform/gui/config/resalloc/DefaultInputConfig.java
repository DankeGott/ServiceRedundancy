package zju.edu.cn.platform.gui.config.resalloc;

import lombok.Getter;

/**
 * @author jfqiao
 * @since 2020/02/24
 */
@Getter
public class DefaultInputConfig {

    private DefaultInputConfig() {}

    public static DefaultInputConfig defaultInputConfig = new DefaultInputConfig();

    // edge config
    private int edgeNum = 4;
    private double bandwidthMean = 80;
    private double bandwidthVar = 8;
    private double computingPowerMean = 200000;
    private double computingPowerVar = 20000;
    private double priceMean = 100;
    private double priceVar = 10;

    // mobile device config
    private double transmissionRateMean = 2;
    private double transmissionRateVar = 0.2;
    private double reqGeneratedMean = 10;
    private double reqGeneratedVar = 1;

    // service config
    private int serviceNum = 2;
    private double inputSizeMean = 3;
    private double inputSizeVar = 0.3;
    private double outputSizeMean = 3;
    private double outputSizeVar = 0.3;
    private double workloadMean = 2000;
    private double workloadVar = 200;
}
