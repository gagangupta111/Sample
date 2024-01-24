package com.async;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

class User {
    private String login;
    private long id;
    private String url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
interface UserService {

    @GET("/users")
    public Call<List<User>> getUsers(
            @Query("per_page") int per_page,
            @Query("page") int page);

    @GET("/users/{username}")
    public Call<User> getUser(@Path("username") String username);
}



public class RetroExample {


    public static void main(String[] args){

        retro();
        System.out.println("meanwhile!");

    }

    public static void retro(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        UserService service = retrofit.create(UserService.class);
        Call<User> callAsync = service.getUser("eugenp");

        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                System.out.println(user.getId());
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });

    }

}
