import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) {
        ExecutorService service =
                new ThreadPoolExecutor(10,20,0L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 30 ; i++) {
            service.submit(new MyTask(i));
        }
    }
}


// 锁
class MyTask implements Runnable{

    private int i = 0;
    public MyTask(int i){
        this.i = i;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = Thread.currentThread().getName();
        System.out.println(name+"===="+(i++));
    }
}
