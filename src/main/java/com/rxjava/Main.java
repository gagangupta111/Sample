package com.rxjava;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class Main {

    public static void main(String[] args) throws Exception{
        replaySubject();
    }

    public static void observable(){

        Observable<String> observable = Observable.just("Hello", "RxJava", "World", "R1", "R2");

        // Creating an Observer
        observable
                .map(s -> s.toUpperCase())
                .filter(s -> s.startsWith("R"))
                .subscribe(
                        System.out::println,  // onNext
                        throwable -> System.err.println("Error: " + throwable.getMessage()),  // onError
                        () -> System.out.println("Completed!")  // onComplete
                );

    }

    public static void publishSubject() throws Exception{

        final StringBuilder result1 = new StringBuilder();
        final StringBuilder result2 = new StringBuilder();

        int i = 1;
        PublishSubject<String> subject = PublishSubject.create();
        Thread.sleep(2000);
        System.out.println( " i + " + i++);
        subject.subscribe(
                value -> result1.append(value),  // onNext
                throwable -> System.err.println("First Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("First Subscriber Completed!")  // onComplete
        );
        subject.onNext("a");
        subject.onNext("b");
        subject.onNext("c");
        Thread.sleep(2000);
        System.out.println( " i + " + i++);
        subject.subscribe(
                value -> result2.append(value),  // onNext
                throwable -> System.err.println("Second Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("Second Subscriber Completed!")  // onComplete
        );
        subject.onNext("d");
        Thread.sleep(2000);
        System.out.println( " i + " + i++);
        subject.onCompleted();
        Thread.sleep(2000);
        System.out.println( " i + " + i++);

        //Output will be abcd
        System.out.println(result1);
        //Output will be d only
        //as subscribed after c item emitted.
        System.out.println(result2);
    }

    public static void behaviourSubject() throws Exception{

        final StringBuilder result1 = new StringBuilder();
        final StringBuilder result2 = new StringBuilder();
        final StringBuilder result3 = new StringBuilder();

        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(
                value -> result1.append(value),  // onNext
                throwable -> System.err.println("First Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("First Subscriber Completed!")  // onComplete
        );
        subject.onNext("a");
        subject.onNext("b");
        subject.onNext("c");
        subject.subscribe(
                value -> result2.append(value),  // onNext
                throwable -> System.err.println("Second Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("Second Subscriber Completed!")  // onComplete
        );
        subject.onNext("d");
        subject.subscribe(
                value -> result3.append(value),  // onNext
                throwable -> System.err.println("Third Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("Third Subscriber Completed!")  // onComplete
        );
        subject.onCompleted();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

    }


    public static void replaySubject() throws Exception{

        final StringBuilder result1 = new StringBuilder();
        final StringBuilder result2 = new StringBuilder();
        final StringBuilder result3 = new StringBuilder();

        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(
                value -> result1.append(value),  // onNext
                throwable -> System.err.println("First Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("First Subscriber Completed!")  // onComplete
        );
        subject.onNext("a");
        subject.onNext("b");
        subject.onNext("c");
        subject.subscribe(
                value -> result2.append(value),  // onNext
                throwable -> System.err.println("Second Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("Second Subscriber Completed!")  // onComplete
        );
        subject.onNext("d");
        subject.subscribe(
                value -> result3.append(value),  // onNext
                throwable -> System.err.println("Third Subscriber Error: " + throwable.getMessage()),  // onError
                () -> System.out.println("Third Subscriber Completed!")  // onComplete
        );
        subject.onCompleted();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

    }
}
