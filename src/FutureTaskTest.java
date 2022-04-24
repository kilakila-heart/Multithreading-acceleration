import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTaskTest {
    public void test(String[] args) {
        ExecutorService taskExe = new ThreadPoolExecutor(10000, 20000, 800L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        long count = 0L;
        //任务列表
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
        for (int i = 1; i < Math.pow(2,15)+1; i++) {
            //创建100个任务放入【任务列表】
            Integer asI = i;
            FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(1000);
                    return asI * asI;
                }
            });
            //执行的结果装回原来的FutureTask中,后续直接遍历集合taskList来获取结果即可
            taskList.add(futureTask);
            taskExe.submit(futureTask);
        }
        //获取结果
        try {
            for (FutureTask<Integer> futureTask : taskList) {
                count += futureTask.get();
            }
        } catch (InterruptedException e) {
            System.err.println("线程执行被中断");
        } catch (ExecutionException e) {
            System.err.println("线程执行出现异常");
        }
        //关闭线程池
        taskExe.shutdown();
        //打印: 100
        System.out.println(count);
    }

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        new FutureTaskTest().test(args);
        long gap=(System.currentTimeMillis()-start)/1000;
        System.out.println("耗时："+(gap)+"s");
    }
}
