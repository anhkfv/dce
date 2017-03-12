package com.example.weather;

import com.example.demolunar.R;
import com.example.weatherday.WeatherIcon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationWeatherByDay extends Activity {
	InformationDto dto;
	ImageView iconDay,iconNight;
	TextView windV,windP,windVN,windPN,rainP,rainV,rainH,rainPN,rainVN,rainHN;
	TextView snowP,snowV,snowPN,snowVN,cloundC,cloundCN,sunH,tempSlow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_weather_by_day);
		dto = (InformationDto) getIntent().getSerializableExtra("entry");
		init();
		WeatherIcon icon=new WeatherIcon(null, iconDay, "@drawable/w", dto.getIconDay());
		icon.setIcon(getPackageName(), getBaseContext());
		WeatherIcon iconN=new WeatherIcon(null, iconNight, "@drawable/w", dto.getIconNight());
		iconN.setIcon(getPackageName(), getBaseContext());
		windV.setText(dto.getWindMin()+"km/h");
		windP.setText(dto.getWindLocalized());
		windVN.setText(dto.getWindMinN()+"km/h");
		windPN.setText(dto.getWindLocalizedN());
		rainP.setText(dto.getRainProbability()+"%");
		rainV.setText(dto.getRainValue()+"mm");
		rainH.setText(dto.getRainHours()+"h");
		rainPN.setText(dto.getRainProbabilityN()+"%");
		rainVN.setText(dto.getRainValueN()+"mm");
		rainHN.setText(dto.getRainHoursN()+"h");
		snowP.setText(dto.getSnowProbability()+"%");
		snowV.setText(dto.getSnowValue()+"cm");
		snowPN.setText(dto.getSnowProbabilityN()+"%");
		snowVN.setText(dto.getSnowValueN()+"cm");
		cloundC.setText(dto.getCloudCover()+"%");
		cloundCN.setText(dto.getCloudCoverN()+"%");
		sunH.setText(dto.getSunHours()+"h");
		tempSlow.setText(""+dto.getTempLow()+"°C"+"---"+dto.getTempHight()+"°C");
		
	}
	private void init(){
		iconDay=(ImageView)findViewById(R.id.imgBauTroi);
		iconNight=(ImageView)findViewById(R.id.imgBauTroiN);
		windV=(TextView)findViewById(R.id.txtMaxWind);
		windP=(TextView)findViewById(R.id.txtMinH);
		windVN=(TextView)findViewById(R.id.txtMaxWindN);
		windPN=(TextView)findViewById(R.id.txtMinHN);
		rainP=(TextView)findViewById(R.id.txtRainP);
		rainV=(TextView)findViewById(R.id.txtRainV);
		rainH=(TextView)findViewById(R.id.txtRainH);
		rainPN=(TextView)findViewById(R.id.txtRainPN);
		rainVN=(TextView)findViewById(R.id.txtRainVN);
		rainHN=(TextView)findViewById(R.id.txtRainHN);
		snowP=(TextView)findViewById(R.id.txtSnowP);
		snowV=(TextView)findViewById(R.id.txtSnowV);
		snowPN=(TextView)findViewById(R.id.txtSnowPN);
		snowVN=(TextView)findViewById(R.id.txtSnowVN);
		cloundC=(TextView)findViewById(R.id.txtClound);
		cloundCN=(TextView)findViewById(R.id.txtCloundN);
		sunH=(TextView)findViewById(R.id.txtHourSun);
		tempSlow=(TextView)findViewById(R.id.txtCurrentAddressName);
	}

}
