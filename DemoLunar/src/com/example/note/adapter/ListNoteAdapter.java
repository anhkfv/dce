package com.example.note.adapter;

import java.util.List;

import com.example.demolunar.R;
import com.example.note.Note;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListNoteAdapter extends ArrayAdapter<Note> {

	public ListNoteAdapter(Context context, int resource) {
		super(context, resource);
	}

	public ListNoteAdapter(Context context, int resource, List<Note> item) {
		super(context, resource, item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.custom_activity_note, null);

		}
		Note p = getItem(position);
		if (p != null) {

			TextView tv1 = (TextView) v.findViewById(R.id.textName);
			tv1.setText(String.valueOf(p.detailNote));
			TextView tv2 = (TextView) v.findViewById(R.id.textDetail);
			tv2.setText(String.valueOf(p.nameNote));
			ImageView imgv = (ImageView) v.findViewById(R.id.imageHT);
			Bitmap bitmap = decodeSampledBitmapFromUri(String.valueOf(p.imageNote),500,500);
			imgv.setImageBitmap(bitmap);
			TextView ttv3 = (TextView) v.findViewById(R.id.textDate);
			ttv3.setText(p.date);

		}
		return v;
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
}
