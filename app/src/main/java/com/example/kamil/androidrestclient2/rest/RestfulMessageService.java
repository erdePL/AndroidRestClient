package com.example.kamil.androidrestclient2.rest;

import com.example.kamil.androidrestclient2.model.Message;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kamil on 29/03/2019.
 */

public interface RestfulMessageService {
    @GET("1")
    Call<Message> getMessages();
}
