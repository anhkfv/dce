package com.example.demolunar;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.note.ListNote;
import com.example.note.Note;
import com.example.note.adapter.ListNoteAdapter;
import com.example.server.DataResult;
import com.example.server.SendData;
import com.example.sqlite_note.DataNoteHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import processcommon.CheckCommon;
import processcommon.TransparentProgressDialog;

public class SyncDataServer extends Activity {
	private ImageButton getInfo, sendData;
	ListView lv;
	private ArrayList<Note> mang;
	private TransparentProgressDialog pd;
	private DataNoteHandler hander = new DataNoteHandler(this);
	private List<Note> notes = new ArrayList<>();
	private String id, password;
	private static String URL_SEND = CheckCommon.localhost + "/Note/Demo/login/insert";
	private static String URL_GET = CheckCommon.localhost + "/Note/Demo/login/get";
	private static String FIND_ALL = "SELECT*FROM Note";
	static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	// private static String FIND_WITH_ID = "SELECT*FROM Note WHERE id = ";
	private ListNoteAdapter adapter;
	private final Lock lock = new ReentrantLock();
	private Cursor note;
	private int tmp;
	private DataNoteHandler dbb = new DataNoteHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aync_data);
		lv = (ListView) findViewById(R.id.listViewAync);
		sendData = (ImageButton) findViewById(R.id.sendData);
		getInfo = (ImageButton) findViewById(R.id.getInfo);
		displayData();
		SharedPreferences pre = getSharedPreferences("login", MODE_PRIVATE);
		SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
		String linkTemp = link.getString("link", "");
		getLink();
		Toast.makeText(SyncDataServer.this, linkTemp, Toast.LENGTH_LONG).show();
		id = pre.getString("id", "");
		password = pre.getString("password", "");
		note = hander.getData(FIND_ALL);
		while (note.moveToNext()) {
			notes.add(new Note(note.getString(1), note.getString(2), note.getString(3), note.getString(4),
					note.getLong(0)));
		}
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				tmp = position;
				AlertDialog.Builder b = new AlertDialog.Builder(SyncDataServer.this);

				b.setMessage("Bạn có muốn xóa ?");
				b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dbb.deleteContact(mang.get(tmp));
						displayData();
					}
				});
				b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

					@Override

					public void onClick(DialogInterface dialog, int which)

					{
						dialog.cancel();
					}

				});

				b.create().show();

				return true;
			}
		});
		sendData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CheckCommon.checkNetwork(SyncDataServer.this)) {
					pd = new TransparentProgressDialog(SyncDataServer.this, R.drawable.spinner);
					pd.show();
					new AsyncTask<Void, Void, String>() {

						@Override
						protected String doInBackground(Void... params) {
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
								pd.hide();
								pd.dismiss();
							} catch (Exception ex) {

							}

							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							super.onPostExecute(data);
							pd.hide();
							pd.dismiss();
							if (!data.equals("")) {
								DataResult result = new DataResult();
								result = new Gson().fromJson(data, DataResult.class);
								Toast.makeText(SyncDataServer.this, result.getDetail(), Toast.LENGTH_LONG).show();
								sendData.setEnabled(true);
							}
							onResume();
						}

					}.execute();

				} else {
					CheckCommon.noNetwork(SyncDataServer.this);
				}
			}
		});

		getInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CheckCommon.checkNetwork(SyncDataServer.this)) {
					pd = new TransparentProgressDialog(SyncDataServer.this, R.drawable.spinner);
					pd.show();
					new AsyncTask<Void, Void, String>() {

						@Override
						protected String doInBackground(Void... params) {
							JSONObject jsonObject = new JSONObject();
							String data = "";
							try {
								jsonObject.put("id", id.toString());
								jsonObject.put("password", password.toString());
								lock.lock();
								data = SendData.sendJson(URL_GET, jsonObject.toString());
								lock.unlock();

							} catch (Exception ex) {
								System.out.print(ex.getMessage());
							}
							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							super.onPostExecute(data);
							// onResume();
							pd.hide();
							pd.dismiss();
							if (!data.equals("")) {
								Type founderListType = new TypeToken<ArrayList<Note>>() {
								}.getType();
								List<Note> result = new Gson().fromJson(data, founderListType);
								Toast.makeText(SyncDataServer.this, "kt: " + result.size(), Toast.LENGTH_LONG).show();
								try {
									for (Note note : result) {
										Note cursor = hander.getNote(String.valueOf(note.id));
										if (cursor != null) {
											hander.updateContact(note.nameNote, note.detailNote, note.imageNote,
													df.parse(note.date), note.id);
										} else {
											hander.inserta(note.id, note.nameNote, note.detailNote, note.imageNote,
													df.parse(note.date));
										}
									}
								} catch (Exception ex) {

								}
								onResume();
								getInfo.setEnabled(true);
							}
						}

					}.execute();

				} else {
					CheckCommon.noNetwork(SyncDataServer.this);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	private void displayData() {
		mang = new ArrayList<Note>();
		Cursor note = hander.getData("SELECT*FROM Note");
		while (note.moveToNext()) {
			mang.add(new Note(note.getString(1), note.getString(2), note.getString(3), note.getString(4),
					note.getLong(0)));
		}
		Collections.sort(mang, new Comparator<Note>() {

			@Override
			public int compare(Note lhs, Note rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}
		});
		adapter = new ListNoteAdapter(getApplicationContext(), R.layout.custom_activity_note, mang);
		lv.setAdapter(adapter);
	}
	private void getLink(){
		SharedPreferences pre = getSharedPreferences("login", MODE_PRIVATE);
		SharedPreferences link = getSharedPreferences("linkServer", MODE_PRIVATE);
		String linkTemp = link.getString("link", "");
		if (!linkTemp.isEmpty()) {
			URL_SEND = linkTemp + "/Note/Demo/login/insert";
			URL_GET = linkTemp + "/Note/Demo/login/get";
		}
	}
}
