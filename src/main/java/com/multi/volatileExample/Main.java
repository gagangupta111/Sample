package com.multi.volatileExample;

public class Main {
    private static volatile int MY_INT = 0;

    public static void main(String[] args)
    {
        new ChangeListener().start();
        new ChangeMaker().start();

        new ChangeListener().start();
        new ChangeMaker().start();

        new ChangeListener().start();
        new ChangeMaker().start();

        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {

        @Override public void run()
        {
            Long id = Thread.currentThread().getId();
            int local_value = MY_INT;
            while (local_value < 5) {
                if (local_value != MY_INT) {
                    System.out.println(
                            id + " Got Change for MY_INT : " + MY_INT);
                    local_value = MY_INT;
                }else {
                    System.out.println(id + " No change");
                }
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ChangeMaker extends Thread {

        @Override public void run()
        {
            Long id = Thread.currentThread().getId();
            int local_value = MY_INT;
            while (MY_INT < 5) {
                System.out.println(
                        id + " Incrementing MY_INT to : " +
                                (local_value + 1));
                MY_INT = ++local_value;
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}