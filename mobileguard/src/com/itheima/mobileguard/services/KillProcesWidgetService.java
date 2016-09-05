package com.itheima.mobileguard.services;

import java.util.Timer;
import java.util.TimerTask;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.receivers.MyAppWidget;
import com.itheima.mobileguard.utils.SystemInfoUtils;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.view.View;
import android.widget.RemoteViews;
/**
 * ============================================================
 * 
 * �� Ȩ �� �������Ա�������� ��Ȩ���� (c) 2015
 * 
 * �� �� : ��ΰ��
 * 
 * �� �� �� 1.0
 * 
 * �������� �� 2015-3-6 ����10:22:31
 * 
 * �� �� ��
 * 
 *      ��������С�ؼ��ķ���
 * �޶���ʷ ��
 * 
 * ============================================================
 **/
public class KillProcesWidgetService extends Service {

	private AppWidgetManager widgetManager;
	private Timer timer;
	private TimerTask timerTask;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		//����С�ؼ��Ĺ�����
		
		widgetManager = AppWidgetManager.getInstance(this);
		
		//ÿ��5���Ӹ���һ������
		//��ʼ����ʱ��
		
		timer = new Timer();
		//��ʼ��һ����ʱ����
		
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				
				System.out.println("KillProcesWidgetService");
				//����ǰѵ�ǰ�Ĳ����ļ���ӽ���
				/**
				 * ��ʼ��һ��Զ�̵�view
				 * Remote Զ��
				 */
				RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
				/**
				 * ��Ҫע�⡣�������findingviewyid�������
				 * ���õ�ǰ�ı�����һ���ж��ٸ�����
				 */
				int processCount = SystemInfoUtils.getProcessCount(getApplicationContext());
				//�����ı�
				views.setTextViewText(R.id.process_count,"�������е����:" + String.valueOf(processCount));
				//��ȡ����ǰ�ֻ�����Ŀ����ڴ�
				long availMem = SystemInfoUtils.getAvailMem(getApplicationContext());
				
				views.setTextViewText(R.id.process_memory, "�����ڴ�:" +Formatter.formatFileSize(getApplicationContext(), availMem));
				
				
				Intent intent = new Intent();
				
				//����һ����ʽ��ͼ
				intent.setAction("com.itheima.mobileguard");
				
				
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
				//���õ���¼�
				views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				
				
				//��һ��������ʾ������
				//�ڶ���������ʾ��ǰ����һ���㲥����ȥ����ǰ������С�ؼ�
				ComponentName provider = new ComponentName(getApplicationContext(), MyAppWidget.class);
				
				
				
				
				//��������
				widgetManager.updateAppWidget(provider, views);
				
			}
		};
		//��0��ʼ��ÿ��5���Ӹ���һ��
		timer.schedule(timerTask, 0, 5000);
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//�Ż�����
		if(timer != null || timerTask != null){
			timer.cancel();
			timerTask.cancel();
			timer = null;
			timerTask = null;
		}
		
	}

}
