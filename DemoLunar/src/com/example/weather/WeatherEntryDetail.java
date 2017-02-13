package com.example.weather;

public class WeatherEntryDetail {
	private String iconDay;
	private String iconNight;

	private String sunHours;
	private String rainHours;

	private String rainProbability;
	private String snowProbability;
	private String cloudCover;

	private String rainValue;
	private String snowValue;

	private String windMin;
	private String windMax;
	private String windLocalized;

	public String getRainProbability() {
		return rainProbability;
	}

	public String getRainValue() {
		return rainValue;
	}

	public String getRainHours() {
		return rainHours;
	}

	public String getSnowProbability() {
		return snowProbability;
	}

	public String getSnowValue() {
		return snowValue;
	}

	public String getWindMin() {
		return windMin;
	}

	public String getWindMax() {
		return windMax;
	}

	public String getWindLocalized() {
		return windLocalized;
	}

	public String getCloudCover() {
		return cloudCover;
	}

	public void setRainProbability(String rainProbability) {
		this.rainProbability = rainProbability;
	}

	public void setRainValue(String rainValue) {
		this.rainValue = rainValue;
	}

	public void setRainHours(String rainHours) {
		this.rainHours = rainHours;
	}

	public void setSnowProbability(String snowProbability) {
		this.snowProbability = snowProbability;
	}

	public String getIconDay() {
		return iconDay;
	}

	public String getIconNight() {
		return iconNight;
	}

	public void setIconDay(String iconDay) {
		this.iconDay = iconDay;
	}

	public void setIconNight(String iconNight) {
		this.iconNight = iconNight;
	}

	public void setSnowValue(String snowValue) {
		this.snowValue = snowValue;
	}

	public void setWindMin(String windMin) {
		this.windMin = windMin;
	}

	public void setWindMax(String windMax) {
		this.windMax = windMax;
	}

	public void setWindLocalized(String windLocalized) {
		this.windLocalized = windLocalized;
	}

	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
	}

	public String getSunHours() {
		return sunHours;
	}

	public void setSunHours(String sunHours) {
		this.sunHours = sunHours;
	}
}
