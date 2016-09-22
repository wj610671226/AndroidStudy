package com.example.jhtwl.zhcity.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jhtwl.zhcity.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/21  上午10:00
 */
public class RefreshListView extends ListView {

    private View mHeaderView;
    private ImageView refresh_arraw;
    private ProgressBar progressBar;
    private TextView refresh_state;
    private TextView refresh_time;
    private int startY;
    private int mheaderViewHeight;

    private static final int STATE_PULL_REFRESH = 0; // 下拉刷新
    private static final int STATE_RELEASE_REFRESH = 1; // 松开刷新
    private static final int STATE_REFRESHING = 2; // 正在刷新

    // 当前刷新状态
    private int mCurrentRefreshState = 0;
    private RotateAnimation downAnimation;
    private RotateAnimation upAnimation;

    // 是否加载更多辨识
    private boolean isLoadingMore = false;
    private View footerView;
    private int footerViewHeight;

    public RefreshListView(Context context) {
        super(context);
        initView();
    }


    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 初始化视图
        mHeaderView = View.inflate(getContext(), R.layout.refresh_lsitview_header_view_layout, null);
        refresh_arraw = (ImageView) mHeaderView.findViewById(R.id.refresh_arraw);
        progressBar = (ProgressBar) mHeaderView.findViewById(R.id.progressBar);
        refresh_state = (TextView) mHeaderView.findViewById(R.id.refresh_state);
        refresh_time = (TextView) mHeaderView.findViewById(R.id.refresh_time);

        refresh_time.setText("最后刷新时间:" + getCurrentTime());
        // 添加头部视图
        mHeaderView.measure(0, 0);
        // 隐藏头布局
        mheaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mheaderViewHeight, 0, 0);
        addHeaderView(mHeaderView);
        initAnimation();

        footerView = View.inflate(getContext(), R.layout.refresh_listview_footer_view_layout, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, 0, 0, -footerViewHeight);
        addFooterView(footerView);

        // 监听滚动
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (mCurrentRefreshState == STATE_RELEASE_REFRESH || mCurrentRefreshState == STATE_PULL_REFRESH) {
                    if (getLastVisiblePosition() == (getCount() - 1) && !isLoadingMore) {
                        // 显示脚部视图
                        footerView.setPadding(0, 0, 0, 0);
                        setSelection(getCount() - 1);

                        isLoadingMore = true;
                        // 加载数据
                        if (refreshListener != null) {
                            refreshListener.onRefreshFooter();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    /**
     * 初始化箭头动画
     */
    private void initAnimation() {
        downAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(200);
        downAnimation.setFillAfter(true);
        // 逆时针   0 ->  -180
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(200);
        upAnimation.setFillAfter(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 确保startY有效
                    startY = (int) ev.getRawY();
                }

                int endY = (int) ev.getRawY();
                int dy = endY - startY;

                if (mCurrentRefreshState == STATE_REFRESHING) {
                    break;
                }

                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    // 移动headerView
                    int padding = dy - mheaderViewHeight;
                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentRefreshState != STATE_RELEASE_REFRESH) { // 状态改为松开刷新
                        mCurrentRefreshState = STATE_RELEASE_REFRESH;
                        changeHeaderViewMessage();
                    } else if (padding < 0 && mCurrentRefreshState != STATE_PULL_REFRESH) { // 状态改为下拉刷新
                        mCurrentRefreshState = STATE_PULL_REFRESH;
                        changeHeaderViewMessage();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentRefreshState == STATE_PULL_REFRESH) {
                    mHeaderView.setPadding(0, -mheaderViewHeight, 0, 0);
                } else if (mCurrentRefreshState == STATE_RELEASE_REFRESH) {
                    mCurrentRefreshState = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    changeHeaderViewMessage();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据刷新状态改变headerView 信息
     */
    private void changeHeaderViewMessage() {
        switch (mCurrentRefreshState) {
            case STATE_PULL_REFRESH: // 下拉刷新
                refresh_arraw.setVisibility(VISIBLE);
                progressBar.setVisibility(INVISIBLE);
                refresh_state.setText("下拉刷新");
                refresh_arraw.startAnimation(downAnimation);
                break;
            case STATE_RELEASE_REFRESH: // 松开刷新
                refresh_arraw.setVisibility(VISIBLE);
                progressBar.setVisibility(INVISIBLE);
                refresh_state.setText("松开刷新");
                refresh_arraw.startAnimation(upAnimation);
                break;
            case STATE_REFRESHING: // 正在刷新
                // 必须清除动画才能隐藏
                refresh_arraw.clearAnimation();
                refresh_arraw.setVisibility(INVISIBLE);
                progressBar.setVisibility(VISIBLE);
                refresh_state.setText("正在努力加载中...");

                refresh_time.setText("最后刷新时间:" + getCurrentTime());

                if (refreshListener != null) {
                    refreshListener.onRefreshHeader();
                }
                break;
        }
    }

    /**
     * 获取当前格式化的时间
     *
     * @return 格式化后的时间
     */
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }


    private OnRefreshListener refreshListener;
    public void setOnRefreshListener(OnRefreshListener listener) {
        refreshListener = listener;
    }

    public interface OnRefreshListener {
        public void onRefreshHeader();
        public void onRefreshFooter();
    }

    public void refreshComplete() {
        if (isLoadingMore) { // 加载更多
            footerView.setPadding(0, 0, 0, -footerViewHeight);
            isLoadingMore = false;
        } else {
            mHeaderView.setPadding(0, - mheaderViewHeight, 0 , 0);
            refresh_arraw.setVisibility(VISIBLE);
            progressBar.setVisibility(INVISIBLE);
            refresh_state.setText("下拉刷新");
            mCurrentRefreshState = STATE_PULL_REFRESH;
        }
    }
}
