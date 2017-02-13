package com.example.weather;

import java.io.Serializable;

public class InformationDto implements Serializable {
	private int tempHight;
	private int tempLow;
	
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


	private String rainHoursN;

	private String rainProbabilityN;
	private String snowProbabilityN;
	private String cloudCoverN;

	private String rainValueN;
	private String snowValueN;

	private String windMinN;
	private String windMaxN;
	private String windLocalizedN;
	
	public InformationDto(WeatherEntry entry){
		this.tempHight=entry.getTempHight().intValue();
		this.tempLow=entry.getTempLow().intValue();
		WeatherEntryDetail detailDay=entry.getWeatherEntryDetail(); 
		WeatherEntryDetail detailNight=entry.getWeatherEntryDetailN();
		this.sunHours=detailDay.getSunHours();
		this.iconDay=detailDay.getIconDay();
		this.iconNight=detailNight.getIconNight();
		
		this.rainHours=detailDay.getRainHours();

		this.rainProbability=detailDay.getRainProbability();
		this.snowProbability=detailDay.getSnowProbability();
		this.cloudCover=detailDay.getCloudCover();

		this.rainValue=detailDay.getRainValue();
		this.snowValue=detailDay.getSnowValue();

		this.windMin=detailDay.getWindMin();
		this.windMax=detailDay.getWindMax();
		this.windLocalized=detailDay.getWindLocalized();
		
		this.rainHoursN=detailNight.getRainHours();

		this.rainProbabilityN=detailNight.getRainProbability();
		this.snowProbabilityN=detailNight.getSnowProbability();
		this.cloudCoverN=detailNight.getCloudCover();

		this.rainValueN=detailNight.getRainValue();
		this.snowValueN=detailNight.getSnowValue();

		this.windMinN=detailNight.getWindMin();
		this.windMaxN=detailNight.getWindMax();
		this.windLocalizedN=detailNight.getWindLocalized();
		
	}
	public int getTempHight() {
		return tempHight;
	}
	public int getTempLow() {
		return tempLow;
	}
	public String getIconDay() {
		return iconDay;
	}
	public String getIconNight() {
		return iconNight;
	}
	public String getSunHours() {
		return sunHours;
	}
	public String getRainHours() {
		return rainHours;
	}
	public String getRainProbability() {
		return rainProbability;
	}
	public String getSnowProbability() {
		return snowProbability;
	}
	public String getCloudCover() {
		return cloudCover;
	}
	public String getRainValue() {
		return rainValue;
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
	public String getRainHoursN() {
		return rainHoursN;
	}
	public String getRainProbabilityN() {
		return rainProbabilityN;
	}
	public String getSnowProbabilityN() {
		return snowProbabilityN;
	}
	public String getCloudCoverN() {
		return cloudCoverN;
	}
	public String getRainValueN() {
		return rainValueN;
	}
	public String getSnowValueN() {
		return snowValueN;
	}
	public String getWindMinN() {
		return windMinN;
	}
	public String getWindMaxN() {
		return windMaxN;
	}
	public String getWindLocalizedN() {
		return windLocalizedN;
	}
	public void setTempHight(int tempHight) {
		this.tempHight = tempHight;
	}
	public void setTempLow(int tempLow) {
		this.tempLow = tempLow;
	}
	public void setIconDay(String iconDay) {
		this.iconDay = iconDay;
	}
	public void setIconNight(String iconNight) {
		this.iconNight = iconNight;
	}
	public void setSunHours(String sunHours) {
		this.sunHours = sunHours;
	}
	public void setRainHours(String rainHours) {
		this.rainHours = rainHours;
	}
	public void setRainProbability(String rainProbability) {
		this.rainProbability = rainProbability;
	}
	public void setSnowProbability(String snowProbability) {
		this.snowProbability = snowProbability;
	}
	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
	}
	public void setRainValue(String rainValue) {
		this.rainValue = rainValue;
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
	public void setRainHoursN(String rainHoursN) {
		this.rainHoursN = rainHoursN;
	}
	public void setRainProbabilityN(String rainProbabilityN) {
		this.rainProbabilityN = rainProbabilityN;
	}
	public void setSnowProbabilityN(String snowProbabilityN) {
		this.snowProbabilityN = snowProbabilityN;
	}
	public void setCloudCoverN(String cloudCoverN) {
		this.cloudCoverN = cloudCoverN;
	}
	public void setRainValueN(String rainValueN) {
		this.rainValueN = rainValueN;
	}
	public void setSnowValueN(String snowValueN) {
		this.snowValueN = snowValueN;
	}
	public void setWindMinN(String windMinN) {
		this.windMinN = windMinN;
	}
	public void setWindMaxN(String windMaxN) {
		this.windMaxN = windMaxN;
	}
	public void setWindLocalizedN(String windLocalizedN) {
		this.windLocalizedN = windLocalizedN;
	}
	

}
