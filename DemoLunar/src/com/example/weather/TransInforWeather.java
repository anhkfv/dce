package com.example.weather;

import java.io.Serializable;

public class TransInforWeather implements Serializable {
	private static final long serialVersionUID = 1L;
	private String latitude;
	 private String longitude;
	 private String keyLocation;
	 private String location;
	 private String icon;
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getKeyLocation() {
		return keyLocation;
	}
	public String getLocation() {
		return location;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setKeyLocation(String keyLocation) {
		this.keyLocation = keyLocation;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
