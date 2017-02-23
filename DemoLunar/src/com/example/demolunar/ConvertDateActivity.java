package com.example.demolunar;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.date.DayMonthYear;
import com.example.date.Lunar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class ConvertDateActivity extends Activity {

	NumberPicker dates, months, years;
	String[] month = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	static String[] date28, date29, date30, date31;
	static String[] year;
	int indexDay = 0;
	int indexLunar = 0;
	int indexMonth = 0;
	int leapTemp = 0;
	DayMonthYear dmy, dmys;

	NumberPicker datel, monthl, yearl;
	String[] monthLunar = new String[13];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convert_date);
		dates = (NumberPicker) findViewById(R.id.dates);
		years = (NumberPicker) findViewById(R.id.years);
		months = (NumberPicker) findViewById(R.id.months);
		datel = (NumberPicker) findViewById(R.id.datel);
		monthl = (NumberPicker) findViewById(R.id.monthl);
		yearl = (NumberPicker) findViewById(R.id.yearl);
		init();
		dates.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				indexDay = newVal;
				setDates(Integer.parseInt(month[months.getValue()]), Integer.parseInt(year[years.getValue()]), indexDay);
				convertSolarLunar();

			}

		});
		months.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				setDates(Integer.parseInt(month[newVal]), Integer.parseInt(year[years.getValue()]), indexDay);
				convertSolarLunar();
			}

		});
		years.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				setDates(Integer.parseInt(month[months.getValue()]), Integer.parseInt(year[newVal]), indexDay);
				convertSolarLunar();
			}

		});

		// lunar

		datel.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				indexLunar = newVal;
				String temp = String.valueOf(monthl.getValue());
				if (temp.endsWith("+")) {
					setDatel(indexMonth, Integer.parseInt(year[yearl.getValue()]), indexLunar + 1, 1);
				} else {
					setDatel(indexMonth, Integer.parseInt(year[yearl.getValue()]), indexLunar + 1, 0);
				}
				convertLunarSolar();
			}

		});
		monthl.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				indexMonth = newVal;
				if (String.valueOf(monthLunar[newVal]).endsWith("+")) {
					setDatel(newVal, Integer.parseInt(year[yearl.getValue()]), indexLunar + 1, 1);
				} else {
					setDatel(newVal, Integer.parseInt(year[yearl.getValue()]), indexLunar + 1, 0);
				}
				convertLunarSolar();
			}

		});
		yearl.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				String temp = String.valueOf(monthl.getValue());
				if (temp.endsWith("+")) {
					setDatel(indexMonth, Integer.parseInt(year[newVal]), indexLunar + 1, 1);
				} else {
					setDatel(indexMonth, Integer.parseInt(year[newVal]), indexLunar + 1, 0);
				}
				convertLunarSolar();
			}

		});

	}

	private void init() {
		Date time = new Date(System.currentTimeMillis());
		int dayt = Integer.parseInt(new SimpleDateFormat("dd").format(time.getTime()));
		int montht = Integer.parseInt(new SimpleDateFormat("MM").format(time.getTime()));
		int yeart = Integer.parseInt(new SimpleDateFormat("yyyy").format(time.getTime()));

		// connvert am lich
		DayMonthYear dmy = new DayMonthYear(dayt, montht, yeart);
		DayMonthYear lunar = Lunar.solar2Lunar(dmy);

		// init lich duong
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

		years.setMinValue(0);
		years.setMaxValue(200);
		years.setDisplayedValues(year);
		years.setWrapSelectorWheel(false);
		indexDay = dayt - 1;

		date28 = new String[28];
		for (int i = 0; i <= 27; i++) {
			date28[i] = String.valueOf(i + 1);
		}

		date29 = new String[29];
		for (int i = 0; i <= 28; i++) {
			date29[i] = String.valueOf(i + 1);
		}

		date30 = new String[30];
		for (int i = 0; i <= 29; i++) {
			date30[i] = String.valueOf(i + 1);
		}

		date31 = new String[31];
		for (int i = 0; i <= 30; i++) {
			date31[i] = String.valueOf(i + 1);
		}

		setDates(montht, yeart, dayt - 1);

		// khoi tao lich am
		datel.setMinValue(0);
		datel.setWrapSelectorWheel(false);

		monthl.setMinValue(0);
		monthl.setWrapSelectorWheel(false);

		yearl.setMinValue(0);
		yearl.setMaxValue(200);
		yearl.setDisplayedValues(year);
		yearl.setWrapSelectorWheel(false);
		setDatel(lunar.getMonth()-1, lunar.getYear(), lunar.getDay(), lunar.getLeap());
		indexLunar = lunar.getDay() - 1;

	}

	private void convertLunarSolar() {
			DayMonthYear lunar1 = Lunar.lunar2Solar(dmy);
			setDates(lunar1.getMonth(), lunar1.getYear(), lunar1.getDay() - 1);
	}

	private void convertSolarLunar() {
			DayMonthYear lunar2 = Lunar.solar2Lunar(dmys);
			setDatel(lunar2.getMonth()-1, lunar2.getYear(), lunar2.getDay(), lunar2.getLeap());
	}

	private void setDates(int montht, int yeart, int dayt) {
		int maxDay = maxDayOfMonth(montht, yeart);
		months.setValue(montht - 1);
		years.setValue(yeart - 1900);
		if (dayt >= (maxDay - 1)) {
			dayt = maxDay - 1;
			indexDay = maxDay - 1;
		}
		// indexLunar = day;
		if (maxDay == 28) {
			dates.setDisplayedValues(null);
			dates.setMaxValue(27);
			dates.setDisplayedValues(date28);
			// dates.setValue(day);
		}
		if (maxDay == 29) {
			dates.setDisplayedValues(null);
			dates.setMaxValue(28);
			dates.setDisplayedValues(date29);
			// dates.setValue(day);
		}
		if (maxDay == 30) {
			dates.setDisplayedValues(null);
			dates.setMaxValue(29);
			dates.setDisplayedValues(date30);
			// dates.setValue(day);
		}
		if (maxDay == 31) {
			dates.setDisplayedValues(null);
			dates.setMaxValue(30);
			dates.setDisplayedValues(date31);
			// dates.setValue(day - 1);
		}
		dates.setValue(dayt);
		dmys = new DayMonthYear(dayt + 1, montht, yeart);
	}

	private void setDatel(int montht, int yeart, int dayt, int leap) {
		yearl.setValue(yeart - 1900);
		leapTemp = leap;
		int maxDay;
		Lunar ln = new Lunar();
		int monthLeap = ln.lunarYear(yeart).length == 14 ? leap(montht, yeart) : -1;
		if (monthLeap != -1) {
			maxDay = maxDay(montht < monthLeap ? montht + 1 : montht, yeart, leap);
			dmy = new DayMonthYear(dayt, montht < monthLeap ? montht + 1 : montht, yeart, leap);
		} else {
			maxDay = maxDay(montht==12?montht:montht + 1, yeart, leap);
			dmy = new DayMonthYear(dayt, montht==12?montht:montht + 1, yeart, leap);
		}
		// indexMonth = ((montht <= monthLeap) && (leap == 0)) ? montht - 1 :
		indexMonth = montht;
		// list month
		if (monthLeap > 0) {
			monthLunar = new String[13];
			int k = 0;
			for (int i = 0; i < 12; i++) {
				if (i == (monthLeap - 1)) {
					monthLunar[k] = String.valueOf(monthLeap);
					monthLunar[k + 1] = String.valueOf(monthLeap) + "+";
					k = k + 2;
				} else {
					monthLunar[k] = String.valueOf(i + 1);
					k++;
				}
			}
			monthl.setDisplayedValues(null);
			monthl.setMaxValue(12);
			monthl.setDisplayedValues(monthLunar);
			if (leap == 1) {
				monthl.setValue(montht);
			} else {
				monthl.setValue(montht);
			}
		} else {
			if(indexMonth == 12)  indexMonth = 11;
			monthLunar = new String[13];
			monthl.setDisplayedValues(null);
			monthl.setMaxValue(11);
			monthl.setDisplayedValues(month);
			monthl.setValue(montht);
			indexMonth = montht;
		}

		if (dayt >= maxDay) {
			dayt = maxDay;
			indexLunar = maxDay - 1;
		}

		if (maxDay == 29) {
			datel.setDisplayedValues(null);
			datel.setMaxValue(28);
			datel.setDisplayedValues(date29);
			// dates.setValue(day);
		}
		if (maxDay == 30) {
			datel.setDisplayedValues(null);
			datel.setMaxValue(29);
			datel.setDisplayedValues(date30);
			// dates.setValue(day);
		}
		datel.setValue(dayt - 1);
		indexLunar = dayt - 1;

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

	public int leap(int m, int y) {
		Lunar ln = new Lunar();
		List<DayMonthYear> dmy = new ArrayList<DayMonthYear>();
		for (int i = 0; i < ln.lunarYear(y).length; i++) {
			dmy.add(ln.lunarYear(y)[i]);

		}
		for (int i = 0; i < ln.lunarYear(y + 1).length; i++) {
			dmy.add(ln.lunarYear(y + 1)[i]);
		}
		for (int k = 0; k < dmy.size(); k++) {
			if (dmy.get(k).getLeap() == 1 && y == dmy.get(k).getYear()) {
				return dmy.get(k).getNm();
			}
		}
		return -1;
	}

	private static int maxDay(int month, int year, int l) {
		DayMonthYear dmy1;
		DayMonthYear tempLunar = new DayMonthYear();
		DayMonthYear tempSolar = new DayMonthYear();
		dmy1 = new DayMonthYear(29, month, year, l);
		tempSolar = Lunar.lunar2Solar(dmy1);
		tempLunar = Lunar.solar2Lunar(Lunar.addDay(tempSolar, 1));
		if (tempLunar.getDay() == 30) {
			return 30;
		} else {
			return 29;
		}
	}
}
