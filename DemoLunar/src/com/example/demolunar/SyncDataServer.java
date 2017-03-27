package com.example.demolunar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.note.Note;
import com.example.server.SendData;
import com.example.sqlite_note.DataNoteHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SyncDataServer extends Activity {
	private ImageButton getInfo, sendData;
	private ProgressDialog pd;
	private TextView tv;
	private DataNoteHandler hander = new DataNoteHandler(this);
	private List<Note> notes = new ArrayList<>();
	private String id, password;
	private static String URL_SEND = "http://192.168.1.78:8080/Note/Demo/login/insert";
	private static String URL_GET = "http://192.168.1.78:8080/Note/Demo/login/get";
	private final Lock lock = new ReentrantLock();
	private Cursor note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aync_data);
		tv = (TextView) findViewById(R.id.textData);
		sendData = (ImageButton) findViewById(R.id.sendData);
		getInfo = (ImageButton) findViewById(R.id.getInfo);

		SharedPreferences pre = getSharedPreferences("login", MODE_PRIVATE);
		id = pre.getString("id", "");
		password = pre.getString("password", "");
		note = hander.GetData("SELECT*FROM Note");
		while (note.moveToNext()) {
			notes.add(new Note(note.getString(1), note.getString(2), note.getString(3), note.getString(4),
					note.getInt(0)));
		}
		sendData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = ProgressDialog.show(SyncDataServer.this, "", "Infomation", true);
				pd.show();
				JSONObject jsonObject = new JSONObject();

				String data = "";
				try {
					jsonObject.put("id", id.toString());
					jsonObject.put("password", password.toString());
					String json = new Gson().toJson(notes);
					JSONArray jsonArray = new JSONArray(json);
					jsonObject.accumulate("notes", jsonArray);
					lock.lock();
					data = SendData.sendJson(URL_SEND, jsonObject.toString());
					lock.unlock();
				} catch (Exception ex) {

				}

				pd.hide();
				pd.dismiss();
				// DataResult result = new DataResult();
				// result = new Gson().fromJson(data, DataResult.class);
				// tv.setText(result.getDetail());
			}
		});

		getInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pd = ProgressDialog.show(SyncDataServer.this, "", "Infomation", true);
				pd.show();
				JSONObject jsonObject = new JSONObject();
				String data = "";
				try {
					jsonObject.put("id", id.toString());
					jsonObject.put("password", password.toString());
					lock.lock();
					data = SendData.sendJson(URL_GET, jsonObject.toString());
					lock.unlock();
				} catch (Exception ex) {

				}

				pd.hide();
				pd.dismiss();
				Note result = new Note();
				Type founderListType = new TypeToken<ArrayList<Note>>(){}.getType();
				List<Note> re = new Gson().fromJson(data, founderListType);
				Toast.makeText(SyncDataServer.this, "kt: "+re.size(), Toast.LENGTH_LONG).show();

			}
		});
	}
}
