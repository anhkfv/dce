package com.example.alarm;

import com.example.demolunar.AlarmActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Umar Bhutta.
 */
public class AlarmReceiver extends BroadcastReceiver{
    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "We are in the receiver");

        //fetch extra Strings from the intent, whether it is coming from the
        //pendingIntent (on button) or the sendBroadcast (off button)
        String getString = intent.getExtras().getString(AlarmActivity.KEY_ON_OFF);

        //create an intent to start the RingtonePlayingService when this AlarmReceiver
        //onReceive method is called, whether by pendingIntent, or sendBroadcast
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        //add String from the intent that started AlarmReceiver that tells us whether on or off button
        //started AlarmReceiver, to the intent we're using to start RingtonePlayingService
        serviceIntent.putExtra(AlarmActivity.KEY_ON_OFF, getString);
        context.startService(serviceIntent);
    }
}
