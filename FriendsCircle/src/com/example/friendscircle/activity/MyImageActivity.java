package com.example.friendscircle.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.friendscircle.R;
import com.example.friendscircle.adapter.MyImageAdapter;
import com.example.friendscircle.util.MyListView;
import com.ionesmile.datasource.DataSouceActivity;

public class MyImageActivity extends Activity {
	private LinearLayout layout;
	private ListView listView;
	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_image);
		layout = (LinearLayout) findViewById(R.id.layout);
		listView = (ListView) findViewById(R.id.listview);
		imageView =(ImageView) findViewById(R.id.images);
		imageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(MyImageActivity.this,DataSouceActivity.class));
				return false;
			}
		});
		ArrayList<String> list = new ArrayList<String>();
		MyListView listView = new MyListView(this, list);
		layout.addView(listView);
		listView.setAdapter(new MyImageAdapter(this,2,R.drawable.photo));
		listView.setDivider(null);
		listView.setDividerHeight(0);
		
		
	}


}
