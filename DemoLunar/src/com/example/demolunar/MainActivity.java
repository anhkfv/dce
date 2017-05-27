package com.example.demolunar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {
	String filename = "data_login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	protected void onResume() {

		super.onResume();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
					ex.getMessage();
				}
				SharedPreferences pre = getSharedPreferences(filename, MODE_PRIVATE);
				String user = pre.getString("user", "");
				if (user.equals("1")) {
					Intent itt = null;
					itt = new Intent(MainActivity.this, TabhostActivity.class);
					MainActivity.this.startActivity(itt);
					MainActivity.this.finish();

				} else {

					Intent it = null;
					it = new Intent(MainActivity.this, GuideActivity.class);
					it.putExtra("from", "welcome");
					
					MainActivity.this.startActivity(it);
					MainActivity.this.finish();
				}
			}
		}).start();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
