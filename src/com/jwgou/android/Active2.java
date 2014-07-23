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

public class Active2 extends BaseActivity implements OnClickListener {

	private EditText et3,et4;
	private String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_2);
		email = this.getIntent().getStringExtra("email");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("用户激活");
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.login)).setOnClickListener(this);
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
			if(IsEmpty(et3.getText().toString())){
				ShowToast("请输入支付密码");
				return;
			}
			if(IsEmpty(et4.getText().toString())){
				ShowToast("请再次输入支付密码");
				return;
			}
			if(!et3.getText().toString().equals(et4.getText().toString())){
				ShowToast("两次支付密码输入不一致");
				return;
			}
			startActivity(new Intent(this, Active3.class).putExtra("email", email).putExtra("pay", et3.getText().toString()));
			break;

		default:
			break;
		}
	}

	private boolean IsEmpty(String s){
		return Util.isEmpty(s);
	}

}
