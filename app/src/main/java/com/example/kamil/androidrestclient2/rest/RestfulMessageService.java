package com.example.kamil.androidrestclient2.rest;

import com.example.kamil.androidrestclient2.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Kamil on 29/03/2019.
 */

public interface RestfulMessageService {
    @GET("messages/1")
    Call<Message> getFirstMessage();
    @GET("messages")
    Call<List<Message>> getMessages();
    @DELETE("messages/1")
    Call<Message> deleteFirstMessage();
    @DELETE("messages")
    Call<String> deleteAllMessages();
    @Headers("Accept: application/json")
    @POST("messages")
    Call<Message> addMessage(@Body Message message);
    @PUT("messages/1")
    Call<Message> editMessage(@Body Message message);

}
