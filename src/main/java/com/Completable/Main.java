package com.Completable;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws Exception{
        completable();
    }

    public static void completable() throws Exception{

        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> newFuture = future
                .thenApply(s -> s + " World");


        System.out.println(newFuture.get());

    }


    }
