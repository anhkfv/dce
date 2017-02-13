package com.example.demolunar;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.date.DayMonthYear;
import com.example.date.Lunar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import processcommon.ProcessEditText;

public class ConvertActivity extends Activity {
	Button solarLunnar, lunnarSolar;
	EditText editDate, editMonth, editYear;
	EditText editView;
	RadioGroup group;
	RadioButton rd;
	ImageButton imageSearch;
	DayMonthYear dmyTemp;
	String date, month, year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convert);
		init();

		date = editDate.getText().toString();
		month = editMonth.getText().toString();
		year = editYear.getText().toString();
		// editView.setFocusable(false);
		solarLunnar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCheck(editDate.getText().toString(), editMonth.getText().toString(),
						editYear.getText().toString())) {
					if (Integer.parseInt(editDate.getText().toString()) > maxDayOfMonth(
							Integer.parseInt(editMonth.getText().toString()),
							Integer.parseInt(editYear.getText().toString()))) {
						editDate.setText("" + maxDayOfMonth(Integer.parseInt(editMonth.getText().toString()),
								Integer.parseInt(editYear.getText().toString())));
						Toast.makeText(getApplicationContext(),
								"Số ngày lớn nhất trong tháng: " + editDate.getText().toString(), Toast.LENGTH_LONG)
								.show();
					}
					if (Integer.parseInt(editYear.getText().toString()) < 1900) {
						editYear.setText("" + 1900);
					}

					DayMonthYear dmy = new DayMonthYear(Integer.parseInt(editDate.getText().toString()),
							Integer.parseInt(editMonth.getText().toString()),
							Integer.parseInt(editYear.getText().toString()));
					dmyTemp = dmy;
					DayMonthYear l = Lunar.solar2Lunar(dmy);
					editView.setText("Ngày " + l.getDay() + " tháng " + l.getMonth() + " năm " + l.getYear());
				}
			}

		});
		lunnarSolar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isCheck(editDate.getText().toString(), editMonth.getText().toString(),
						editYear.getText().toString())) {
					date = editDate.getText().toString();
					month = editMonth.getText().toString();
					year = editYear.getText().toString();
					int temp = 0;
					String tempNhuan;
					int idCheck = group.getCheckedRadioButtonId();
					switch (idCheck) {
					case R.id.LeapN:
						temp = 0;
						break;
					case R.id.LeapY:
						temp = 1;
						break;
					}
					if (!Nhuan(Integer.parseInt(editMonth.getText().toString()),
							Integer.parseInt(editYear.getText().toString()))) {
						temp = 0;
						tempNhuan = "Không phải tháng nhuận";
						if (!rd.isChecked()) {
							Toast.makeText(getApplicationContext(), tempNhuan, Toast.LENGTH_LONG).show();
							rd.setChecked(true);
						}

					}
					int maxDay=maxDay(Integer.parseInt(date),
							Integer.parseInt(month),
							Integer.parseInt(year), temp);
					if (Integer.parseInt(editDate.getText().toString()) > maxDay) {
						editDate.setText("" +maxDay);
						Log.d("max Day", "thang " + Integer.parseInt(editMonth.getText().toString()) + "nam"
								+ Integer.parseInt(editYear.getText().toString()));
						date = editDate.getText().toString();
						Toast.makeText(getApplicationContext(),
								"Số ngày lớn nhất trong tháng:" + editDate.getText().toString(), Toast.LENGTH_LONG)
								.show();
					}
					if (Integer.parseInt(editYear.getText().toString()) < 1900) {
						editYear.setText("" + 1900);
						year = "1990";
					}
					Log.d("Check:", "ngay:" + date + "thang:" + month + " nam:" + year);
					DayMonthYear dmy = new DayMonthYear(Integer.parseInt(date), Integer.parseInt(month),
							Integer.parseInt(year), temp);

					DayMonthYear l = Lunar.lunar2Solar(dmy);
					dmyTemp = l;

					editView.setText("Ngày " + l.getDay() + " tháng " + l.getMonth() + " năm " + l.getYear());

				}
			}
		});
		imageSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(ConvertActivity.this, LunarActivity.class);
				Bundle bd = new Bundle();
				bd.putSerializable("month", dmyTemp);
				in.putExtras(bd);
				ConvertActivity.this.startActivity(in);

			}
		});

	}

	public void init() {
		editView = (EditText) findViewById(R.id.editView);
		editDate = (EditText) findViewById(R.id.editDateCover);
		editMonth = (EditText) findViewById(R.id.editMonthCover);
		editYear = (EditText) findViewById(R.id.editYearCover);
		group = (RadioGroup) findViewById(R.id.radioLeap);
		solarLunnar = (Button) findViewById(R.id.button1);
		lunnarSolar = (Button) findViewById(R.id.button2);
		rd = (RadioButton) findViewById(R.id.LeapN);
		imageSearch = (ImageButton) findViewById(R.id.imageSearch);
		Date time = new Date(System.currentTimeMillis());
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(time.getTime()));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(time.getTime()));
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(time.getTime()));

		editDate.setText("" + day);
		editMonth.setText("" + month);
		editYear.setText("" + year);
		editView.setFocusable(false);
		editDate.setInputType(InputType.TYPE_CLASS_NUMBER);
		editMonth.setInputType(InputType.TYPE_CLASS_NUMBER);
		editYear.setInputType(InputType.TYPE_CLASS_NUMBER);

		editDate.setFilters(new InputFilter[] { new ProcessEditText("1", "31") });
		editMonth.setFilters(new InputFilter[] { new ProcessEditText("1", "12") });
		editYear.setFilters(new InputFilter[] { new ProcessEditText("1", "2100") });
		DayMonthYear dmy = new DayMonthYear(Integer.parseInt(editDate.getText().toString()),
				Integer.parseInt(editMonth.getText().toString()), Integer.parseInt(editYear.getText().toString()));
		dmyTemp = dmy;
		DayMonthYear l = Lunar.solar2Lunar(dmy);

		editView.setText("Ngày " + l.getDay() + " tháng " + l.getMonth() + " năm " + l.getYear());
	}

	public static int maxDayOfMonth(int m, int y) {
		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12)
			return 31;
		if (m == 4 || m == 6 || m == 9 || m == 11)
			return 30;
		if (m == 2) {
			if ((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) {
				return 29;
			} else
				return 28;
		}
		return 0;
	}
	public static boolean Nhuan(int m, int y) {
		Lunar ln = new Lunar();
		List<DayMonthYear> dmy = new ArrayList<DayMonthYear>();
		for (int i = 0; i < ln.lunarYear(y).length; i++) {
			dmy.add(ln.lunarYear(y)[i]);

		}
		for (int i = 0; i < ln.lunarYear(y + 1).length; i++) {
			dmy.add(ln.lunarYear(y + 1)[i]);
		}
		for (int k = 0; k < dmy.size(); k++) {
			if (m == dmy.get(k).getNm() && dmy.get(k).getLeap() == 1 && y == dmy.get(k).getYear()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCheck(String date, String month, String year) {
		if (date.isEmpty() || month.isEmpty() || year.isEmpty()) {
			return false;
		}
		return true;
	}
  private static int  maxDay(int day,int month,int year,int l){
	  DayMonthYear dmy1;
	  DayMonthYear tempLunar=new DayMonthYear();
	  DayMonthYear tempSolar=new DayMonthYear();
	  if(day==30){
		  dmy1=new DayMonthYear(day-1, month, year,l);
		  Lunar lunar=new Lunar();
		  tempSolar=lunar.lunar2Solar(dmy1);
		  tempLunar=lunar.solar2Lunar(lunar.addDay(tempSolar, 1));
		  if(tempLunar.getDay()==30){
			  return 30;
		  }
		  else{
			return 29;  
		  }
		  
	  }
	  return 29;
  }
}