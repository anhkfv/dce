package com.example.sqlite_note;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.note.Note;

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

	public DataNoteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " VARCHAR," + KEY_PH_NO + " VARCHAR," + KEY_IM
				+ " VARCHAR," + KEY_DATE + " DATE" + ")";
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

	public void inserta(String nameNote, String detailNote, String hinh, Date date) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "INSERT INTO Note VALUES(null,?,?,?,?)";
		SQLiteStatement statement = db.compileStatement(sql);
		statement.clearBindings();
		statement.bindString(1, nameNote);
		statement.bindString(2, detailNote);
		statement.bindString(3, hinh);
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		statement.bindString(4, ft.format(date));
		statement.executeInsert();
	}

	public void inserNote(String nameNote, String detailNote, String hinh, String date) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "INSERT INTO Note VALUES(null,?,?,?,?)";
		SQLiteStatement statement = db.compileStatement(sql);
		statement.clearBindings();
		statement.bindString(1, nameNote);
		statement.bindString(2, detailNote);
		statement.bindString(3, hinh);
		statement.bindString(4, date);
		statement.executeInsert();
	}
	
	public void updateContact(String nameNote, String detailNote, String hinh, Date date,int id) {
		SQLiteDatabase db = getWritableDatabase();
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		String sql = " UPDATE Note SET nameNote = '"+nameNote+"' , detailNote =' "+detailNote+"' ,imageNote = '"+hinh+"' "+" , dateNote = '"+ ft.format(date)+"' WHERE " + KEY_ID + " = "+id;
		Log.d("update sql= ",""+sql);
		SQLiteStatement statement = db.compileStatement(sql);
		Log.d("update id= ",""+id);
		statement.executeUpdateDelete();
	}
	public void updateNote(String nameNote, String detailNote, String hinh, String date,int id) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = " UPDATE Note SET nameNote = '"+nameNote+"' , detailNote =' "+detailNote+"' ,imageNote = '"+hinh+"' "+" , dateNote = '"+ date +"' WHERE " + KEY_ID + " = "+id;
		Log.d("update sql= ",""+sql);
		SQLiteStatement statement = db.compileStatement(sql);
		Log.d("update id= ",""+id);
		statement.executeUpdateDelete();
	}
	public void deleteContact(Note contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.id) });
		db.close();
	}

}
