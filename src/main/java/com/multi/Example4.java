package com.multi;

import java.util.*;
import java.util.concurrent.*;

class Singleton{

    private static int first;
    private static int second;
    private static int third;

    private static Singleton singleton;

    public static String getCounts(){
        return first + "_" + second + "_" + third;
    }

    public static Singleton getInstance(){
        {
            Long aLong = Thread.currentThread().getId();
            System.out.println("Stage 1 : Thread:  " + aLong);
            first++;

            if (singleton == null){
                System.out.println("Stage 2 : Thread : " + aLong);
                second++;

                synchronized (Singleton.class){

                    if (singleton == null){
                        System.out.println("Stage 3 : Thread : " + aLong);
                        third++;

                        singleton = new Singleton();

                    }

                }
            }
        }
        return singleton;

    }

}

class ResourceQueue{

    private List<String> queue;
    private Map<Long, List<String>> map = new HashMap<>();

    public Map<Long, List<String>> getMap() {
        return map;
    }

    public ResourceQueue(List<String> queue) {
        this.queue = queue;
    }

    public List<String> getQueue() {
        return queue;
    }
}

class RemoveData implements Runnable{

    private ResourceQueue resourceQueue;

    public RemoveData(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
    }

    @Override
    public void run() {

        synchronized (resourceQueue){
            try {
                while (resourceQueue.getQueue().isEmpty()){
                    Long id = Thread.currentThread().getId();
                    if (resourceQueue.getMap().get(id) == null){
                        resourceQueue.getMap().put(id, new ArrayList<>());
                    }
                    resourceQueue.getMap().get(id).add(" waiting ");

                    System.out.println( id + " waiting ");
                    Thread.sleep(200);
                    resourceQueue.wait();
                }

                {
                    Long id = Thread.currentThread().getId();
                    if (resourceQueue.getMap().get(id) == null){
                        resourceQueue.getMap().put(id, new ArrayList<>());
                    }
                    resourceQueue.getMap().get(id).add(" woke up  ");

                    System.out.println(Thread.currentThread().getId() + " woke up ");
                    String s  =resourceQueue.getQueue().remove(0);
                    resourceQueue.getMap().get(id).add(" printing  " + s);
                    System.out.println(Thread.currentThread().getId() + " printing :  " + s);
                }
            }catch (Exception exception){
                System.out.println(exception.getLocalizedMessage());
            }
        }
    }
}

class AddData implements Runnable{

    private ResourceQueue resourceQueue;

    public AddData(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
    }

    @Override
    public void run() {

        synchronized (resourceQueue){
            try {
                while (resourceQueue.getQueue().size() > 5){

                    Long id = Thread.currentThread().getId();
                    if (resourceQueue.getMap().get(id) == null){
                        resourceQueue.getMap().put(id, new ArrayList<>());
                    }
                    resourceQueue.getMap().get(id).add(" adding waiting  ");

                    System.out.println(Thread.currentThread().getId() + " adding waiting ");
                    Thread.sleep(200);
                    resourceQueue.notifyAll();
                    Thread.sleep(500);
                    resourceQueue.wait();
                }

                {
                    Random random = new Random();
                    Integer integer = random.ints(0, 100).findAny().getAsInt();
                    String s = integer + ":" + random.ints(0, 100).findAny().getAsInt();
                    resourceQueue.getQueue().add("" + s);
                    System.out.println(Thread.currentThread().getId() + " added " + s);

                    Long id = Thread.currentThread().getId();
                    if (resourceQueue.getMap().get(id) == null){
                        resourceQueue.getMap().put(id, new ArrayList<>());
                    }
                    resourceQueue.getMap().get(id).add(" added  " + s);

                    resourceQueue.notifyAll();
                }
            }catch (Exception exception){

            }
        }
    }
}

class SingletonThread implements Callable<String> {

    public Singleton singleton;

    @Override
    public String call() {

        singleton = Singleton.getInstance();
        Long aLong = Thread.currentThread().getId();
        System.out.println("Thread : " + aLong + " Instance: " + singleton);
        return "";
    }
}

public class Example4 {

    public static void singleton() throws Exception{

        ExecutorService executorService = Executors.newFixedThreadPool(40);
        List<SingletonThread> list = new ArrayList<>();
        for (int i = 0 ; i < 50;i++){
            list.add(new SingletonThread());
        }

        List<Future<String>> result = executorService.invokeAll(list);

        executorService.shutdown();
        while (!executorService.awaitTermination(10L, TimeUnit.MINUTES)) {
            Thread.sleep(2000);
            System.out.println("Not yet. Still waiting for termination");
        }

        System.out.println(Singleton.getCounts());
    }

    public static void multiThread() throws Exception{

        ResourceQueue resourceQueue = new ResourceQueue(new ArrayList<>());
        Collection<Future<?>> tasks = new LinkedList<Future<?>>();

        ExecutorService executorService = Executors.newFixedThreadPool(40);
        for (int i = 0 ; i < 20; i++){
            Future<?> future = executorService.submit(new Thread(new RemoveData(resourceQueue)));
            tasks.add(future);
        }

        for (int i = 0 ; i < 20; i++){
            Future<?> future = executorService.submit(new Thread(new AddData(resourceQueue)));
            tasks.add(future);
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(10L, TimeUnit.MINUTES)) {
            Thread.sleep(2000);
            System.out.println("Not yet. Still waiting for termination");
        }

        for (Long s : resourceQueue.getMap().keySet()){
            System.out.println(s);
            System.out.println(resourceQueue.getMap().get(s).stream().reduce((a,b) -> a + "_" + b));
        }
    }

    public static void main(String[] args) throws Exception {

        singleton();

    }
}
