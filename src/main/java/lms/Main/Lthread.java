package lms.Main;

import java.util.Map;

public class Lthread extends Thread implements Runnable {
    private final int threadsNumber;
    private final Map<String, Integer> map;
    private volatile boolean interrupted = false;

    public Lthread(Map<String, Integer> map, int threadsNumber) {
        this.threadsNumber = threadsNumber;
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            if (interrupted) {
                System.out.println("Thread " + threadsNumber + " interrupted! Exiting...");
                break;
            }

            synchronized (map) {
                map.put("key " + threadsNumber + " _ " + i, i);
                System.out.println("key " + i + " added on thread number " + threadsNumber);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread " + threadsNumber + " interrupted during sleep! Exiting...");
                break;
            }
        }
    }
    public void interruptThread() {
        interrupted = true;
    }
}

