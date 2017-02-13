package com.example.sqlite_battu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerBatTu extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static String DB_PATH = "/data/data/com.example.demolunar/databases/";
	// Database Name
	private static final String DATABASE_NAME = "NhiThapBatTu";

	// Contacts table name
	private static final String TABLE_CONTACTS = "NhiThapBatTu";

	// Contacts Table Columns names
	private static final String KEY_ID = "sao";
	private static final String KEY_NAME_CV = "convat";
	private static final String KEY_NAME_T = "thuoc";
	private static final String KEY_NAME = "nenlam";
	private static final String KEY_PH_NO = "khongnen";
	private static final String KEY_PH_NL = "ngoaile";
	private final Context myContext;

	public DatabaseHandlerBatTu(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	// Adding new contact
	void addBatTu(BatTu batTu) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME_CV, batTu.getConvat());
		values.put(KEY_NAME_T, batTu.getThuoc());
		values.put(KEY_NAME, batTu.getNenLam());
		values.put(KEY_PH_NO, batTu.getKhongNen());
		values.put(KEY_PH_NL, batTu.ngoaiLe);

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public BatTu getBatTu(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		BatTu batTu = new BatTu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5));
		// return contact
		return batTu;
	}

	// Getting All Contacts
	public List<BatTu> getAllBatTu() {
		List<BatTu> batTuList = new ArrayList<BatTu>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BatTu batTu = new BatTu();
				batTu.setSao(cursor.getString(0));
				batTu.setConvat(cursor.getString(1));
				batTu.setThuoc(cursor.getString(2));
				batTu.setNenLam(cursor.getString(3));
				batTu.setKhongNen(cursor.getString(4));
				batTu.setNgoaiLe(cursor.getString(5));
				// Adding contact to list
				batTuList.add(batTu);
			} while (cursor.moveToNext());
		}

		// return contact list
		return batTuList;
	}

	// Getting contacts Count
	public int getBatTusCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}

	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
		} else {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			} finally {
				this.close();
			}
		}

	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		} catch (Exception e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		try {
			// Open your local db as the input stream
			InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
			// Path to the just created empty db
			String outFileName = DB_PATH + DATABASE_NAME;
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
