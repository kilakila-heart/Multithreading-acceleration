import java.util.concurrent.*;

public class Singleton {
    private static volatile Singleton _instance = null;
    volatile static ThreadPoolExecutor threadPoolExecutor;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (_instance == null) {
            _instance = new Singleton();
            System.out.println("--initialized once.");
        } else
            System.out.println("不要初始化直接拿值");
        return _instance;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            Singleton.getInstance();

        });
        Thread thread2 = new Thread(() -> {

            Singleton.getInstance();

        });
        SynchronousQueue synchronousQueue = new SynchronousQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.MILLISECONDS, synchronousQueue);

        threadPoolExecutor.execute(thread1);
        threadPoolExecutor.execute(thread2);
        System.err.println(threadPoolExecutor.isTerminated());
        threadPoolExecutor.shutdown();
        while(!threadPoolExecutor.isTerminated()){
            System.out.println("我的战斗力很强");
        }
        threadPoolExecutor.awaitTermination(1,TimeUnit.HOURS);
        System.out.println("我结束了");

    }
}
