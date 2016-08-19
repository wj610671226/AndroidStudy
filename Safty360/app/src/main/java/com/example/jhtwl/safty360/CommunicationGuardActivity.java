package com.example.jhtwl.safty360;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhtwl.safty360.Bean.BlackNumber;
import com.example.jhtwl.safty360.Dao.BlackNumberDao;

import java.util.List;

// 通讯卫士
public class CommunicationGuardActivity extends AppCompatActivity {

    private ListView list_view;
    private List<BlackNumber> blackNumbers;
    private LinearLayout ll_pb;
    private BlackNumberDao dao;

    /**
     * 开始的位置
     */
    private int mStartIndex = 0;
    /**
     * 每页展示20条数据
     */
    private int maxCount = 20;

    /**
     * 一共有多少页面
     */
    private int totalPage;
    private int totalNumber;

    private CommunicationGuardAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ll_pb.setVisibility(View.INVISIBLE);
            if (adapter == null) {
                adapter = new CommunicationGuardAdapter();
                list_view.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_guard);

        initUI();
        initData();
    }

    public void addBlackNumber(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CommunicationGuardActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(CommunicationGuardActivity.this, R.layout.dialog_add_black_number_item, null);
        final EditText et_number = (EditText) view.findViewById(R.id.et_number);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancel);

        final CheckBox cb_phone = (CheckBox) view.findViewById(R.id.cb_phone);
        final CheckBox cb_sms = (CheckBox) view.findViewById(R.id.cb_sms);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = et_number.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(CommunicationGuardActivity.this, "请输入黑名单号码", Toast.LENGTH_SHORT).show();;
                    return;
                }

                String mode = "";

                if (cb_phone.isChecked() && cb_sms.isChecked()) {
                    mode = "1";
                } else if (cb_phone.isChecked()) {
                    mode = "2";
                } else if (cb_sms.isChecked()) {
                    mode = "3";
                } else {
                    Toast.makeText(CommunicationGuardActivity.this, "请勾选拦截模式", Toast.LENGTH_SHORT).show();;
                    return;
                }
                BlackNumber blackNumber = new BlackNumber();
                blackNumber.setNumber(number);
                blackNumber.setMode(mode);
                blackNumbers.add(0, blackNumber);
                // 把电话号码添加到数据库
                dao.add(number, mode);

                if (adapter == null) {
                    adapter = new CommunicationGuardAdapter();
                    list_view.setAdapter(adapter);
                } else  {
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void initUI() {
        ll_pb = (LinearLayout) findViewById(R.id.ll_pb);
        // 展示加载的圈圈
        ll_pb.setVisibility(View.VISIBLE);
        list_view = (ListView) findViewById(R.id.list_view);
        // 设置滚动监听
        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 获取到最后一条显示的数据
                        int lastVisiblePosition = list_view.getLastVisiblePosition();
                        if (lastVisiblePosition == blackNumbers.size() - 1) {
                            // 加载更多数据，更改加载数据额开始位置
                            mStartIndex += maxCount;
                            if (mStartIndex >= totalNumber) {
                                Toast.makeText(CommunicationGuardActivity.this, "没有更多数据了.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            initData();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    private void initData() {
        dao = new BlackNumberDao(CommunicationGuardActivity.this);
        // 一共有多少条数据
        totalNumber = dao.getTotleNumber();
        new Thread() {
            @Override
            public void run() {
                // 分批加载数据
                if (blackNumbers == null) {
                    blackNumbers = dao.findPr2(mStartIndex, maxCount);
                } else {
                    // 把后面的数据，追加到blackNumbers集合里面 防止黑名单被覆盖
                    blackNumbers.addAll(dao.findPr2(mStartIndex, maxCount));
                }

                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private class CommunicationGuardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return blackNumbers.size();
        }

        @Override
        public Object getItem(int i) {
            return blackNumbers.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(CommunicationGuardActivity.this, R.layout.comm_guard_item, null);
                holder = new ViewHolder();
                holder.tv_number = (TextView) view.findViewById(R.id.tv_number);
                holder.tv_mode = (TextView) view.findViewById(R.id.tv_mode);
                holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_number.setText(blackNumbers.get(i).getNumber());
            String mode = blackNumbers.get(i).getMode();
            if (mode.equals("1")) {
                holder.tv_mode.setText("来电拦截+短信");
            } else if (mode.equals("2")) {
                holder.tv_mode.setText("电话拦截");
            } else if (mode.equals("短信拦截")) {
                holder.tv_mode.setText("短信拦截");
            }
            final BlackNumber numberInfo = blackNumbers.get(i);
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = numberInfo.getNumber();
                    boolean result = dao.delete(number);
                    if (result) {
                        Toast.makeText(CommunicationGuardActivity.this, "删除成", Toast.LENGTH_SHORT).show();;
                        blackNumbers.remove(numberInfo);
                        // 刷新
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CommunicationGuardActivity.this, "删除失败", Toast.LENGTH_SHORT).show();;
                    }
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        TextView tv_number;
        TextView tv_mode;
        ImageView iv_delete;
    }
}


