package com.itheima.mobileguard.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

	private static final String TAG = "BootCompleteReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "�ֻ����������");
		// ���sim���Ƿ����仯
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		// ��ȡ����������״̬
		boolean protecting = sp.getBoolean("protecting", false);
		if (protecting) {
			// �õ��󶨵�sim������
			String bindsim = sp.getString("sim", "");
			// �õ��ֻ����ڵ�sim������
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String realsim = tm.getSimSerialNumber() + "dafa";
			if (bindsim.equals(realsim)) {
				Log.i(TAG, "sim��δ�����仯�����������ֻ�");
			} else {
				Log.i(TAG, "SIM���仯��");
				String safenumber = sp.getString("safenumber", "");
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(safenumber, null, "sim changed!",
						null, null);
			}
		}else{
			Log.i(TAG, "��������ûӴu����");
		}
	}

}
