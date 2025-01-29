package com.multi.samples;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }

}

class CyclicBarrierThread implements Runnable{

     CountDownLatch countDownLatch;
     CyclicBarrier cyclicBarrier;

    public CyclicBarrierThread(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
        this.countDownLatch = countDownLatch;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        System.out.println("Before : " + Thread.currentThread().getId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println("After : " + Thread.currentThread().getId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class MyObject{}

 class ObjectFactory {

    private volatile MyObject object;

    public MyObject getInstance() {
        if (object == null) {
            synchronized (this) {
                if (object == null) {
                    object = new MyObject();
                }
            }
        }
        return object;
    }
}

class TxtReader implements Runnable
{
    private String threadName;
    private String fileName;
    private CyclicBarrier cb;
    TxtReader(String threadName, String fileName, CyclicBarrier cb)
    {
        this.threadName = threadName;
        this.fileName = fileName;
        this.cb = cb;
    }
    @Override
    public void run()
    {
        System.out.println("Reading file " + fileName + " thread " + threadName);

        try
        {

            Thread.sleep(1000);

        //calling await() so the current thread may suspends
            cb.await();

            System.out.println("Done Reading file " + fileName + " thread " + threadName);
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }
        catch (BrokenBarrierException e)
        {
            System.out.println(e);
        }
    }
}

class AfterAction implements Runnable
{
    @Override
    public void run()
    {
        try {
            Thread.sleep(1000);
            System.out.println("In after action class, start further processing as all files are read");
            Thread.sleep(1000);
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }
}

public class Example {

     public static void cyclicBarrier(){

         CyclicBarrier cb = new CyclicBarrier(4, new AfterAction());
            //initializing three threads to read 3 different files
         Thread t1 = new Thread(new TxtReader("thread-1", "file-1", cb));
         Thread t2 = new Thread(new TxtReader("thread-2", "file-2", cb));
         Thread t3 = new Thread(new TxtReader("thread-3", "file-3", cb));
            //start begin execution of threads
         t1.start();
         t2.start();
         t3.start();

         System.out.println("Done ");
     }

    public static void awaitAndBarrier() throws Exception {

        int threadCount = 10;
        final ObjectFactory factory = new ObjectFactory();

        final CountDownLatch startSignal = new CountDownLatch(1);
        final CountDownLatch stopSignal = new CountDownLatch(threadCount);
        class MyThread extends Thread {
            MyObject instance;

            @Override
            public void run() {
                try {

                    System.out.println("Before await : " + Thread.currentThread().getId());
                    Thread.sleep(1000);

                    startSignal.await();

                    instance = factory.getInstance();

                    Thread.sleep(1000);
                    System.out.println("After await : " + Thread.currentThread().getId());

                    System.out.println("Before Countdown : " + Thread.currentThread().getId());
                    Thread.sleep(1000);

                    stopSignal.countDown();

                    Thread.sleep(1000);
                    System.out.println("After Countdown : " + Thread.currentThread().getId());


                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }

        MyThread[] threads = new MyThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }

        System.out.println("Before Countdown completed!");
        Thread.sleep(5000);
        startSignal.countDown();
        System.out.println("Countdown completed!");

        System.out.println("Before await completed!");
        Thread.sleep(5000);
        stopSignal.await();
        System.out.println("await completed!");

        MyObject instance = factory.getInstance();
        for (MyThread myThread : threads) {
            System.out.println(instance);
        }
    }

     public static void countdown(){

         final CountDownLatch countdown = new CountDownLatch(1);

         for (int i = 0; i < 10; ++ i) {
             Thread racecar = new Thread() {
                 public void run() {
                     try {

                         System.out.println("Before waiting!" + Thread.currentThread().getId());
                         Thread.sleep(1000);

                         countdown.await(); //all threads waiting

                         System.out.println("After waiting!" + Thread.currentThread().getId());
                         Thread.sleep(1000);


                     } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                     }
                     System.out.println("Vroom!" + "__" + Thread.currentThread().getId());
                 }
             };
             racecar.start();
         }
         System.out.println("Go");
         countdown.countDown();

     }

    public static Integer getRandomInteger(){
        int min = 1;
        int max = 100;
        Integer integer = min + (int)(Math.random() * ((max - min) + 1));
        return integer;
    }

    public static void exception(){

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        for (String string : list){
            if (string.equals("a")){
                list.remove(string);
            }
        }

    }

    public static void executorService(){

        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        //start the monitoring thread


    }


    public static void exception1(){

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        for (int i = 0 ; i < list.size(); i++){
            if (list.get(i).equals("a")){
                list.remove(list.get(i));
            }
        }

        for (String s : list){
            System.out.println(s);
        }

    }

    public static void completable() throws Exception {

        CompletableFuture<String> greetingFuture = CompletableFuture
                .supplyAsync(() -> {
            // some async computation
            try {
                Thread.sleep(1000);
                int value = 10/0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello from CompletableFuture";
        })

                .exceptionally((ex) -> "exceptionally error")
                .handle((result, exception) -> {
                    if (exception != null){
                        return "handle error";
                    }else {
                        return result;
                    }
                })
                .whenComplete((a, b) -> System.out.println("completed with result: " + a + " exception : " + b));
        System.out.println(greetingFuture.get());

        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> {

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return 100;

                        })
                        .thenApply(x -> x+1);
        System.out.println(future.get());

        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(() -> 1)
                        .thenCompose(x -> CompletableFuture.supplyAsync(() -> x+1))
                        .thenCompose(x -> CompletableFuture.supplyAsync(() -> x+1));

        System.out.println(future1.get());

    }

    public static long stream(List<Node> list){

        Instant start = Instant.now();
        List<Node> newList = list.parallelStream().filter((a) -> a.value % 2 == 0).collect(Collectors.toList());
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        return timeElapsed.toNanos();
    }

    public static void main(String[] args) throws Exception {

         List<Node> list = new ArrayList<>();
         for (int i = 0; i < 100; i++) {
             list.add(new Node(i, null));
         }

        for (int i = 0; i < 100; i++) {
            System.out.println(stream(list));
        }

    }

    public static void method1(List<?> list){

         for (Object object : list){
             System.out.println(object.getClass());
         }
    }


}

class Generics<E>{

     public E e;

}

class Node implements Cloneable{

     public int value;
     public Node next;

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public Node clone() throws CloneNotSupportedException {
        return (Node) super.clone();
    }

}

class ThreadPull implements Runnable{

    private Queue queue;
    public ThreadPull(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        queue.remove();

    }
}

class ThreadPush implements Runnable{

    private Queue queue;
    public ThreadPush(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        queue.add();

    }
}

class Queue{

    private List<Integer> queue = new ArrayList<>();

    public synchronized Integer add(){

        while (!canAdd()){
            try {
                Thread.sleep(1000);
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Integer integer = Example.getRandomInteger();
        System.out.println("Added by : " + Thread.currentThread().getId() + " :: " + integer);

        queue.add(integer);
        this.notifyAll();
        return integer;
    }

    public synchronized Integer remove(){

        while (!canRemove()){
            try {
                System.out.println("Waiting: " + Thread.currentThread().getId() + " :: ");
                Thread.sleep(1000);
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Integer integer = queue.remove(0);
        System.out.println("Removed by : " + Thread.currentThread().getId() + " :: " + integer);
        return integer;
    }

    private boolean canAdd(){
        return true;
    }

    private boolean canRemove(){
        return !queue.isEmpty();
    }

    public synchronized void put(Integer value){

        while (queue.size() > 5){
            try {
                System.out.println("Waiting put " + Thread.currentThread().getId());
                Thread.sleep(1000);
                wait();
                System.out.println("Out of Waiting put " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.add(value);

    }

}



