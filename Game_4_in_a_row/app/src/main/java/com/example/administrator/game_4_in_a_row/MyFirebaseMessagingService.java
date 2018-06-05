package com.example.administrator.game_4_in_a_row;


import android.util.Log;

import com.example.lib.FCMWrapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FBMessagingService";


    @Override
    public void onMessageReceived(RemoteMessage message) {

        Log.d(TAG,"onMessageReceived:" + message.getData().toString());

        FCMWrapper.ExecuteCommand(
                getApplicationContext(),
                message.getData().toString());
    }

}
