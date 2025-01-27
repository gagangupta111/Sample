package com.rateLimit;

import java.util.ArrayList;
import java.util.List;


class HitThread implements Runnable{

    Counter counter;

    public HitThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        Long id = Thread.currentThread().getId();
        boolean success = counter.hit(id + "");
        while (!success){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            success = counter.hit(id + "");
        }
    }
}

class Counter{

    public static final int window = 5000; // 100 milliseconds
    public static final int hitLimit = 5; // 10 hits allowed in above window

    private List<Long> allHitsLogs = new ArrayList<>();

    public boolean hit(String threadID){

        System.out.println("Thread ID : " + threadID + " hitting ... ");

        // get current time
        Long currentTime = System.currentTimeMillis();
        boolean success = hitDetails(currentTime, allHitsLogs);

        if (success){
            System.out.println("Thread ID : " + threadID + " Success ! ");
        }else {
            System.out.println("Thread ID : " + threadID + " Fail ! ");
        }
        return success;
    }

    public boolean hitDetails(Long time, List<Long> allHitsLogs){

        // check count of all previous hits in that window defined
        // using binary search
        // success or false do action

        int size = allHitsLogs.size();
        if (size < hitLimit){
            allHitsLogs.add(time);
            return true;
        }

        int firstIndex = 0;
        int lastIndex = size-1;
        int midIndex = (lastIndex + firstIndex)/2;

        long firstTime = allHitsLogs.get(firstIndex);
        long lastTime = allHitsLogs.get(lastIndex);
        long midTime = allHitsLogs.get(midIndex);

        long timeToBeSearched = time-window;
        while (true){

            if (timeToBeSearched < midTime){
                lastIndex = midIndex;
                midIndex = (lastIndex + firstIndex)/2;

                lastTime = allHitsLogs.get(lastIndex);
                midTime = allHitsLogs.get(midIndex);
            }

            if (timeToBeSearched > midTime){
                firstIndex = midIndex;
                midIndex = (lastIndex + firstIndex)/2;

                firstTime = allHitsLogs.get(firstIndex);
                midTime = allHitsLogs.get(midIndex);
            }

            if (timeToBeSearched == midTime || firstIndex == midIndex || lastIndex == midIndex){
                break;
            }
        }

        allHitsLogs = allHitsLogs.subList(midIndex, size);
        if (size - midIndex < hitLimit){
            return true;
        }
        return false;
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        for (int i = 0 ; i < 100; i++){
            if (i % 15 == 0){
                System.out.println("Sleeping!");
                Thread.sleep(1000);
            }
            new Thread(new HitThread(counter)).start();
        }

    }

}
