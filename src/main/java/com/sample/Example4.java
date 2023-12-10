package com.sample;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

public class Example4 {

    public static void main(String[] args) throws InterruptedException {

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

}
