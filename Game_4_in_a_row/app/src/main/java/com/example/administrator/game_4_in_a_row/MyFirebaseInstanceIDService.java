package com.example.administrator.game_4_in_a_row;

import android.util.Log;
import android.widget.Toast;

import com.example.lib.SystemInit;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String TAG = "FBInstanceIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if (refreshedToken != null)
        {
            try
            {
                SystemInit.initLib(getApplicationContext(), refreshedToken);
            }
            catch (Exception e)
            {
                Log.e(TAG, "Exception error: " + e);
            }
        }
        else
            {
            Log.e(TAG, "Token error == null ");
            }
    }
}
