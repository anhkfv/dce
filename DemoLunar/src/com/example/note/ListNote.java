package com.example.note;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.example.date.DayMonthYear;
import com.example.demolunar.R;
import com.example.note.adapter.ListNoteAdapter;
import com.example.sqlite_note.DataNoteHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class ListNote extends Activity implements Serializable {
	private ListView lv;
	private ArrayList<Note> mang;
	private int tmp;
	private ListNoteAdapter adapter;
	private ImageButton bt, btPrint;
	private DayMonthYear dmyt;
	private DataNoteHandler dbb = new DataNoteHandler(this);
	static final String tempPath = "print.xlsx";
	static final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/printNote.xlsx";
	static final SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_note);
		lv = (ListView) findViewById(R.id.listView);
		bt = (ImageButton) findViewById(R.id.buttonAdd);
		btPrint = (ImageButton) findViewById(R.id.buttonPrint);
		dmyt = (DayMonthYear) getIntent().getSerializableExtra("detailNoteList");

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				tmp = position;
				AlertDialog.Builder b = new AlertDialog.Builder(ListNote.this);

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
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Note nt = new Note(mang.get(position).nameNote, mang.get(position).detailNote,
						mang.get(position).imageNote, mang.get(position).date, mang.get(position).id);
				Intent it = null;
				it = new Intent(ListNote.this, NoteActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("detailNote", nt);
				it.putExtras(mBundle);
				ListNote.this.startActivity(it);

			}
		});
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = null;
				it = new Intent(ListNote.this, NoteActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("detailNoteDay", dmyt);
				it.putExtras(bundle);
				ListNote.this.startActivity(it);
			}
		});

		btPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if(mang.size()!=0){
				// PrintNote print=new PrintNote(ListNote.this,tempPath);
				// List<NoteDto>dtos=new ArrayList<>();
				// for(Note note :mang){
				// NoteDto dto=new NoteDto();
				// dtos.add(dto.convert(note));
				// }
				// print.printNote(savePath, dtos);
				// }
				// else{
				//
				// }
			}
		});
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	private void displayData() {
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DATE, dmyt.getDay());
//		cal.set(Calendar.MONTH, dmyt.getMonth() - 1);
//		cal.set(Calendar.YEAR, dmyt.getYear());
//		Date dat = cal.getTime();
//		String temp = ft.format(dat);
		mang = new ArrayList<Note>();
		Cursor note = dbb.getData("SELECT*FROM Note");
		while (note.moveToNext()) {
			// if (temp.equals(note.getString(4))) {
			mang.add(new Note(note.getString(1), note.getString(2), note.getString(3), note.getString(4),
					note.getInt(0)));
			// }
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

}
