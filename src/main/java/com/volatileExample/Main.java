package com.volatileExample;

// this example explains How volatile helps boost visibility to all threads
// we have taken an integer MY_INT which will be changed by change maker
// all listeners will keep on checking any changes made to MY_INT
// the changing and listening will be printed in console

public class Main {
    private static volatile int MY_INT = 0;
    public static final int my_int_comparison = 5;
    public static final int change_listener_sleep = 1000;
    public static final int change_maker_sleep = 2000;
    public static final int total_threads = 5;

    public static void main(String[] args)
    {
        for (int i = 0 ; i < total_threads; i++ ){
            new ChangeListener().start();
            new ChangeMaker().start();
        }
    }

    public static String currentTime(){
        return System.currentTimeMillis() + "";
    }

    static class ChangeListener extends Thread {

        @Override public void run()
        {
            Long id = Thread.currentThread().getId();
            int local_value = MY_INT;
            while (local_value < my_int_comparison) {
                if (local_value != MY_INT) {
                    System.out.println(
                            id + " Got Change for MY_INT : " + MY_INT + " : time : " + currentTime());
                    local_value = MY_INT;
                }else {
                    System.out.println(id + " No change" + " : time : " + currentTime());
                }
                try {
                    Thread.sleep(change_listener_sleep);
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
            while (MY_INT < my_int_comparison) {
                System.out.println(id + " Incrementing MY_INT to : " + (local_value + 1) + " : time : " + currentTime());
                MY_INT = ++local_value;
                try {
                    Thread.sleep(change_maker_sleep);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}