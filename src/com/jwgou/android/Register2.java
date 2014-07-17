package com.jwgou.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;
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
			startActivity(new Intent(this, Register3.class).putExtra("email", email).putExtra("login", et1.getText().toString()).putExtra("pay", et3.getText().toString()));
			break;

		default:
			break;
		}
	}

	private boolean IsEmpty(String s){
		return Util.isEmpty(s);
	}

}
