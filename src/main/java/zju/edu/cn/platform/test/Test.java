package zju.edu.cn.platform.test;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author: Zijie Liu
 * @Date: 2020-02-19 15:13
 * @Version: 1.0
 */
//public class Test implements Lock{
//
//    /**
//     * 静态内部类，自定义同步器
//     */
//    private static class Sync extends AbstractQueuedSynchronizer{
//
//        //是否处于占用状态
//
//        @Override
//        protected boolean isHeldExclusively() {
//            return getState() == 1;
//        }
//
//        //当前状态为0的时候获取锁
//
//        @Override
//        protected boolean tryAcquire(int arg) {
//            if(compareAndSetState(0 ,1)){
//                setExclusiveOwnerThread(Thread.currentThread());
//                return true;
//            }
//            return false;
//        }
//
//        //当前状态为1的时候释放锁
//
//        @Override
//        protected boolean tryRelease(int arg) {
//            if(compareAndSetState(1, 0)){
//                setExclusiveOwnerThread(null);
//                return true;
//            }
//            return false;
//        }
//
//        //返回一个Condition，每个Condition都包含一个Condition队列
//        Condition newCondation(){
//            return new ConditionObject();
//        }
//
//        final boolean nonfairTryAcquire(int acquires){
//            final Thread current = Thread.currentThread();
//            int c = getState();
//            if(c == 0){
//                if(compareAndSetState(0, acquires)){
//                    setExclusiveOwnerThread(current);
//                    return true;
//                }
//            }
//            else if( current == getExclusiveOwnerThread() ){
//                int next = c + acquires;
//                if(c < 0){
//                    throw new Error("");
//                }
//                setState(next);
//                return true;
//            }
//            return false;
//        }
//    }
//
//
//
//    private final Sync sync = new Sync();
//
//    @Override
//    public void lock() {
//        //acquire调用tryAcquire
//        sync.acquire(1);
//    }
//
//    @Override
//    public void lockInterruptibly() throws InterruptedException {
//        sync.acquireInterruptibly(1);
//    }
//
//    @Override
//    public boolean tryLock() {
//        return sync.tryAcquire(1);
//    }
//
//    @Override
//    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
//        return sync.tryAcquireNanos(1, unit.toNanos(time));
//    }
//
//    @Override
//    public void unlock() {
//        sync.release(1);
//    }
//
//    @Override
//    public Condition newCondition() {
//        return sync.newCondation();
//    }
//}

//public class Test{
//
//    static Map<String, Object> map = new HashMap<>();
//    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
//    static Lock rl = rwl.readLock();
//    static Lock wl = rwl.writeLock();
//
//    public static final Object get(String key){
//        rl.lock();
//        try {
//            return map.get(key);
//        }finally {
//            rl.unlock();
//        }
//    }
//
//    public static final Object put(String key, Object value){
//        wl.lock();
//        try {
//            return map.put(key, value);
//        }finally {
//            wl.unlock();
//        }
//    }
//}

//public class Test{
//
//    ReentrantLock lock = new ReentrantLock();
//    Condition condition = lock.newCondition();
//
//    public void conditionWait() throws InterruptedException {
//        lock.lock();
//        try {
//            condition.await();
//        }finally {
//            lock.unlock();
//        }
//    }
//
//    public void conditionSignal{
//        lock.lock();
//        try {
//            condition.signalAll();
//        }finally {
//            lock.unlock();
//        }
//    }
//
//}

//public class Test{
//
//    public static void main(String[] args) {
//        ClassLoader cl = Thread.currentThread().getContextClassLoader();
//        System.out.println(cl.getResource("alg_icon.png"));
//        System.out.println(cl.getParent());
//    }
//}

//class  SuperClass{
//
//    static {
//        System.out.println("SuperClass init!");
//    }
//    public  static  int value=123;
//
//}
//class  SubClass extends  SuperClass{
//
//    static {
//        System.out.println("SubClass init!");
//    }
//
//}


//public class Test {
//    public static void main(String[] args)throws  Exception{
//        Object lock = new Object();
//        ReentrantLock lock1 = new ReentrantLock();
//        lock1.lock();
//        lock1.unlock();
//        synchronized (lock){
//
//        }
//        System.out.println(SubClass.value);
//        //输出:
//        //SuperClass init!
//        //123
//
//    }
//
//}
//

//
//public class Test{
//    public static void sort(int[] arr) {
//        sort(arr,0, arr.length - 1);
//    }
//
//    private static void sort(int[] arr,int startIndex, int endIndex) {
//        if (endIndex <= startIndex) {
//            return;
//        }
//        //切分
//        int pivotIndex = partition(arr, startIndex, endIndex);
//        sort(arr, startIndex, pivotIndex-1);
//        sort(arr, pivotIndex+1, endIndex);
//    }
//
//    private static int partition(int[] arr, int startIndex, int endIndex) {
//        int pivot = arr[startIndex];//取基准值
//        int mark = startIndex;//Mark初始化为起始下标
//
//        for(int i=startIndex+1; i<=endIndex; i++){
//            if(arr[i]<pivot){
//                //小于基准值 则mark+1，并交换位置。
//                mark ++;
//                int p = arr[mark];
//                arr[mark] = arr[i];
//                arr[i] = p;
//            }
//        }
//        //基准值与mark对应元素调换位置
//        arr[startIndex] = arr[mark];
//        arr[mark] = pivot;
//        return mark;
//    }
//
//    public static void main(String[] args) {
//        int[] array = {8,2,5,0,7,4,6,1};
//        sort(array);
//        System.out.println(Arrays.toString(array));
//    }
//}
public class Test{

    static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        lock.lock();
        Stack<Integer> s = new Stack<>();
        s.push(3);
        s.push(2);
        s.push(1);
//        for(int a: s){
//            System.out.println(a);
//        }
        System.out.println(Arrays.toString(s.toArray()));
    }

}
