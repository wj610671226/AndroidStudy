package com.itheima.mobileguard.activities;

import java.lang.reflect.Method;
import java.util.List;

import com.itheima.mobileguard.R;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
/**
 * ============================================================
 * 
 * �� Ȩ �� �������Ա�������� ��Ȩ���� (c) 2015
 * 
 * �� �� : ��ΰ��
 * 
 * �� �� �� 1.0
 * 
 * �������� �� 2015-3-7 ����5:02:37
 * 
 * �� �� ��
 * 
 *      ��������
 * �޶���ʷ ��
 * 
 * ============================================================
 **/
public class CleanCacheActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.activity_clean_cache);
		PackageManager packageManager = getPackageManager();
		/**
		 * ����2������
		 * ��һ����������һ������
		 * �ڶ�����������aidl�Ķ���
		 */
//		  * @hide
//		     */
//		    public abstract void getPackageSizeInfo(String packageName,
//		            IPackageStatsObserver observer);
//		packageManager.getPackageSizeInfo();
		
		//��װ���ֻ��������е�Ӧ�ó���
		List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
		
		for (PackageInfo packageInfo : installedPackages) {
			getCacheSize(packageInfo);
		}
		
		
	}

	private void getCacheSize(PackageInfo packageInfo) {
		try {
			Class<?> clazz = getClassLoader().loadClass("packageManager");
			//ͨ�������ȡ����ǰ�ķ���
			Method method = clazz.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
