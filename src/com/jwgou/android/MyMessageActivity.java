package com.jwgou.android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseFragmentActivity;
import com.jwgou.android.fragments.MessageFragment;

public class MyMessageActivity extends BaseFragmentActivity implements OnClickListener{

	private FragmentManager mFragmentManager;
	private FragmentTransaction transaction;
	private MessageFragment fragment1,fragment2;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mymessage);
		initView();
		setMyTabHost(1);
	}

	private void setMyTabHost(int index) {
		transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		clearText();
		Bundle b = new Bundle();
		b.putInt("INDEX", index);
		switch (index) {
		case 1:
			if(fragment1 == null){
				fragment1 = new MessageFragment();
				fragment1.setArguments(b);
				transaction.add(R.id.content, fragment1);
			}else{
				transaction.show(fragment1);
			}
			break;
		case 2:
			if(fragment2 == null){
				fragment2 = new MessageFragment();
				fragment2.setArguments(b);
				transaction.add(R.id.content, fragment2);
			}else{
				transaction.show(fragment2);
			}
			break;

		default:
			break;
		}
		transaction.commit();
	}

	private void clearText() {
	}

	private void hideFragments(FragmentTransaction transaction2) {

		if (fragment1 != null) {
			transaction.hide(fragment1);
		}
		if (fragment2 != null) {
			transaction.hide(fragment2);
		}
	
	}

	private void initView() {
		mFragmentManager = getSupportFragmentManager();
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("评论列表");
		((TextView)findViewById(R.id.tab1)).setOnClickListener(this);
		((TextView)findViewById(R.id.tab2)).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.tab1:
			setMyTabHost(1);
			break;
		case R.id.tab2:
			setMyTabHost(2);
			break;

		default:
			break;
		}
		
	}

}
