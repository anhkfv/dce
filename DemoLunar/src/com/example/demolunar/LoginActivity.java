package com.example.demolunar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity{

	EditText userName,password,passwordConfim;
	Button login;
	TextView register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userName = (EditText)findViewById(R.id.username_edtext);
		password = (EditText)findViewById(R.id.passwd_edtext);
		register = (TextView)findViewById(R.id.register_tv);
		login =(Button)findViewById(R.id.login_button);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this,SyncDataServer.class);
				startActivity(it);
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(it);
			}
		});
	}

}
