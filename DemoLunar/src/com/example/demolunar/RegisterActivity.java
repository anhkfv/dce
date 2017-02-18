package com.example.demolunar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	EditText userName, password, passwordConfim;
	Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		userName = (EditText) findViewById(R.id.username_edtext);
		password = (EditText) findViewById(R.id.passwd_edtext);
		passwordConfim = (EditText) findViewById(R.id.cnfpasswd_edtext);
		register = (Button) findViewById(R.id.register_button);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
               Intent it = new Intent(RegisterActivity.this,SyncDataServer.class);
               startActivity(it);
               finish();
			}
		});
	}
}
