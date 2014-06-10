package com.jwgou.android.baseactivities;

import com.jwgou.android.Login;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class BaseFragmentActivity extends FragmentActivity {

	private BaseApplication app;
	
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
