package com.example.friendscircle.activity;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendscircle.R;
import com.example.friendscircle.util.DecodeUtils;
import com.ionesmile.datasource.WeixinDataSource;

public class ShowBitImgActivity extends Activity {

	private ArrayList<String> listfile = new ArrayList<String>();
	private AtomicInteger what = new AtomicInteger(0);
	private ViewPager advPager;
	private ImageView[] imageViews;
	private ImageView imageView;
	private LinearLayout ll_show_img;
	private TextView tv_time,tv_zan;
	private ImageViewTouch mImage;
	private ViewGroup group;
	static int displayTypeCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_bit_img);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
		init();

		initshowimage();
		initviewpage();
	}

	Matrix imageMatrix;

	private void initviewpage() {
		List<View> advPics = new ArrayList<View>();
		for (int i = 0; i < listfile.size(); i++) {
			String path = listfile.get(i);
			View img = new View(this);
			img = LayoutInflater.from(this).inflate(
					R.layout.activity_show_image_view, null);
			mImage = (ImageViewTouch) img.findViewById(R.id.image);
			Uri imageUri = Uri.parse(path);
			Log.d("image", imageUri.toString());

			final int size = -1; // use the original image size
			Bitmap bitmap = DecodeUtils.decode(this, imageUri, size, size);
			if (null != bitmap) {
				if (null == imageMatrix) {
					imageMatrix = new Matrix();
				} else {
					// get the current image matrix, if we want restore the
					// previous matrix once the bitmap is changed
					// imageMatrix = mImage.getDisplayMatrix();
				}
				mImage.setImageBitmap(bitmap, imageMatrix.isIdentity() ? null
						: imageMatrix, ImageViewTouchBase.ZOOM_INVALID,
						ImageViewTouchBase.ZOOM_INVALID);
			} else {
				Toast.makeText(this, "Failed to load the image",
						Toast.LENGTH_LONG).show();
			}
			// img.setImageBitmap(bitmap);
			// img.setBackgroundResource(R.drawable.ic_launcher);
			advPics.add(img);
		}
		imageViews = new ImageView[advPics.size()];
		for (int i = 0; i < advPics.size(); i++) {

			imageView = new ImageView(this);
			imageView.setVisibility(View.GONE);
			imageView.setLayoutParams(new LayoutParams(20, 20));

			imageView.setPadding(5, 5, 5, 5);

			imageViews[i] = imageView;

			if (i == 0) {
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.vote_true));

			} else {

				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.vote_false));

			}

			group.addView(imageViews[i]);

		}

		advPager.setAdapter(new AdvAdapter(advPics));

		advPager.setOnPageChangeListener(new GuidePageChangeListener());
	}

	private final class GuidePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.vote_true));
				if (arg0 != i) {
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.vote_false));
				}
			}
		}
	}

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}
	}

	private void init() {
		
		// set the default image display type
		//mImage.setDisplayType(DisplayType.FIT_IF_BIGGER);
		advPager = (ViewPager) findViewById(R.id.guidePagers);
		group = (ViewGroup) findViewById(R.id.viewGroup);
		ll_show_img = (LinearLayout) findViewById(R.id.ll_show_img);
		tv_time = (TextView) findViewById(R.id.time);
		tv_zan = (TextView) findViewById(R.id.zan);
		tv_time.setText(new SimpleDateFormat("MM月dd号HH:mm").format(WeixinDataSource.getInstance(getApplicationContext()).getDate()));
		tv_zan.setText(WeixinDataSource.getInstance(getApplicationContext()).getZanCount()+"");
		ll_show_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ShowBitImgActivity.this,DetailActivity.class));
			}
		});
	}

	private void initshowimage() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getStringArrayList("files") != null) {
				ArrayList<String> bfile = bundle.getStringArrayList("files");
				for (String item : bfile) {
					listfile.add(item);
				}
				// String path = listfile.get(1);
				// Bitmap bitmap = getLoacalBitmap(path); // 从本地取图片(在cdcard中获取)
				// //
				// imageView.setImageBitmap(bitmap); // 设置Bitmap
			}

		}
	}

	/**
	 * 加载本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String fileName) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 3;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
		Bitmap b = BitmapFactory.decodeFile(fileName, options);
		return b;
	}

	
}
