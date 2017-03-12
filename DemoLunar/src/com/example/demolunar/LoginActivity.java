package com.example.demolunar;

import org.json.JSONObject;

import com.example.server.InfoResult;
import com.example.server.SendData;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
				String url = "http://192.168.1.78:8080/Note/Demo/Login/check";
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
				result = new Gson().fromJson(data, InfoResult.class);
				if (result.isStatus()) {
					Toast.makeText(LoginActivity.this, result.getNameNotice(), Toast.LENGTH_LONG).show();
//					Intent it = new Intent(LoginActivity.this, SyncDataServer.class);
//					it.putExtra("data", data);
//					startActivity(it);
				} else {
					Toast.makeText(LoginActivity.this, result.getNameNotice(), Toast.LENGTH_LONG).show();
				}
			}
		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://192.168.1.78:8080/Note/Demo/Login/get";
				JSONObject jsonObject = new JSONObject();
				String data = SendData.sendJson(url, jsonObject.toString());
				Toast.makeText(LoginActivity.this, data, Toast.LENGTH_LONG).show();
//				Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
//				startActivity(it);
			}
		});
	}

}
