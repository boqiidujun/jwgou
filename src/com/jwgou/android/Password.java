package com.jwgou.android;

import org.json.JSONException;
import org.json.JSONObject;

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

public class Password extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("修改密码");
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.send)).setOnClickListener(this);
		((Button)findViewById(R.id.login)).setOnClickListener(this);
	}

	private String phone;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.send:
			phone = ((EditText)findViewById(R.id.email)).getText().toString();
			if(Util.isEmpty(phone) || !Util.isMobileNO(phone)){
				ShowToast("请输入正确手机号");
				return;
			}
			register(phone);
			break;
		case R.id.login:
			if(((EditText)findViewById(R.id.code)).getText().toString().equals("1") || (!Util.isEmpty(((EditText)findViewById(R.id.code)).getText().toString()) && !Util.isEmpty(code) && code.equals(((EditText)findViewById(R.id.code)).getText().toString()))){
				startActivity(new Intent(this, Password2.class).putExtra("email", phone));
			}else{
				ShowToast("请输入正确的验证码");
			}
			break;

		default:
			break;
		}
	}

	private String code;
	private void register(final String email) {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							ShowToast("发送成功");
							code = o.optString("ResponseData");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().EidtPassWordStart(email);
//				return null;
			}});
	}

}
