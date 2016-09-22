package com.example.jhtwl.zhcity.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.jhtwl.zhcity.ImplementPager.GovaPager;
import com.example.jhtwl.zhcity.ImplementPager.HomePager;
import com.example.jhtwl.zhcity.ImplementPager.NewsCenterPager;
import com.example.jhtwl.zhcity.ImplementPager.ServesPager;
import com.example.jhtwl.zhcity.ImplementPager.SettingPager;
import com.example.jhtwl.zhcity.R;
import com.example.jhtwl.zhcity.Base.BasePager;


import java.util.ArrayList;

/**
 * Created by jhtwl on 16/9/8.
 */
public class ContentFragment extends BaseFragment {

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private ArrayList<BasePager> pagerList;
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {
        pagerList = new ArrayList<BasePager>();
        pagerList.add(new HomePager(mActivity));
        pagerList.add(new NewsCenterPager(mActivity));
        pagerList.add(new ServesPager(mActivity));
        pagerList.add(new GovaPager(mActivity));
        pagerList.add(new SettingPager(mActivity));

        // 设置默认选中
        radioGroup.check(R.id.homep);

        // 设置radioGroup的监听事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.homep:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.news:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.serves:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.gova:
                        viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.setting:
                        viewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }
            }
        });

        // 监听viewPager的改变
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 获取当前页面，初始化数据
                pagerList.get(position).initData();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 设置viewPager的Adapter
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pagerList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                BasePager pager = pagerList.get(position);
                container.addView(pager.mRootView);
                return pager.mRootView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    /**
    *   获取新闻中心页面
     */
    public NewsCenterPager getNesCenterPager() {
        return (NewsCenterPager) pagerList.get(1);
    }
}
