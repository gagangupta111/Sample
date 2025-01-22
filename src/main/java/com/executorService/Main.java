package com.executorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class LatchExampleThreadNew implements Callable<String> {

    private CountDownLatch start;
    private CountDownLatch end;

    public LatchExampleThreadNew(CountDownLatch start, CountDownLatch end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String call() {

        Long l = Thread.currentThread().getId();
        System.out.println("Thread waiting : " + l);

        try {
            start.await();

            System.out.println("Thread Started : " + l);
            Thread.sleep(2000);

            System.out.println("Thread before countdown : " + l + "__" + end.getCount());
            end.countDown();

            System.out.println("Thread done : " + l);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Thread ID : " + l;
    }
}

public class Main {

    public static void main(String[] args) throws Exception{

        System.out.println("Hello!");
        int total = 20;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(total);

        List<Future> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        for (int i = 0 ; i < total; i++){
            Future<String> future = executorService.submit((new LatchExampleThreadNew(start, end)));
            list.add(future);
        }

        Thread.sleep(5000);
        start.countDown();

        end.await();

        System.out.println("Main done!");

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            executorService.shutdownNow();
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        for (Future future : list){
            System.out.println(future.get());
        }

    }
}
