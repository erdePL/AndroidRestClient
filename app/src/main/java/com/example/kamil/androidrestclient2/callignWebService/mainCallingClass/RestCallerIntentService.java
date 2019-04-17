package com.example.kamil.androidrestclient2.callignWebService.mainCallingClass;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.kamil.androidrestclient2.activity.MainActivity;
import com.example.kamil.androidrestclient2.callignWebService.dataModel.Message;
import com.example.kamil.androidrestclient2.callignWebService.restServiceInterface.RestfulMessageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestCallerIntentService extends IntentService {
    private static final String CLASS_TAG = RestCallerIntentService.class.getSimpleName();
    public static final String BASE_URL = "http://1bbb018e.ngrok.io/RestfulMessageService/webapi/";
    public final static String PARAM_OUT_REST_RESPONSE = "rest-response";
    public final static String PARAM_IN_METHOD_ID = "method-id";
    public final static String PARAM_IN_ADDITIONAL_CONTENT = "additional-content";
    public final static int METHOD_ID_GET_ALL_MESSAGES = 1;
    public final static int METHOD_ID_GET_MESSAGE = 2;
    public final static int METHOD_ID_ADD_MESSAGE = 3;
    public final static int METHOD_ID_UPDATE_MESSAGE = 4;
    public final static int METHOD_ID_REMOVE_MESSAGE = 5;
    public final static int METHOD_ID_REMOVE_ALL_MESSAGES = 6;
    private RestfulMessageService restfulMessageService;
    private Intent callingIntent;
    public RestCallerIntentService(){
        super("RestCallerIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        callingIntent = intent;
        initializeConnection();
        int serviceMethodId = callingIntent.getIntExtra(PARAM_IN_METHOD_ID, 0);
        callRightMethod(serviceMethodId);
    }

    private void initializeConnection() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        restfulMessageService = retrofit.create(RestfulMessageService.class);
    }
    private void callRightMethod(int serviceMethodId) {
        long messageId;
        Message message;
        switch(serviceMethodId){
            case METHOD_ID_GET_ALL_MESSAGES:
            default:
                getAllMessages();
                break;
            case METHOD_ID_GET_MESSAGE:
                messageId = callingIntent.getLongExtra(PARAM_IN_ADDITIONAL_CONTENT,0);
                getSingleMessage(messageId);
                break;
            case METHOD_ID_ADD_MESSAGE:
                message = callingIntent.getParcelableExtra(PARAM_IN_ADDITIONAL_CONTENT);
                addMessage(message);
                break;
            case METHOD_ID_UPDATE_MESSAGE:
                message = callingIntent.getParcelableExtra(PARAM_IN_ADDITIONAL_CONTENT);
                updateMessage(message);
                break;
            case METHOD_ID_REMOVE_MESSAGE:
                messageId = callingIntent.getLongExtra(PARAM_IN_ADDITIONAL_CONTENT,0);
                removeMessage(messageId);
                break;
            case METHOD_ID_REMOVE_ALL_MESSAGES:
                removeAllMessages();
                break;
        }
    }
    private void getAllMessages() {
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
                Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_REST_CALLER);
                sendResponseIntent.putExtra(PARAM_OUT_REST_RESPONSE, sb.toString() );
                LocalBroadcastManager.getInstance(RestCallerIntentService.this).sendBroadcast(sendResponseIntent);
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable throwable) {
                Log.e(CLASS_TAG, throwable.toString());
            }
        });
    }
    private void getSingleMessage(long id) {
        Call<Message> singleMessageCall = restfulMessageService.getMessage(id);
        singleMessageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_REST_CALLER);
                sendResponseIntent.putExtra(PARAM_OUT_REST_RESPONSE, sb.toString() );
                LocalBroadcastManager.getInstance(RestCallerIntentService.this).sendBroadcast(sendResponseIntent);
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(CLASS_TAG, throwable.toString());
            }
        });
    }
    private void addMessage(Message message) {
        Call<Message> addMessageCallback = restfulMessageService.addMessage(message);
        addMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_REST_CALLER);
                sendResponseIntent.putExtra(PARAM_OUT_REST_RESPONSE, sb.toString() );
                LocalBroadcastManager.getInstance(RestCallerIntentService.this).sendBroadcast(sendResponseIntent);
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(CLASS_TAG, throwable.toString());
            }
        });
    }
    private void updateMessage(Message message) {
        Call<Message> editMessageCallback = restfulMessageService.editMessage(message.getId(), message);
        editMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_REST_CALLER);
                sendResponseIntent.putExtra(PARAM_OUT_REST_RESPONSE, sb.toString() );
                LocalBroadcastManager.getInstance(RestCallerIntentService.this).sendBroadcast(sendResponseIntent);
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(CLASS_TAG, throwable.toString());
            }
        });
    }
    private void removeMessage(long id) {
        Call<Message> deleteFirstMessageCallback = restfulMessageService.deleteMessage(id);
        deleteFirstMessageCallback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                StringBuilder sb = new StringBuilder();
                sb.append(message.getId() + ": ").append(message.getMessageContent() + ", ")
                        .append(message.getAuthor() + ", ").append(message.getCreationDate() + "\n");
                Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_REST_CALLER);
                sendResponseIntent.putExtra(PARAM_OUT_REST_RESPONSE, sb.toString() );
                LocalBroadcastManager.getInstance(RestCallerIntentService.this).sendBroadcast(sendResponseIntent);
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Log.e(CLASS_TAG, throwable.toString());
            }
        });
    }
    private void removeAllMessages() {
        Call<String> deleteFirstMessageCallback = restfulMessageService.deleteAllMessages();
        deleteFirstMessageCallback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseMessage = response.body();
                Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_REST_CALLER);
                sendResponseIntent.putExtra(PARAM_OUT_REST_RESPONSE, responseMessage );
                LocalBroadcastManager.getInstance(RestCallerIntentService.this).sendBroadcast(sendResponseIntent);
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e(CLASS_TAG, throwable.toString());
            }
        });
    }
}
