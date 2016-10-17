package com.example.jhtwl.zhcity.Utils;

import android.content.Context;

/**
 * dp 和 px 之间的转化
 */
public class DensityUtils {

	/**
	 * dp转px
	 */
	public static int dp2px(Context ctx, float dp) {
		// dp和px的关系: dp = px/设备密度
		// 获取设备屏幕密度
		float density = ctx.getResources().getDisplayMetrics().density;
		int px = (int) (dp * density + 0.5f);   // 4.9->5 4.4->4
		return px;
	}

	public static float px2dp(Context ctx, int px) {
		float density = ctx.getResources().getDisplayMetrics().density;
		float dp = px / density;

		return dp;
	}
}
