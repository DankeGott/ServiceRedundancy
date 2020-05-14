package zju.edu.cn.platform.gui.button;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import zju.edu.cn.platform.gui.EdgeGUI;
import zju.edu.cn.platform.statistics.BurstloadDrawStatisticGraph;
import zju.edu.cn.platform.statistics.DrawStatisticGraph;
import zju.edu.cn.platform.util.PlatformUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@Getter
@Setter
public class StatisticsButton extends EdgeIconButton {

    private EdgeGUI edgeGUI;

    public StatisticsButton(EdgeGUI edgeGUI) {
        super(EdgeIconButton.STATISTICS_BUTTON, "", "statistics.png");
        this.edgeGUI = edgeGUI;
        setAction();
    }

    public void setAction() {
        getButton().addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.RESOURCE_ALLOC_AND_SCHEDULING_PROBLEM) {
                    try {
                        DrawStatisticGraph.showStatistics(PlatformUtils.PATH + "/result/requests/requests_1.txt");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if (PlatformUtils.PROBLEM_TYPE == PlatformUtils.BURST_LOAD_EVACUATION) {
                    BurstloadDrawStatisticGraph burstloadDrawStatisticGraph = new
                            BurstloadDrawStatisticGraph(edgeGUI.getAlgButton().getPathAlgDir());
                }
            }
        });
    }
}
