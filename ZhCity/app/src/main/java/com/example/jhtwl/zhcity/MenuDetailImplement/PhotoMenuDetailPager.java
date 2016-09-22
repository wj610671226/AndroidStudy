package com.example.jhtwl.zhcity.MenuDetailImplement;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jhtwl.zhcity.Base.BaseMenuDetailPager;
import com.example.jhtwl.zhcity.Bean.PhotoDataBean;
import com.example.jhtwl.zhcity.CommonData.CommonDataMessage;
import com.example.jhtwl.zhcity.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 描述：组图页面
 * 创建人: jhtwl
 * 时间: 16/9/14  上午11:10
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private ListView photoListView;
    private GridView photoGridView;
    private ImageButton photoMenu;
    private PhotoAdapter adapter;
    private ArrayList<PhotoDataBean.DataBean.PhotoNewsBean> listData;

    private boolean isShowListView = true;

    public PhotoMenuDetailPager(Activity mActivity, ImageButton photo_menu) {
        super(mActivity);
        photoMenu = photo_menu;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.photo_menu_detail_pager_layout, null);
        photoListView = (ListView) view.findViewById(R.id.photo_list_view);
        photoGridView = (GridView) view.findViewById(R.id.photo_grid_view);
        return view;
    }

    @Override
    public void initData() {
        getDataFormServers();

        photoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowListView) {
                    photoListView.setVisibility(View.INVISIBLE);
                    photoGridView.setVisibility(View.VISIBLE);
                    photoMenu.setImageResource(R.drawable.icon_pic_grid_type);
                } else {
                    photoListView.setVisibility(View.VISIBLE);
                    photoGridView.setVisibility(View.INVISIBLE);
                    photoMenu.setImageResource(R.drawable.icon_pic_list_type);
                }

                isShowListView = !isShowListView;

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getDataFormServers() {
        RequestParams params = new RequestParams(CommonDataMessage.PHOTOS_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("photo - onError", ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析数据
     */
    private void parseData(String result) {
        Gson gson = new Gson();
        PhotoDataBean photoDataBean =  gson.fromJson(result, PhotoDataBean.class);

        listData = (ArrayList<PhotoDataBean.DataBean.PhotoNewsBean>) photoDataBean.getData().getNews();
        if (adapter == null) {
            adapter = new PhotoAdapter();
            photoListView.setAdapter(adapter);
            photoGridView.setAdapter(adapter);
        }
    }

    class PhotoAdapter extends BaseAdapter {

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
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(mActivity, R.layout.photo_item_layout, null);
                holder.iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
                holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            PhotoDataBean.DataBean.PhotoNewsBean newsBean = listData.get(i);
            x.image().bind(holder.iv_photo, newsBean.getListimage());
            holder.tv_title.setText(newsBean.getTitle());
            return view;
        }
    }

    private static class ViewHolder{
        ImageView iv_photo;
        TextView tv_title;
    }
}
