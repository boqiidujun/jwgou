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
	private boolean manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		manager = this.getIntent().getBooleanExtra("MANAGER", false);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		pageNums = 1;
		list.clear();
		getData();
	}

	private void initView() {
		((TextView)findViewById(R.id.addaddress)).setOnClickListener(this);
		((Button) findViewById(R.id.back)).setOnClickListener(this);
		((Button) findViewById(R.id.back)).setVisibility(View.VISIBLE);
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
		mAdapter = new MyAddressAdapter(this, list, this, manager);
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
		case R.id.addaddress:
			startActivity(new Intent(this, AddAddressActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void ClickListener(int index, int type) {
		Address a = list.get(index);
		switch (type) {
		case 1://default
			for (int i = 0; i < list.size(); i++) {
				list.get(i).UserDefault = 0;
			}
			list.get(index).UserDefault = 1;
			mAdapter.notifyDataSetChanged();
			Default(a, index);
			break;
		case 2://delete
			Delete(a, index);
			break;
		case 3://select
			Intent i = new Intent();
			i.putExtra("ADDRESS", a);
			setResult(RESULT_OK, i);
			finish();
			break;

		default:
			break;
		}
	}

	private void Delete(final Address a,final int index) {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus", -1) == Config.SUCCESS) {
							list.remove(index);
							mAdapter.notifyDataSetChanged();
						}else{
							ShowToast(o.optString("ResponseMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().DelAddress(a.AddressId, getApp().user.UId);
			}});
	}

	private void Default(final Address a, final int index) {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus", -1) == Config.SUCCESS) {
							for (int i = 0; i < list.size(); i++) {
								list.get(i).UserDefault = 0;
							}
							list.get(index).UserDefault = 1;
							mAdapter.notifyDataSetChanged();
						}else{
							ShowToast(o.optString("ResponseMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().EidtDefaultAddress(a.AddressId, getApp().user.UId);
			}});
	}

}
