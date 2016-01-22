package com.example.friendscircle.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.friendscircle.R;
import com.loopj.android.image.SmartImageView;

public class ShowImgAdapter extends BaseAdapter {

	private ArrayList<String> arrayList;
	private Context context;

	public ShowImgAdapter(Context context, ArrayList<String> arrayList) {
		this.arrayList = arrayList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.showimg_adapter, null);
			holder.imageView = (SmartImageView) convertView
					.findViewById(R.id.show_imgview);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		String path = arrayList.get(position);
		Bitmap bitmap = getLoacalBitmap(path); // 从本地取图片(在cdcard中获取) //
		holder.imageView.setImageBitmap(bitmap); // 设置Bitmap
		return convertView;
	}

	private class Holder {
		public SmartImageView imageView;
	}

	/**
	 * 加载本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String fileName) {
		
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds =false;
            options.inSampleSize = 4;//图片宽高都为原来的二分之一，即图片为原来的四分之一 
            Bitmap b = BitmapFactory.decodeFile(fileName, options); 
            return b;
	}
}
