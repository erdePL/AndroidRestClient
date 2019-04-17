package com.example.kamil.androidrestclient2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.kamil.androidrestclient2.R;
import com.example.kamil.androidrestclient2.callignWebService.mainCallingClass.RestCallerIntentService;
import com.example.kamil.androidrestclient2.callignWebService.dataModel.Message;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    public static final String INTENT_FILTER_REST_CALLER = "intent-filter-rest-caller";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(INTENT_FILTER_REST_CALLER));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restCallerIntent = createGetAllMessagesIntent();
//                Intent restCallerIntent = createGetMessageIntent(1);
//                Intent restCallerIntent = createRemoveMessageIntent(1);
//                Intent restCallerIntent = createRemoveAllMessagesIntent();
//                Intent restCallerIntent = createAddMessageIntent("Miau from ANDROID !", "El Kocurro");
//                Intent restCallerIntent = createUpdateMessageIntent(4, "Miau EDITED from ANDROID !", "El Kocurro");
                startService(restCallerIntent);
            }
        });
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(RestCallerIntentService.PARAM_OUT_REST_RESPONSE);
            textView.setText(message);
        }
    };
    private Intent createGetAllMessagesIntent(){
        return new Intent(MainActivity.this, RestCallerIntentService.class)
                .putExtra(RestCallerIntentService.PARAM_IN_METHOD_ID, RestCallerIntentService.METHOD_ID_GET_ALL_MESSAGES);
    }
    private Intent createGetMessageIntent(long id){
        return new Intent(MainActivity.this, RestCallerIntentService.class)
                .putExtra(RestCallerIntentService.PARAM_IN_METHOD_ID, RestCallerIntentService.METHOD_ID_GET_MESSAGE)
                .putExtra(RestCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, id);
    }
    private Intent createRemoveMessageIntent(long id){
        return new Intent(MainActivity.this, RestCallerIntentService.class)
                .putExtra(RestCallerIntentService.PARAM_IN_METHOD_ID, RestCallerIntentService.METHOD_ID_REMOVE_MESSAGE)
                .putExtra(RestCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, id);
    }
    private Intent createRemoveAllMessagesIntent(){
        return new Intent(MainActivity.this, RestCallerIntentService.class)
                .putExtra(RestCallerIntentService.PARAM_IN_METHOD_ID, RestCallerIntentService.METHOD_ID_REMOVE_ALL_MESSAGES);
    }
    private Intent createAddMessageIntent(String messageContent, String author) {
        return new Intent(MainActivity.this, RestCallerIntentService.class)
                .putExtra(RestCallerIntentService.PARAM_IN_METHOD_ID, RestCallerIntentService.METHOD_ID_ADD_MESSAGE)
                .putExtra(RestCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, new Message(0, messageContent, author));
    }
    private Intent createUpdateMessageIntent(long id, String messageContent, String author) {
        return new Intent(MainActivity.this, RestCallerIntentService.class)
                .putExtra(RestCallerIntentService.PARAM_IN_METHOD_ID, RestCallerIntentService.METHOD_ID_UPDATE_MESSAGE)
                .putExtra(RestCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, new Message(id, messageContent, author));
    }
}
