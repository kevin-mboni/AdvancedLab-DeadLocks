package lms.Main;

import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {
    private final Map<String, Integer> map;
    private final int start;
    private final int end;

    private static final int THRESHOLD = 2;

    public SumTask(Map<String, Integer> map, int start, int end) {
        this.map = map;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - start) <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                String key = "key " + (i / 3) + " _ " + (i % 3);
                Integer value = map.get(key);
                if (value != null) {
                    sum += value;
                } else {
                    System.out.println("Warning: Null value for key " + key);
                }
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(map, start, mid);
            SumTask rightTask = new SumTask(map, mid, end);

            invokeAll(leftTask, rightTask);  // Fork and join tasks
            return leftTask.join() + rightTask.join();
        }
    }
}

