package com.itheima.mobileguard.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.utils.Md5Utils;
import com.itheima.mobileguard.utils.UIUtils;
/*
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                   O\  =  /O
 *                ____/`---'\____
 *              .'  \\|     |//  `.
 *             /  \\|||  :  |||//  \
 *            /  _||||| -:- |||||-  \
 *            |   | \\\  -  /// |   |
 *            | \_|  ''\---/''  |   |
 *            \  .-\__  `-`  ___/-. /
 *          ___`. .'  /--.--\  '. .'__
 *       ."" '<  `.___\_<|>_/___.'  >'"".
 *      | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *      \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *				  	`=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *               ���汣��       ����BUG
*/
public class HomeActivity extends Activity {
	private GridView gv_home;
	
	//������Ϣ
	private SharedPreferences sp;
	
	private String[] names = { "�ֻ�����", "ͨѶ��ʿ", "����ܼ�", "���̹���", "����ͳ��", "�ֻ�ɱ��",
			"��������", "�߼�����", "��������" };
	private int[] icons = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app_selector, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		gv_home = (GridView) findViewById(R.id.gv_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		//����gridview��Ŀ�ĵ���¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				case 0: //�ֻ�����
					//�ж��û��Ƿ����ù�����
					if(isSetupPwd()){
						//���ù����룬��ʾ��������Ի���
						showEnterPwdDialog();
					}else{
						//û�����ù����룬��ʾ��������Ի���
						showSetupPwdDialog();
					}
					break;
				case 1://ͨѶ��ʿ
					intent = new Intent(HomeActivity.this,CallSmsSafeActivity.class);
					startActivity(intent);
					break;
				case 2://����ܼ�
					intent = new Intent(HomeActivity.this,AppManagerActivity.class);
					startActivity(intent);
					break;
				case 3://���̹���
					intent = new Intent(HomeActivity.this,TaskManagerActivity.class);
					startActivity(intent);
					break;
				case 5://�ֻ�ɱ��
					intent = new Intent(HomeActivity.this,AntivirusActivity.class);
					startActivity(intent);
					break;
				case 7://�߼�����
					intent = new Intent(HomeActivity.this,AtoolsActivity.class);
					startActivity(intent);
					break;
				case 8://��������
					intent = new Intent(HomeActivity.this,SettingCenterActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		gv_home.setAdapter(new HomeAdapter());
		super.onStart();
	}
	
	private EditText et_pwd;
	private EditText et_pwd_confirm;
	private Button bt_ok;
	private Button bt_cancel;
	//��Ԫģʽ
	private AlertDialog dialog;
	
	/**
	 * ��ʾ��������Ի���
	 */
	protected void showSetupPwdDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_setup_pwd, null);
		et_pwd = (EditText) view.findViewById(R.id.et_pwd);
		et_pwd_confirm = (EditText) view.findViewById(R.id.et_pwd_confirm);
		bt_ok = (Button) view.findViewById(R.id.bt_ok);
		bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		//ȡ����ť
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String pwd = et_pwd.getText().toString().trim();
				String pwd_confirm = et_pwd_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(pwd)||TextUtils.isEmpty(pwd_confirm)){
					UIUtils.showToast(HomeActivity.this, "���벻��Ϊ��");
					return;
				}
				if(!pwd.equals(pwd_confirm)){
					UIUtils.showToast(HomeActivity.this, "�������벻һ��");
					return;
				}
				Editor editor = sp.edit();
				editor.putString("password", Md5Utils.encode(pwd));
				editor.commit();
				dialog.dismiss();
			}
		});
		builder.setView(view);
		dialog = builder.show();
	}

	
	private EditText et_enter_pwd;
	/**
	 * ��ʾ��������Ի���
	 */
	protected void showEnterPwdDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_enter_pwd, null);
		et_enter_pwd = (EditText) view.findViewById(R.id.et_enter_pwd);
		bt_ok = (Button) view.findViewById(R.id.bt_ok);
		bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		//ȡ����ť
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.��ȡ�û����������
				String enterPwd = et_enter_pwd.getText().toString().trim();
				if(TextUtils.isEmpty(enterPwd)){
					UIUtils.showToast(HomeActivity.this, "���벻��Ϊ��");
					return;
				}
				//2.��ȡ�û�ԭ�����õ����� 
				String savedPwd = sp.getString("password", "");
				//3.��������Ƿ���ȷ
				if(Md5Utils.encode(enterPwd).equals(savedPwd)){
					//����һ�£������ֻ���������
					Intent intent  = new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					dialog.dismiss();
				}else{
					//���������������
					UIUtils.showToast(HomeActivity.this, "���������������");
				}
			}
		});
		builder.setView(view);
		dialog = builder.show();
	}

	/**
	 * �ж��û��Ƿ����ù�����
	 * @return
	 */
	private boolean isSetupPwd(){
		String password = sp.getString("password", null);
		if(TextUtils.isEmpty(password)){
			return false;
		}else{
			return true;
		}
	}
	

	private class HomeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.item_home_grid, null);
			} else {
				view = convertView;
			}
			ImageView iv = (ImageView) view.findViewById(R.id.iv_homeitem_icon);
			TextView tv = (TextView) view.findViewById(R.id.tv_homeitem_name);
			
			tv.setText(names[position]);
			if(position==0){
				String newname = getSharedPreferences("config", MODE_PRIVATE).getString("newname", "");
				if(!TextUtils.isEmpty(newname)){
					//����û��������µ����� Ӧ����ʾ�µ�����
					tv.setText(newname);
				}
			}
			iv.setImageResource(icons[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
}
