package com.example.jhtwl.customview;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdScrollViewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tv_intro;
    private LinearLayout dot_layout;
    private ArrayList<Ad> list = new ArrayList<Ad>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_scroll_view);

        initView();
        initListener();
        initData();
    }

    private void initData() {
        list.add(new Ad(R.drawable.a, "巩俐不低俗，我就不能低俗"));
        list.add(new Ad(R.drawable.b, "朴树又回来了，再唱经典老歌引百万人同唱啊"));
        list.add(new Ad(R.drawable.c, "揭秘北京电影如何升级"));
        list.add(new Ad(R.drawable.d, "乐视网TV版大放送"));
        list.add(new Ad(R.drawable.e, "热血屌丝的反杀"));


        initDots();

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            // true: 表示不去创建新的page，使用缓存
            // view：当前滑动的view
            // object：表示将要显示的view
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            // 类似于BaseAdaper的getView 方法
            // 由于它最多就3个界面，不需要viewHolder
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(AdScrollViewActivity.this, R.layout.adapter_ad, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);

                Ad ad = list.get(position % list.size());
                imageView.setImageResource(ad.getIconResId());
                // 将view加入到viewPager中
                container.addView(view);
                return view;
            }

            // 销毁view
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        updateIntroAndDot();
    }

    // 更新文本
    private void updateIntroAndDot() {
        int currentPage = viewPager.getCurrentItem() % list.size();
        tv_intro.setText(list.get(currentPage).getIntro());

        for (int i = 0; i < dot_layout.getChildCount(); i ++) {
            dot_layout.getChildAt(i).setEnabled( i == currentPage);
        }
    }

    private void initDots() {
        for (int i = 0; i < list.size(); i ++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {
                params.leftMargin = 5;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            dot_layout.addView(view);
        }
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("--------page滚动中----------");
                updateIntroAndDot();
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("--------page被选中----------");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("--------page状态改变----------");
            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
    }
}
