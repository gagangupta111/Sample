package com.kaprekar;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// this Program check the number of iterations it takes for any random number to transform into Kaprekar Number.
// also, multithreading have been added to boost number of computations concurrently
// CountDownLatch have been added to control the execution of threads

// read about Kaprekar number here : https://en.wikipedia.org/wiki/Kaprekar_number

class KaprekarNumberMultiThreaded implements Runnable{

    private CountDownLatch start;
    private CountDownLatch end;

    private Map<String, Integer> map;
    private int randomInt;

    public KaprekarNumberMultiThreaded(CountDownLatch start, CountDownLatch end, Map<String, Integer> map, int randomInt) {
        this.start = start;
        this.end = end;
        this.map = map;
        this.randomInt = randomInt;
    }

    @Override
    public void run() {

        try {
            start.await();
            int totalIterations = 0;
            String returnString = Main.descending(String.valueOf(randomInt));
            while (!returnString.equals(Main.kaprekarNumberDescendingOrder)){
                totalIterations++;
                returnString = String.valueOf(Integer.valueOf(returnString) - Integer.valueOf(Main.ascending(returnString)));
                returnString = Main.descending(returnString);
            }
            map.put(String.valueOf(randomInt), totalIterations);
            end.countDown();
        }catch (Exception e){}
    }
}

public class Main {

    public static final String kaprekarCombination = "6174";
    public static final String kaprekarNumberDescendingOrder = "7641";

    public static void main(String[] args){

        int totalThreads = 500;
        Map<String,Integer> sortedMap = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(totalThreads);

        for (int i = 0 ; i < totalThreads; i++){
            Random random = new Random();
            int randomInt = random.nextInt(9999-999) + 999;
            executorService.submit(new Thread(new KaprekarNumberMultiThreaded(start, end, sortedMap, randomInt)));
        }

        try {
            Thread.sleep(2000);
            start.countDown();
            System.out.println("Count down started!");
            Thread.sleep(2000);
            end.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("All Done!");
        sortedMap =  sortedMap.entrySet().
                stream().
                sorted(Map.Entry.comparingByValue()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        for (String string : sortedMap.keySet()){
            System.out.println(string + " : " + sortedMap.get(string));
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            executorService.shutdownNow();
        }

    }

    public static String ascending(String string){

        String finalString = "";
        int length = string.length();
        List<String> list = new ArrayList<>();
        for (int i = 0 ; i < length; i++){
            list.add(string.substring(i, i+1));
        }

        while (!list.isEmpty()){
            String min = list.get(0);
            int size = list.size();
            for (int i = 0 ; i<size; i++ ){
                if (list.get(i).compareTo(min) < 0){
                    min = list.get(i);
                }
            }
            finalString += min;
            list.remove(min);
        }

        return length < 4 ? "0" + finalString : finalString;
    }

    public static String descending(String string){

        String finalString = "";
        int length = string.length();
        List<String> list = new ArrayList<>();
        for (int i = 0 ; i < length; i++){
            list.add(string.substring(i, i+1));
        }

        while (!list.isEmpty()){
            String max = list.get(0);
            int size = list.size();
            for (int i = 0 ; i<size; i++ ){
                if (list.get(i).compareTo(max) > 0){
                    max = list.get(i);
                }
            }
            finalString += max;
            list.remove(max);
        }

        return length < 4 ? finalString + "0" : finalString;
    }

}
