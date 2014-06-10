package com.jwgou.android.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.MyMessageActivity;
import com.jwgou.android.R;
import com.jwgou.android.adapter.MyMessageAdapter;
import com.jwgou.android.adapter.MyMessageAdapter.ItemListener;
import com.jwgou.android.baseactivities.BaseApplication;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.Message;
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

public class MessageFragment extends Fragment implements ItemListener {

	private View view;
	private int index;
	private PullToRefreshListView listview;
	private LinearLayout fullscreen_loading_root;
	private ArrayList<Message> list = new ArrayList<Message>();
	private BaseApplication app;
	private int pageNums = 1;
	private MyMessageAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey("INDEX"))
			index = getArguments().getInt("INDEX");
		app = ((MyMessageActivity)getActivity()).getApp();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.refresh_list, container, false);
		initView();
		return view;
	}

	private void initView() {
		listview = (PullToRefreshListView) view.findViewById(R.id.lvPullToRefresh);
		mAdapter = new MyMessageAdapter(getActivity(), list, this, index);
		listview.setAdapter(mAdapter);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageNums = 1;
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
									Message m = new Message();
									m.Json2Self(data.optJSONObject(i));
									list.add(m);
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
					return NetworkService.getInstance().GetReplyMyLetterListing(app.user.UId, pageNums++);
				return NetworkService.getInstance().GetIReplyOtherLetterListing(app.user.UId, pageNums++);
			}
		});
	}

	@Override
	public void OnClick(Message m, int type) {
		Toast.makeText(getActivity(), "type=" + type, Toast.LENGTH_SHORT).show();
		
	}

}
