package com.jwgou.android;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseFragmentActivity;
import com.jwgou.android.fragments.RedBagFragment;

public class RedPackage extends BaseFragmentActivity implements OnClickListener {

	private FragmentManager mFragmentManager;
	private FragmentTransaction transaction;
	private RedBagFragment fragment1,fragment2;
	
//	private PullToRefreshListView listview;
//	private MyRedBagAdapter mAdapter;
//	private ArrayList<RedBag> list;
//	private LinearLayout fullscreen_loading_root;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redpackage);
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
				fragment1 = new RedBagFragment();
				fragment1.setArguments(b);
				transaction.add(R.id.content, fragment1);
			}else{
				transaction.show(fragment1);
			}
			break;
		case 2:
			if(fragment2 == null){
				fragment2 = new RedBagFragment();
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
		// TODO Auto-generated method stub
		
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
		((TextView)findViewById(R.id.title)).setText("查看红包列表");
		((TextView)findViewById(R.id.tab1)).setOnClickListener(this);
		((TextView)findViewById(R.id.tab2)).setOnClickListener(this);
		
	
//		((Button)findViewById(R.id.back)).setOnClickListener(this);
//		((TextView)findViewById(R.id.title)).setText("红包列表");
//		listview = (PullToRefreshListView)findViewById(R.id.listview);
//		list = new ArrayList<RedBag>();
//		mAdapter = new MyRedBagAdapter(this, list);
//		listview.setAdapter(mAdapter);
//		fullscreen_loading_root = (LinearLayout)findViewById(R.id.fullscreen_loading_root);
//		getData();
	}

//	private void getData() {
//		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){
//
//			@Override
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
//				fullscreen_loading_root.setVisibility(View.GONE);
//			}
//
//			@Override
//			protected void onPreExecute() {
//				super.onPreExecute();
//				fullscreen_loading_root.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			protected String doInBackground(Void... params) {
//				return NetworkService.getInstance().GetUserRedList(getApp().user.UId);
//			}});
//		
//	}

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
