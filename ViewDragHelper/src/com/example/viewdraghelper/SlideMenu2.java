package com.example.viewdraghelper;

import com.nineoldandroids.view.ViewHelper;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SlideMenu2 extends FrameLayout{
	private String TAG = SlideMenu2.class.getSimpleName();
	public SlideMenu2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private ViewDragHelper viewDragHelper;
	private void init(){
		viewDragHelper = ViewDragHelper.create(this, callback);
	}
	
	private View menuView,mainView;
	private int menuWidth,menuHeight,mainWidth;
	private int dragRange;
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(getChildCount()<2){
			throw new IllegalStateException("Your layout must has 2 children or more!");
		}
		menuView = getChildAt(0);
		mainView = getChildAt(1);
		setBackgroundColor(Color.BLACK);
	}
	
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
//		menuView.layout(left, top, right, bottom);
//		mainView.layout(left, top, right, bottom);
//	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		menuWidth = menuView.getMeasuredWidth();
		menuHeight = menuView.getMeasuredHeight();
		mainWidth = mainView.getMeasuredWidth();
		dragRange = (int) (menuWidth * 0.6);
//		ViewHelper.setScaleX(menuView, 0.5f);
//		ViewHelper.setScaleY(menuView, 0.5f);
//		ViewHelper.setTranslationX(menuView, -dragRange/2);
	}
	
	private int lastX,lastY;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		if(mStatus==Status.Open && viewDragHelper.isViewUnder(mainView, x, y)){
			return true;
		}
//		Log.e(TAG, "onInterceptTouchEvent : "+);
		if(viewDragHelper.isViewUnder(mainView, x, y)){
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				int deltaX = x - lastX;
				int deltaY = y - lastY;
				if(Math.abs(deltaX)>Math.abs(deltaY)*2) {
//					Log.e(TAG, "移动斜角太大，拦截事件");
					viewDragHelper.cancel();
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				lastX = 0;
				lastY = 0;
				break;
			}
			lastX = x;
			lastY = y;
		}
		return viewDragHelper.shouldInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(mStatus==Status.Open && viewDragHelper.isViewUnder(mainView, x, y)){
			if(event.getAction()==MotionEvent.ACTION_UP){
				Log.e(TAG, "抬起");
				close();
			}
		}
		viewDragHelper.processTouchEvent(event);
		return true;
	}
	
	private ViewDragHelper.Callback callback = new Callback() {
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child==menuView || child==mainView;
		}
		@Override
		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
//			Log.e(TAG, "onViewPositionChanged   dx: "+dx);
			if(changedView==menuView){
				menuView.layout(0, 0, menuWidth, menuHeight);
				if(mainView.getLeft()>dragRange){
					mainView.layout(dragRange, 0, dragRange+mainWidth, mainView.getBottom());
				}else {
					mainView.layout(mainView.getLeft()+dx, 0, mainView.getRight()+dx, mainView.getBottom());
				}
			}
			
			float percent = mainView.getLeft()/(float)dragRange;
			excuteAnimation(percent);
			
			
			if(mainView.getLeft()==0 && mStatus != Status.Close){
				mStatus = Status.Close;
				if(onDragStatusChangeListener!=null ){
					onDragStatusChangeListener.onClose();
				}
			}else if (mainView.getLeft()==dragRange && mStatus != Status.Open) {
				mStatus = Status.Open;
				if(onDragStatusChangeListener!=null ){
					onDragStatusChangeListener.onOpen();
				}
			}else {
				if(onDragStatusChangeListener!=null){
					onDragStatusChangeListener.onDragging(percent);
				}
			}
			
		}

		private void excuteAnimation(float percent) {
			ViewHelper.setScaleX(menuView, 0.5f + 0.5f * percent);
			ViewHelper.setScaleY(menuView, 0.5f + 0.5f * percent);

			ViewHelper.setScaleX(mainView, 1 - percent * 0.2f);
			ViewHelper.setScaleY(mainView, 1 - percent * 0.2f);

			ViewHelper.setTranslationX(menuView, -dragRange / 2 + dragRange / 2
					* percent);

			ViewHelper.setAlpha(menuView, percent);

			getBackground().setAlpha((int) ((1 - percent) * 255));
		}
		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
//			Log.e(TAG, "onViewReleased ："+(releasedChild==mainView));
			if(mainView.getLeft()>dragRange/2){
				open();
			}else {
				close();
			}
		}
		@Override
		public int getViewHorizontalDragRange(View child) {
			return menuWidth;
		}
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if(child==mainView){
				if(left<0)return 0;
				if(left>dragRange) return dragRange;
			}
			if(child==menuView){
				mainView.layout(mainView.getLeft()+dx, 0, mainView.getRight()+dx, mainView.getBottom());
				menuView.layout(0, 0, menuWidth, menuHeight);
				return 0;
			}
			return left;
		}
	};
	
	public void open(){
		viewDragHelper.smoothSlideViewTo(mainView, dragRange, 0);
		ViewCompat.postInvalidateOnAnimation(SlideMenu2.this);
	}
	
	public void close(){
		viewDragHelper.smoothSlideViewTo(mainView, 0, 0);
		ViewCompat.postInvalidateOnAnimation(SlideMenu2.this);
	}
	
	public void computeScroll() {
		if(viewDragHelper.continueSettling(true)){
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	public OnDragStatusChangeListener getOnDragStatusChangeListener() {
		return onDragStatusChangeListener;
	}

	public void setOnDragStatusChangeListener(OnDragStatusChangeListener onDragStatusChangeListener) {
		this.onDragStatusChangeListener = onDragStatusChangeListener;
	}

	public Status mStatus = Status.Close;;
	public enum Status{
		Open,Close
	}
	
	private OnDragStatusChangeListener onDragStatusChangeListener;
	
	public interface OnDragStatusChangeListener{
		void onOpen();
		void onClose();
		void onDragging(float dragProgress);
	}
	
}
