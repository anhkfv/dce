package com.example.demolunar;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import com.example.server.InfoResult;
import com.example.server.SendData;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText userName, password, passwordConfim;
	Button login;
	TextView register;
	private ProgressDialog pd;
	private final Lock lock = new ReentrantLock();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userName = (EditText) findViewById(R.id.username_edtext);
		password = (EditText) findViewById(R.id.passwd_edtext);
		register = (TextView) findViewById(R.id.register_tv);
		login = (Button) findViewById(R.id.login_button);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = ProgressDialog.show(LoginActivity.this, "", "Login", true);
				pd.show();
				String url = "http://192.168.1.78:8080/Note/Demo/login/check";
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("id", userName.getText().toString());
					jsonObject.put("password", password.getText().toString());
				} catch (Exception ex) {

				}
				String data = SendData.sendJson(url, jsonObject.toString());
				pd.hide();
				pd.dismiss();
				InfoResult result = new InfoResult();
				lock.lock();
				result = new Gson().fromJson(data, InfoResult.class);
				lock.unlock();
				if (result.isStatus()) {
					Toast.makeText(LoginActivity.this, result.getNameNotice(), Toast.LENGTH_LONG).show();
					SharedPreferences pre = getSharedPreferences("login", MODE_PRIVATE);
					SharedPreferences.Editor editor = pre.edit();
					editor.putString("id", userName.getText().toString());
					editor.putString("password", password.getText().toString());
					editor.commit();
					 Intent it = new Intent(LoginActivity.this,
					 SyncDataServer.class);
					 it.putExtra("data", data);
					 startActivity(it);
				} else {
					Toast.makeText(LoginActivity.this, result.getNameNotice(), Toast.LENGTH_LONG).show();
				}
			}
		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent it = new Intent(LoginActivity.this,
				 RegisterActivity.class);
				 startActivity(it);
			}
		});
	}

}
