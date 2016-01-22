package com.example.friendscircle.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class CircleAnimation {

	public static void startCWAnimation(View animateView, float fromDegree, float toDegree) {
		RotateAnimation cwAnimation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		cwAnimation.setFillAfter(true);
		animateView.startAnimation(cwAnimation);
	}

	public static void startRotateAnimation(View animateView) {
		RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(1000);
		rotate.setRepeatCount(Animation.INFINITE);
		rotate.setInterpolator(new LinearInterpolator());
		animateView.startAnimation(rotate);
	}

	public static void stopRotateAnmiation(View animatedView) {
		animatedView.getAnimation().cancel();
	}
}
