package com.example.weather;

import java.io.Serializable;
import java.util.Date;

public class WeatherEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	private Float tempHight;
	private Float tempLow;
	private String summary;
	private String category;
	private String icon;
	private WeatherEntryDetail weatherEntryDetail;
	private WeatherEntryDetail weatherEntryDetailN;

	WeatherEntry() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public String getIcon() {
		return icon;
	}

	public WeatherEntryDetail getWeatherEntryDetail() {
		return weatherEntryDetail;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setWeatherEntryDetail(WeatherEntryDetail weatherEntryDetail) {
		this.weatherEntryDetail = weatherEntryDetail;
	}

	public Float getTempHight() {
		return tempHight;
	}

	public Float getTempLow() {
		return tempLow;
	}

	public String getSummary() {
		return summary;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTempHight(Float tempHight) {
		this.tempHight = tempHight;
	}

	public void setTempLow(Float tempLow) {
		this.tempLow = tempLow;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public WeatherEntryDetail getWeatherEntryDetailN() {
		return weatherEntryDetailN;
	}

	public void setWeatherEntryDetailN(WeatherEntryDetail weatherEntryDetailN) {
		this.weatherEntryDetailN = weatherEntryDetailN;
	}
}
