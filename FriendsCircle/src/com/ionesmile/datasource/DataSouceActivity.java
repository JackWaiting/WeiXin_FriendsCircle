package com.ionesmile.datasource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.friendscircle.R;

public class DataSouceActivity extends Activity {

	private EditText etUsername, etZanCount;
	private Button btDate, btTime;
	private ImageView ivLogo;
	private Spinner spZanType;
	private ListView lvComm;
	private WeixinDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_souce);
		initBase();
		setupViews();
		initListener();
		initData();
	}
	
	@Override
	protected void onResume() {
		ivLogo.setImageResource(LogoResouces.LOGOS_ZAN[logoPosition]);
		super.onResume();
	}

	private void initBase() {
		dataSource = WeixinDataSource.getInstance(this.getApplicationContext());
	}

	private void initData() {
		commCalendar = Calendar.getInstance();
		commCalendar.setTimeInMillis(dataSource.getDate());
		btDate.setText(sdfDate.format(commCalendar.getTime()));
		btTime.setText(sdfTime.format(commCalendar.getTime()));
		etUsername.setText(dataSource.getUsername());
		etZanCount.setText(String.valueOf(dataSource.getZanCount()));
		spZanType.setSelection(dataSource.getZanSelectedItemPosition());
		logoPosition = dataSource.getLogoPosition();
	}

	private Calendar commCalendar = Calendar.getInstance();
	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
	
	private void initListener() {
		btDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(DataSouceActivity.this, new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						commCalendar.set(Calendar.YEAR, year);
						commCalendar.set(Calendar.MONTH, monthOfYear);
						commCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						btDate.setText(sdfDate.format(commCalendar.getTime()));
					}
				}, commCalendar.get(Calendar.YEAR), commCalendar.get(Calendar.MONTH), commCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		btTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new TimePickerDialog(DataSouceActivity.this, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						commCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						commCalendar.set(Calendar.MINUTE, minute);
						btTime.setText(sdfTime.format(commCalendar.getTime()));
					}
				}, commCalendar.get(Calendar.HOUR_OF_DAY), commCalendar.get(Calendar.MINUTE), true).show();
			}
		});
		ivLogo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DataSouceActivity.this, LogoSelectActivity.class));
			}
		});
	}

	private void setupViews() {
		etUsername = (EditText) findViewById(R.id.et_username);
		etZanCount = (EditText) findViewById(R.id.et_zan_count);
		btDate = (Button) findViewById(R.id.bt_date);
		btTime = (Button) findViewById(R.id.bt_time);
		ivLogo = (ImageView) findViewById(R.id.iv_logo);
		spZanType = (Spinner) findViewById(R.id.sp_zan_type);
		lvComm = (ListView) findViewById(R.id.lv_comm);
	}
	
	// 添加评论
	public void addComm(View v){
		
	}
	
	public static int logoPosition = 0;
	// 确认修改
	public void confirmData(View v){
		String username = etUsername.getText().toString();
		String zanCount = etZanCount.getText().toString();
		dataSource.setUsername(username);
		if (null != zanCount && !"".equals(zanCount.trim())) {
			int zanNum = Integer.valueOf(zanCount);
			dataSource.setZanCount(zanNum);
		}
		dataSource.setZanType(spZanType.getSelectedItemPosition());
		dataSource.setLogoPosition(logoPosition);
		dataSource.setPublishDate(commCalendar.getTimeInMillis());
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
	}

}
