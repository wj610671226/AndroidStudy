package com.jhtwl.googlestore.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jhtwl.googlestore.Base.BaseViewHolder;
import com.jhtwl.googlestore.Bean.TopBean;
import com.jhtwl.googlestore.R;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/14  上午9:46
 */
public class TopViewHolder extends BaseViewHolder<TopBean> {

    public TopViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initHolderView(Context context) {
        View view = View.inflate(context, R.layout.top_hoider_view_layout, null);
        return view;
    }

    @Override
    protected void setSubViewsData(TopBean data) {
        Log.e("TopViewHolder","设置子视图控件的值");
    }
}
