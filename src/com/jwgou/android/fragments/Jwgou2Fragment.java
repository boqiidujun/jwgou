package com.jwgou.android.fragments;

import com.jwgou.android.AddressManagerActivity;
import com.jwgou.android.MainActivity;
import com.jwgou.android.MyMessageActivity;
import com.jwgou.android.MyOrderActivity;
import com.jwgou.android.R;
import com.jwgou.android.RedPackage;
import com.jwgou.android.WebViewActivity;
import com.jwgou.android.entities.User;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Jwgou2Fragment extends Fragment implements OnClickListener {

	private FragmentManager mFragmentManager;
	private FragmentTransaction transaction;
	private JwgouOrderFragment fragment1,fragment2;
	private View v1,v2;
	private View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.jwgouorder, container, false);
		initView(view);
		return view;
	}

	private void initView(View v) {
		mFragmentManager = getChildFragmentManager();
		((TextView)v.findViewById(R.id.title)).setText("已参团产品列表");
		((TextView)v.findViewById(R.id.tab1)).setOnClickListener(this);
		((TextView)v.findViewById(R.id.tab2)).setOnClickListener(this);
		v1 = (View)v.findViewById(R.id.view1);
		v2 = (View)v.findViewById(R.id.view2);
		setMyTabHost(1);
		
	}

	private void clearText() {
		v1.setBackgroundColor(Color.parseColor("#aaaaaa"));
		v2.setBackgroundColor(Color.parseColor("#aaaaaa"));
	}

	private void hideFragments(FragmentTransaction transaction2) {

		if (fragment1 != null) {
			transaction.hide(fragment1);
		}
		if (fragment2 != null) {
			transaction.hide(fragment2);
		}
	
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
				fragment1 = new JwgouOrderFragment();
				fragment1.setArguments(b);
				transaction.add(R.id.content, fragment1);
			}else{
				transaction.show(fragment1);
			}
			break;
		case 2:
			v2.setBackgroundColor(Color.RED);
			if(fragment2 == null){
				fragment2 = new JwgouOrderFragment();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
