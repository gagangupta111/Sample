package com.package2;

import com.google.gson.*;
import com.package1.Interface1;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Class1 implements Interface1 {

    public static void main(String[] args){

        System.out.println(longestDuration("empower integrated markets", "Reggae"));

    }

    public static String longestDuration(String organizer, String genre) {

        String maxID = "-1";
        int maxDuration = 0;

        int currentPage = 1;
        int total_pages = 0;

        String pageQuery = "&page=";
        String url = "";

        try {

            String[] organizerArray = organizer.split(" ");
            String urlOrganizedBy = Arrays.stream(organizerArray).map(String::valueOf).collect(Collectors.joining("%20"));

            url = "https://jsonmock.hackerrank.com/api/events?organized_by=";
            url += urlOrganizedBy;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonParser parser = new JsonParser();
            Object obj = parser.parse(response.body()); //the location of the file
            JsonObject jsonObject = (JsonObject) obj;
            total_pages = Integer.parseInt(jsonObject.get("total_pages").getAsString());

            while (currentPage <= total_pages){

                JsonArray jsonArray = getAllJsonObjects(url + pageQuery + currentPage);
                int size = jsonArray.size();
                for (int i = 0 ; i < size ; i++){
                    JsonObject data = jsonArray.get(i).getAsJsonObject();
                    int duration = Integer.parseInt(data.get("duration").getAsString());
                    String id = (data.get("id").getAsString());
                    JsonArray genres = data.get("genres").getAsJsonArray();

                    String[] arrName = new Gson().fromJson(genres, String[].class);
                    boolean isGenrePresent = Arrays.stream(arrName).filter(a -> a.equals(genre)).findAny().isPresent();

                    if (isGenrePresent){
                        if (duration > maxDuration){
                            maxDuration = duration;
                            maxID = id;
                        }else if (duration == maxDuration){
                            if (id.compareTo(maxID) < 0){
                                maxID = id;
                            }
                        }
                    }
                }
                currentPage++;
            }
            }catch (Exception e){}
        return maxID;
    }

    public static JsonArray getAllJsonObjects(String url) {

        JsonArray jsonObjects = new JsonArray();

        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(response.body()); //the location of the file
            JsonObject jsonObject = (JsonObject) obj;
            jsonObjects = (jsonObject.get("data").getAsJsonArray());
        }catch (Exception e){}
        return jsonObjects;
    }

}
