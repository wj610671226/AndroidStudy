package com.example.jhtwl.safty360;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jhtwl.safty360.Bean.AppInfo;
import com.example.jhtwl.safty360.Dao.AppInfoParser;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class AppMangerActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AppManagerActivity";
    private TextView tv_avail_rom;
    private TextView tv_avail_sd;
    private LinearLayout ll_loading;
    private Button button;
    private TextView textView = null;

    // 使用应用程序信息集合
    private List<AppInfo> infos;
    // 用户程序集合
    private List<AppInfo> userAppInfos;
    // 系统程序集合
    private List<AppInfo> systemAppInfos;

    private TextView tv_appsize_lable;
    private ListView lv_appmanger;

    private LinearLayout ll_start;
    private LinearLayout ll_share;
    private LinearLayout ll_uninstall;
    private LinearLayout ll_setting;

    // 被点击的条目对应的appinfo 对象
    private AppInfo clickedAppinfo;
    // 悬浮窗体
    private PopupWindow popupWindow;

//    private UninstallReceiver receiver;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 隐藏正在加载的界面
            ll_loading.setVisibility(View.INVISIBLE);
            lv_appmanger.setAdapter( new AppManagerAdapter());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manger);

        tv_appsize_lable = (TextView) findViewById(R.id.tv_appsize_lable);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        tv_avail_rom = (TextView) findViewById(R.id.tv_avail_rom);
        tv_avail_sd = (TextView) findViewById(R.id.tv_avail_sd);
        lv_appmanger = (ListView) findViewById(R.id.lv_appmanager);

        long avail_sd = Environment.getExternalStorageDirectory().getFreeSpace();
        long avail_rom = Environment.getDataDirectory().getFreeSpace();
        String str_avail_sd = android.text.format.Formatter.formatFileSize(this, avail_sd);
        String str_avail_rom = android.text.format.Formatter.formatFileSize(this, avail_rom);
        tv_avail_sd.setText("剩余手机内部" + str_avail_sd);
        tv_avail_rom.setText("剩余SD卡：" + str_avail_rom);

        // 填充数据
        fillData();

        // 设置滚动监听
        lv_appmanger.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            // 当listView被滚动的时候调用的方法
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                dismissPopupWindow();
                if (userAppInfos != null && systemAppInfos != null) {
                    if (i >= userAppInfos.size() + 1) {
                        tv_appsize_lable.setText("系统程序：" + systemAppInfos.size() + "个");
                    } else {
                        tv_appsize_lable.setText("用户程序：" + userAppInfos.size() + "个");
                    }
                }
            }
        });

        // 给listView 注册一个点击事件
        lv_appmanger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object obj = lv_appmanger.getItemAtPosition(i);
                if (obj != null && obj instanceof AppInfo) {
                    clickedAppinfo = (AppInfo) obj;
                    View contentView = View.inflate(getApplicationContext(), R.layout.pop_itme_layout, null);
                    ll_uninstall = (LinearLayout) contentView.findViewById(R.id.ll_unistall);
                    ll_start = (LinearLayout) contentView.findViewById(R.id.ll_start);
                    ll_share = (LinearLayout) contentView.findViewById(R.id.ll_share);
                    ll_setting = (LinearLayout) contentView.findViewById(R.id.ll_setting);

                    ll_uninstall.setOnClickListener(AppMangerActivity.this);
                    ll_start.setOnClickListener(AppMangerActivity.this);
                    ll_share.setOnClickListener(AppMangerActivity.this);
                    ll_setting.setOnClickListener(AppMangerActivity.this);

                    dismissPopupWindow();

                    popupWindow = new PopupWindow(contentView, -2, -2);
                    // 动画播放有一个前提条件：窗体必须要有背景资源，如果窗体没有背景，动画就播放不出来
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    int location[] = new int[2];
                    view.getLocationInWindow(location);
                    popupWindow.showAtLocation(adapterView, Gravity.LEFT + Gravity.TOP, 60, location[1]);

                    ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(200);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
                    alphaAnimation.setDuration(200);
                    AnimationSet set = new AnimationSet(false);
                    set.addAnimation(alphaAnimation);
                    set.addAnimation(animation);
                    contentView.startAnimation(set);
                }
            }
        });
    }

    private void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void fillData() {
        ll_loading.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() {
                infos = AppInfoParser.getAppInfos(AppMangerActivity.this);
                userAppInfos = new ArrayList<AppInfo>();
                systemAppInfos = new ArrayList<AppInfo>();
                for (AppInfo info: infos) {
                    if (info.isUserApp()) {
                        // 用户程序
                        userAppInfos.add(info);
                    } else {
                        // 系统程序
                        systemAppInfos.add(info);
                    }
                }
                // 设置界面
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    public void onClick(View view) {

    }

    private class AppManagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // 多了两个显示条目个数额textview 小标签 所有加2
            return userAppInfos.size() + systemAppInfos.size() + 2;
        }

        @Override
        public Object getItem(int i) {
            if (i == 0) {
                // 第0个位置显示的应该是 用户程序的个数的标签
                return null;
            } else if (i== (userAppInfos.size() + 1)) {
                return null;
            }
            AppInfo appInfo;
            if (i < (userAppInfos.size() + 1)) {
                // 用户程序

                appInfo = userAppInfos.get(i - 1);
            } else {
                // 系统程序
                int location = i - 1 - userAppInfos.size() - 1;
                appInfo = systemAppInfos.get(location);
            }
            return appInfo;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (i == 0) {
                // 第0个位置显示的应该是 用户程序的个数的标签
                TextView tv = new TextView(getApplicationContext());
                tv.setBackgroundColor(Color.GRAY);
                tv.setText("用户程序: " + userAppInfos.size() + "个");
                return tv;
            } else if (i == (userAppInfos.size()) + 1) {
                TextView tv = new TextView(getApplicationContext());
                tv.setBackgroundColor(Color.GRAY);
                tv.setTextColor(Color.WHITE);
                tv.setText("系统程序：" + systemAppInfos.size() + "个");
                return tv;
            }
            AppInfo appInfo;
            if (i < (userAppInfos.size() + 1)) {
                // 用户程序
                // 多了一个textview的标签。位置需要减一
                appInfo = userAppInfos.get(i - 1);
            } else {
                // 系统程序
                int location = i - 1 - userAppInfos.size() - 1;
                appInfo = systemAppInfos.get(location);
            }
            View newView;
            ViewHolder holder;
            if (view != null && view instanceof LinearLayout) {
                newView = view;
                holder = (ViewHolder) view.getTag();
            } else {
                newView = View.inflate(getApplicationContext(), R.layout.item_app_manger_layout, null);
                holder = new ViewHolder();
                holder.iv_app_icon = (ImageView) newView.findViewById(R.id.iv_app_icon);
                holder.tv_app_name = (TextView) newView.findViewById(R.id.tv_app_name);
                holder.tv_app_size = (TextView) newView.findViewById(R.id.tv_app_size);
                holder.tv_app_location = (TextView) newView.findViewById(R.id.tv_app_location);
                newView.setTag(holder);
            }
            // 得到当前位置的appinfo对象
            holder.iv_app_icon.setImageDrawable(appInfo.getIcon());
            holder.tv_app_name.setText(appInfo.getName());
            holder.tv_app_size.setText(android.text.format.Formatter.formatFileSize(getApplicationContext(), appInfo.getAppSize()));
            if (appInfo.isInRom()) {
                holder.tv_app_location.setText("手机内存");
            } else {
                holder.tv_app_location.setText("外部存储");
            }
            return newView;
        }
    }

    static class ViewHolder {
        ImageView iv_app_icon;
        TextView tv_app_name;
        TextView tv_app_size;
        TextView tv_app_location;
    }
}
