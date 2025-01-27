package com.rxjava;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

class ObserverClass implements Observer<String>{

    public static Integer commonInteger = new Integer(100);
    private final Integer id;

    public ObserverClass(Integer id) {
        this.id = id;
    }

    @Override
    public void onCompleted() {
        System.out.println("ID : " + id + " Completed! ");
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    @Override
    public void onNext(String string) {
        System.out.println("ID : " + id + " data  : " + string);
    }
}

public class Main {

    public static void main(String[] args) throws Exception{
        observableClass();
    }

    public static void observableClass(){

        Observable<String> observable = Observable.just("Hello", "RxJava", "World", "R1", "R2");

        Observer observer1 = new ObserverClass(101);
        Observer observer2 = new ObserverClass(102);

        // Creating an Observer
        observable
                .map(s -> s.toUpperCase())
                .filter(s -> s.startsWith("R"))
                .subscribe( observer1);

        observable.subscribe(observer2);


    }

    public static void observableNaive(){

        Observable<String> observable = Observable.just("Hello", "RxJava", "World", "R1", "R2");

        // Creating an Observer
        observable
                .map(s -> s.toUpperCase())
                .filter(s -> s.startsWith("R"))
                .subscribe( a ->
                                System.out.println(a),  // onNext
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

    public static void trampolineSchedulers() throws Exception{

        Observable.just("A", "AB", "ABC")
                .flatMap(v -> observableCreationWithDelay(v)
                        .doOnNext(s -> System.out.println("Processing Thread : "
                                + Thread.currentThread().getName()))
                        .subscribeOn(Schedulers.trampoline()))
                .subscribe(item -> System.out.println("Receiver Thread : "
                        + Thread.currentThread().getName()
                        + ", Item : " + item));

        Thread.sleep(1000);

    }

    public static void newThreadSchedulers() throws Exception{

        Observable.just("A", "AB", "ABC", "hgjh", "kjgjhgj")
                .flatMap(v -> observableCreationWithDelay(v)
                        .doOnNext(s -> System.out.println("Processing Thread : "
                                + Thread.currentThread().getName()))
                        .subscribeOn(Schedulers.newThread()))
                .subscribe(item -> System.out.println("Receiver Thread : "
                        + Thread.currentThread().getName()
                        + ", Item : " + item));

        Thread.sleep(1000);

    }

    public static void computationsSchedulers() throws Exception{

        Observable.just("A", "AB", "ABC", "kjhgh", "jhgfhjk")
                .flatMap(v -> observableCreationWithDelay(v)
                        .doOnNext(s -> System.out.println("Processing Thread : "
                                + Thread.currentThread().getName()))
                        .subscribeOn(Schedulers.computation()))
                .subscribe(item -> System.out.println("Receiver Thread : "
                        + Thread.currentThread().getName()
                        + ", Item : " + item));

        Thread.sleep(1000);

    }

    public static void ioSchedulers() throws Exception{

        Observable.just("A", "AB", "ABC", "kjhgh", "jhgfhjk")
                .flatMap(v -> observableCreationWithDelay(v)
                        .doOnNext(s -> System.out.println("Processing Thread : "
                                + Thread.currentThread().getName()))
                        .subscribeOn(Schedulers.io()))
                .subscribe(item -> System.out.println("Receiver Thread : "
                        + Thread.currentThread().getName()
                        + ", Item : " + item));

        Thread.sleep(1000);

    }

    public static void immediateSchedulers() throws Exception{

        Observable.just("A", "AB", "ABC", "kjhgh", "jhgfhjk")
                .flatMap(v -> observableCreationWithDelay(v)
                        .doOnNext(s -> System.out.println("Processing Thread : "
                                + Thread.currentThread().getName()))
                        .subscribeOn(Schedulers.immediate()))
                .subscribe(item -> System.out.println("Receiver Thread : "
                        + Thread.currentThread().getName()
                        + ", Item : " + item));

        Thread.sleep(1000);

    }

    public static Observable<String> observableCreationWithDelay(String string){

        try {
            Thread.sleep(2000);
        }catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }

        return Observable.just(string);
    }

}
