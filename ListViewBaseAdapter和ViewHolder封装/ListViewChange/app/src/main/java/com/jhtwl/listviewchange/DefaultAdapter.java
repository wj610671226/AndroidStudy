package com.jhtwl.listviewchange;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/12  下午3:06
 */
public abstract class DefaultAdapter<T> extends BaseAdapter {
    private ArrayList<T> dataList;

    public DefaultAdapter(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder<T> holder = null;
        if (convertView == null) {
            holder = getHolder();
            convertView = holder.getConvertView();
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        T data = dataList.get(position);
        holder.setSubViewsData(data);
        return convertView;
    }

    protected abstract BaseViewHolder<T> getHolder();


}
