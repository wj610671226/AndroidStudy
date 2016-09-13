package com.example.jhtwl.zhcity.Fragment;

import android.view.View;

import com.example.jhtwl.zhcity.R;

/**
 * Created by jhtwl on 16/9/8.  侧边栏
 */
public class LeftMenuFragment extends BaseFragment {

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        return view;
    }
}
