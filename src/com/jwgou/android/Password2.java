package com.jwgou.android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
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
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class Password2 extends BaseActivity implements OnClickListener {

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
		mDialog = new ProgressDialog(this);
		((TextView)findViewById(R.id.title)).setText("修改密码");
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.login)).setOnClickListener(this);
		et1 = (EditText)findViewById(R.id.et1); 
		et2 = (EditText)findViewById(R.id.et11); 
		et3 = (EditText)findViewById(R.id.et2); 
		et4 = (EditText)findViewById(R.id.et22); 
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.login:
			if(IsEmpty(et1.getText().toString())){
				ShowToast("请输入登录密码");
				return;
			}
			if(IsEmpty(et2.getText().toString())){
				ShowToast("请再次输入登录密码");
				return;
			}
			if(IsEmpty(et3.getText().toString())){
				ShowToast("请输入支付密码");
				return;
			}
			if(IsEmpty(et4.getText().toString())){
				ShowToast("请再次输入支付密码");
				return;
			}
			if(!et1.getText().toString().equals(et2.getText().toString())){
				ShowToast("两次登录密码输入不一致");
				return;
			}
			if(!et3.getText().toString().equals(et4.getText().toString())){
				ShowToast("两次支付密码输入不一致");
				return;
			}
			Modify();
			startActivity(new Intent(this, Password3.class).putExtra("email", email).putExtra("login", et1.getText().toString()).putExtra("pay", et3.getText().toString()));
			break;

		default:
			break;
		}
	}
	private ProgressDialog mDialog;

	private void Modify() {
		String Phone1, password1, paypassword1;
		Phone1 = email;
		password1 = et1.getText().toString();
		paypassword1 = et3.getText().toString();
		final String Phone = Phone1;
		final String passwrod = password1;
		final String paypassword = paypassword1;
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				mDialog.dismiss();
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							startActivity(new Intent(Password2.this, Password3.class));
						}else
							ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				mDialog.setMessage("正在修改密码...");
				mDialog.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().EidtPassWord(Phone, passwrod, paypassword);
			}});
	}

	private boolean IsEmpty(String s){
		return Util.isEmpty(s);
	}

}
