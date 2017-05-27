package com.example.sqlite_note;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.note.Note;
import com.example.sqlite_truc.Contact;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DataNoteHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "Note";

	// Contacts table name
	private static final String TABLE_CONTACTS = "Note";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "nameNote";
	private static final String KEY_PH_NO = "detailNote";
	private static final String KEY_IM = "imageNote";
	private static final String KEY_DATE = "dateNote";
	SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");

	public DataNoteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" + KEY_ID
				+ " REAL PRIMARY KEY ," + KEY_NAME + " VARCHAR," + KEY_PH_NO + " VARCHAR," + KEY_IM + " VARCHAR,"
				+ KEY_DATE + " DATE" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public Cursor getData(String sql) {
		SQLiteDatabase db = getWritableDatabase();
		return db.rawQuery(sql, null);
	}

	public Note getNote(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_NAME, KEY_PH_NO, KEY_IM, KEY_DATE, KEY_ID },
				KEY_ID + "=?", new String[] { id }, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {

				Note note = new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
						Long.parseLong(cursor.getString(4)));
				return note;
			}
		}
		return null;
		// return contact

	}

	public void inserta(long id, String nameNote, String detailNote, String hinh, Date date) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "INSERT INTO Note VALUES(?,?,?,?,?)";
		SQLiteStatement statement = db.compileStatement(sql);
		statement.clearBindings();
		statement.bindLong(1, id);
		statement.bindString(2, nameNote == null ? "" : nameNote);
		statement.bindString(3, detailNote == null ? "" : detailNote);
		statement.bindString(4, hinh);
		statement.bindString(5, ft.format(date));
		statement.executeInsert();
	}

	public void updateContact(String nameNote, String detailNote, String hinh, Date date, long id) {
		if (detailNote == null)
			detailNote = "";
		if (nameNote == null)
			nameNote = "";
		SQLiteDatabase db = getWritableDatabase();
		String sql = " UPDATE Note SET nameNote = '" + nameNote + "' , detailNote =' " + detailNote + "' ,imageNote = '"
				+ hinh + "' " + " , dateNote = '" + ft.format(date) + "' WHERE " + KEY_ID + " = " + id;
		Log.d("update sql= ", "" + sql);
		SQLiteStatement statement = db.compileStatement(sql);
		Log.d("update id= ", "" + id);
		statement.executeUpdateDelete();
	}

	public void deleteContact(Note contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.id) });
		db.close();
	}

}
