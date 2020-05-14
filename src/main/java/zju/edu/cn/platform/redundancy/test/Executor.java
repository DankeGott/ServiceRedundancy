package zju.edu.cn.platform.redundancy.test;

/**
 * @ClassName: Executor
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-03-02 15:03
 * @Version: 1.0
 */
public class Executor implements Runnable {

    @Override
    public void run() {
       Object instance = ContainerSingleton.getInstance("Singleton");
        System.out.println(Thread.currentThread().getName() + instance);
    }
}
