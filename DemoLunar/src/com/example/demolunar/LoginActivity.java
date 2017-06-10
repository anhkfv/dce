package com.example.demolunar;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import com.example.server.InfoResult;
import com.example.server.SendData;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import processcommon.CheckCommon;
import processcommon.TransparentProgressDialog;

public class LoginActivity extends Activity {

	EditText userName, password, passwordConfim;
	Button login;
	TextView register;
	// private ProgressDialog pd;
	private TransparentProgressDialog pd;
	private final Lock lock = new ReentrantLock();
	private  AlertDialog  ad;
	private static String URL_LOGIN = CheckCommon.localhost + "/Note/Demo/login/check";

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
		SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
		String linkTemp = link.getString("link", "");
		if (!linkTemp.isEmpty()) {
			URL_LOGIN = linkTemp + "/Note/Demo/login/check";
		}
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CheckCommon.checkNetwork(LoginActivity.this)) {
					// pd = ProgressDialog.show(LoginActivity.this, "",
					// "Register", true);
					pd = new TransparentProgressDialog(LoginActivity.this, R.drawable.spinner);
					pd.show();
					SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
					String linkTemp = link.getString("link", "");
					if (!linkTemp.isEmpty()) {
						URL_LOGIN = linkTemp + "/Note/Demo/login/check";
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
							String data = SendData.sendJson(URL_LOGIN, jsonObject.toString());
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
									Toast.makeText(LoginActivity.this, result.getNameNotice(), Toast.LENGTH_LONG)
											.show();
									SharedPreferences pre = getSharedPreferences("login", MODE_PRIVATE);
									SharedPreferences.Editor editor = pre.edit();
									editor.putString("id", userName.getText().toString());
									editor.putString("password", password.getText().toString());
									editor.commit();
									Intent it = new Intent(LoginActivity.this, SyncDataServer.class);
									it.putExtra("data", data);
									startActivity(it);
								} else {
									Toast.makeText(LoginActivity.this, result.getNameNotice(), Toast.LENGTH_LONG)
											.show();
								}

							}
						}
					}.execute();
				} else {
					CheckCommon.noNetwork(LoginActivity.this);
					Intent it = new Intent(LoginActivity.this, SyncDataServer.class);
					startActivity(it);
				}
			}
		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.links:
			final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
			alert.setTitle("Link Server");
			//ad = alert.show();
			LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
			// this is what I did to added the layout to the alert dialog
			View layout = inflater.inflate(R.layout.menu, null);
			alert.setView(layout);
			final EditText usernameInput = (EditText) layout.findViewById(R.id.editLinkServer);
			SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
			String linkTemp = link.getString("link", "");
			if (linkTemp.isEmpty()) {
				usernameInput.setText(CheckCommon.localhost);
			}else{
				usernameInput.setText(linkTemp);
			}
			final Button save= (Button) layout.findViewById(R.id.save);
			final Button cancel= (Button) layout.findViewById(R.id.cancel);
			save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SharedPreferences pre = getSharedPreferences("linkServer", MODE_PRIVATE);
					SharedPreferences.Editor editor = pre.edit();
					editor.putString("link", usernameInput.getText().toString());
					editor.commit();
					ad.cancel();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ad.cancel();
				}
			});
			ad = alert.show();
			//alert.show();
			break;
		case R.id.about:
			break;
		}
		return true;
	}

}
