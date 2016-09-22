package com.example.jhtwl.zhcity.Fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jhtwl.zhcity.Bean.NewsData;
import com.example.jhtwl.zhcity.ImplementPager.NewsCenterPager;
import com.example.jhtwl.zhcity.Activity.MainActivity;
import com.example.jhtwl.zhcity.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by jhtwl on 16/9/8.  侧边栏
 */
public class LeftMenuFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<NewsData.NewsMenuData> listData;
    private MyAdapder adapter;
    private int currentItem;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        listView = (ListView) view.findViewById(R.id.lv_listView);
        return view;
    }

    @Override
    public void initData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentItem = i;
                // 更新界面
                adapter.notifyDataSetChanged();
                setCurrentMenuDetailpager(i);
                // 关闭slidingMenu
                showSlidingMenu();
            }
        });
    }

    // 设置当前菜单详情页
    private void setCurrentMenuDetailpager(int postion) {
        MainActivity mainActivity = (MainActivity) mActivity;
        // 获取主页面fragment
        ContentFragment fragment = mainActivity.getContentFragment();
        // 获取新闻中心页面
        NewsCenterPager pager = fragment.getNesCenterPager();
        pager.setCurrentMenuDetailPager(postion);
    }

    // 关闭slidingMenu
    private void showSlidingMenu() {
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu menu = mainActivity.getSlidingMenu();
        menu.toggle();
    }

    class MyAdapder extends BaseAdapter {
        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int i) {
            return listData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(mActivity, R.layout.left_menu_list_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_title);
            textView.setText(listData.get(i).title);
            if (currentItem == i) {
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
            }
            return view;
        }
    }

    /**
     * 设置网络数据
     * @param data
     */
    public void setMenuData(NewsData data) {
        listData = data.data;
        adapter = new MyAdapder();
        listView.setAdapter(adapter);
    }
}
