package com.example.kamil.androidrestclient2.restServiceInterface;

import com.example.kamil.androidrestclient2.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Kamil on 29/03/2019.
 */

public interface RestfulMessageService {
    @GET("messages/{messageId}")
    Call<Message> getMessage(@Path ("messageId") int id);

    @GET("messages")
    Call<List<Message>> getAllMessages();

    @Headers("Accept: application/json") //TODO I think I can delete that line
    @POST("messages")
    Call<Message> addMessage(@Body Message message);

    @PUT("messages/{messageId}")
    Call<Message> editMessage(@Path ("messageId") int id, @Body Message message);

    @DELETE("messages/{messageId}")
    Call<Message> deleteMessage(@Path ("messageId") int id);

    @DELETE("messages")
    Call<String> deleteAllMessages();

}
