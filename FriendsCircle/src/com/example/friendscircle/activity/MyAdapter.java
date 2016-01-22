package com.example.friendscircle.activity;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.friendscircle.R;
import com.example.friendscircle.adapter.ImageAdapter;
import com.example.friendscircle.dialog.ActionSheetDialog;
import com.example.friendscircle.entity.FriendEntity;

public class MyAdapter extends BaseAdapter {
	private List<FriendEntity> data;
	private Context context;
	private Bitmap bitmap;
	private int openbitmap;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final String TEMP_JPG_NAME = "temp.jpg";
    private int positions;
	public MyAdapter(List<FriendEntity> list, Context context) {
		data = list;
		this.context = context;
	}
	
	public MyAdapter(List<FriendEntity> list, Context context,Bitmap bitmap,int openbitmap) {
		data = list;
		this.context = context;
		this.bitmap = bitmap;
		this.openbitmap = openbitmap;
		notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (data.get(position).getOtherlayout() == 1) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.circle_item, null);
				holder = new ViewHolder();
				holder.gridView = (GridView) convertView
						.findViewById(R.id.gridView);
				holder.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				// gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.commentary = (TextView) convertView
						.findViewById(R.id.commentary);
				holder.date = (TextView) convertView
						.findViewById(R.id.publish_time);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.praise = (TextView) convertView
						.findViewById(R.id.praise);
				holder.images = (ImageView) convertView
						.findViewById(R.id.images);
				holder.contentimage = (ImageView) convertView
						.findViewById(R.id.contentimage);
				final TextView more = (TextView) convertView
						.findViewById(R.id.more);
				final ImageButton button = (ImageButton) convertView
						.findViewById(R.id.imgButton);
				
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
					}
				});
				more.getViewTreeObserver().addOnPreDrawListener(
						new OnPreDrawListener() {

							@Override
							public boolean onPreDraw() {
								/*
								 * ViewHolder holder = new ViewHolder(); // TODO
								 * Auto-generated method stub if
								 * (holder.content.getLineCount() >= 4) {
								 * more.setVisibility(View.VISIBLE); }
								 */
								return true;
							}
						});
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			if(position ==0 &&openbitmap!=4){
				holder.gridView.setAdapter(new ImageAdapter(context,3,R.drawable.w002,0,bitmap));
				openbitmap = 0;
			}
			
			if(position ==0 &&openbitmap==4){
				holder.gridView.setAdapter(new ImageAdapter(context,3,R.drawable.w002,4,bitmap));
				openbitmap = 0;
			}
			/*if(position ==1){
				holder.gridView.setAdapter(new ImageAdapter(context,1,R.drawable.q01));
			}*/
			
			if(position ==5){
				holder.gridView.setAdapter(new ImageAdapter(context,1,R.drawable.x03));
			}
			
			if(position ==6){
				holder.gridView.setAdapter(new ImageAdapter(context,1,R.drawable.q03));
			}
			
			final ViewHolder holdergridview = holder;
			holder.gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View arg1,
						int positions, long arg3) {
					/*if(position ==0){
						holdergridview.gridView.setAdapter(new ImageAdapter(context,3,R.drawable.w002,positions,R.drawable.w003));
					}*/
					showLogoSwitchWindow();
				}
			});
			holder.content.setText(data.get(position).getContent());
			holder.commentary.setText(data.get(position).getCommentary());
			holder.date.setText(data.get(position).getDate());
			holder.name.setText(data.get(position).getName());
			holder.praise.setText(data.get(position).getPraise());
			holder.images.setBackgroundResource(data.get(position).getImages());
		} else {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.otherlayout_item, null);
				holder = new ViewHolder();
				
				holder.gridView = (GridView) convertView
						.findViewById(R.id.gridView);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.commentary = (TextView) convertView
						.findViewById(R.id.commentary);
				holder.praise = (TextView) convertView
						.findViewById(R.id.praise);
				holder.date = (TextView) convertView
						.findViewById(R.id.publish_time);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.contentimage = (ImageView) convertView
						.findViewById(R.id.contentimage);
				holder.images = (ImageView) convertView
						.findViewById(R.id.images);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.content.setText(data.get(position).getContent());
			holder.date.setText(data.get(position).getDate());
			holder.name.setText(data.get(position).getName());
			holder.commentary.setText(data.get(position).getCommentary());
			if(data.get(position).getContentimages()!=0 ){
				holder.contentimage.setBackgroundResource(data.get(position)
						.getContentimages());
			}
			
			holder.images.setBackgroundResource(data.get(position).getImages());
		}
		return convertView;
	}
    //展现对话框
    private void showLogoSwitchWindow() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                takePicture();
                            }
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openAlbum();
                            }
                        })
                .show();
    }
    
    // 打开相册
    private void openAlbum() {
      /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image*//*");*/

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
     /*
       Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);*/
        ((MainActivity) context).startActivityForResult(intent, PHOTOZOOM);
    }

    // 拍照
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), TEMP_JPG_NAME)));
        ((MainActivity) context).startActivityForResult(intent, PHOTOHRAPH);
    }
    
    
    
	private static class ViewHolder {
		private TextView name;
		private ImageView images; // 头像
		private ImageView contentimage; // 头像
		private TextView content; // 内容
		private TextView date; // 发表时间
		private TextView praise; // 点赞人
		private TextView commentary; // 评论
		private GridView gridView;
	}

}
