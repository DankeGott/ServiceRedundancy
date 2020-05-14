package zju.edu.cn.platform.redundancy.test;

/**
 * @ClassName: Singleton
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-03-02 14:59
 * @Version: 1.0
 */
public class Singleton {

    private Singleton(){

    }

    public static Singleton getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static Singleton INSTANCE = new Singleton();
    }

}
