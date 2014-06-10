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
import com.jwgou.android.fragments.OrderFragment;

public class MyOrderActivity extends BaseFragmentActivity implements OnClickListener{

	private FragmentManager mFragmentManager;
	private FragmentTransaction transaction;
	private OrderFragment fragment1,fragment2,fragment3;
	private View v1,v2,v3;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.myorder);
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
			v1.setBackgroundColor(Color.RED);
			if(fragment1 == null){
				fragment1 = new OrderFragment();
				fragment1.setArguments(b);
				transaction.add(R.id.content, fragment1);
			}else{
				transaction.show(fragment1);
			}
			break;
		case 2:
			v2.setBackgroundColor(Color.RED);
			if(fragment2 == null){
				fragment2 = new OrderFragment();
				fragment2.setArguments(b);
				transaction.add(R.id.content, fragment2);
			}else{
				transaction.show(fragment2);
			}
			break;
		case 3:
			v3.setBackgroundColor(Color.RED);
			if(fragment3 == null){
				fragment3 = new OrderFragment();
				fragment3.setArguments(b);
				transaction.add(R.id.content, fragment3);
			}else{
				transaction.show(fragment3);
			}
			break;

		default:
			break;
		}
		transaction.commit();
	}

	private void clearText() {
		v1.setBackgroundColor(Color.parseColor("#aaaaaa"));
		v2.setBackgroundColor(Color.parseColor("#aaaaaa"));
		v3.setBackgroundColor(Color.parseColor("#aaaaaa"));
	}

	private void hideFragments(FragmentTransaction transaction2) {

		if (fragment1 != null) {
			transaction.hide(fragment1);
		}
		if (fragment2 != null) {
			transaction.hide(fragment2);
		}
		if (fragment3 != null) {
			transaction.hide(fragment3);
		}
	
	}

	private void initView() {
		mFragmentManager = getSupportFragmentManager();
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("订单列表");
		((TextView)findViewById(R.id.tab1)).setOnClickListener(this);
		((TextView)findViewById(R.id.tab2)).setOnClickListener(this);
		((TextView)findViewById(R.id.tab3)).setOnClickListener(this);
		v1 = (View)findViewById(R.id.view1);
		v2 = (View)findViewById(R.id.view2);
		v3 = (View)findViewById(R.id.view3);
		
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
		case R.id.tab3:
			setMyTabHost(3);
			break;

		default:
			break;
		}
		
	}

}
