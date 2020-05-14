package zju.edu.cn.platform.redundancy.test;

import java.lang.reflect.Constructor;

/**
 * @ClassName: myTest
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-03-02 14:59
 * @Version: 1.0
 */
public class myTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Executor());
        Thread t2 = new Thread(new Executor());

        t1.start();
        t2.start();

        Class<?> clazz = Singleton.class;

        try {
            Constructor c = clazz.getDeclaredConstructor();
            c.setAccessible(true);

            System.out.println(c.newInstance());
            System.out.println(c.newInstance());
            System.out.println(c.newInstance());
            System.out.println(c.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
