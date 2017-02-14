package com.example.demolunar;

import java.io.File;
import java.util.Calendar;

import com.example.alarm.AlarmReceiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

@SuppressLint("NewApi")
public class AlarmActivity extends Activity{
    public static final String KEY_ON_OFF = "KEY_ON_OFF";

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private TimePicker timePicker;
    private Button onButton, offButton;
    private TextView stateTextView,textPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AlarmActivity.this);

        //initialize alarmManager, and all views
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        onButton = (Button) findViewById(R.id.onButton);
        offButton = (Button) findViewById(R.id.offButton);
        stateTextView = (TextView) findViewById(R.id.stateTextView);
        textPath=(TextView)findViewById(R.id.textPath);
        if(sharedPreferences.getString("rington", null)!=null){
			textPath.setText(sharedPreferences.getString("rington", null));
		}
        if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
		}
        //initialize calender
        final Calendar calendar = Calendar.getInstance();

        final Intent receiverIntent = new Intent(this, AlarmReceiver.class);

        //onClickListeners for button
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get values from timePicker
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                //set values from timePicker to calender
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                //store hour and minute in String and handle edge cases
                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);
                if (hour > 12) {
                    hourString = String.valueOf(hour - 12);
                }
                if (minute < 12) {
                    minuteString = "0" + String.valueOf(minute);
                }

                //set stateTextView text
                setAlarmText("Alarm set to: " + hourString + ":" + minuteString);

                //add an extra to receiverIntent telling it that ON button triggered it. this receiverIntent will
                //be added in next line to pendingIntent, so it can be sent to AlarmReceiver at time selected by user via timePicker
                receiverIntent.putExtra(KEY_ON_OFF, "on");

                //create pending intent that delays the intent until specified calendar time
                pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set alarmManager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set stateTextView text
                setAlarmText("Alarm off");

                //cancel the alarm
                alarmManager.cancel(pendingIntent);

                //add an extra to receiverIntent telling it that off button triggered it
                receiverIntent.putExtra(KEY_ON_OFF, "off");

                //turn off the ringtone by sending a broadcast to AlarmReceiver immediately. It is AlarmReceiver's job to realize based on putExtra above that it is being
                //triggered via the off button, and that it has to communicate with the service to turn the alarm off
                sendBroadcast(receiverIntent);
            }
        });



    }
    public void addPath(View v) {
//		 Intent pickMedia = new Intent(Intent.ACTION_GET_CONTENT);
//	        pickMedia.setType("audio/*");
//	        startActivityForResult(pickMedia,1);
		File musicFolder;
		musicFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		Uri audio = Uri.parse("file://" + musicFolder + "/");
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setDataAndType(audio, "audio/*");
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == RESULT_OK) {
		        if (requestCode == 1) {
		            Uri audioFileUri = data.getData();

		            String MP3Path = audioFileUri.getPath();
		            textPath.setText(MP3Path);
		            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AlarmActivity.this);
		            Editor editor = prefs.edit();
		            editor.putString("rington", MP3Path);
		            editor.commit();

		        }
		    }
		super.onActivityResult(requestCode, resultCode, data);
	}
    private void setAlarmText(String text) {
        stateTextView.setText(text);
    }
}
