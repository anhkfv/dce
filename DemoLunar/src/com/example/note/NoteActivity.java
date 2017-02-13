package com.example.note;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.date.DayMonthYear;
import com.example.demolunar.R;
import com.example.sqlite_note.DataNoteHandler;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class NoteActivity extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_READ = 2;
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF startPoint = new PointF();
	PointF midPoint = new PointF();
	float oldDist = 1f;
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	static String PATH_IMAGE=Environment.getExternalStorageDirectory().getAbsolutePath();
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	ImageView imgvHinh;
	ImageButton imgAdd;
	EditText edtTen, edtGia;
	ImageButton btnSave, imageReplace;
	Context context;
	Note note;
	DayMonthYear dmyt;
	DayMonthYear dmyTemp;
	String tempNameNote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		copyDataBase();
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

				// Show an expanation to the user *asynchronously* -- don't
				// block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.

			} else {

				// No explanation needed, we can request the permission.

				ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
						RESULT_READ);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}
		AnhXa();
		dmyt = (DayMonthYear) getIntent().getSerializableExtra("detailNoteDay");

		note = (Note) getIntent().getSerializableExtra("detailNote");
		tempNameNote = edtTen.getText().toString();
		if (note != null) {
			edtTen.setText(note.nameNote);
			edtGia.setText(note.detailNote);
			tempNameNote = edtTen.getText().toString();
			Bitmap bitmap = decodeSampledBitmapFromUri(note.getImageNote(), 500, 500);
			imgvHinh.setImageBitmap(bitmap);
		}
		final DataNoteHandler db = new DataNoteHandler(this);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (note != null) {
						Log.d("nhan note: ", "" + note.id);
						String temp;
						Date date;
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						date = df.parse(note.date);
						if (getPath() == null) {
							temp = note.imageNote;
						} else {
							temp = getPath();
						}

						db.updateContact(edtTen.getText().toString(), edtGia.getText().toString(), temp, date, note.id);
						Toast.makeText(NoteActivity.this, "cap nhat thanh cong", Toast.LENGTH_LONG).show();
						finish();
					} else {
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.DATE, dmyt.getDay());
						cal.set(Calendar.MONTH, dmyt.getMonth() - 1);
						cal.set(Calendar.YEAR, dmyt.getYear());
						Date d = cal.getTime();
						if(getPath()==null){
							setPath(PATH_IMAGE+"/ars3.jpg");
						}
						db.inserta(edtTen.getText().toString(), edtGia.getText().toString(), getPath(), d);
						Toast.makeText(NoteActivity.this, "luu thanh cong", Toast.LENGTH_LONG).show();
						finish();
					}
				} catch (Exception ex)

				{

				}

			}

		});
		// camera
		imgAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
		case 2: {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			} else {
				imgvHinh.setImageResource(R.id.addImage);
			}
			return;
		}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			Log.d("file", picturePath);
			setPath(picturePath);

			Bitmap bm = decodeSampledBitmapFromUri(picturePath, 500, 500);
			imgvHinh.setImageBitmap(bm);
			// imageView.setOnTouchListener(new Touch());
			
			imgvHinh.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					ImageView view = (ImageView) v;
					System.out.println("matrix=" + savedMatrix.toString());
					switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
						savedMatrix.set(matrix);
						startPoint.set(event.getX(), event.getY());
						mode = DRAG;
						break;
					case MotionEvent.ACTION_POINTER_DOWN:
						oldDist = spacing(event);
						if (oldDist > 10f) {
							savedMatrix.set(matrix);
							midPoint(midPoint, event);
							mode = ZOOM;
						}
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
						mode = NONE;
						break;
					case MotionEvent.ACTION_MOVE:
						if (mode == DRAG) {
							matrix.set(savedMatrix);
							matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
						} else if (mode == ZOOM) {
							float newDist = spacing(event);
							if (newDist > 10f) {
								matrix.set(savedMatrix);
								float scale = newDist / oldDist;
								matrix.postScale(scale, scale, midPoint.x, midPoint.y);
							}
						}
						break;
					}
					view.setImageMatrix(matrix);
					return true;
				}

				@SuppressLint("FloatMath")
				private float spacing(MotionEvent event) {
					float x = event.getX(0) - event.getX(1);
					float y = event.getY(0) - event.getY(1);
					return (float) Math.sqrt(x * x + y * y);
				}

				private void midPoint(PointF point, MotionEvent event) {
					float x = event.getX(0) + event.getX(1);
					float y = event.getY(0) + event.getY(1);
					point.set(x / 2, y / 2);
				}
			});
		}
	};

	public void AnhXa() {
		imgvHinh = (ImageView) findViewById(R.id.imageView1);
		edtTen = (EditText) findViewById(R.id.editText1);
		edtGia = (EditText) findViewById(R.id.editText2);
		btnSave = (ImageButton) findViewById(R.id.button1);
		imgAdd = (ImageButton) findViewById(R.id.addImage);
	}

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

		Bitmap bm = null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		Log.d("size", "" + options.inSampleSize);

		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}

	public int calculateInSampleSize(

			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	private void copyDataBase() {
		try {
			InputStream myInput = getApplicationContext().getAssets().open("ars3.jpg");
			String outFileName = PATH_IMAGE+ "/ars3.jpg";
			OutputStream myOutput = new FileOutputStream(outFileName);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			Log.d("anh ", outFileName);
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
