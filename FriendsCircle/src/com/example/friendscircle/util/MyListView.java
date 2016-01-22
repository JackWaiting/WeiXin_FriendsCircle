package com.example.friendscircle.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.friendscircle.R;
import com.example.friendscircle.activity.MyImageActivity;

public class MyListView extends ListView implements OnScrollListener {
	private View headView;
	private int headViewHeight;
	int currentScrollState;
	private float lastDownY;
	private int deltaCount;
	private ArrayList<String> data;
	private ArrayAdapter<String> adapter;
	private int currentState;
	private final int DECREASE_HEADVIEW_PADDING = 100;
	private final int LOAD_DATA = 101;
	private final int DISMISS_CIRCLE = 102;
	private ImageView circle,logo;
	private int CircleMarginTop;

	private int firstVisibleItem;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DECREASE_HEADVIEW_PADDING:
				setHeadViewPaddingTop(deltaCount);
				setCircleMargin();
				break;
			case LOAD_DATA:
				// clearCircleViewMarginTop();
				Thread thread = new Thread(new DismissCircleThread());
				thread.start();
				currentState = LoadState.LOADSTATE_IDLE;
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
				break;
			case DISMISS_CIRCLE:
				int margin = msg.arg1;
				setCircleMargin(margin);
				if (margin == 0) {
					CircleAnimation.stopRotateAnmiation(circle);
				}
				break;

			}
		}

	};

	protected void setCircleMargin(int margin) {
		MarginLayoutParams lp = (MarginLayoutParams) circle.getLayoutParams();
		lp.topMargin = margin;
		circle.setLayoutParams(lp);
	}

	protected void setCircleMargin() {
		// TODO Auto-generated method stub
		MarginLayoutParams lp = (MarginLayoutParams) circle.getLayoutParams();
		lp.topMargin = CircleMarginTop - headView.getPaddingTop();
		circle.setLayoutParams(lp);
	}

	private class DecreaseThread implements Runnable {
		private final static int TIME = 25;
		private int decrease_length;

		public DecreaseThread(int count) {
			decrease_length = count / TIME;
		}

		@Override
		public void run() {
			for (int i = 0; i < TIME; i++) {
				if (i == 24) {
					deltaCount = 0;
				} else {
					deltaCount = deltaCount - decrease_length;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Message msg = Message.obtain();
				msg.what = DECREASE_HEADVIEW_PADDING;
				handler.sendMessage(msg);
			}
		}
	}

	public MyListView(Context context, ArrayList<String> dataList) {
		this(context);
		this.data = dataList;
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHeadView(context);
	}

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initHeadView(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initHeadView(context);
	}

	private void initHeadView(final Context context) {

		headView = LayoutInflater.from(context).inflate(R.layout.header, null);
		addHeaderView(headView);
		circle = (ImageView) headView.findViewById(R.id.circleprogress);
		logo = (ImageView) headView.findViewById(R.id.imageView2);
		logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,MyImageActivity.class));
			}
		});
		headView.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						if (headView.getMeasuredHeight() > 0) {
							headViewHeight = headView.getMeasuredHeight();
							headView.getViewTreeObserver()
									.removeOnPreDrawListener(this);
						}
						return true;
					}
				});
		setOnScrollListener(this);
		currentScrollState = OnScrollListener.SCROLL_STATE_IDLE;
		currentState = LoadState.LOADSTATE_IDLE;
		firstVisibleItem = 0;
		CircleMarginTop = 76;
		setSelector(new ColorDrawable(Color.TRANSPARENT));
	    setItemsCanFocus(true);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		float downY = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastDownY = downY;
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float downY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (deltaCount > 0 && currentState != LoadState.LOADSTATE_LOADING
					&& firstVisibleItem == 0
					&& headView.getBottom() >= headViewHeight) {
				decreasePadding(deltaCount);
				loadDataForThreeSecond();
				startCircleAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			int nowDeltaCount = (int) ((downY - lastDownY) / 3.0);
			int grepDegree = nowDeltaCount - deltaCount;
			deltaCount = nowDeltaCount;
			if (deltaCount > 0 && currentState != LoadState.LOADSTATE_LOADING
					&& firstVisibleItem == 0
					&& headView.getBottom() >= headViewHeight) {
				setHeadViewPaddingTop(deltaCount);
				setCircleViewStay();
				CircleAnimation.startCWAnimation(circle,
						5 * (deltaCount - grepDegree), 5 * deltaCount);
			}
			break;
		}

		return super.onTouchEvent(event);
	}

	private void startCircleAnimation() {
		// TODO Auto-generated method stub
		CircleAnimation.startRotateAnimation(circle);

	}

	private class DismissCircleThread implements Runnable {
		private final int COUNT = 10;
		private final int deltaMargin;

		public DismissCircleThread() {
			deltaMargin = CircleMarginTop / COUNT;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int temp = 0;
			for (int i = 0; i <= COUNT; i++) {
				if (i == 10) {
					temp = 0;
				} else {
					temp = CircleMarginTop - deltaMargin * i;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Message msg = Message.obtain();
				msg.what = DISMISS_CIRCLE;
				msg.arg1 = temp;
				handler.sendMessage(msg);
			}

		}
	}

	private void setCircleViewStay() {
		// TODO Auto-generated method stub
		if (headView.getPaddingTop() > (CircleMarginTop)) {
			MarginLayoutParams lp = (MarginLayoutParams) circle
					.getLayoutParams();
			lp.topMargin = CircleMarginTop - headView.getPaddingTop();
			circle.setLayoutParams(lp);
		}
	}

	private void loadDataForThreeSecond() {
		currentState = LoadState.LOADSTATE_LOADING;
		data.add("New Data");
		Message msg = Message.obtain();
		msg.what = LOAD_DATA;
		handler.sendMessageDelayed(msg, 3000);
	}

	public void setAdapter(ArrayAdapter<String> adapter) {
		super.setAdapter(adapter);
		this.adapter = adapter;
	}

	private void setHeadViewPaddingTop(int deltaY) {
		headView.setPadding(0, deltaY, 0, 0);
	}

	private void decreasePadding(int count) {
		Thread thread = new Thread(new DecreaseThread(count));
		thread.start();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		// System.out.println(headView.getHeight());
		this.firstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

		switch (scrollState) {
		case SCROLL_STATE_FLING:
			currentScrollState = SCROLL_STATE_FLING;
			break;
		case SCROLL_STATE_IDLE:
			currentScrollState = SCROLL_STATE_IDLE;
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			currentScrollState = SCROLL_STATE_TOUCH_SCROLL;
			break;

		}

	}
}
