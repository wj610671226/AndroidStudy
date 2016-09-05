package com.itheima.mobileguard.receivers;

import com.itheima.mobileguard.services.KillProcesWidgetService;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MyAppWidget extends AppWidgetProvider {

	/**
	 * ��һ�δ�����ʱ��Ż���õ�ǰ���������ڵķ���
	 * 
	 * ��ǰ�Ĺ㲥����������ֻ��10���ӡ�
	 * ��������ʱ�Ĳ���
	 */
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		System.out.println("onEnabled");
		
		Intent intent = new Intent(context,KillProcesWidgetService.class);
		context.startService(intent);
	}

	/**
	 * �������������е�����С�ؼ���ɾ����ʱ��ŵ��õ�ǰ�������
	 */
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Intent intent = new Intent(context,KillProcesWidgetService.class);
		context.stopService(intent);
		System.out.println("onDisabled");
	}
}
