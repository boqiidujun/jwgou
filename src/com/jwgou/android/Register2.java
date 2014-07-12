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

public class Register2 extends BaseActivity implements OnClickListener {

	private EditText et1,et2,et3,et4;
	private String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_2);
		email = this.getIntent().getStringExtra("email");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("账号注册");
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.login)).setOnClickListener(this);
		et1 = (EditText)findViewById(R.id.et11); 
		et2 = (EditText)findViewById(R.id.et2); 
		et3 = (EditText)findViewById(R.id.et3); 
		et4 = (EditText)findViewById(R.id.et4); 
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.login:
			Register();
			break;

		default:
			break;
		}
	}

	private void Register() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(!Util.isEmpty(result)){
					
				}
				startActivity(new Intent(Register2.this, Register3.class));
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().Register(email);
//				return null;
			}});
	}

}
