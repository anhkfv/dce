package com.example.demolunar;

import org.json.JSONObject;

import com.example.server.DataResult;
import com.example.server.SendData;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SyncDataServer extends Activity {
	ImageButton getInfo;
	ProgressDialog pd;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aync_data);
		tv = (TextView) findViewById(R.id.textData);
		getInfo = (ImageButton) findViewById(R.id.getInfo);
		getInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = ProgressDialog.show(SyncDataServer.this, "", "Infomation", true);
				String url = "http://192.168.1.78:8080/Note/Demo/Login/get";
				JSONObject jsonObject = new JSONObject();
				String data = SendData.sendJson(url, jsonObject.toString());
				pd.hide();
				pd.dismiss();
				DataResult result = new DataResult();
				result = new Gson().fromJson(data, DataResult.class);
				tv.setText(result.getDetail());
			}
		});
	}
}
