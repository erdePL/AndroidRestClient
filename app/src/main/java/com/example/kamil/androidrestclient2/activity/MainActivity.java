package com.example.kamil.androidrestclient2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.kamil.androidrestclient2.R;
import com.example.kamil.androidrestclient2.model.Message;
import com.example.kamil.androidrestclient2.rest.RestfulMessageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://25c07069.ngrok.io/RestfulMessageService/webapi/";
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
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        RestfulMessageService restfulMessageService = retrofit.create(RestfulMessageService.class);

        Message messageToUpdate = new Message(0,"Message EDITED with Retrofit", "Still the Cat");
        Call<Message> editMessageCallback = restfulMessageService.editMessage(messageToUpdate);
        editMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                String message = response.body().getMessageContent();
                textView.setText(message);
                Log.d(TAG, "Seems like we have added a messages !" );

            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

        /*hl ADD MESSAGE
        Message messageToAdd = new Message(0,"Message added with Retrofit", "Still the Cat");
        Call<Message> addMessageCallback = restfulMessageService.addMessage(messageToAdd);
        addMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                String message = response.body().getMessageContent();
                textView.setText(message);
                Log.d(TAG, "Seems like we have added a messages !" );

            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        */

        /* hl DELETE ALL MESSAGES
        Call<String> deleteFirstMessageCallback = restfulMessageService.deleteAllMessages();
        deleteFirstMessageCallback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                textView.setText(message);
                Log.d(TAG, "Seems like we have deleted all the messages !" );

            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        */

        /* hl DELETE FIRST MESSAGE
        Call<Message> deleteFirstMessageCallback = restfulMessageService.deleteFirstMessage();
        deleteFirstMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                textView.setText(message.getMessageContent());
                Log.d(TAG, "Seems like we have deleted a message !" );

            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        */

        /* hl ALL MESSAGES CALLBACK
        Call<List<Message>> allMessagesCallback = restfulMessageService.getMessages();
        allMessagesCallback.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                textView.setText(messages.get(0).getMessageContent());
                Log.d(TAG, "Seems like we have a message !" );

            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        */

        /* hl SIGNLE MESSAGE CALLBACK
        Call<Message> singleMessageCall = restfulMessageService.getFirstMessage();
        singleMessageCall.enqueue(new Callback<Message>() {
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
        */
    }
}
