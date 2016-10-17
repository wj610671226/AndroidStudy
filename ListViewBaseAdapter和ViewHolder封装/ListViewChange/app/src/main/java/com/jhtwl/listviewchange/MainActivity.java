package com.jhtwl.listviewchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView main_lv;
    private MainAdapter mainadapter;
    private ArrayList<String> mainDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // 初始化data
        mainDataList = new ArrayList<String>();

        for (int i = 0; i < 30; i++) {
            mainDataList.add("hello word1" + i);
        }

        main_lv = (ListView) findViewById(R.id.main_lv);
        mainadapter = new MainAdapter(mainDataList);
        main_lv.setAdapter(mainadapter);
    }

    class MainAdapter extends DefaultAdapter<String> {

        public MainAdapter(ArrayList dataList) {
            super(dataList);
        }


        @Override
        protected BaseViewHolder<String> getHolder() {
            return new MainViewHolder();
        }
    }

    class MainViewHolder extends BaseViewHolder<String> {
        private TextView textView;

        @Override
        protected View initHolderView() {
            View view = View.inflate(getApplicationContext(), R.layout.main_list_view_item, null);
            this.textView = (TextView) view.findViewById(R.id.main_lv_tv);
            return view;
        }

        @Override
        protected void setSubViewsData(String data) {
            this.textView.setText(data);
        }
    }


    public void nextActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
