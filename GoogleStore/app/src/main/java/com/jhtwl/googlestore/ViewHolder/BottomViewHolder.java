package com.jhtwl.googlestore.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jhtwl.googlestore.Base.BaseViewHolder;
import com.jhtwl.googlestore.Bean.BottomBean;
import com.jhtwl.googlestore.R;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/14  上午10:27
 */
public class BottomViewHolder extends BaseViewHolder<BottomBean> {
    public BottomViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initHolderView(Context context) {
        View view = View.inflate(context, R.layout.bottom_layout, null);
        return view;
    }

    @Override
    protected void setSubViewsData(BottomBean data) {
        Log.e("BottomViewHolder","设置子视图控件的值");
    }
}
