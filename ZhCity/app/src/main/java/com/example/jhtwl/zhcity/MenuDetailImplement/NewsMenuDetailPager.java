package com.example.jhtwl.zhcity.MenuDetailImplement;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.jhtwl.zhcity.Base.BaseMenuDetailPager;
import com.example.jhtwl.zhcity.Base.TabDetailPager;
import com.example.jhtwl.zhcity.Bean.NewsData;
import com.example.jhtwl.zhcity.Activity.MainActivity;
import com.example.jhtwl.zhcity.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * 描述：新闻页面
 * 创建人: jhtwl
 * 时间: 16/9/14  上午11:10
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager news_detail_pager;
    private TabPageIndicator tabPageIndicator;
    private ArrayList<NewsData.NewsTabData> newPagerData;
    private NewsPagerAdapter newsPagerAdapter;
    private ArrayList<TabDetailPager> tabDetailList;
    private ImageButton btn_next;

    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsData.NewsTabData> children) {
        super(mActivity);
        newPagerData = children;
    }

    @Override
    public View initViews() {
        View newsCenterDetailView = View.inflate(mActivity, R.layout.news_center_detail_layout, null);
        news_detail_pager = (ViewPager) newsCenterDetailView.findViewById(R.id.news_detail_pager);
        tabPageIndicator = (TabPageIndicator) newsCenterDetailView.findViewById(R.id.tabPageIndicator);
        btn_next = (ImageButton) newsCenterDetailView.findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = news_detail_pager.getCurrentItem();
                news_detail_pager.setCurrentItem(++currentItem);
            }
        });

        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity mainActivity = (MainActivity) mActivity;
                SlidingMenu menu = mainActivity.getSlidingMenu();

                if (position == 0) {
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return newsCenterDetailView;
    }

    @Override
    public void initData() {
        newsPagerAdapter = new NewsPagerAdapter();

        tabDetailList = new ArrayList<TabDetailPager>();
        // 初始化页签数据
        for (int i = 0; i < newPagerData.size(); i ++) {
            TabDetailPager pager = new TabDetailPager(mActivity, newPagerData.get(i));
            tabDetailList.add(pager);
        }

        // 放在setViewPager之前
        news_detail_pager.setAdapter(newsPagerAdapter);
        tabPageIndicator.setViewPager(news_detail_pager);
    }

    public class NewsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabDetailList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            NewsData.NewsTabData data = newPagerData.get(position);
            return data.title;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = tabDetailList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
