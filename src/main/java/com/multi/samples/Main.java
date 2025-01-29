package com.multi.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class OneInstance implements Callable<String>{

    private String url;
    private CountDownLatch start;
    private CountDownLatch end;

    public OneInstance(String url, CountDownLatch start, CountDownLatch end) {
        this.url = url;
        this.start = start;
        this.end = end;
    }

    @Override
    public String call() throws Exception {

        Long id = Thread.currentThread().getId();
        System.out.println(id  + " : awaiting to start!");
        start.await();

        System.out.println(id + " : Started!");
        Thread.sleep(2000);
        System.out.println(id + " : Executed!");

        end.countDown();

        return "url : " + url + " fetched ";
    }
}

public class Main {

    public static void usingThread() throws Exception{

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(4);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        List<Future<String>> returned = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            returned.add(executorService.submit(new OneInstance("url" + i, start, end)));
        }


        System.out.println("Starting all threads");
        Thread.sleep(3000);
        start.countDown();
        System.out.println("Started all threads");

        System.out.println("ending all threads");
        Thread.sleep(3000);
        end.await();
        System.out.println("ended all threads");

        String response = "";
        for (Future<String> task : returned){
            response += task.get();
        }
        System.out.println("Response : " + response);

    }

    public static void main(String[] args) throws Exception {
        usingThread();
    }
}
