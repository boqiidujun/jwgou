package com.jwgou.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jwgou.android.adapter.MyActionAdapter;
import com.jwgou.android.adapter.MyViewPagerAdapter;
import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.Action;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class AllLiveActivity extends BaseActivity implements OnClickListener{

	private PullToRefreshListView listview;
	private MyActionAdapter mAdapter;
	private ArrayList<Action> actionList = new ArrayList<Action>();
	private LinearLayout fullscreen_loading_root;
	private int pageNums = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initView();
	}

	private void initView() {
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("");
		listview = (PullToRefreshListView)findViewById(R.id.listview);
		mAdapter = new MyActionAdapter(this, actionList);
		listview.setAdapter(mAdapter);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					pageNums = 1;
					actionList.clear();
				} else if (refreshView.isFooterShown()) {
				}
				getData();
			}
		});
		fullscreen_loading_root = (LinearLayout) findViewById(R.id.fullscreen_loading_root);
		getData();
	}

	protected void getData() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				fullscreen_loading_root.setVisibility(View.GONE);
				listview.onRefreshComplete();
				Toast.makeText(AllLiveActivity.this, result, Toast.LENGTH_SHORT).show();

				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								for (int i = 0; i < data.length(); i++) {
									Action a = new Action();
									a.Json2Self(data.optJSONObject(i));
									actionList.add(a);
								}
								mAdapter.notifyDataSetChanged();
							}
						}else
							ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				if(actionList.size() == 0)
					fullscreen_loading_root.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetActivityDoing();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
