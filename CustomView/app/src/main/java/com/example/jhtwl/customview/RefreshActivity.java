package com.example.jhtwl.customview;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jhtwl.customview.MyCustomView.RefreshView;

import java.util.ArrayList;

public class RefreshActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>();
    private RefreshView refreshView;
    private BaseAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 更新UI
            adapter.notifyDataSetChanged();
            refreshView.completeRefresh();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        intiView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i ++) {
            list.add("listView初始的数据 -- " + i);
        }

        adapter = new MyAdapter();
        refreshView.setAdapter(adapter);

        refreshView.setOnRefreshListener(new RefreshView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                //需要联网请求服务器的数据，然后更新UI
                requestDataFromServer(false);
            }

            @Override
            public void onLoadingMore() {
                requestDataFromServer(true);
            }
        });
    }

    // 向服务器发送数据
    private void requestDataFromServer(final boolean isLoadingMore) {
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(3000);

                if (isLoadingMore) {
                    list.add("加载的数据 --》 ");
                } else  {
                    list.add(0, "下拉刷新的数据");
                }

                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void intiView() {
        refreshView = (RefreshView) findViewById(R.id.refreshView);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = new TextView(RefreshActivity.this);
                holder.textView = (TextView) view;
                holder.textView.setPadding(20, 20, 20, 20);
                holder.textView.setTextSize(18);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.textView.setText(list.get(i));
            return view;
        }
    }

    static class ViewHolder {
        TextView textView;
    }
}
