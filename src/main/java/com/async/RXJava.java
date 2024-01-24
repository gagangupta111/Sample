/*
package com.async;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import rx.Single;

import java.util.AbstractMap;
import java.util.concurrent.CountDownLatch;

public class RXJava {

    private static Single<Integer> service1(WebClient webClient) {
        return webClient.getAbs("http://httpstat.us/200?sleep=5000")
                .rxSend()
                .doOnSuccess(response -> System.out.println("[" + Thread.currentThread().getName() + "] service 1: response received"))
                .map(HttpResponse::statusCode);
    }

    private static Single<Integer> service2(WebClient webClient) {
        return webClient.getAbs("http://httpstat.us/200?sleep=2000")
                .rxSend()
                .doOnSuccess(response -> System.out.println("[" + Thread.currentThread().getName() + "] service 2 response received"))
                .map(HttpResponse::statusCode);
    }

    public static void main(String[] args){

        System.out.println("Hello!");

        Vertx vertx = Vertx.vertx();
        WebClient webClient = WebClient.create(vertx);

// single sources. Lazy evaluation, no invocation at this point
        Single<Integer> service1Code = service1(webClient);
        Single<Integer> service2Code = service2(webClient);

// combine results together and create tuple
        Single<AbstractMap.SimpleEntry<Integer, Integer>> tupleSource =
                service1Code.zipWith(service2Code, (s1, s2) -> new AbstractMap.SimpleEntry<>(s1, s2));

// subscribe and invoke services
        tupleSource
                .doFinally(CountDownLatch::countDown)
                .subscribe(Services::printResult);

    }

}
*/
