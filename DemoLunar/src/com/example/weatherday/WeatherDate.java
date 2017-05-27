package com.example.weatherday;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.util.Log;

public class WeatherDate {
	private String _url = "http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/";

	private static String weatherIcon;
	private static String temp;

	public WeatherDate(String key) {
		this._url = _url + key + "?apikey=YJLHdPEDXZ3pcfL8pZxuoeGzgBXKOCef&language=vi-vn&metric=true";
	}

	public void loadData() throws IOException {

		// Connect to the URL
		Log.d("url", "" + _url);
		URL url = new URL(_url);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// get the root element
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

		// get number of days
		JsonArray rootobj = root.getAsJsonArray();
		System.out.println("thu" + rootobj.toString());

		int icon = rootobj.get(0).getAsJsonObject().get("WeatherIcon").getAsInt();
		Log.d("icon:", "" + icon);
		setWeatherIcon("" + icon);
		Float tempT = rootobj.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Value").getAsFloat();
		Log.d("temp:", "" + tempT);
		setTemp("" + tempT);
	}

	public String getWeatherIcon() {
		return weatherIcon;
	}

	public static String getTemp() {
		return temp;
	}

	public static void setWeatherIcon(String weatherIcon) {
		WeatherDate.weatherIcon = weatherIcon;
	}

	public static void setTemp(String temp) {
		WeatherDate.temp = temp;
	}

}
