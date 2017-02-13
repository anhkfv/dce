package com.example.alarm;

import com.example.demolunar.AlarmActivity;
import com.example.demolunar.R;
import com.example.weather.MainActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Umar Bhutta.
 */
public class RingtonePlayingService extends Service {
    private static final String TAG = RingtonePlayingService.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    boolean isRunning = false;
    private NotificationManager alarmNotificationManager;

    
    
    @SuppressLint("NewApi")
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "In the service, onStartCommand");

        //get the string from the intent to tell us whether on button or off button started this service
        String state = intent.getExtras().getString(AlarmActivity.KEY_ON_OFF);

        //this converts extra string from intent to startIds, values 0 or 1
        assert state != null;
        switch (state) {
            case "on":
                startId = 1;
                break;
            case "off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        //if-else statements to either start or stop ringtone
        if (!this.isRunning && startId == 1) {
            //if there is no music playing and the user pressed "alarm on", music should start playing
            Log.e(TAG, "No music playing, user requested it to start");

            //create an instance of mediaPlayer and start it
//            mediaPlayer = MediaPlayer.create(this, R.raw.eyeofthetiger);
//            mediaPlayer.start();
            mediaPlayer = new MediaPlayer();
          //  final AudioManager audioManager = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
			if (sharedPreferences.getString("rington", null) == null) {
			//	if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
					mediaPlayer = MediaPlayer.create(this, R.raw.eyeofthetiger);
					mediaPlayer.start();
			//	}
			} else {
				//if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
					Log.d("Path rington", sharedPreferences.getString("rington", null));
					mediaPlayer = MediaPlayer.create(this, Uri.parse(sharedPreferences.getString("rington", null)));
					mediaPlayer.start();
				//}
			}
            //notification
            //set up notification service
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//            //set up an intent that goes to MainActivity when user presses the notification
//            Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);
//            //put the intent in a pendingIntent that goes off when alarm goes off
//            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intentMainActivity, 0);
//
//            //make the notification parameters
//            Notification notification = new Notification.Builder(this)
//                    .setContentTitle("An alarm has been triggered!")
//                    .setContentText("Click here to turn off.")
//                    .setContentIntent(pendingIntentMainActivity)
//                    .setAutoCancel(true)
//                    .build();
//
//            //set up notification call command
//            notificationManager.notify(0, notification);

            this.isRunning = true;
            startId = 0;

        } else if (this.isRunning && startId == 0) {
            //if there is music playing and the user pressed "alarm off", music should stop playing
            Log.e(TAG, "Music is playing, user requested it to stop");
            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            startId = 0;

        } else if (!this.isRunning && startId == 0) {
            //if there is no music playing and the user pressed "alarm off", do nothing
            Log.e(TAG, "No music playing, user requested it to stop");

            this.isRunning = false;
            startId = 0;


        } else if (this.isRunning && startId == 1) {
            //if there is music playing and the user pressed "alarm on", do nothing
            Log.e(TAG, "Music is playing, user requested it to start");

            this.isRunning = true;
            startId = 1;

        } else {
            //edge cases, odd events
            Log.e(TAG, "This is the else clause");

        }


        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

	@Override
	public void onCreate() {
		super.onCreate();
		sendNotification("Thong bao");
		Intent dialogIntent = new Intent(this, AlarmActivity.class);
		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(dialogIntent);
	}
	@SuppressLint("NewApi")
	private void sendNotification(String msg) {
		// NotificationManager class to notify the user of events // that
		// happen. This is how you tell the user that something // has happened
		// in the background.
		alarmNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, AlarmActivity.class), 0);

		// set icon, title and message for notification
		Notification.Builder alamNotificationBuilder = new Notification.Builder(this)
				.setContentTitle("Alarm").setSmallIcon(R.drawable.ic_launcher)
				.setStyle(new Notification.BigTextStyle().bigText(msg)).setContentText(msg);

		alamNotificationBuilder.setContentIntent(contentIntent);
		alarmNotificationManager.notify(1, alamNotificationBuilder.build());

	}
}
