package com.jhtwl.googlestore.Base;

import android.content.Context;
import android.view.View;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/12  下午2:51
 */
public abstract class BaseViewHolder<T> {
    private View convertView;
    private T viewHolderData;

    public void setViewHolderData(T viewHolderData) {
        this.viewHolderData = viewHolderData;
        setSubViewsData(viewHolderData);
    }

    public View getConvertView() {
        return this.convertView;
    }

    public BaseViewHolder(Context context) {
        convertView = initHolderView(context);
        this.convertView.setTag(this);
    }

    /**
     * 初始化holder包含的控件和listView界面
     */
    protected abstract View initHolderView(Context context);

    /**
     * 设置item中控件数据
     * @param data
     */
    protected abstract void setSubViewsData(T data);
}
