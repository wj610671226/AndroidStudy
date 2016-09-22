package com.example.jhtwl.zhcity.Base;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhtwl.zhcity.Bean.NewsData;
import com.example.jhtwl.zhcity.Bean.TabDetailPagerData;
import com.example.jhtwl.zhcity.CommonData.CommonDataMessage;
import com.example.jhtwl.zhcity.R;
import com.example.jhtwl.zhcity.View.RefreshListView;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 描述：页签详情页
 * 创建人: jhtwl
 * 时间: 16/9/14  上午10:52
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private ViewPager tabDetail_HeaderViewPager;
    private CirclePageIndicator tabDetail_HeaderView_Indicator;
    private TextView top_news_title;
    private RefreshListView tab_detail_content_listView;

    private TabDetailPagerAdapter mAdapter;
    private TabDetailPagerListViewAdapter listViewAdapter;

    private NewsData.NewsTabData newsTabData;
    // 服务器返回的该页面的所有
    private TabDetailPagerData tabDetailPagerData;
    // 广告栏数据
    private ArrayList<TabDetailPagerData.DataBean.TopnewsBean> topnewsBeanArrayList;
    // listView  数据
    private ArrayList<TabDetailPagerData.DataBean.NewsBean> newsBeanArrayList;
    private IndicatorOnPageChangeListener indicatorOnPageChangeListener;

    public TabDetailPager(Activity mActivity, NewsData.NewsTabData newsTabMessage) {
        super(mActivity);

        newsTabData = newsTabMessage;
    }

    @Override
    public View initViews() {
        View contentView = View.inflate(mActivity, R.layout.tab_detail_content_view_layout, null);
        tab_detail_content_listView = (RefreshListView) contentView.findViewById(R.id.tab_detail_content_listView);
        View tabDetailHeaderView = View.inflate(mActivity, R.layout.tabdetail_pager_header_view_layout, null);
        tabDetail_HeaderViewPager = (ViewPager) tabDetailHeaderView.findViewById(R.id.tabDetail_HeaderViewPager);
        tabDetail_HeaderView_Indicator = (CirclePageIndicator) tabDetailHeaderView.findViewById(R.id.tabDetail_HeaderView_Indicator);
        top_news_title = (TextView) tabDetailHeaderView.findViewById(R.id.top_news_title);
        tab_detail_content_listView.addHeaderView(tabDetailHeaderView);

        return contentView;
    }

    @Override
    public void initData() {
        // 从服务器请求数据
        getDataFromServers(CommonDataMessage.BASE_URL + newsTabData.url, false);

        tab_detail_content_listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefreshHeader() {
                getDataFromServers(CommonDataMessage.BASE_URL + newsTabData.url, false);
            }

            @Override
            public void onRefreshFooter() {
                getMoreDataFromServers();
            }
        });
    }

    private void getMoreDataFromServers() {
        String moreUrl = tabDetailPagerData.getData().getMore();
        if (TextUtils.isEmpty(moreUrl)) { // 空
            Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
            tab_detail_content_listView.refreshComplete();
        } else { // 加载更多
            getDataFromServers(CommonDataMessage.BASE_URL + moreUrl, true);
        }
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServers(String url, final boolean isLodingMore) {

        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TabDetail - onSuccess", result);
                serversDataWithModel(result, isLodingMore);
                tab_detail_content_listView.refreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TabDetail - onError", ex.toString());
                tab_detail_content_listView.refreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析数据，把数据转换为bean对象
     * @param result 服务器返回的数据
     * @param isLodingMore 是否加载更多
     */
    private void serversDataWithModel(String result, boolean isLodingMore) {
        Gson gson = new Gson();
        tabDetailPagerData = gson.fromJson(result, TabDetailPagerData.class);

        if (isLodingMore) { // 加载更多
            // 处理listView数据
            newsBeanArrayList.addAll(tabDetailPagerData.getData().getNews());
            listViewAdapter.notifyDataSetChanged();
        } else {
            // 轮播图数据
            topnewsBeanArrayList = (ArrayList<TabDetailPagerData.DataBean.TopnewsBean>) tabDetailPagerData.getData().getTopnews();
            // 处理listView数据
            newsBeanArrayList = (ArrayList<TabDetailPagerData.DataBean.NewsBean>) tabDetailPagerData.getData().getNews();

            // 设置tabDetail_HeaderView_Indicator  adapter
            if (mAdapter == null) {
                mAdapter = new TabDetailPagerAdapter();
                tabDetail_HeaderViewPager.setAdapter(mAdapter);
                tabDetail_HeaderView_Indicator.setViewPager(tabDetail_HeaderViewPager);
            }

            // 设置第一次的标题
            top_news_title.setText(topnewsBeanArrayList.get(0).getTitle());

            if (indicatorOnPageChangeListener == null) {
                // 设置滑动监听
                indicatorOnPageChangeListener = new IndicatorOnPageChangeListener();
                tabDetail_HeaderView_Indicator.setOnPageChangeListener(indicatorOnPageChangeListener);
            }

            if (listViewAdapter == null) {
                listViewAdapter = new TabDetailPagerListViewAdapter();
                tab_detail_content_listView.setAdapter(listViewAdapter);
            }
        }
    }

    class IndicatorOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.e("tabDetail_HeaderView_Indicator", "" + position);
            TabDetailPagerData.DataBean.TopnewsBean topnewsBean = topnewsBeanArrayList.get(position);
            top_news_title.setText(topnewsBean.getTitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    /**
     *  tab_detail_content_listView adapter
     */
    class TabDetailPagerListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsBeanArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = View.inflate(mActivity, R.layout.tab_detail_pager_listview_item_layout, null);
                viewHolder.detail_pager_listView_icon = (ImageView) view.findViewById(R.id.detail_pager_listView_icon);
                viewHolder.item_title = (TextView) view.findViewById(R.id.item_title);
                viewHolder.pubdate = (TextView) view.findViewById(R.id.pubdate);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            TabDetailPagerData.DataBean.NewsBean newsBean = newsBeanArrayList.get(i);
            x.image().bind(viewHolder.detail_pager_listView_icon, newsBean.getListimage());
            viewHolder.item_title.setText(newsBean.getTitle());
            viewHolder.pubdate.setText(newsBean.getPubdate());
            return view;
        }
    }

    static class ViewHolder {
        ImageView detail_pager_listView_icon;
        TextView item_title;
        TextView pubdate;
    }

    class TabDetailPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return topnewsBeanArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPagerData.DataBean.TopnewsBean topnewsBean = topnewsBeanArrayList.get(position);
            ImageView imageView = new ImageView(mActivity);
            x.image().bind(imageView, topnewsBean.getTopimage());
            imageView.setBackgroundColor(Color.BLUE);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
