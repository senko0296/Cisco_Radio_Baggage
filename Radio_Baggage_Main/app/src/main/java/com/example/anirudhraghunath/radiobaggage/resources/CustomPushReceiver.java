package com.example.anirudhraghunath.radiobaggage.resources;

import android.content.Context;
import android.content.Intent;

import com.example.anirudhraghunath.radiobaggage.activities.CheckStatusActivity;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by anirudhraghunath on 28/01/16.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {

        Intent resultIntent = new Intent(context, CheckStatusActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtras(intent.getExtras());
        context.startActivity(resultIntent);
    }
}
