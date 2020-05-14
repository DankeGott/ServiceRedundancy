package zju.edu.cn.platform.redundancy.jsoninfo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import zju.edu.cn.platform.redundancy.application.Application;
import zju.edu.cn.platform.redundancy.application.ApplicationServiceNode;
import zju.edu.cn.platform.redundancy.util.PlatformUtils;

@Data
public class AppInfo {
    private String name;
    private Node[] nodes;

    @Data
    public static class Node {
        private int id;
        private String name;
        private double inputSize;
        private double outputSize;
    }
}
