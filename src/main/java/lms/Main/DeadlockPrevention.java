package lms.Main;

public class DeadlockPrevention implements Runnable {
    private final Object lock1;
    private final Object lock2;

    public DeadlockPrevention(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " locked " + lock1);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(Thread.currentThread().getName() + " waiting to lock " + lock2);
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " locked " + lock2);
            }
        }
    }
}

