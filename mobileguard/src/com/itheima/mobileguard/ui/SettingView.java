package com.itheima.mobileguard.ui;

import com.itheima.mobileguard.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * �������Բ��֡� ������ʱ�� �Ͱ���������ݳ�ʼ������
 * 
 * @author Administrator
 * 
 */
public class SettingView extends RelativeLayout {
	private CheckBox cb_status;
	private TextView tv_desc;
	private TextView tv_title;
	private String[] descs;

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		System.out.println("---3");
	}

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobileguard", "title");
		String desc = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobileguard", "desc");
		//��|�ر�
		init(context);
		setTitle(title);
		descs = desc.split("#");
		setDesc(descs, false);
	}

	public SettingView(Context context) {
		super(context);
		init(context);
		System.out.println("---1");
	}

	/**
	 * ��ʼ���ķ�����
	 */
	private void init(Context context) {
		// ����Դ�ļ�ת����view������ʾ���Լ�����
		View view = View.inflate(context, R.layout.ui_setting_view, null);
		this.addView(view);
		cb_status = (CheckBox) findViewById(R.id.cb_status);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_title = (TextView) findViewById(R.id.tv_title);
		this.setBackgroundResource(R.drawable.list_selector);
	}

	/**
	 * �����Զ���ؼ��ı���
	 * 
	 * @param text
	 */
	public void setTitle(String text) {
		tv_title.setText(text);
	}

	/**
	 * �����Զ���ؼ�������
	 * 
	 * @param text
	 */
	public void setDesc(String[] descs, boolean checked) {
		this.descs = descs;
		if (checked) {
			tv_desc.setText(descs[0]);
		} else {
			tv_desc.setText(descs[1]);
		}
	}

	/**
	 * �ж���Ͽؼ��Ƿ�ѡ��
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return cb_status.isChecked();
	}

	/**
	 * ������Ͽؼ���ѡ�з�ʽ
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		cb_status.setChecked(checked);
		if (checked) {
			tv_desc.setTextColor(Color.GREEN);
			if (descs != null) {
				tv_desc.setText(descs[0]);
			}
		} else {
			tv_desc.setTextColor(Color.RED);
			if (descs != null) {
				tv_desc.setText(descs[1]);
			}
		}
	}

}
