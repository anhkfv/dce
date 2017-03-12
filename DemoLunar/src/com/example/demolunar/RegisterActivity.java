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
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText userName, password, passwordConfim;
	Button register;
	private ProgressDialog pd;

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
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = ProgressDialog.show(RegisterActivity.this, "", "Register", true);
				String url = "http://192.168.1.78:8080/Note/Demo/Login/Login";
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
					Toast.makeText(RegisterActivity.this, result.getNameNotice(), Toast.LENGTH_LONG).show();
					Intent it = new Intent(RegisterActivity.this, SyncDataServer.class);
					startActivity(it);
					finish();
				}else{
					Toast.makeText(RegisterActivity.this, result.getNameNotice(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
