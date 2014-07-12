package com.jwgou.android.fragments;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.JwgouOrderActivity;
import com.jwgou.android.R;
import com.jwgou.android.adapter.JwgouOrderAdapter;
import com.jwgou.android.adapter.JwgouOrderAdapter.BtnListener;
import com.jwgou.android.baseactivities.BaseApplication;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.JwgouOrder;
import com.jwgou.android.utils.HttpManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class JwgouOrderFragment extends Fragment implements BtnListener {

	private View view;
	private int index;
	private PullToRefreshListView listview;
	private LinearLayout fullscreen_loading_root;
	private ArrayList<JwgouOrder> list = new ArrayList<JwgouOrder>();
	private BaseApplication app;
	private int pageNums = 1;
	private JwgouOrderAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey("INDEX"))
			index = getArguments().getInt("INDEX");
		app = ((JwgouOrderActivity) getActivity()).getApp();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.refresh_list, container, false);
		initView();
		return view;
	}

	private void initView() {
		listview = (PullToRefreshListView) view.findViewById(R.id.lvPullToRefresh);
		mAdapter = new JwgouOrderAdapter(getActivity(), list, this, index);
		listview.setAdapter(mAdapter);
		listview.setMode(Mode.PULL_FROM_START);
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
				return index == 0? NetworkService.getInstance().GetJwGouOrderList_Pay(app.user.UId) : NetworkService.getInstance().GetJwGouOrderList_Creat(app.user.UId);
			}
		});
	}

	@Override
	public void Pay(JwgouOrder o) {
		
	}

}
