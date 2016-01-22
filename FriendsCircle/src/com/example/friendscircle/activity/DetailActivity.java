package com.example.friendscircle.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.friendscircle.R;
import com.example.friendscircle.adapter.ShowImgAdapter;
import com.ionesmile.datasource.WeixinDataSource;

public class DetailActivity extends Activity {
	private GridView gvImg, zanImg;
	private ImageView logoImg,back;
	private TextView name, time, content;
	private ArrayList<String> listfile = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		gvImg = (GridView) findViewById(R.id.gv_img);
		logoImg = (ImageView) findViewById(R.id.images);
		back = (ImageView) findViewById(R.id.back);
		zanImg = (GridView) findViewById(R.id.praise);
		name = (TextView) findViewById(R.id.name);
		time = (TextView) findViewById(R.id.time);
		content = (TextView) findViewById(R.id.content);
		name.setText(WeixinDataSource.getInstance(getApplicationContext())
				.getUsername());
		time.setText(new SimpleDateFormat("MM月dd号HH:mm")
				.format(WeixinDataSource.getInstance(getApplicationContext())
						.getDate()));
		logoImg.setImageResource(WeixinDataSource.getInstance(
				getApplicationContext()).getmLogo());
		initimage();
		gvImg.setAdapter(new ShowImgAdapter(this, listfile));
		zanImg.setAdapter(new LogoAdapter(WeixinDataSource.getInstance(
				getApplicationContext()).getZanLogos()));
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		gvImg.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				deleteFilePathCache();
				return true;
			}
		});
	}

	class LogoAdapter extends BaseAdapter {

		private int[] images;

		public LogoAdapter(int[] images) {
			this.images = images;
		}

		@Override
		public int getCount() {
			return images.length;
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
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(DetailActivity.this).inflate(
						R.layout.zan_image, null);
				holder.image = (ImageView) convertView.findViewById(R.id.images);
				
				convertView.setTag(holder);
			} 
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setImageResource(images[position]);
			return convertView;
		}
	}
	private static class ViewHolder {
		private ImageView image; // 头像
	}

	// 获得传过来的图片
	private void initimage() {
		ArrayList<String> cfile = readFilePathCache();
		if (cfile != null) {
			listfile = cfile;
		}

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getStringArrayList("files") != null) {
				ArrayList<String> bfile = bundle.getStringArrayList("files");
				for (String item : bfile) {
					listfile.add(item);
				}

				System.out.println(listfile);
				// 将路劲写到缓存
				writeFilePathCache(listfile);

			}

		}
	}

	/**
	 * 读取缓存里的路径
	 */
	private ArrayList<String> readFilePathCache() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					"/sdcard/friendscircle/imgges/ShowImage.bin"));
			return (ArrayList<String>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除文件路劲
	 * 
	 * @return
	 */
	private boolean deleteFilePathCache() {
		try {
			File pathFile = new File(
					"/sdcard/friendscircle/imgges/ShowImage.bin");
			if (pathFile.exists()) {
				pathFile.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 把路径写到缓存里
	 */
	private void writeFilePathCache(ArrayList<String> result) {
		File file = new File("/sdcard/friendscircle/imgges");
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file.getPath() + "/ShowImage.bin"));
			oos.writeObject(result);
			oos.close();
			System.out.println("写入成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
