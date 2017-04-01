package com.example.demolunar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.example.adapter.FragmentDayAdapter;
import com.example.date.DayMonthYear;
import com.example.date.Lunar;
import com.example.note.ListNote;
import com.example.note.Note;
import com.example.sqlite_note.DataNoteHandler;
import com.example.weather.MainActivity;
import com.example.weather.TransInforWeather;
import com.example.weatherday.GPSTracker;
import com.example.weatherday.LocationKey;
import com.example.weatherday.WeatherDate;
import com.example.weatherday.WeatherIcon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import fragment.FragmentDay.IGetItem;

public class LunarActivity extends FragmentActivity implements IGetItem {
	Handler handler;
	ViewPager viewPager;
	Button tvCir;
	ImageButton ibtNote, ibtPreviewMonth, ibtNextMonth;
	TextView tvMonthYear, tvThu, tvLunar, tvLunarCanChi, tvMonthYearCenter, tvTime, tvLocation, tvText;
	TableLayout table;
	FragmentDayAdapter adapter;
	private LinearLayout linearLayout;
	final Handler mHandler = new Handler();
	private LocationKey locationKey;
	ImageButton imgBt;
	WeatherDate forecast = null;
	WeatherIcon weatherIcon;
	DataNoteHandler dbb = new DataNoteHandler(this);
	List<Note> mang = new ArrayList<>();;
	List<Note> mangNotet = new ArrayList<>();
	List<Note> mangNote = new ArrayList<Note>();
	DayMonthYear dmyCurrent, dmyChanger, dmyTable, dmyCustomMOnth, dmyt;
	private static final int REQUEST_CODE_PERMISSION = 2;
	String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
	GPSTracker gps;
	Location location;
	private final Lock lock = new ReentrantLock();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_lunar);
		init();
		setTime();
		loadData();
		// loadDataNote();

		dmyt = (DayMonthYear) getIntent().getSerializableExtra("month");
		if (dmyt != null) {
			setDay(dmyt);
			printInfo(dmyt);
			dmyCurrent = dmyt;
			mangNotet.clear();
			mangNotet = displayData(dmyt);
			if (mangNotet.size() == 0) {
				setBackground(dmyt);
			} else {
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(mangNotet.get(0).imageNote), size.x,
						size.y, true);
				BitmapDrawable background = new BitmapDrawable(bmp);
				linearLayout.setBackgroundDrawable(background);
			}
			tvCir.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = null;
					it = new Intent(LunarActivity.this, DeatailDateActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("detailDate", dmyt);
					it.putExtras(bundle);
					LunarActivity.this.startActivity(it);

				}
			});
			ibtNote.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = null;
					it = new Intent(LunarActivity.this, ListNote.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("detailNoteList", dmyt);
					it.putExtras(bundle);
					LunarActivity.this.startActivity(it);
				}
			});
			displayData(dmyt);

		} else {
			displayData(dmyCurrent);
			setDay(dmyCurrent);
			printInfo(dmyCurrent);
			mangNotet.clear();
			mangNotet = displayData(dmyCurrent);
			if (mangNotet.size() == 0) {
				setBackground(dmyCurrent);
			} else {
				Bitmap bmp = decodeSampledBitmapFromUri(mangNotet.get(0).imageNote, 500, 500);
				BitmapDrawable background = new BitmapDrawable(bmp);
				linearLayout.setBackgroundDrawable(background);
			}
			tvCir.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = null;
					it = new Intent(LunarActivity.this, DeatailDateActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("detailDate", dmyCurrent);
					it.putExtras(bundle);
					LunarActivity.this.startActivity(it);

				}
			});
			ibtNote.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = null;
					it = new Intent(LunarActivity.this, ListNote.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("detailNoteList", dmyCurrent);
					it.putExtras(bundle);
					LunarActivity.this.startActivity(it);
				}
			});

		}
		imgBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (forecast != null) {
					Intent it = new Intent(LunarActivity.this, MainActivity.class);
					Bundle bundle = new Bundle();
					TransInforWeather transInforWeather = new TransInforWeather();
					transInforWeather.setLocation(locationKey.getRegionName());
					transInforWeather.setLatitude(locationKey.getLatitude());
					transInforWeather.setLongitude(locationKey.getLongitude());
					transInforWeather.setKeyLocation(locationKey.getLocatioKey());
					transInforWeather.setIcon(forecast.getWeatherIcon());
					bundle.putSerializable("Trans", transInforWeather);
					it.putExtras(bundle);
					startActivity(it);
				}
			}
		});

	}

	public void init() {
		handler = new Handler();
		Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
		Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Bold.ttf");
		tvCir = (Button) findViewById(R.id.tvCir);
		ibtNote = (ImageButton) findViewById(R.id.ibtNote);
		tvMonthYear = (TextView) findViewById(R.id.tvMonthYear);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvText = (TextView) findViewById(R.id.tvText);
		linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		viewPager = (ViewPager) findViewById(R.id.pager);

		// tvThu = (TextView) findViewById(R.id.tvThu);
		tvLunar = (TextView) findViewById(R.id.tvLunar);
		tvTime.setTypeface(face1);
		tvLocation.setTypeface(face1);
		tvMonthYear.setTypeface(face2);
		// tvThu.setTypeface(face2);
		tvCir.setTypeface(face2);
		tvLunar.setTypeface(face2);
		tvText.setTypeface(face2);
		imgBt = (ImageButton) findViewById(R.id.image);

	}

	public void setTime() {
		Date time = new Date(System.currentTimeMillis());
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(time.getTime()));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(time.getTime()));
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(time.getTime()));
		dmyCurrent = new DayMonthYear(day, month, year);
		dmyChanger = dmyCurrent;
		Runnable r = new Runnable() {

			@Override
			public void run() {
				tvTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()).getTime()));
				handler.postDelayed(this, 1000);
			}
		};
		handler.postDelayed(r, 1000);
	}

	@SuppressWarnings("deprecation")
	public void setDay(DayMonthYear dmy) {

		adapter = new FragmentDayAdapter(getSupportFragmentManager(), dmy, getApplicationContext());
		viewPager.setAdapter(adapter);

		dmyChanger = new DayMonthYear(dmy.getDay(), dmy.getMonth(), dmy.getYear());

		viewPager.setCurrentItem(dmyChanger.getDay());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				dmyChanger.setDay(arg0);
				// displayData(dmyChanger);

				if (arg0 - 1 == Lunar.maxDayOfMonth(dmyChanger.getMonth(), dmyChanger.getYear())) {

					dmyChanger = Lunar.addDay(dmyChanger, 1);

					adapter = new FragmentDayAdapter(getSupportFragmentManager(), dmyChanger, getApplicationContext());
					// displayData(dmyChanger);
					adapter.notifyDataSetChanged();
					viewPager.setAdapter(adapter);
					viewPager.setCurrentItem(1);
				}

				if (arg0 == 0) {

					dmyChanger = Lunar.addDay(dmyChanger, -1);
					// displayData(dmyChanger);
					adapter = new FragmentDayAdapter(getSupportFragmentManager(), dmyChanger, getApplicationContext());

					adapter.notifyDataSetChanged();
					viewPager.setAdapter(adapter);
					viewPager.setCurrentItem(Lunar.maxDayOfMonth(dmyChanger.getMonth(), dmyChanger.getYear()));

				}
				dmyCurrent = dmyChanger;
				dmyt = dmyChanger;
				printInfo(dmyChanger);
				mangNotet.clear();
				mangNotet = displayData(dmyChanger);
				if (mangNotet.size() == 0) {
					setBackground(dmyChanger);
				} else {
					Bitmap bmp = decodeSampledBitmapFromUri(mangNotet.get(0).imageNote, 500, 500);
					BitmapDrawable background = new BitmapDrawable(bmp);
					linearLayout.setBackgroundDrawable(background);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public void setBackground(DayMonthYear dmytt) {

		String uri = "@drawable/img_00";
		int temp = dmytt.getDay() % 10;
		if (temp == 0) {
			uri += 5;
		} else {
			uri += temp;
		}
		int imageResource = getResources().getIdentifier(uri, null, getPackageName());
		Drawable res = getResources().getDrawable(imageResource);
		linearLayout.setBackgroundDrawable(res);
	}

	public void printInfo(DayMonthYear dmy) {
		displayData(dmy);
		tvMonthYear.setText("Tháng " + dmy.getMonth() + " Năm " + dmy.getYear());
		// tvThu.setText(Lunar.THU[Lunar.thu(dmy)]);
		tvCir.setText(Lunar.THU[Lunar.thu(dmy)]);

		DayMonthYear l = Lunar.solar2Lunar(dmy);
		int[] can = Lunar.can(dmy);
		int[] chi = Lunar.chi(dmy);
		String str = "Ngày " + Lunar.CAN[can[0]] + " " + Lunar.CHI[chi[0]] + "\n" + "Tháng " + Lunar.CAN[can[1]] + " "
				+ Lunar.CHI[chi[1]] + "\n" + "Năm " + Lunar.CAN[can[2]] + " " + Lunar.CHI[chi[2]];
		tvLunar.setText("Ngày " + l.getDay() + "\n" + "Tháng " + l.getMonth() + "\n" + "Năm " + l.getYear());
		tvText.setText("" + str);
	}

	@Override
	public int getMaxDay() {
		return Lunar.maxDayOfMonth(dmyChanger.getMonth(), dmyChanger.getYear());
	}

	@Override
	public int getMaxDayPre() {
		if (dmyChanger.getMonth() > 1)
			return Lunar.maxDayOfMonth(dmyChanger.getMonth() - 1, dmyChanger.getYear());
		else
			return 31;

	}

	public void loadData() {
		// ConnectivityManager connManager = (ConnectivityManager)
		// getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo mWifi =
		// connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// NetworkInfo mMobileInternet =
		// connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		//
		// if (mWifi.isConnected()) {
		// getWUndergroundWeather();
		// } else if (!mMobileInternet.isConnected()) {
		// Toast.makeText(LunarActivity.this, "Không kết nối mạng",
		// Toast.LENGTH_LONG).show();
		// imgBt.setEnabled(false);
		//
		// } else {
		// getWUndergroundWeather();
		// }
		try {
			if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {

				ActivityCompat.requestPermissions(this, new String[] { mPermission }, REQUEST_CODE_PERMISSION);
			}
			PackageManager packageManager = this.getPackageManager();
			boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
			gps = new GPSTracker(LunarActivity.this);
			if (hasGPS) {
				new AsyncTask<Void, Void, Location>() {

					@Override
					protected Location doInBackground(Void... params) {
						
						lock.lock();
						int count = 0;
						while (gps.location == null && count < 1000){
							location  = gps.getLocation();
							try {
								Thread.sleep(2000);
								count ++;
								Log.d("log dem ", ""+count);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						lock.unlock();
						return location;
					}

					@Override
					protected void onPostExecute(Location result) {
						// check if GPS enabled
						if (gps.canGetLocation()) {

							double latitude = gps.getLatitude();
							double longitude = gps.getLongitude();
							getWUndergroundWeather();
							Toast.makeText(getApplicationContext(),
									"Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
						} else {
							gps.showSettingsAlert();
						}
						super.onPostExecute(result);
					}
					

		
				}.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void renderWeather() {
		weatherIcon = new WeatherIcon(imgBt, null, "@drawable/w", forecast.getWeatherIcon());
		weatherIcon.setIcon(getPackageName(), getBaseContext());
		tvLocation.setText(forecast.getTemp() + "°C");
		imgBt.setClickable(true);
	}

	public void getWUndergroundWeather() {

		Thread t = new Thread() {
			public void run() {
				locationKey = new LocationKey();
				try {
					lock.lock();
					locationKey.loadData(gps.getLatitude(), gps.getLongitude());
					lock.unlock();
					if (locationKey != null) {
						forecast = new WeatherDate(locationKey.getLocatioKey());
						try {
							forecast.loadData();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (forecast != null) {
							renderWeather();
						}
					}
				});
			}
		};
		t.start();

	}

	private List<Note> displayData(DayMonthYear dmytt) {
		String date;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, dmytt.getDay());
		cal.set(Calendar.MONTH, dmytt.getMonth() - 1);
		cal.set(Calendar.YEAR, dmytt.getYear());
		Date dat = cal.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		String temp = ft.format(dat);
		for (Note note : mang) {
			if (temp.equals(note.date)) {
				mangNote.add(note);
			}
		}
		return mangNote;
	}

	private void loadRe() {
		mang = new ArrayList<Note>();
		Cursor note = dbb.getData("SELECT*FROM Note");
		while (note.moveToNext()) {
			mang.add(new Note(note.getString(1), note.getString(2), note.getString(3), note.getString(4),
					note.getInt(0)));
		}
	}

	@Override
	protected void onResume() {
		loadRe();
		mangNotet.clear();
		mangNotet = displayData(dmyCurrent);
		if (mangNotet.size() == 0) {
			setBackground(dmyCurrent);
		} else {
			// Bitmap bmp =
			// decodeSampledBitmapFromUri(mangNotet.get(0).imageNote, 500, 500);
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(mangNotet.get(0).imageNote), size.x, size.y,
					true);
			BitmapDrawable background = new BitmapDrawable(bmp);
			linearLayout.setBackgroundDrawable(background);
		}
		super.onResume();
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
