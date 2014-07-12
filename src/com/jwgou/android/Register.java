package com.jwgou.android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class Register extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("账号注册");
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.login)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.login:
			String email = ((EditText)findViewById(R.id.email)).getText().toString();
			if(Util.isEmpty(email) || !Util.isEmail(email)){
				ShowToast("请输入正确邮箱");
				return;
			}
			register(email);
//			startActivity(new Intent(this, Register2.class).putExtra("email", email));
			break;

		default:
			break;
		}
	}

	private void register(final String email) {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(!Util.isEmpty(result)){
					ShowToast(result);
				}
				startActivity(new Intent(Register.this, Register2.class));
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().Register(email);
//				return null;
			}});
	}

}
