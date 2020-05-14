package zju.edu.cn.platform.redundancy.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ContainerSingleton
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-03-03 10:36
 * @Version: 1.0
 */

public class ContainerSingleton {

    private ContainerSingleton(){

    }

    private static Map<String, Object> ioc = new ConcurrentHashMap<>();

    public static Object getInstance(String className){
        synchronized (ioc){
            if(!ioc.containsKey(className)){
                Object obj = null;
                try{
                    obj = Class.forName(className).newInstance();

                    ioc.put(className, obj);

                }catch (Exception e){
                    e.printStackTrace();
                }
                return obj;
            }
            else{
                return ioc.get(className);
            }
        }
    }
}
