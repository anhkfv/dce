package com.example.demolunar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideActivity extends Activity {
	private Intent intent;
	String filename = "data_login";
	private boolean isGuest;
	private ImageView[] indicator_image_views;
	private View view_group_main;
	private ArrayList<View> imagesList;
	private ViewGroup view_group_indicator;
	private ViewPager viewPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
		setContentView(this.view_group_main);
	}

	public void init() {
		this.intent = this.getIntent();
		LayoutInflater layout_inflator = getLayoutInflater();
		this.imagesList = new ArrayList<View>();
		View iv1 = layout_inflator.inflate(R.layout.guide_1, null);
		this.imagesList.add(iv1);

		View iv2 = layout_inflator.inflate(R.layout.guide_2, null);
		this.imagesList.add(iv2);

		View iv3 = layout_inflator.inflate(R.layout.guide_3, null);
		this.imagesList.add(iv3);

		View iv4 = layout_inflator.inflate(R.layout.guide_4, null);
		this.imagesList.add(iv4);
		iv4.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				guideEnd();

			}
		});
		this.view_group_main = layout_inflator.inflate(R.layout.activity_guide, null);
		this.viewPage = (ViewPager) this.view_group_main.findViewById(R.id.pager);
		this.viewPage.setAdapter(new GuidePageAdapter(this.imagesList));
		this.viewPage.setFocusable(true);

	}

	public void guideEnd() {
		if (this.intent.getStringExtra("from").equals("setting")) {

		} else {
			this.startActivity(new Intent(this, TabhostActivity.class));
			savingPreferences();
		}
		this.finish();
		this.overridePendingTransition(R.drawable.slide_in_from_right, R.drawable.slide_in_from_left);
	}

	public void savingPreferences() {
		// tạo đối tượng getSharedPreferences
		SharedPreferences pre = getSharedPreferences(filename, MODE_PRIVATE);
		// tạo đối tượng Editor để lưu thay đổi
		SharedPreferences.Editor editor = pre.edit();
		editor.putString("user", "1");
		// chấp nhận lưu xuống file
		editor.commit();
	}

	private class GuidePageAdapter extends PagerAdapter {
		ArrayList<View> imageList;

		private GuidePageAdapter(ArrayList<View> imageList) {
			this.imageList = imageList;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) GuideActivity.this.imagesList.get(position));

		}

		@Override
		public int getCount() {
			return GuideActivity.this.imagesList.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView((View) GuideActivity.this.imagesList.get(position));
			return GuideActivity.this.imagesList.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			if (arg0 == arg1) {
				return true;
			}
			return false;
		}

		public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader) {
		}

		public Parcelable saveState() {
			return null;
		}

	}

	class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {

		}

	}

}
