package com.example.weatherday;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class LocationKey {
	private static String _url = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=x4NmQoNwh7mp0AFE2oWUD5V0pI8ZnbWo&q=";

	private String locationKey;
	private String regionName;
	private String latitude;
	private String longitude;

	public void loadData(Double lat , Double lon) throws IOException {

		// Connect to the URL
		URL url = new URL(_url+lat+","+lon+"&language=vi-vn&details=false&toplevel=true");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// get the root element
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

		// get the ZIP code
		locationKey = root.getAsJsonObject().get("Key").getAsString();
		regionName  = root.getAsJsonObject().get("LocalizedName").getAsString();
//		latitude    = root.getAsJsonObject().get("GeoPosition").getAsJsonObject().get("Latitude").getAsString();
//		longitude   = root.getAsJsonObject().get("GeoPosition").getAsJsonObject().get("Longitude").getAsString();
		latitude = lat.toString();
		longitude = lon.toString();

	}

	public String getLocatioKey() {
		return locationKey;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

}
