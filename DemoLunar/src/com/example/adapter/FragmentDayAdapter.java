package com.example.adapter;

import com.example.date.DayMonthYear;
import com.example.date.Lunar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fragment.FragmentDay;
public class FragmentDayAdapter extends FragmentPagerAdapter {

	private DayMonthYear dmy;
	private Context mContext;
		
	public FragmentDayAdapter(FragmentManager fm, DayMonthYear dmy,Context context) {
		super(fm);
		this.dmy= dmy;
		this.mContext=context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub

		return new FragmentDay (arg0,dmy,mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (Lunar.maxDayOfMonth(dmy.getMonth(), dmy.getYear())+2);
	}

}
