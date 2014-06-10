package com.jwgou.android.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.R;
import com.jwgou.android.RedPackage;
import com.jwgou.android.adapter.MyRedBagAdapter;
import com.jwgou.android.adapter.MyRedBagAdapter.ItemListener;
import com.jwgou.android.baseactivities.BaseApplication;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.RedBag;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class RedBagFragment extends Fragment implements ItemListener {

	private View view;
	private int index;
	private PullToRefreshListView listview;
	private LinearLayout fullscreen_loading_root;
	private ArrayList<RedBag> list = new ArrayList<RedBag>();
	private BaseApplication app;
	private MyRedBagAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey("INDEX"))
			index = getArguments().getInt("INDEX");
		app = ((RedPackage)getActivity()).getApp();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.refresh_list, container, false);
		initView();
		return view;
	}

	private void initView() {
		listview = (PullToRefreshListView) view.findViewById(R.id.lvPullToRefresh);
		mAdapter = new MyRedBagAdapter(getActivity(), list);
		listview.setAdapter(mAdapter);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				list.clear();
				getData();
			}
		});
		fullscreen_loading_root = (LinearLayout) view.findViewById(R.id.fullscreen_loading_root);
		getData();
	}

	private void getData() {
		new HttpManager(getActivity()).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				fullscreen_loading_root.setVisibility(View.GONE);
				listview.onRefreshComplete();
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
				//TODO
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus", -1) == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								for (int i = 0; i < data.length(); i++) {
									RedBag r = new RedBag();
									r.Json2Self(data.optJSONObject(i));
									list.add(r);
								}
								mAdapter.notifyDataSetChanged();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				if(list.size() > 0)
					fullscreen_loading_root.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(Void... params) {
				if(index == 1)
					return NetworkService.getInstance().GetUserRedList(app.user.UId);
				return NetworkService.getInstance().GetUserOverdueRedList(app.user.UId);
			}
		});
	}

	@Override
	public void OnClick(RedBag m, int type) {
		Toast.makeText(getActivity(), "type=" + type, Toast.LENGTH_SHORT).show();
		
	}

}
