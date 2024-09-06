package lms.Main;

import java.util.Map;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        Map<String, Integer> concurrencyMap = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Lthread[] threads = new Lthread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new Lthread(concurrencyMap, i);
            executorService.execute(threads[i]);
        }

        Thread.sleep(1000);
        threads[0].interruptThread();  // Interrupt the second thread

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask task = new SumTask(concurrencyMap, 0, concurrencyMap.size());
        int totalSum = forkJoinPool.invoke(task);
        System.out.println("Total sum of all values in the map: " + totalSum);

        forkJoinPool.shutdown();

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread deadlockThread1 = new Thread(new DeadlockExample(lock1, lock2), "DeadlockThread1");
        Thread deadlockThread2 = new Thread(new DeadlockExample(lock2, lock1), "DeadlockThread2");

        deadlockThread1.start();
        deadlockThread2.start();

        deadlockThread1.join();
        deadlockThread2.join();

        Thread preventionThread1 = new Thread(new DeadlockPrevention(lock1, lock2), "PreventionThread1");
        Thread preventionThread2 = new Thread(new DeadlockPrevention(lock1, lock2), "PreventionThread2");

        preventionThread1.start();
        preventionThread2.start();

        preventionThread1.join();
        preventionThread2.join();
    }
}

