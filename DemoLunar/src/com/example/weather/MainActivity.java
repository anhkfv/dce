package com.example.weather;

import java.io.IOException;
import java.util.ArrayList;

import com.example.demolunar.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	Handler mHandler = new Handler();
	private ArrayList<WeatherEntry> _forecast = null;
	WeatherCustomAdapter userAdapter;
	private ProgressDialog pd;
	Thread t;
	ListView lstForecast;
	TransInforWeather trans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_weather);
		trans = (TransInforWeather) getIntent().getSerializableExtra("Trans");
		pd = ProgressDialog.show(this, "Thời tiết 5 ngày", "Đang tải dữ liệu..!", true);
		loadData();
	}

	public void loadData() {

		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mMobileInternet = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (mWifi.isConnected()) {
			 getWUndergroundWeather();
		} else if (!mMobileInternet.isConnected()) {
			TextView txtZipCode = (TextView) findViewById(R.id.txtRegionCode);
			txtZipCode.setText("Không thể kết nối mạng");
			pd.hide();
			pd.dismiss();
		} else {
			 getWUndergroundWeather();
		}
	}

	public void renderWeather(String summary) {
		CharSequence region = "";
		CharSequence latitude = "";
		CharSequence longitude = "";

		if (trans != null) {
			region = "Địa điểm : " + trans.getLocation();
			latitude = "Latitude : " + trans.getLatitude();
			longitude = "Longitude : " + trans.getLongitude();
		}
		TextView txtZipCode = (TextView) findViewById(R.id.txtRegionCode);
		txtZipCode.setText(region);
		TextView txtLatitude = (TextView) findViewById(R.id.txtLatitude);
		txtLatitude.setText(latitude);
		TextView txtLongitude = (TextView) findViewById(R.id.txtLongitude);
		txtLongitude.setText(longitude);
		TextView txtSummary = (TextView) findViewById(R.id.textSumary);
		txtSummary.setText(summary);

		// render the forecast in the list

		lstForecast = (ListView) findViewById(R.id.lstForecast);
		userAdapter = new WeatherCustomAdapter(MainActivity.this, R.layout.activity_weather_row, _forecast);
		lstForecast.setAdapter(userAdapter);
		lstForecast.setItemsCanFocus(false);
		lstForecast.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent it = new Intent(MainActivity.this, InformationWeatherByDay.class);
				Log.d("nhiet do ngay:",""+_forecast.get(position).getTempHight() );
				InformationDto dto=new InformationDto(_forecast.get(position));
				Bundle bundle = new Bundle();
				bundle.putSerializable("entry", dto);
				it.putExtras(bundle);
				startActivity(it);
			}
			
		});
		pd.hide();
		pd.dismiss();
	}

	public void getWUndergroundWeather() {
		Thread t = new Thread() {
			public void run() {
				Weather forecast = new Weather(trans.getKeyLocation(),trans.getIcon());
				try {
					if (forecast.getForecast().size() != 0) {
						forecast.getForecast().clear();

					}
					forecast.loadData();

					_forecast = forecast.getForecast();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						renderWeather(_forecast.get(0).getSummary());
					}
				});
			}
		};
		t.start();

	}
}
