package com.example.print;

import java.io.ByteArrayOutputStream;

import com.example.note.Note;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import processcommon.CheckCommon;

public class NoteDto {
	private String nameNote;
	private String detailNote;
	private byte[] imageNote;
	private String date;

	public NoteDto() {

	}

	public NoteDto(String nameNote, String detailNote, byte[] imageNote, String date) {
		this.date = date;
		this.nameNote = nameNote;
		this.detailNote = detailNote;
		this.imageNote = imageNote;
	};

	public NoteDto convert(Note note) {
		NoteDto dto = new NoteDto();
		dto.setDate(note.getDate());
		dto.setNameNote(note.nameNote);
		dto.setDetailNote(note.getDetailNote());
		//byte[] image = new byte[1024];
		if(CheckCommon.checkHasFile(note.getImageNote())){
		Bitmap bm=decodeSampledBitmapFromUri(note.getImageNote(), 500, 500);
		dto.setImageNote(imageView_To_Byte(bm));
		}else{
			dto.setImageNote(null);
		}
		return dto;
	}
	public byte[] imageView_To_Byte(Bitmap bmp) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
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

	private int calculateInSampleSize(

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

	public String getNameNote() {
		return nameNote;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public byte[] getImageNote() {
		return imageNote;
	}

	public String getDate() {
		return date;
	}

	public void setNameNote(String nameNote) {
		this.nameNote = nameNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}

	public void setImageNote(byte[] imageNote) {
		this.imageNote = imageNote;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
}
