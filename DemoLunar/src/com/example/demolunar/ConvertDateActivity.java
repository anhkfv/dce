package com.example.demolunar;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.date.DayMonthYear;
import com.example.date.Lunar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class ConvertDateActivity extends Activity {

	NumberPicker dates, months, years;
	List<Integer> valueDates, valueMonths;
	String[] month = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	static String[] date28, date29, date30, date31;
	static String[] year;
	List<Integer> valueYears;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convert_date);
		dates = (NumberPicker) findViewById(R.id.dates);
		months = (NumberPicker) findViewById(R.id.months);
		years = (NumberPicker) findViewById(R.id.years);
		init();
		months.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				setDates(Integer.parseInt(month[newVal]), years.getValue(), dates.getValue());
			}

		});
		years.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				setDates(months.getValue(), Integer.parseInt(year[newVal]), dates.getValue());
			}

		});

	}

	private void init() {
		Date time = new Date(System.currentTimeMillis());
		int dayt = Integer.parseInt(new SimpleDateFormat("dd").format(time.getTime()));
		int montht = Integer.parseInt(new SimpleDateFormat("MM").format(time.getTime()));
		int yeart = Integer.parseInt(new SimpleDateFormat("yyyy").format(time.getTime()));
		year = new String[201];
		for (int i = 1900; i <= 2100; i++) {
			year[i - 1900] = String.valueOf(i);
		}
		dates.setMinValue(0);
		dates.setWrapSelectorWheel(false);

		months.setMinValue(0);
		months.setMaxValue(11);
		months.setDisplayedValues(month);
		months.setWrapSelectorWheel(false);
		months.setValue(montht - 1);

		years.setMinValue(0);
		years.setMaxValue(200);
		years.setDisplayedValues(year);
		years.setWrapSelectorWheel(false);
		years.setValue(yeart - 1900);
		setDates(montht, yeart, dayt);

	}

	private void setDates(int montht, int yeart, int dayt) {
		int maxDay = maxDayOfMonth(montht, yeart);
		int day = dayt;
		if(day > maxDay){
			day = maxDay;
		}
		dates.setMaxValue(maxDay - 1);
		if (maxDay == 28) {
			date28 = new String[28];
			for (int i = 0; i <= 27; i++) {
				date28[i] = String.valueOf(i + 1);
			}
			dates.setDisplayedValues(date28);
			dates.setValue(Integer.parseInt(date28[day - 1]));
		}
		if (maxDay == 29) {
			date29 = new String[29];
			for (int i = 0; i <= 28; i++) {
				date29[i] = String.valueOf(i + 1);
			}
			dates.setDisplayedValues(date29);
			dates.setValue(Integer.parseInt(date29[day - 1]));
		}
		if (maxDay == 30) {
			date30 = new String[30];
			for (int i = 0; i <= 29; i++) {
				date30[i] = String.valueOf(i + 1);
			}
			dates.setDisplayedValues(date30);
			dates.setValue(Integer.parseInt(date30[day - 1]));
		}
		if (maxDay == 31) {
			date31 = new String[31];
			for (int i = 0; i <= 30; i++) {
				date31[i] = String.valueOf(i + 1);
			}
			dates.setDisplayedValues(date31);
			dates.setValue(Integer.parseInt(date31[day - 1]));
		}

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

	public boolean Nhuan(int m, int y) {
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

	private static int maxDay(int day, int month, int year, int l) {
		DayMonthYear dmy1;
		DayMonthYear tempLunar = new DayMonthYear();
		DayMonthYear tempSolar = new DayMonthYear();
		if (day == 30) {
			dmy1 = new DayMonthYear(day - 1, month, year, l);
			Lunar lunar = new Lunar();
			tempSolar = lunar.lunar2Solar(dmy1);
			tempLunar = lunar.solar2Lunar(lunar.addDay(tempSolar, 1));
			if (tempLunar.getDay() == 30) {
				return 30;
			} else {
				return 29;
			}

		}
		return 29;
	}
}
