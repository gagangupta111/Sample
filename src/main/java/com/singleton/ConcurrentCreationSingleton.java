package com.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/*
    below code illustrates different ways to create singleton
    the thread safe mechanism in singleton
    some common mistakes while implementing thread safe mechanism in singleton
    proof of concept by creation of multiple instances using multithreading and countdown latch
*/

// type 1 : synchronized double checking : thread safe
class DoubleChecking
{
    private static DoubleChecking instance;

    private DoubleChecking()
    {
        // private constructor
    }

    public static DoubleChecking getInstance()
    {
        if (instance == null)
        {
            //synchronized block to remove overhead
            synchronized (DoubleChecking.class)
            {
                if(instance==null)
                {
                    // if instance is null, initialize
                    instance = new DoubleChecking();
                }

            }
        }
        return instance;
    }
}
// type 2 : inner static class : thread safe
 class InnerStatic {
    private static class SingletonHolder {
        public static final InnerStatic instance = new InnerStatic();
    }

    public static InnerStatic getInstance() {
        return SingletonHolder.instance;
    }
}

// type 3 : inner static class : not thread safe : requires double checking
 class InnerStaticMistake {
    private static class SingletonHolder {
        public static InnerStaticMistake instance;

        // https://stackoverflow.com/questions/17799976/why-is-static-inner-class-singleton-thread-safe
        public static InnerStaticMistake getInstance() {
            if (null == instance) {
                instance = new InnerStaticMistake();
            }
            return instance;
        }
    }

    public static InnerStaticMistake getInstance() {
        return SingletonHolder.getInstance();
    }
}

// type 4 : same as type 3 with double checking : thread safe
class InnerStaticDoubleChecking {
    private static class SingletonHolder {
        public static InnerStaticDoubleChecking instance;
        public static InnerStaticDoubleChecking getInstance()
        {
            if (instance == null)
            {
                synchronized (InnerStaticDoubleChecking.class)
                {
                    if(instance==null)
                    {
                        instance = new InnerStaticDoubleChecking();
                    }
                }
            }
            return instance;
        }
    }

    public static InnerStaticDoubleChecking getInstance() {
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

        // just change this variable type to different singletons classes and see How many gets created
        DoubleChecking singleton;
        try {
            start.await();
            singleton = DoubleChecking.getInstance();
            end.countDown();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return singleton.toString();
    }
}

public class ConcurrentCreationSingleton {

    public static void main(String[] args) throws Exception{

        int totalThreads = 300;
        System.out.println("Hello!");
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(totalThreads);

        List<Future> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(totalThreads);
        for (int i = 0 ; i < totalThreads; i++){
            Future<String> future = executorService.submit((new LatchSingleton(start, end)));
            list.add(future);
        }

        Thread.sleep(2000);
        start.countDown();

        end.await();

        System.out.println("Main done!");

        executorService.shutdown();
        try {
            while (!executorService.isShutdown()) {
                Thread.sleep(2000);
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

        System.out.println(" Total Singleton Objects created : " + set.size());
        for (String string : set){
            System.out.println(string);
        }

    }
}
