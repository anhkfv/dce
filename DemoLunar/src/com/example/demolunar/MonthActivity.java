package com.example.demolunar;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.adapter.CustomMonth;
import com.example.adapter.FragmentDayAdapter;
import com.example.date.DayMonthYear;
import com.example.date.Lunar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import fragment.FragmentDay.IGetItem;

public class MonthActivity extends FragmentActivity implements IGetItem,Serializable {

//	Typeface face1, face2;
	Handler handler;
	ViewPager viewPager;
	ImageButton ibtPreviewMonth, ibtNextMonth;
	TextView  tvMonthYearCenter;
	TableLayout table;
	FragmentDayAdapter adapter;

	DayMonthYear dmyCurrent, dmyChanger, dmyTable, dmyCustomMOnth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_month);
		init();
		setTime();
		setTable(dmyCurrent);
		listenButtonChange();
	}

	public void init() {
		handler = new Handler();
		ibtPreviewMonth = (ImageButton) findViewById(R.id.ibtPreviewMonth);
		ibtNextMonth = (ImageButton) findViewById(R.id.ibtNextMonth);
		tvMonthYearCenter = (TextView) findViewById(R.id.tvMonthYearCenter);
		table = (TableLayout) findViewById(R.id.month);
	}
	public void setTime() {

		Date time = new Date(System.currentTimeMillis());

		int day = Integer.parseInt(new SimpleDateFormat("dd").format(time
				.getTime()));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(time
				.getTime()));
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(time
				.getTime()));

		dmyCurrent = new DayMonthYear(day, month, year);
	}
	public void setTable(DayMonthYear dmy) {
		tvMonthYearCenter.setText("Tháng " + dmy.getMonth() + " năm "
				+ dmy.getYear());

		dmy = new DayMonthYear(1, dmy.getMonth(), dmy.getYear());

		DayMonthYear dmyException;
		int m = dmy.getMonth();
		boolean flag = true;

		table.removeAllViews();
		TableRow[] row = new TableRow[6];

		for (int i = 0; i < 6; i++) {
			row[i] = new TableRow(this);

			CustomMonth[] cm = new CustomMonth[7];

			for (int j = 0; j < 7; j++) {
				cm[j] = new CustomMonth(this);

				if (j == Lunar.thu(dmy)) {

					if (dmy.getMonth() != m)
						flag = false;
					else
						flag = true;

					cm[j].setText(dmy, dmyCurrent, flag);
					dmy = Lunar.addDay(dmy, 1);

				} else {
					dmyException = Lunar.addDay(dmy, j - Lunar.thu(dmy));
					cm[j].setText(dmyException, dmyCurrent, false);
				}

				row[i].addView(cm[j]);
				cm[j].setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						1f));
			}

			table.addView(row[i]);
		}

		listenClickTableMonth();
	}

	public void listenButtonChange() {
		dmyTable = new DayMonthYear(dmyCurrent .getDay(), dmyCurrent .getMonth(),
				dmyCurrent.getYear());

		ibtPreviewMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (dmyTable.getMonth() == 1) {
					setTable(new DayMonthYear(1, 12, dmyTable.getYear() - 1));

					dmyTable.setMonth(12);
					dmyTable.setYear(dmyTable.getYear() - 1);

				} else {
					setTable(new DayMonthYear(1, dmyTable.getMonth() - 1,
							dmyTable.getYear()));

					dmyTable.setMonth(dmyTable.getMonth() - 1);
				}
			}
		});

		ibtNextMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (dmyTable.getMonth() == 12) {
					setTable(new DayMonthYear(1, 1, dmyTable.getYear() + 1));

					dmyTable.setMonth(1);
					dmyTable.setYear(dmyTable.getYear() + 1);
				} else {
					setTable(new DayMonthYear(1, dmyTable.getMonth() + 1,
							dmyTable.getYear()));

					dmyTable.setMonth(dmyTable.getMonth() + 1);
				}
			}
		});
	}

	public void listenClickTableMonth() {
		for (int i = 0; i < table.getChildCount(); i++) {
			final TableRow row = (TableRow) table.getChildAt(i);
			for (int j = 0; j < row.getChildCount(); j++) {
				final CustomMonth cm = (CustomMonth) row.getChildAt(j);
				if (cm.getDayD() != dmyCurrent.getDay() && cm.getDayD() != 0)
					cm.setBackgroundResource(R.drawable.buttontable);

				cm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cm.getDayD() != 0){
							DayMonthYear dmyn=new DayMonthYear(cm.getDayD(), dmyTable
									.getMonth(), dmyTable.getYear());
							Intent it = null;
							it = new Intent(MonthActivity.this, LunarActivity.class);
							Bundle mBundle=new Bundle();
							mBundle.putSerializable("month", dmyn);
							it.putExtras(mBundle);
							MonthActivity.this.startActivity(it);
							
						}
					}
				});

			}
		}
	}

	@Override
	public int getMaxDay() {
		// TODO Auto-generated method stub
		return Lunar.maxDayOfMonth(dmyChanger.getMonth(), dmyChanger.getYear());
	}

	@Override
	public int getMaxDayPre() {
		// TODO Auto-generated method stub
		if (dmyChanger.getMonth() > 1)
			return Lunar.maxDayOfMonth(dmyChanger.getMonth() - 1,
					dmyChanger.getYear());
		else
			return 31;
	}


}
