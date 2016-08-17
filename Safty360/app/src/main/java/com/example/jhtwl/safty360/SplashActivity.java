package com.example.jhtwl.safty360;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_ENTER_HOME = 4;
    private TextView tvVersion;

    // 服务器返回的信息
    private String mVersionName; // 版本名
    private int mVersionCode; // 版本号
    private String mDesc; // 版本描述
    private String mDownloadUrl; // 下载地址

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDailog();
                    break;
                case CODE_ENTER_HOME:
                    startHomeActivity();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本名:" + getVersionCode());

        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean updateState = sharedPreferences.getBoolean("auto_update", true);
        if (updateState) {
            // 检查版本号
            checkVersion();
        } else {
            startHomeActivity();
        }

    }

    // 获取版本名称
    private String getVersionName() {
        PackageManager manager = getPackageManager();

        try {// 获取包的信息
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
//            System.out.println("info = " + info);
            String versionName = info.packageName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    *  获取本地APP的版本号
    * */
    private int getVersionCode() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*
    *   从服务器获取版本信息进行校验
    * */
    private void checkVersion() {

        // 模拟网络请求
        new Thread() {
            @Override
            public void run() {
                Message message = Message.obtain();
                mVersionName = "mVersionName";
                mVersionCode = 2;
                mDesc = "新版本功能";
                if (mVersionCode > getVersionCode()) {
                    // 有更新，弹出更新框
                    message.what = CODE_UPDATE_DIALOG;
                } else {
                    // 没有更新，进入主界面
                    message.what = CODE_ENTER_HOME;
                }
                mHandler.sendMessage(message);
            }
        }.start();




//        final long startTime = System.currentTimeMillis();
//        // 启动子线程异步加载数据
//        new Thread() {
//            @Override
//            public void run() {
//                Message message = Message.obtain();
//                HttpURLConnection conn = null;
//                URL url = null;
//                try {
//                    url = new URL("http://10.0.2.2:8080/update.json");
//                    conn = (HttpURLConnection) url.openConnection();
//                    // 设置请求方法
//                    conn.setRequestMethod("GET");
//                    // 设置连接超时
//                    conn.setConnectTimeout(5000);
//                    // 设置读取超时
//                    conn.setReadTimeout(5000);
//                    // 连接服务器
//                    conn.connect();
//
//                    if (conn.getResponseCode() == 200) {
//                        // 请求成功
//                        InputStream inputStream = conn.getInputStream();
//                        String result = StreamUtils.readFromStream(inputStream)；
//
//                        // 解析json
//                        JSONObject jsonObject = new JSONObject(result);
//                        mVersionName = jsonObject.getString("versionName");
//                        mVersionCode = jsonObject.getInt("versionCode");
//                        mDesc = jsonObject.getString("description");
//                        mDownloadUrl = jsonObject.getString("downloadUrl");
//
//                        if (mVersionCode > getVersionCode()) {
//                            // 说明有更新，弹出升级对话框
//                            message.what = CODE_UPDATE_DIALOG;
//                        } else {
//                            // 没有更新
//                            message.what = CODE_ENTER_HOME;
//                        }
//                    }
//                } catch (MalformedURLException e) {
//                    message.what = CODE_URL_ERROR;
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    message.what = CODE_NET_ERROR;
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    message.what = CODE_JSON_ERROR;
//                    e.printStackTrace();
//                } finally {
//                     long endTime = System.currentTimeMillis();
//                    long timeUsed = endTime - startTime; // 访问网络花费的时间
//                    if (timeUsed < 2000) {
//                        try {
//                            Thread.sleep(200 - timeUsed);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    mHandler.sendMessage(message);
//                    if (conn != null) {
//                        conn.disconnect(); // 关闭网络连接
//                    }
//                }
//            }
//        }.start();
    }

    /*
    *  创建对话框
    * */
    private void showUpdateDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本" + mVersionName);
        builder.setMessage(mDesc);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("立即更新");
                Toast.makeText(SplashActivity.this, "更新中。。。", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(2000);
                    startHomeActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startHomeActivity();
            }
        });

        // 设置取消的监听，用户点击返回键时会触发
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                startHomeActivity();
            }
        });

        builder.show();
    }

    /*
    *  进入主界面
    * */
    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
