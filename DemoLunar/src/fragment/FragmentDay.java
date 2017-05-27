package fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.date.DayMonthYear;
import com.example.demolunar.R;
import com.example.note.Note;
import com.example.sqlite_note.DataNoteHandler;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentDay extends Fragment {

	private int day;
	TextView tvDay;
	TextView tvNote;
	IGetItem item;
	DayMonthYear dmy;
	Context context;
	DataNoteHandler dbb;
	List<Note> mang = new ArrayList<>();
	List<Note> mangNote = new ArrayList<Note>();

	public interface IGetItem {
		public int getMaxDay();

		public int getMaxDayPre();

	}

	public FragmentDay(int day, DayMonthYear dmy, Context context) {
		this.context = context;
		this.day = day;
		this.dmy = dmy;
		dbb = new DataNoteHandler(context);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		item = (IGetItem) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_day, container, false);

		init(rootView);

		return rootView;
	}

	public void init(View view) {

		tvDay = (TextView) view.findViewById(R.id.tvDay);
		tvNote = (TextView) view.findViewById(R.id.textNote);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (day > item.getMaxDay()) {
			tvDay.setText("1");
		} else if (day <= 0) {
			tvDay.setText(item.getMaxDayPre() + "");
		} else
			tvDay.setText(day + "");
		DayMonthYear dmyt = new DayMonthYear(day, dmy.getMonth(), dmy.getYear());
		// if(mang.size()!=0)mang.clear();
		mang.clear();
		tvNote.setText("");
		displayData(dmyt);
		if (mang.size() != 0) {
			tvNote.setText(mang.get(0).nameNote);
		}

	}

	public List<Note> displayData(DayMonthYear dmytt) {
		String date;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, dmytt.getDay());
		cal.set(Calendar.MONTH, dmytt.getMonth() - 1);
		cal.set(Calendar.YEAR, dmytt.getYear());
		Date dat = cal.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		String temp = ft.format(dat);
		mang = new ArrayList<Note>();
		Cursor note = dbb.getData("SELECT*FROM Note");
		while (note.moveToNext()) {
			if (temp.equals(note.getString(4))) {
				mang.add(new Note(note.getString(1), note.getString(2), note.getString(3), note.getString(4),
						note.getLong(0)));
			}
		}
		return mang;
	}
}
