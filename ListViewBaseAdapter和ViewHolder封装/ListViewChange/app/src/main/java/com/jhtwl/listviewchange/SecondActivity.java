package com.jhtwl.listviewchange;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ListView second_lv;
    private ArrayList<Integer> secondDataList;
    private SecondAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();

    }

    private void initView() {
        secondDataList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            secondDataList.add(R.drawable.ic_launche);
        }

        second_lv = (ListView) findViewById(R.id.sec_lv);
        adapter = new SecondAdapter(secondDataList);
        second_lv.setAdapter(adapter);

    }

    class SecondAdapter extends DefaultAdapter {


        public SecondAdapter(ArrayList dataList) {
            super(dataList);
        }

        @Override
        protected BaseViewHolder getHolder() {
            return new SecondViewHolder();
        }
    }


    class SecondViewHolder extends BaseViewHolder<Integer> {
        ImageView imageView;

        @Override
        protected View initHolderView() {
            View view = View.inflate(getApplicationContext(), R.layout.second_listview_item, null);
            this.imageView = (ImageView) view.findViewById(R.id.second_iv);
            return view;
        }

        @Override
        protected void setSubViewsData(Integer data) {
            this.imageView.setImageResource(data);
        }


    }
}
