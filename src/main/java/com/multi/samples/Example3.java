package com.multi.samples;

import java.util.concurrent.*;
import java.util.function.Predicate;

class PaymentObject{

    private String paymentID;
    private String paymentName;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
}

class Rule{
    private String name;
    private Predicate<PaymentObject> predicate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Predicate<PaymentObject> getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate<PaymentObject> predicate) {
        this.predicate = predicate;
    }
}

class Download implements Callable{

    private Integer integer;
    private CountDownLatch start;
    private CountDownLatch end;

    public Download(Integer integer, CountDownLatch start, CountDownLatch end) {
        this.integer = integer;
        this.start = start;
        this.end = end;
    }

    @Override
    public String call() throws Exception{

        System.out.println("Awaiting : " + Thread.currentThread().getId());
        start.await();
        System.out.println("Running : " + Thread.currentThread().getId());
        Thread.sleep(3000 + (integer *10));
        String finalResult = "";
        for (int i = integer; i < integer+20; i++){
            finalResult += i + "_";
        }
        Thread.sleep(1000);
        System.out.println("CountDown : " + Thread.currentThread().getId());
        end.countDown();
        return finalResult;

    }
}

public class Example3 {

    public static void fileDownload() throws Exception {

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> future1 = executorService.submit(new Download(100, start, end));
        Future<String> future2 = executorService.submit(new Download(200, start, end));
        Future<String> future3 = executorService.submit(new Download(300, start, end));

        CompletableFuture<Boolean> one = CompletableFuture.supplyAsync(() -> {
            while (!future1.isDone() || !future2.isDone() || !future3.isDone()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String finalResult = "";
                if (!future1.isDone()){
                    finalResult += ("future1 not done yet : ");
                }
                if (!future2.isDone()){
                    finalResult += ("future2 not done yet : ");
                }
                if (!future3.isDone()){
                    finalResult += ("future3 not done yet : ");
                }
                System.out.println(finalResult);
            }
            return true;
        });

        start.countDown();
        end.await();
        Boolean done = one.get();
        one.complete(true);

        System.out.println(future1.get() + "__" + future2.get() + "__" + future3.get());

    }

    public static void ruleEngine(){

        Rule rule = new Rule();
        rule.setName("NullOrEmpty");
        rule.setPredicate((paymentObject) -> {
            if (paymentObject.getPaymentName() == null || "".equals(paymentObject.getPaymentName())){
                return false;
            }else return true;
        });
        PaymentObject paymentObject = new PaymentObject();

        boolean result = rule.getPredicate().test(paymentObject);
        System.out.println(result);

    }

    public static void main(String[] args) throws Exception {

        fileDownload();

    }
}
