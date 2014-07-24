package com.jwgou.android.baseactivities;

import com.jwgou.android.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

public class BaseActivity extends Activity {

	private BaseApplication app;

	private ProgressDialog mDialog;
	public ProgressDialog GetDialog(){
		if(mDialog == null){
			mDialog = new ProgressDialog(this);
			mDialog.setMessage("努力加载中...");
		}
		return mDialog;
	}
	public void ShowToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	};
	
	public BaseApplication getApp(){
		if(app == null)
			app = (BaseApplication) getApplication();
		return app;
	}
	
	public void UserLogin(){
		startActivity(new Intent(this, Login.class));
	}
	
	public void UserLoginForResult(int requestCode){
		startActivityForResult(new Intent(this, Login.class), requestCode);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
