package com.example.kamil.androidrestclient2.activity;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kamil.androidrestclient2.R;
import com.example.kamil.androidrestclient2.model.Message;
import com.example.kamil.androidrestclient2.rest.RestfulMessageService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://25c07069.ngrok.io/RestfulMessageService/webapi/messages/";
    private static Retrofit retrofit = null;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        connectAndGetApiData();
    }

    private void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RestfulMessageService restfulMessageService = retrofit.create(RestfulMessageService.class);
        Call<Message> call = restfulMessageService.getMessages();
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                textView.setText(message.getMessageContent());
                Log.d(TAG, "Seems like we have a message !" );

            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
