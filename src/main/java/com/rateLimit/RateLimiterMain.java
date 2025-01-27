package com.rateLimit;

import java.util.ArrayList;
import java.util.List;

// below code demonstrates a sample rate limiter, nothing complex, a very simple one
// just creating a list of timestamps, searching binary into that and finding a timestamp within a window

class Thread_Hit implements Runnable{

    BasicStructure basicStructure;

    public Thread_Hit(BasicStructure basicStructure) {
        this.basicStructure = basicStructure;
    }

    @Override
    public void run() {
        Long id = Thread.currentThread().getId();
        boolean success = basicStructure.hit(id + "");
        while (!success){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            success = basicStructure.hit(id + "");
        }
    }
}

class BasicStructure {

    public static final int window = 5000; // 100 milliseconds
    public static final int hitLimit = 5; // 10 hits allowed in above window

    private List<Long> allHitsLogs = new ArrayList<>();

    public boolean hit(String threadID){

        System.out.println("Thread ID : " + threadID + " hitting ... ");

        // get current time
        Long currentTime = System.currentTimeMillis();
        boolean success = hitAttemptWithDetails(currentTime, allHitsLogs);

        if (success){
            System.out.println("Thread ID : " + threadID + " Success ! ");
        }else {
            System.out.println("Thread ID : " + threadID + " Fail ! ");
        }
        return success;
    }

    public boolean hitAttemptWithDetails(Long time, List<Long> allHitsLogs){

        // check count of all previous hits in that window defined
        // using binary search
        // success or false : do action

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

public class RateLimiterMain {

    public static void main(String[] args) throws InterruptedException {

        BasicStructure basicStructure = new BasicStructure();

        // a simple loop to create threads, otherwise Executor service and countdown latch can also be used.
        for (int i = 0 ; i < 100; i++){
            // just delaying new threads creations
            if (i % 15 == 0){
                System.out.println("Sleeping!");
                Thread.sleep(1000);
            }
            new Thread(new Thread_Hit(basicStructure)).start();
        }
    }
}
