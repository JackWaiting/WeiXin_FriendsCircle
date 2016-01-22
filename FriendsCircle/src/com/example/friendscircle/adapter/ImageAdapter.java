package com.example.friendscircle.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.friendscircle.R;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Integer> data;
	private List list;
	private int position;
	private int drawable;
	private int gridviewposition;
	private Bitmap gridviewdrawable;
	public ImageAdapter(Context context) {
		this.context = context;
		this.notifyDataSetChanged();
	}

	public ImageAdapter(Context context, List list, int position) {
		this.context = context;
		this.list = list;
		this.position = position;
	}

	public ImageAdapter(Context context, int position, int drawable) {
		this.context = context;
		this.position = position;
		this.drawable = drawable;
		this.notifyDataSetChanged();
	}
	
	public ImageAdapter(Context context, int position, int drawable,int gridviewposition,Bitmap gridviewdrawable) {
		this.context = context;
		this.position = position;
		this.drawable = drawable;
		this.gridviewposition = gridviewposition;
		this.gridviewdrawable = gridviewdrawable;
		this.notifyDataSetChanged();
	}

	public void notifyData() {
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gridview_image, null);
			holder.image = (ImageView) convertView.findViewById(R.id.images);
			
			if (position > 2) {
				LayoutParams params = (LayoutParams) holder.image.getLayoutParams();
				params.height = 100;
				params.width = 100;
				holder.image.setLayoutParams(params);
			}
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(gridviewposition==4){
			holder.image.setImageResource(R.drawable.x06);	
			holder.image.setImageBitmap(gridviewdrawable);
		}
		else{
			holder.image.setImageResource(drawable);	
		}
		return convertView;
	}

	private static class ViewHolder {
		private ImageView image; // Í·Ïñ
	}
}
