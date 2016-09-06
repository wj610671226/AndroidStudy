package com.example.jhtwl.customview.MyCustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jhtwl.customview.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jhtwl on 16/9/6.
 */
public class RefreshView extends ListView implements AbsListView.OnScrollListener {
    private View headerView;
    private ImageView iv_arrow;
    private ProgressBar pb_rotate;
    private TextView tv_state, tv_time;
    private View footerView;
    private int footerViewHeight;

    private int headerViewHeight;
    private int downY;

    private final int PULL_REFRESH = 0; // 下拉刷新的状态
    private final int RELEASE_REFRESH = 1; // 松开刷新的状态
    private final int REFRESHING = 2; // 正在刷新的状态
    private int currentState = PULL_REFRESH;

    private RotateAnimation upAnimation, downAnimation;

    private boolean isLoadingMore = false; // 当前是否正在处于加载更多


    private OnRefreshListener listener;

    public RefreshView(Context context) {
        super(context);
        init();
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnScrollListener(this);
        initHeaderView();
        initRotateAnimation();
        initFooterView();
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.layout_footer, null);
        // 主动通知系统去测量该View
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        addFooterView(footerView);
    }

    private void initRotateAnimation() {
        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);
        downAnimation = new RotateAnimation(-180,  -360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.header_layout, null);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
        tv_state = (TextView) headerView.findViewById(R.id.tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        headerView.measure(0, 0);// 主动通知系统去测量该View
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0 , 0);

        addHeaderView(headerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == REFRESHING) {
                    break;
                }
                int deltay = (int) (ev.getY() - downY);
                int paddingTop = -headerViewHeight + deltay;
                if (paddingTop > -headerViewHeight && getFirstVisiblePosition() == 0) {
                    headerView.setPadding(0, paddingTop, 0, 0);

                    if (paddingTop >= 0 && currentState == PULL_REFRESH) {
                        // 从下拉刷新进入松开状态
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop >= 0 && currentState == RELEASE_REFRESH) {
                        // 进入下拉刷新状态
                        currentState = PULL_REFRESH;
                        refreshHeaderView();
                    }
                    // 拦截TouchMove，不让listView处理该次move事件，会造成listView无法滑动
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentState == PULL_REFRESH) {
                    // 隐藏headerView
                    headerView.setPadding(0 , -headerViewHeight, 0 ,0);
                } else if (currentState == RELEASE_REFRESH) {
                    headerView.setPadding(0, 0 , 0, 0);
                    currentState = REFRESHING;
                    refreshHeaderView();

                    if (listener != null) {
                        listener.onPullRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    // 根据currentState来更新headerView
    private void refreshHeaderView() {
        switch (currentState) {
            case PULL_REFRESH:
                tv_state.setText("下拉刷新");
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                tv_state.setText("松开刷新");
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_rotate.setVisibility(View.VISIBLE);
                tv_state.setText("正在刷新...");
                break;
        }
    }

    public interface OnRefreshListener {
        void onPullRefresh();
        void onLoadingMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public void completeRefresh() {
        if (isLoadingMore) {
            // 重置footerView状态
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            // 重置headerView
            headerView.setPadding(0, -headerViewHeight, 0, 0);
            currentState = PULL_REFRESH;
            pb_rotate.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
            tv_state.setText("下拉刷新");
            tv_time.setText("最后刷新：" + getCurrentTime());
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

        return format.format(new Date());
    }

    // SCROLL_STATE_IDLE：闲置状态，就是手指松开
    // SCROLL_STATE_TOUCH_SCROLL: SCROLL_STATE_TOUCH_SCROLL
    // SCROLL_STATE_FLING: 快速滑动后松开
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == (getCount() - 1)
                && !isLoadingMore) {
            isLoadingMore = true;

            // 显示出footerView
            footerView.setPadding(0, 0 ,0 ,0);
            // 让listView最后一条显示出来
            setSelection(getCount());

            if (listener != null) {
                listener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
