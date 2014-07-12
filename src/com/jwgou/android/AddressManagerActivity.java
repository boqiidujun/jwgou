package com.jwgou.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.adapter.MyAddressAdapter;
import com.jwgou.android.adapter.MyListViewAdapter;
import com.jwgou.android.adapter.MyAddressAdapter.ItemListener;
import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.Action;
import com.jwgou.android.entities.Address;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;
import com.jwgou.android.utils.Utility;

public class AddressManagerActivity extends BaseActivity implements OnClickListener, ItemListener {

	private PullToRefreshListView listview;
	private MyAddressAdapter mAdapter;
	private ArrayList<Address> list = new ArrayList<Address>();
	private LinearLayout fullscreen_loading_root;
	private int pageNums = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initView();
	}

	private void initView() {
		((Button) findViewById(R.id.back)).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText("收货地址");
		listview = (PullToRefreshListView) findViewById(R.id.listview);
		listview.setMode(Mode.BOTH);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					pageNums = 1;
					list.clear();
				} else if (refreshView.isFooterShown()) {
				}
				getData();
			}
		});
		list = new ArrayList<Address>();
		mAdapter = new MyAddressAdapter(this, list, this);
		listview.setAdapter(mAdapter);
		fullscreen_loading_root = (LinearLayout) findViewById(R.id.fullscreen_loading_root);
		getData();
	}

	private void getData() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				listview.onRefreshComplete();
				fullscreen_loading_root.setVisibility(View.GONE);
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus", -1) == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								for (int i = 0; i < data.length(); i++) {
									Address a = new Address();
									a.Json2Self(data.optJSONObject(i));
									list.add(a);
								}
								mAdapter.notifyDataSetChanged();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if(list.size() == 0)
					fullscreen_loading_root.setVisibility(View.VISIBLE);
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetAddressListing(getApp().user.UId, pageNums++);
			}
		});

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

	@Override
	public void ClickListener(int index, int type) {
		ShowToast("index= " + index + ",type=" + type);
		switch (type) {
		case 1://default
			
			break;
		case 2://delete
			break;
		case 3://select
			Address a = list.get(index);
			Intent i = new Intent();
			i.putExtra("ADDRESS", a);
			setResult(RESULT_OK, i);
			finish();
			break;

		default:
			break;
		}
	}

}
