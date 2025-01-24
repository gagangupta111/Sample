package com.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

// this explain illustrates different ways to create singleton
// the thread safe mechanism in singleton
// some common mistakes while implementing thread safe mechanism in singleton

class DoubleCheckingSingleton
{
    private static DoubleCheckingSingleton instance;

    private DoubleCheckingSingleton()
    {
        // private constructor
    }

    public static DoubleCheckingSingleton getInstance()
    {
        if (instance == null)
        {
            //synchronized block to remove overhead
            synchronized (DoubleCheckingSingleton.class)
            {
                if(instance==null)
                {
                    // if instance is null, initialize
                    instance = new DoubleCheckingSingleton();
                }

            }
        }
        return instance;
    }
}
 class BillPughSingleton  {
    private static class SingletonHolder {
        public static final BillPughSingleton instance = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHolder.instance;
    }
}

 class NotThreadSafe {
    private static class SingletonHolder {
        public static NotThreadSafe instance;

        // https://stackoverflow.com/questions/17799976/why-is-static-inner-class-singleton-thread-safe
        public static NotThreadSafe getInstance() {
            if (null == instance) {
                instance = new NotThreadSafe();
            }
            return instance;
        }
    }

    public static NotThreadSafe getInstance() {
        return SingletonHolder.getInstance();
    }
}

class LatchSingleton implements Callable<String> {

    private CountDownLatch start;
    private CountDownLatch end;

    public LatchSingleton(CountDownLatch start, CountDownLatch end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String call() {

        Long l = Thread.currentThread().getId();
        NotThreadSafe singleton;
        try {
            start.await();
            singleton = NotThreadSafe.getInstance();
            end.countDown();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return singleton.toString();
    }
}

public class Main {

    public static void main(String[] args) throws Exception{

        int total = 300;
        System.out.println("Hello!");
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(total);

        List<Future> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        for (int i = 0 ; i < total; i++){
            Future<String> future = executorService.submit((new LatchSingleton(start, end)));
            list.add(future);
        }

        Thread.sleep(2000);
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
        Set<String> set = new HashSet<>();
        for (Future future : list){
            set.add(future.get().toString());
        }

        for (String string : set){
            System.out.println(string);
        }

    }
}
