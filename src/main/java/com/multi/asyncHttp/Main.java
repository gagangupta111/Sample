package com.multi.asyncHttp;

import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;

public class Main {

    public static void main(String ...args) {


        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
                .setConnectTimeout(500);
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

        Request request = new RequestBuilder(HttpConstants.Methods.GET)
                .setUrl("http://www.baeldung.com")
                .build();



    }
}