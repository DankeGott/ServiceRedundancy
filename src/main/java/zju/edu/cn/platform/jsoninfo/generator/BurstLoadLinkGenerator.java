package zju.edu.cn.platform.jsoninfo.generator;

import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeLinkBean;
import zju.edu.cn.platform.gui.config.burstloadbeans.EdgeServerBean;

import java.util.*;

@Getter
@Setter
public class BurstLoadLinkGenerator {
    private int[] parallelWidthRange;
    private int[] transRates;
    private List<EdgeServerBean> edgeServerBeans;
    private int[] edgeDegreeRange;

    /**
     * @param parallelWidthRange the range of generating link parallel width, size 2
     * @param edgeServerBeans
     * @param edgeDegreeRange    the control value for the degree of link sparsity
     */
    public BurstLoadLinkGenerator(int[] parallelWidthRange, List<EdgeServerBean> edgeServerBeans, int[] edgeDegreeRange,
                                  int[] transRates) {
        assert parallelWidthRange.length == 2;
        assert edgeDegreeRange.length == 2;
        this.edgeServerBeans = edgeServerBeans;

        this.parallelWidthRange = parallelWidthRange;
        this.edgeDegreeRange = edgeDegreeRange;
        this.transRates = transRates;
    }

    public List<EdgeLinkBean> generateLinkConfigs() {
        List<EdgeLinkBean> burstLoadLinkConfigList = new ArrayList<>();
        int numEdges = this.edgeServerBeans.size();
        int[][] connectMatrix = new int[numEdges][];
        for (int i = 0; i < numEdges; ++i) {
            connectMatrix[i] = new int[numEdges];
            for (int j = 0; j < numEdges; ++j) {
                connectMatrix[i][j] = 0;
            }
        }
        Random random = new Random();
        for (int i = 0; i < numEdges - 1; ++i) {
            Set<Integer> flag = new HashSet<>();
            int numSelected = random.nextInt(edgeDegreeRange[1] - edgeDegreeRange[0]) + edgeDegreeRange[0];
            numSelected = Math.min(numSelected, numEdges - i - 1);
            while (flag.size() < numSelected) {
                int idx = random.nextInt(numEdges - i - 1) + i + 1;
                connectMatrix[i][idx] = 1;
                connectMatrix[idx][i] = 1;
                flag.add(idx);
            }
        }
        int idx = 0;
        for (int i = 0; i < numEdges; ++i) {
            for (int j = i + 1; j < numEdges; ++j) {
                if (connectMatrix[i][j] == 1) {
                    EdgeLinkBean linkConfig = new EdgeLinkBean();
                    linkConfig.setName("l" + idx);
                    idx++;
                    linkConfig.setStartEdge(this.edgeServerBeans.get(i).getName());
                    linkConfig.setEndEdge(this.edgeServerBeans.get(j).getName());
                    linkConfig.setMaxCapacity(random.nextInt(parallelWidthRange[1] - parallelWidthRange[0]) + parallelWidthRange[0]);
//                    linkConfig.setTransRate(random.nextInt(transRateRange[1]-transRateRange[0])+transRateRange[0]);
//                    linkConfig.setTravelTime((int) transRates[random.nextInt(transRates.length)]);
                    linkConfig.setTravelTime(random.nextInt(transRates[1] - transRates[0]) + transRates[0]);
                    burstLoadLinkConfigList.add(linkConfig);
                }
            }
        }
        return burstLoadLinkConfigList;
    }

//    public static void main(String[] args) {
//        List<BurstLoadLinkConfig> linkConfigList=burstLoadLinkGenerator.generateLinkConfigs();
//        for(BurstLoadLinkConfig linkConfig : linkConfigList){
//            System.out.println(linkConfig);
//        }
//    }
}
