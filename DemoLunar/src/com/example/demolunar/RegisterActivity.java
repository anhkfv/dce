package com.example.demolunar;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import com.example.server.InfoResult;
import com.example.server.SendData;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import processcommon.CheckCommon;
import processcommon.TransparentProgressDialog;

public class RegisterActivity extends Activity {
	EditText userName, password, passwordConfim;
	Button register;
	// private ProgressDialog pd;
	private TransparentProgressDialog pd;
	private final Lock lock = new ReentrantLock();
	private String URL_REGISTER = CheckCommon.localhost + "/Note/Demo/login/login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		userName = (EditText) findViewById(R.id.username_edtext);
		password = (EditText) findViewById(R.id.passwd_edtext);
		passwordConfim = (EditText) findViewById(R.id.cnfpasswd_edtext);
		register = (Button) findViewById(R.id.register_button);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
		String linkTemp = link.getString("link", "");
		if (!linkTemp.isEmpty()) {
			URL_REGISTER = linkTemp + "/Note/Demo/login/login";
		}
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CheckCommon.checkNetwork(RegisterActivity.this)) {
					if (password.getText().toString().equals(passwordConfim.getText().toString())) {
						pd = new TransparentProgressDialog(RegisterActivity.this, R.drawable.spinner);
						pd.show();
						SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
						String linkTemp = link.getString("link", "");
						if (!linkTemp.isEmpty()) {
							URL_REGISTER = linkTemp + "/Note/Demo/login/login";
						}
						new AsyncTask<Void, Void, String>() {

							@Override
							protected String doInBackground(Void... params) {
								JSONObject jsonObject = new JSONObject();
								try {
									jsonObject.put("id", userName.getText().toString());
									jsonObject.put("password", password.getText().toString());
								} catch (Exception ex) {

								}
								lock.lock();
								String data = SendData.sendJson(URL_REGISTER, jsonObject.toString());
								lock.unlock();
								return data;
							}

							@Override
							protected void onPostExecute(String data) {
								super.onPostExecute(data);
								pd.hide();
								pd.dismiss();
								if (!data.equals("")) {
									InfoResult result = new InfoResult();
									result = new Gson().fromJson(data, InfoResult.class);
									if (result.isStatus()) {
										Toast.makeText(RegisterActivity.this, result.getNameNotice(), Toast.LENGTH_LONG)
												.show();
										SharedPreferences pre = getSharedPreferences("login", MODE_PRIVATE);
										SharedPreferences.Editor editor = pre.edit();
										editor.putString("id", userName.getText().toString());
										editor.putString("password", password.getText().toString());
										editor.commit();
										Intent it = new Intent(RegisterActivity.this, SyncDataServer.class);
										startActivity(it);
										finish();
									} else {
										Toast.makeText(RegisterActivity.this, result.getNameNotice(), Toast.LENGTH_LONG)
												.show();
									}
								}
							}

						}.execute();
					} else {
						Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp ", Toast.LENGTH_LONG).show();
					}
				} else {
					CheckCommon.noNetwork(RegisterActivity.this);
				}
			}
		});
	}
}
