package com.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SampleThread implements Callable<String> {

    private String id;

    public SampleThread(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String call() throws Exception {

        try {
            System.out.println("Thread : " + id + " running");
            Thread.sleep(3000);
            System.out.println("Thread : " + id + " completed");
        } catch (Exception e) {
            System.out.println("Thread : " + id + " exception" + e.getMessage());
        }
        return "" + id;
    }
}

public class StopInterrupt {

    public static void main(String[] args){

        System.out.println("Hello!");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future> list = new ArrayList<>();

        for (int i = 0 ; i < 10; i++){
            Future<String> future = executorService.submit(new SampleThread(i + ""));
            list.add(future);
        }
        for (int i = 0 ; i < 5; i++){
            Future<String> future = list.get(i);
            future.cancel(true);
        }
        for (int i = 0 ; i < 5; i++){
            Future<String> future = list.get(i);
            System.out.println(" is cancelled : " + future.isCancelled());
            System.out.println(" is done : " + future.isDone());
            System.out.println("++++++++++++++");
        }
    }
}
