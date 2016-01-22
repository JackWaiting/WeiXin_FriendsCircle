package com.example.friendscircle.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.friendscircle.R;
import com.example.friendscircle.entity.FriendEntity;
import com.example.friendscircle.util.MyListView;

public class MainActivity extends Activity {
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final String TEMP_JPG_NAME = "temp.jpg";
	private ListView listView;
	private TextView textView;
	private LinearLayout relativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		relativeLayout = (LinearLayout) findViewById(R.id.RelativeLayout);
		listView = (ListView) findViewById(R.id.listview);
		ArrayList<String> list = new ArrayList<String>();
		MyListView listView = new MyListView(this, list);
		relativeLayout.addView(listView);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, list);
		listView.setAdapter(new MyAdapter(setData(), this));
	}

	private List<FriendEntity> setData() {
		List<FriendEntity> friendEntities = new ArrayList<FriendEntity>();
		FriendEntity friendEntity = new FriendEntity();
		friendEntity.setOtherlayout(1);
		friendEntity.setImages(R.drawable.w002);
		friendEntity.setContentimages(R.drawable.s03);
		friendEntity.setName("张刚");
		friendEntity.setContent("我们公司今天的运动会，大家快来赞吧");
		friendEntity.setDate("29分钟前");
		friendEntity.setPraise("涛涛，淦成，瑞瑞，重重");
		friendEntity.setCommentary("不错啊!");
		friendEntities.add(friendEntity);

		FriendEntity friendEntity1 = new FriendEntity();
		friendEntity1.setOtherlayout(1);
		friendEntity1.setImages(R.drawable.w001);
		friendEntity1.setContentimages(R.drawable.s03);
		friendEntity1.setName("盛天奇");
		friendEntity1
				.setContent("对于俄罗斯来说，“伊斯兰国”只是叙利亚的反政府武装之一，冠以“反恐”之名，所有反对巴沙尔政权的都照打不误。");
		friendEntity1.setDate("2小时前 QQ空间动态");
		friendEntity1.setPraise("今夜孤魂");
		friendEntity1.setCommentary("俄罗斯是想取得叙利亚战争的主控权，已此为条件让沙特提高原油价格……");
		friendEntities.add(friendEntity1);
		FriendEntity friendEntity6 = new FriendEntity();
		friendEntity6.setOtherlayout(2);
		friendEntity6.setImages(R.drawable.w004);
		friendEntity6.setContentimages(R.drawable.s03);
		friendEntity6.setName("汪乐");
		friendEntity6.setPraise("弟弟");
		friendEntity6.setContent("我就拿了一张");
		friendEntity6.setDate("一天前");
		friendEntity6.setCommentary("新币才出生，假钞已横行。速看，快传！！！");
		friendEntities.add(friendEntity6);
		FriendEntity friendEntity2 = new FriendEntity();
		friendEntity2.setOtherlayout(2);
		friendEntity2.setPraise("弟弟");
		friendEntity2.setImages(R.drawable.w003);
		friendEntity2.setContentimages(R.drawable.s02);
		friendEntity2.setName("朱威凤");
		friendEntity2.setContent("PAGEONE给你发红包了");
		friendEntity2.setCommentary("[PAGEONE工厂体验店]向您发了一个红包");
		friendEntity2.setDate("一天前");
		friendEntities.add(friendEntity2);
		FriendEntity friendEntity3 = new FriendEntity();
		friendEntity3.setOtherlayout(2);
		friendEntity3.setImages(R.drawable.w004);
		friendEntity3.setContentimages(R.drawable.s01);
		friendEntity3.setPraise("弟弟");
		friendEntity3.setName("汪乐");
		friendEntity3.setContent("大家注意了！");
		friendEntity3.setDate("一天前");
		friendEntity3.setCommentary("千万不要在朋友圈乱点赞，快看，出大事了！");
		friendEntities.add(friendEntity3);
		FriendEntity friendEntity4 = new FriendEntity();
		friendEntity4.setOtherlayout(1);
		friendEntity4.setImages(R.drawable.w005);
		friendEntity4.setContentimages(R.drawable.s03);
		friendEntity4.setName("Jackwaiting");
		friendEntity4
				.setContent("原创品牌感banner制作，活动型电商banner成品制作，设计技巧分享，今天晚上19:30—20:30倪老师与你不见不散，期待大家到来呦~");
		friendEntity4.setDate("2天前");
		friendEntity4.setPraise("寂寞");
		friendEntity4.setCommentary("不错!");
		friendEntities.add(friendEntity4);
		FriendEntity friendEntity5 = new FriendEntity();
		friendEntity5.setOtherlayout(1);
		friendEntity5.setImages(R.drawable.w006);
		friendEntity5.setContentimages(R.drawable.s03);
		friendEntity5.setName("陈云胜");
		friendEntity5
				.setContent("现在我需要做的是闭嘴，然后向前，再去开口说话。用说话的时间去做以前没做的事。加油！！！！！！！");
		friendEntity5.setDate("2天前");
		friendEntity5.setPraise("小松子，路人甲，黑，肖梦君，飞哥");
		friendEntity5.setCommentary("小松子  : 胜哥威武！\n 好  : 加油!加油!");
		friendEntities.add(friendEntity5);
		return friendEntities;
	}

	private Bitmap photo;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {

			if (resultCode == NONE)
				return;

			// 拍照
			if (requestCode == PHOTOHRAPH) {
				// 设置文件保存路径这里放在跟目录下
				File picture = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath(),
						TEMP_JPG_NAME);
				startPhotoZoom(Uri.fromFile(picture));
			}

			if (data == null)
				return;

			// 读取相册缩放图片
			if (requestCode == PHOTOZOOM) {
				/*
				 * Uri image = data.getData();
				 * Toast.makeText(MymessageActivity.this,image+"",
				 * Toast.LENGTH_LONG).show();
				 */

				if (data != null) {
					startPhotoZoom(data.getData());
				} else {
					System.out.println("================");
				}

			}

			// 处理结果
			if (requestCode == PHOTORESOULT) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					// Toast.makeText(MainActivity.this,Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartTableLamp/",
					// Toast.LENGTH_LONG).show();
					photo = extras.getParcelable("data");

					listView.setAdapter(new MyAdapter(setData(), this, photo, 4));
					// ByteArrayOutputStream stream = new
					// ByteArrayOutputStream();
					// comp(photo);
					// photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					// riv_logo.setImageBitmap(photo);
					// 将该bitmap写入缓存，准备上传
					// SelectPicUtil.bitmapToBase64(photo,
					// MymessageActivity.this);
					/*
					 * logoName =
					 * FileUtils.getFilename(MainAppUtil.getCustom().getSusername
					 * ()); FileUtils.writeFile(Constants.LOGO_CACHE_PATH,
					 * logoName, photo);
					 */
				}
			}

			super.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {
			// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*500分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 500f;// 这里设置宽度为500f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

}
