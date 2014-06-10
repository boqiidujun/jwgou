package com.jwgou.android;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.adapter.MyRedAdapter;
import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.entities.RedBag;
import com.jwgou.android.utils.HttpManager;

public class RedPackage extends BaseActivity implements OnClickListener {

	private PullToRefreshListView listview;
	private MyRedAdapter mAdapter;
	private ArrayList<RedBag> list;
	private LinearLayout fullscreen_loading_root;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redpackage);
		initView();
	}

	private void initView() {
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("红包列表");
		listview = (PullToRefreshListView)findViewById(R.id.listview);
		list = new ArrayList<RedBag>();
		mAdapter = new MyRedAdapter(this, list);
		listview.setAdapter(mAdapter);
		fullscreen_loading_root = (LinearLayout)findViewById(R.id.fullscreen_loading_root);
		getData();
	}

	private void getData() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				fullscreen_loading_root.setVisibility(View.GONE);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				fullscreen_loading_root.setVisibility(View.VISIBLE);
			}

			@Override
			protected String doInBackground(Void... params) {
				//TODO
				return null;
			}});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

}
