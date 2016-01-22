package com.ionesmile.datasource;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.friendscircle.bean.CommonBean;
import com.example.friendscircle.bean.IDataSource;

public class WeixinDataSource implements IDataSource{
	
	private static final String ZAN_TYPE_POSITION = "zanTypePosition";
	private static final String COMM_COUNT = "commCount";
	private static final String ZAN_COUNT = "zanCount";
	private static final String USER_LOGO_POSITION = "userLogoPosition";
	private static final String PUBLISH_DATE = "publishDate";
	private static final String USERNAME = "username";
	private static WeixinDataSource instance;
	
	private SharedPreferences mSp;
	
	private WeixinDataSource(Context context) {
		mSp = context.getSharedPreferences("WeixinData", Context.MODE_PRIVATE);
	}
	
	public static WeixinDataSource getInstance(Context context){
		if (instance == null) {
			instance = new WeixinDataSource(context);
		}
		return instance;
	}

	@Override
	public String getUsername() {
		return mSp.getString(USERNAME, "уе╦у");
	}

	@Override
	public long getDate() {
		return mSp.getLong(PUBLISH_DATE, 1448692200000L);
	}

	@Override
	public int getmLogo() {
		int position = mSp.getInt(USER_LOGO_POSITION, 0);
		if (position >= 0 && position < LogoResouces.LOGOS_ZAN.length) {
			return LogoResouces.LOGOS_ZAN[position];
		}
		return 0;
	}

	@Override
	public int[] getZanLogos() {
		int[] result = null;
		switch (getZanSelectedItemPosition()) {
		case 0:
			result = LogoResouces.ZAN_TYPE_1;
			break;
		case 1:
			result = LogoResouces.ZAN_TYPE_2;
			break;
		case 2:
			result = LogoResouces.ZAN_TYPE_3;
			break;
		case 3:
			result = LogoResouces.ZAN_TYPE_4;
			break;
		}
		int zanCount = getZanCount();
		int[] copyArrs = new int[zanCount];
		//System.arraycopy(result, 0, copyArrs, 0, zanCount);
		for (int i = 0; i < zanCount; i++) {
			copyArrs[i] = LogoResouces.LOGOS_ZAN[result[i]];
		}
		return copyArrs;
	}

	@Override
	public int getZanCount() {
		return mSp.getInt(ZAN_COUNT, 0);
	}

	@Override
	public List<CommonBean> getCommonBeans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCommonCount() {
		return mSp.getInt(COMM_COUNT, 0);
	}

	public void setUsername(String username) {
		mSp.edit().putString(USERNAME, username).commit();
	}

	public void setZanCount(int zanNum) {
		mSp.edit().putInt(ZAN_COUNT, zanNum).commit();
	}

	public void setZanType(int selectedItemPosition) {
		mSp.edit().putInt(ZAN_TYPE_POSITION, selectedItemPosition).commit();
	}

	public void setLogoPosition(int logoPosition) {
		mSp.edit().putInt(USER_LOGO_POSITION, logoPosition).commit();
	}

	public void setPublishDate(long publishDate) {
		mSp.edit().putLong(PUBLISH_DATE, publishDate).commit();
	}
	
	public int getLogoPosition(){
		return mSp.getInt(USER_LOGO_POSITION, 0);
	}
	
	public int getZanSelectedItemPosition(){
		return mSp.getInt(ZAN_TYPE_POSITION, 0);
	}
	
	
}
