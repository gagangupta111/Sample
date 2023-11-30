package org.example;

import java.util.ArrayList;
import java.util.List;

class ThreadExamplePrinting implements Runnable{

    private PrintDS printDS;
    private Integer start;
    private boolean even;

    public ThreadExamplePrinting(PrintDS printDS, Integer start, boolean even) {
        this.printDS = printDS;
        this.start = start;
        this.even = even;
    }

    @Override
    public void run() {

        for (int i = start; i < 20; i += 2){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (even) {
                System.out.println("even : " + even + " i : " + i);
                printDS.addEven(i);
            } else {
                System.out.println("even : " + even + " i : " + i);
                printDS.addOdd(i);
            }

        }

    }
}

class PrintThread implements Runnable{

    private PrintDS printDS;

    public PrintThread(PrintDS printDS) {
        this.printDS = printDS;
    }

    @Override
    public void run() {

        synchronized (PrintDS.class){
            try {
                while (true){
                    while (printDS.evenNumbers.isEmpty()){
                        System.out.println("Print waiting on evenNumbers ");
                        Thread.sleep(1000);
                        printDS.wait();
                    }
                    System.out.println("Printing even : " + printDS.evenNumbers.remove(0));
                }
            }catch (Exception exception){
                System.out.println("Exception : " + exception.getMessage());
            }
        }
    }
}

class PrintDS {

    public List<Integer> oddNumbers = new ArrayList<>();
    public List<Integer> evenNumbers = new ArrayList<>();

    public synchronized void addEven(Integer integer){
        evenNumbers.add(integer);
        System.out.println("evenNumbers : " + evenNumbers.size());
        this.notifyAll();
    }

    public synchronized void addOdd(Integer integer){
        oddNumbers.add(integer);
        System.out.println("oddNumbers : " + oddNumbers.size());
        this.notifyAll();
    }
}

public class AlternatePrintThreads {

    public static void main(String[] args) throws InterruptedException {

        PrintDS printDS = new PrintDS();

        System.out.println("Hello!");
        Thread thread1 = new Thread(new ThreadExamplePrinting(printDS, 1, false));

        Thread thread2 = new Thread(new ThreadExamplePrinting(printDS, 2, true));

        Thread thread3 = new Thread(new PrintThread(printDS));

        thread1.start();
        thread2.start();
        thread3.start();

    }

}
