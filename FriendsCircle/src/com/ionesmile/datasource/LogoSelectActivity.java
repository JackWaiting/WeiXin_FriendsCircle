package com.ionesmile.datasource;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.friendscircle.R;

public class LogoSelectActivity extends Activity {

	private GridView gvLogos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo_select);
		gvLogos = (GridView) findViewById(R.id.gv_logos);
		gvLogos.setAdapter(new LogoAdapter());
		gvLogos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				DataSouceActivity.logoPosition = position;
				finish();
			}
		});
	}
	
	class LogoAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return LogoResouces.LOGOS_ZAN.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(LogoSelectActivity.this);
			iv.setImageResource(LogoResouces.LOGOS_ZAN[position]);
			return iv;
		}
	}

}
