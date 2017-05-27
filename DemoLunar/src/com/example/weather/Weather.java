package com.example.weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.util.Log;

public class Weather {
	private static String _url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
	static ArrayList<WeatherEntry> _forecast = new ArrayList<WeatherEntry>();
	private String icon;

	public Weather(String key, String icon) {
		_url = _url + key + "?apikey=YJLHdPEDXZ3pcfL8pZxuoeGzgBXKOCef&language=vi-vn&details=true&metric=true";
		this.icon = icon;
	}

	public void loadData() throws IOException {

		// Connect to the URL
		URL url = new URL(_url);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// get the root element
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

		// get number of days
		try {
			JsonObject rootobj = root.getAsJsonObject();
			System.out.println(rootobj.toString());
			JsonObject headLine = rootobj.get("Headline").getAsJsonObject();

			JsonArray daysJson = rootobj.get("DailyForecasts").getAsJsonArray();

			int n = daysJson.size();
			Log.d("gia tri n:", "" + n);
			Log.d("DailyForecasts:", "" + daysJson);

			// iterate through each day of the forecast
			for (int i = 0; i < n; i++) {

				WeatherEntry entry = new WeatherEntry();
				// date
				// icon
				//entry.setIcon(icon);
				// summary
				String text = headLine.get("Text").getAsString();
				entry.setSummary(text);

				String textCategory = headLine.get("Category").getAsString();
				entry.setCategory(textCategory);

				Long unixTime = daysJson.get(i).getAsJsonObject().get("EpochDate").getAsLong();
				Date x = new Date(unixTime * 1000);
				entry.setDate(x);
				// get the high temperature
				String unit = daysJson.get(i).getAsJsonObject().get("Temperature").getAsJsonObject().get("Maximum")
						.getAsJsonObject().get("Unit").getAsString();
				Float tempHigh = daysJson.get(i).getAsJsonObject().get("Temperature").getAsJsonObject().get("Maximum")
						.getAsJsonObject().get("Value").getAsFloat();

				// get the low temperature
				Float tempLow = daysJson.get(i).getAsJsonObject().get("Temperature").getAsJsonObject().get("Minimum")
						.getAsJsonObject().get("Value").getAsFloat();
				if (unit.equals("F")) {
					entry.setTempHight((int) (tempHigh - 32) / 1.8f);
					entry.setTempLow((int) (tempLow - 32) / 1.8f);
				} else {
					entry.setTempHight(tempHigh);
					entry.setTempLow(tempLow);
				}

				WeatherEntryDetail detail = new WeatherEntryDetail();

				// get the condition
				Float sunHours = daysJson.get(i).getAsJsonObject().get("HoursOfSun").getAsFloat();
				detail.setSunHours("" + sunHours);
				// object day
				JsonObject jsonDay = daysJson.get(i).getAsJsonObject().get("Day").getAsJsonObject();
				String iconDay = jsonDay.get("Icon").getAsString();
				detail.setIconDay(iconDay);
				entry.setIcon(iconDay);

				Float rainHours = jsonDay.get("HoursOfRain").getAsFloat();
				detail.setRainHours("" + rainHours);

				int cloudCover = jsonDay.get("CloudCover").getAsInt();
				detail.setCloudCover("" + cloudCover);

				int rainProbability = jsonDay.get("RainProbability").getAsInt();
				detail.setRainProbability("" + rainProbability);

				int snowProbability = jsonDay.get("SnowProbability").getAsInt();
				detail.setSnowProbability("" + snowProbability);

				Float rainV = jsonDay.get("Rain").getAsJsonObject().get("Value").getAsFloat();
				detail.setRainValue("" + rainV);

				Float snowV = jsonDay.get("Snow").getAsJsonObject().get("Value").getAsFloat();
				detail.setSnowValue("" + snowV);

				Float wind = jsonDay.get("Wind").getAsJsonObject().get("Speed").getAsJsonObject().get("Value")
						.getAsFloat();
				detail.setWindMin("" + wind);

				String local = jsonDay.get("Wind").getAsJsonObject().get("Direction").getAsJsonObject().get("Localized")
						.getAsString();
				detail.setWindLocalized("" + local);
				entry.setWeatherEntryDetail(detail);
				// objectNight
				WeatherEntryDetail detailN = new WeatherEntryDetail();
				JsonObject jsonNight = daysJson.get(i).getAsJsonObject().get("Night").getAsJsonObject();

				String iconNight = jsonNight.get("Icon").getAsString();
				detailN.setIconNight(iconNight);

				Float rainHoursN = jsonNight.get("HoursOfRain").getAsFloat();
				detailN.setRainHours("" + rainHoursN);

				int cloudCoverN = jsonNight.get("CloudCover").getAsInt();
				detailN.setCloudCover("" + cloudCoverN);

				int rainProbabilityN = jsonNight.get("RainProbability").getAsInt();
				detailN.setRainProbability("" + rainProbabilityN);

				int snowProbabilityN = jsonNight.get("SnowProbability").getAsInt();
				detailN.setSnowProbability("" + snowProbabilityN);

				Float rainVN = jsonNight.get("Rain").getAsJsonObject().get("Value").getAsFloat();
				detailN.setRainValue("" + rainVN);

				Float snowVN = jsonNight.get("Snow").getAsJsonObject().get("Value").getAsFloat();
				detailN.setSnowValue("" + snowVN);

				Float windN = jsonNight.get("Wind").getAsJsonObject().get("Speed").getAsJsonObject().get("Value")
						.getAsFloat();
				detailN.setWindMin("" + windN);

				String localN = jsonNight.get("Wind").getAsJsonObject().get("Direction").getAsJsonObject()
						.get("Localized").getAsString();
				detailN.setWindLocalized("" + localN);
				entry.setWeatherEntryDetailN(detailN);

				_forecast.add(entry);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public ArrayList<WeatherEntry> getForecast() {
		return _forecast;
	}

}
