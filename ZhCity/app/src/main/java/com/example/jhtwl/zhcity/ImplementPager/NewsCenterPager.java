package com.example.jhtwl.zhcity.ImplementPager;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.jhtwl.zhcity.Base.BaseMenuDetailPager;
import com.example.jhtwl.zhcity.Base.BasePager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 描述：新闻中心
 * 创建人: jhtwl
 * 时间: 16/9/13  上午10:56
 */
public class NewsCenterPager extends BasePager {

    // 4个菜单详情页的集合
    private ArrayList<BaseMenuDetailPager> mPages;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        tx_title.setText("新闻中心");
        setSildingMenuEnable(true);
        // 从服务器请求数据
        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        Toast.makeText(mactivity,"开始请求数据", Toast.LENGTH_SHORT).show();
        RequestParams params = new RequestParams("http://10.10.0.113:8080/zhbj/categories.json");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess", result);
                parseNetData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError", ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("onCancelled", cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("onFinished", "onFinished");
            }
        });

//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("http://10.10.0.113:8080/zhbj/categories.json");
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(5000);
//                    connection.setReadTimeout(5000);
//                    InputStream inputStream = connection.getInputStream();
//                    if (connection.getResponseCode() == 200) {
//                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//                        byte[] buffer = new byte[1024];
//                        int len = -1;
//                        while ((len = inputStream.read(buffer)) != -1) {
//                            outStream.write(buffer, 0, len);
//                        }
//                        Log.e("result", outStream.toString());
//                        outStream.close();
//                        inputStream.close();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    /**
     *  解析网络数据
     */
    private void parseNetData(String data) {

    }

    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int postion) {
        // 获取当前要显示的菜单详情页
        BaseMenuDetailPager pager = mPages.get(postion);
        // 清楚之前的布局
        flContent.removeAllViews();
        // 将菜单详情的布局设置给帧布局
        flContent.addView(pager.mRootView);

        // 设置当前页的标题

    }
}
