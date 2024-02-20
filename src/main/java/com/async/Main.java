package com.async;

import org.asynchttpclient.*;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Main {

    public static String asyncCallUsingAsyncHttpClient(){

        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

        // Future<Response> whenResponse = asyncHttpClient.prepareGet("http://www.example.com/").execute();

        Request request = asyncHttpClient.prepareGet("http://www.example.com/").build();
        // whenResponse=asyncHttpClient.executeRequest(request);

        ListenableFuture<Response> whenResponse = asyncHttpClient.executeRequest(request);
        Runnable callback = () -> {
        try {
            Response response = null;
            System.out.println("Before response");
            try {
                response = whenResponse.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println(response.getContentType());
            System.out.println("After response");
        } catch (Exception e) {
            e.printStackTrace();
        }
        };

        whenResponse.addListener(callback, Executors.newFixedThreadPool(1));
        return "";
    }


    public static void asyncUsingRXJava(String... args) {

    }


    public static void main(String ...args) {

        asyncCallUsingAsyncHttpClient();
        System.out.println("meanwhile!");

    }

}
