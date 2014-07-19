package com.jwgou.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;

public class Password3 extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_4);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("修改密码");
		((TextView)findViewById(R.id.finish_txt)).setText("修改完成");
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
			startActivity(new Intent(this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;

		default:
			break;
		}
	}

}
