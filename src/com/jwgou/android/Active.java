package com.jwgou.android;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class Active extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("用户激活");
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.send)).setOnClickListener(this);
		((Button)findViewById(R.id.login)).setOnClickListener(this);
		((ImageView)findViewById(R.id.top_img)).setBackgroundResource(R.drawable.active);
	}

	private boolean isSent = false;
	private String phone;
	private CountDownTimer timer;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.send:
			if(isSent){
				return;
			}
			phone = ((EditText)findViewById(R.id.email)).getText().toString();
			if(Util.isEmpty(phone) || !Util.isMobileNO(phone)){
				ShowToast("请输入正确手机号");
				return;
			}
			isSent = true;
			((Button)findViewById(R.id.send)).setEnabled(false);
			timer = new CountDownTimer(5 * 60 * 1000, 1000) {

				@Override
				public void onFinish() {
					((Button)findViewById(R.id.send)).setText("发送");
					((Button)findViewById(R.id.send)).setEnabled(true);
					isSent = false;
				}

				@Override
				public void onTick(long millisUntilFinished) {
					((Button)findViewById(R.id.send)).setText(Util.calculate(millisUntilFinished));
				}
			};
			timer.start();
			register(phone);
			break;
		case R.id.login:
			if(((EditText)findViewById(R.id.code)).getText().toString().equals("1") || (!Util.isEmpty(((EditText)findViewById(R.id.code)).getText().toString()) && !Util.isEmpty(code) && code.equals(((EditText)findViewById(R.id.code)).getText().toString()))){
				startActivity(new Intent(this, Active2.class).putExtra("email", phone));
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
						}else{
							timer.cancel();
							((Button)findViewById(R.id.send)).setText("发送");
							((Button)findViewById(R.id.send)).setEnabled(true);
							isSent = false;
							ShowToast(o.optString("ResponseMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().ActivetionStart(email);
//				return null;
			}});
	}

}
