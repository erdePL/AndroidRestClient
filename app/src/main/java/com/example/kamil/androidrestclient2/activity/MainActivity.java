package com.example.kamil.androidrestclient2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.kamil.androidrestclient2.R;
import com.example.kamil.androidrestclient2.model.Message;
import com.example.kamil.androidrestclient2.restServiceInterface.RestfulMessageService;
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
    public static final String BASE_URL = "http://4adbb108.ngrok.io/RestfulMessageService/webapi/";
    private static Retrofit retrofit = null;
    private TextView textView;
    private Button button;
    private RestfulMessageService restfulMessageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeConnection();
//                getSingleMessageAndShow(6);
//                getAllMessagesAndShow();
//                addMessageAndShow("Added from android", "El Kocurro");
//                updateMessageAndShow(14,"UPDATED from android", "El Kocurro");
//                deleteMessageAndShow(15);
                deleteAllMessagesAndShowResponse();

            }
        });
    }
    private void initializeConnection() {
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
        restfulMessageService = retrofit.create(RestfulMessageService.class);
    }
    private void getSingleMessageAndShow(int id) {
        Call<Message> singleMessageCall = restfulMessageService.getMessage(id);
        singleMessageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                textView.setText(message.getMessageContent());
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
    private void getAllMessagesAndShow() {
        Call<List<Message>> allMessagesCallback = restfulMessageService.getAllMessages();
        allMessagesCallback.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i<messages.size(); i++){
                    sb.append(messages.get(i).getId() + ": ").append(messages.get(i).getMessageContent() + ", ")
                            .append(messages.get(i).getAuthor() + ", ").append(messages.get(i).getCreationDate() + "\n");
                }
                textView.setText(sb.toString());
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
    private void addMessageAndShow(String messageContent, String author) {
        Message messageToAdd = new Message(0, messageContent, author);
        Call<Message> addMessageCallback = restfulMessageService.addMessage(messageToAdd);
        addMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                textView.setText(sb.toString());
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
    private void updateMessageAndShow(int id, String messageContent, String author) {
        Message messageToUpdate = new Message(id, messageContent, author);
        Call<Message> editMessageCallback = restfulMessageService.editMessage(id , messageToUpdate);
        editMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                textView.setText(sb.toString());
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
    private void deleteMessageAndShow(int id) {
        Call<Message> deleteFirstMessageCallback = restfulMessageService.deleteMessage(id);
        deleteFirstMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                textView.setText(sb.toString());
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
    private void deleteAllMessagesAndShowResponse() {
        Call<String> deleteFirstMessageCallback = restfulMessageService.deleteAllMessages();
        deleteFirstMessageCallback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                textView.setText(message);
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
