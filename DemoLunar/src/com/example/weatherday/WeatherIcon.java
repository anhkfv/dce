package com.example.weatherday;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WeatherIcon  extends Activity{
	private String uri = "";
	private ImageButton imgBt;
	private ImageView imgV;
	private String path;
	private String rootPath;
	private LinearLayout linearLayout;
	public WeatherIcon(ImageButton imgBt,ImageView imgV,String rootPath,String path){
		this.imgBt=imgBt;
		this.imgV=imgV;
		this.path=path;
		this.rootPath=rootPath;
	}
	public WeatherIcon(LinearLayout ln,String path){
		this.path=path;
		this.linearLayout=ln;
	}
	@SuppressWarnings("deprecation")
	public void setIcon(String pkg,Context mContext){
		uri=rootPath+path;
		Log.d("uri img",uri);
		int imageResource = mContext.getResources().getIdentifier(uri, null, pkg);
		Drawable res = mContext.getResources().getDrawable(imageResource);
		if(imgBt!=null){
			imgBt.setBackgroundDrawable(res);
		}
		if(imgV!=null){
			Log.d("imgV",""+imgV);
			imgV.setImageDrawable(res);
		}
	}
	public void setBackGround(String pkg,Context mContext){
		uri=path;
		Log.d("imgPath",""+uri);
		int imageResource = mContext.getResources().getIdentifier(uri, null, pkg);
		Drawable res = mContext.getResources().getDrawable(imageResource);
		if(linearLayout!=null){
			linearLayout.setBackgroundDrawable(res);
		}
	}
}
