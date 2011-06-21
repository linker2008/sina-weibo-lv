package com.lv.sinaweibo;

import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.androidexamples.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Login extends Activity {

	private String TAG = "Login";
	final int MESSAGE_LOGIN_START = 1;
	final int MESSAGE_LOGIN_END = 2;
	private EditText et_username=null;
	private EditText et_password=null;
	private ProgressBar progress=null;
	private Button bt_login=null;
	private boolean isLogining = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_login);
		et_username = (EditText)findViewById(R.id.editTextUserName);
		et_password = (EditText)findViewById(R.id.editTextPassWord);
		bt_login = (Button)findViewById(R.id.buttonLogin);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		if(bt_login != null){
			bt_login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					buttonListener();
				}
			});
		}
	}
	
	private void buttonListener(){
		ui_handler.sendMessage(ui_handler.obtainMessage(MESSAGE_LOGIN_START));
		Log.e(TAG, "buttonListener,out");
	}

	class LoginHandler extends Handler {

		@Override
		public synchronized void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_LOGIN_START:
				isLogining = true;
				progress.setVisibility(ProgressBar.VISIBLE);
				bt_login.setEnabled(false);
				Thread thread = new Thread(){

					@Override
					public void run() {
						login();
						Log.e(TAG, "run,out");
					}
				};
				thread.start();
				break;

			case MESSAGE_LOGIN_END:
				isLogining = false;
				progress.setVisibility(ProgressBar.GONE);
				bt_login.setEnabled(true);
				break;
				
			default:
				break;
			}
		}
		
	}
	
	LoginHandler ui_handler = new LoginHandler();
	
	private void login(){
		et_username.getText();
		et_password.getText();
		
		try {
//			String args[] = {"e0bcd11246be4e83b7e186958fd3551b", "fdd481f26139ddef57909a826a60695d"};
			String args[] = {et_username.getText().toString(), et_password.getText().toString()};
			User user = getWeibo(false,args).verifyCredentials();
			if(user.isVerified()){
				Log.e(TAG, "verified");
			}else{
			}
			System.out.println(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
			Log.e(TAG, "MESSAGE_LOGIN_END");
			ui_handler.sendMessage(ui_handler.obtainMessage(MESSAGE_LOGIN_END));
		}

	}
	
	private static Weibo getWeibo(boolean isOauth,String[] args) {
		Weibo weibo = new Weibo();
		if(isOauth) {//oauth验证方式 args[0]:访问的token；args[1]:访问的密匙
			weibo.setToken(args[0], args[1]);
		}else {//用户登录方式
   			weibo.setUserId(args[0]);//用户名/ID
   			weibo.setPassword(args[1]);//密码
		}
		return weibo;
	}

}
