
package com.multi.sample1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LatchExampleThread implements Runnable{

    private CountDownLatch start;
    private CountDownLatch end;

    public LatchExampleThread(CountDownLatch start, CountDownLatch end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {

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


    }
}

public class Sample {

    public static void main(String[] args) throws Exception{

        System.out.println("Hello!");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(20);

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0 ; i < 20; i++){
            executorService.submit(new Thread(new LatchExampleThread(countDownLatch, end)));
        }

        Thread.sleep(5000);
        countDownLatch.countDown();

        end.await();

        System.out.println("Main done!");

    }
}
