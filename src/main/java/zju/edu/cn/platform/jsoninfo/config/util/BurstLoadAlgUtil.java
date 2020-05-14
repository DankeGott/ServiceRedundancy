package zju.edu.cn.platform.jsoninfo.config.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import zju.edu.cn.platform.gui.config.burstloadbeans.BurstLoadStageTimeBean;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于提供BurstLoad算法相关使用接口
 * 必须信息：python编写的算法的位置，算法需要输出指定的文件，输出的文件中包含仿真以及绘制统计图所需要的数据
 * 该类用于运行算法、转换数据并提供数据的访问与获取
 */
@Getter
@Setter
@ToString
public class BurstLoadAlgUtil {

    private String algPathStr; // 待运行算法的路径
    // 统计图数据最终转换的格式
    private List<Float> algIterTimeSeriesList;  // 算法的迭代情况
    private List<BurstLoadStageTimeBean> taskStageTimeList; // 每个任务在各个阶段的运行时长

    // 算法结果仿真模拟所需要的文件位置
    private String stringEdgeNumTasksPath; //记录每个时间点每个边缘服务器上的任务个数json文件位置
    private String stringLinkCapStatusPath; //记录每个时间点每个链路的负载情况的json文件位置

    /**
     * 运行算法
     * @return 返回0表示正常运行，否则表示算法运行中出现错误
     * @throws IOException
     * @throws InterruptedException
     */
    public int runAlg() throws IOException, InterruptedException {
        System.out.println("运行算法...");
        System.out.println("算法脚本路径=" + algPathStr);
        Path pathDir = Paths.get(algPathStr).getParent();

        stringEdgeNumTasksPath = Paths.get(pathDir.toString(), "edge_num_tasks.json").toString();
        stringLinkCapStatusPath = Paths.get(pathDir.toString(), "link_cap_status.json").toString();
        // 创建临时运行系统脚本(windows *.bat)
        createRunScript();
        final Process process = Runtime.getRuntime().exec(Paths.get(pathDir.toString(), "run.bat").toString());
        int retVal = process.waitFor();
        System.out.println("process wait for ret val = " + retVal);
        removeRunScript();
        return retVal;
    }

    /**
     * FIXME 其他操作系统运行脚本的创建
     * 在选择的算法的目录下创建临时的运行脚本
     * 为了在不修改原算法路径关系的前提下，创建系统脚本文件(windows *.bat)切换到算法所在的目录运行python程序
     */
    private void createRunScript() throws IOException {
        Path pathDir = Paths.get(algPathStr).getParent();
        File file = new File(Paths.get(pathDir.toString(), "run.bat").toString());
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(pathDir.getRoot().toString().substring(0, 2)); // 切换盘符
        bufferedWriter.newLine();
        bufferedWriter.write("cd " + pathDir);
        bufferedWriter.newLine();
        bufferedWriter.write("python " + algPathStr); //运行脚本
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    private void removeRunScript() {
        Path pathDir = Paths.get(algPathStr).getParent();
        File file = new File(Paths.get(pathDir.toString(), "run.bat").toString());
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 读取算法运行的统计信息
     */
    public void loadStatisticInfo() throws IOException {
        System.out.println("加载算法运行输出数据...");
        Path dirPath = Paths.get(algPathStr).getParent(); //目录路径
        final String iterInfoPath = Paths.get(dirPath.toString(), "iter_info.txt").toString();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(iterInfoPath));
        algIterTimeSeriesList = new ArrayList<>();
        float val = 0;
        String lineStr = "";
        do {
            lineStr = bufferedReader.readLine();
            System.out.println(lineStr);
            if (lineStr != null && !lineStr.equals("")) {
                val = Float.parseFloat(lineStr);
                algIterTimeSeriesList.add(val);
            }
        } while (lineStr != null && !lineStr.equals(""));
        bufferedReader.close();
        // 读取每个任务各个阶段的时间长度
        final String tasksTimeSeriesStr = Paths.get(dirPath.toString(), "tasks_tm_series.txt").toString();
        bufferedReader = new BufferedReader(new FileReader(tasksTimeSeriesStr));
        taskStageTimeList = new ArrayList<>();
        do {
            lineStr = bufferedReader.readLine();
            System.out.println(lineStr);
            if (lineStr != null && !lineStr.equals("")) {
                String[] strs = lineStr.split(" ");
                BurstLoadStageTimeBean burstLoadStageTimeBean = new BurstLoadStageTimeBean();
                burstLoadStageTimeBean.setWaitAtSrcf(Float.parseFloat(strs[0]));
                burstLoadStageTimeBean.setMigratef(Float.parseFloat(strs[1]));
                burstLoadStageTimeBean.setWaitExecf(Float.parseFloat(strs[2]));
                burstLoadStageTimeBean.setExecf(Float.parseFloat(strs[3]));
                taskStageTimeList.add(burstLoadStageTimeBean);
            }
        } while (lineStr != null && !lineStr.equals(""));
        System.out.println("加载完毕");
    }
}
