package com.example.friendscircle.imgfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.friendscircle.R;


public class ImgFileListAdapter extends BaseAdapter{

	Context context;
	String filecount="filecount";
	String filename="filename";
	String imgpath="imgpath";
	List<HashMap<String, String>> listdata;
	Util util;
	Bitmap[] bitmaps;
	private int index=-1;
	List<View> holderlist;
	
	public ImgFileListAdapter(Context context,List<HashMap<String, String>> listdata) {
		this.context=context;
		this.listdata=listdata;
		bitmaps=new Bitmap[listdata.size()];
		util=new Util(context);
		holderlist=new ArrayList<View>();
	}
	
	@Override
	public int getCount() {
		return listdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		Holder holder;
		if (arg0 != index && arg0 > index) {
			holder=new Holder();
			arg1=LayoutInflater.from(context).inflate(R.layout.imgfileadapter, null);
			holder.photo_imgview=(ImageView) arg1.findViewById(R.id.filephoto_imgview);
			holder.filecount_textview=(TextView) arg1.findViewById(R.id.filecount_textview);
			holder.filename_textView=(TextView) arg1.findViewById(R.id.filename_textview);
			arg1.setTag(holder);
			holderlist.add(arg1);
		}else{
			holder= (Holder)holderlist.get(arg0).getTag();
			arg1=holderlist.get(arg0);
		}
		
		holder.filename_textView.setText(listdata.get(arg0).get(filename));
		holder.filecount_textview.setText(listdata.get(arg0).get(filecount));
		if (bitmaps[arg0] == null) {
			util.imgExcute(holder.photo_imgview,new ImgCallBack() {
				@Override
				public void resultImgCall(ImageView imageView, Bitmap bitmap) {
					bitmaps[arg0]=bitmap;
					imageView.setImageBitmap(bitmap);
				}
			}, listdata.get(arg0).get(imgpath));
		}
		else {
			holder.photo_imgview.setImageBitmap(bitmaps[arg0]);
		}
		
		return arg1;
	}
	
	class Holder{
		public ImageView photo_imgview;
		public TextView filecount_textview;
		public TextView filename_textView;
	}

	
	
}
