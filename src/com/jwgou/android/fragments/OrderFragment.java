package com.jwgou.android.fragments;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.MyOrderActivity;
import com.jwgou.android.R;
import com.jwgou.android.adapter.MyOrderAdapter;
import com.jwgou.android.adapter.MyOrderAdapter.BtnListener;
import com.jwgou.android.baseactivities.BaseApplication;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.Order;
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

public class OrderFragment extends Fragment implements BtnListener {

	private View view;
	private int index;
	private PullToRefreshListView listview;
	private LinearLayout fullscreen_loading_root;
	private ArrayList<Order> list = new ArrayList<Order>();
	private BaseApplication app;
	private int pageNums = 1;
	private MyOrderAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey("INDEX"))
			index = getArguments().getInt("INDEX");
		app = ((MyOrderActivity)getActivity()).getApp();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.refresh_list, container, false);
		initView();
		return view;
	}

	private void initView() {
		listview = (PullToRefreshListView) view.findViewById(R.id.lvPullToRefresh);
		mAdapter = new MyOrderAdapter(getActivity(), list, this);
		listview.setAdapter(mAdapter);
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
				if(list.size() == 0)
					fullscreen_loading_root.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(Void... params) {
				int OrderState = 0;
				switch (index) {
				case 1:
					OrderState = 0;
					break;
				case 2:
					OrderState = 2;
					break;
				case 3:
					OrderState = -1;
					break;

				default:
					break;
				}
				return NetworkService.getInstance().GetOrderList(app.user.UId, OrderState, pageNums++);
			}
		});
	}

	@Override
	public void Pay(Order o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Receive(Order o) {
		// TODO Auto-generated method stub
		
	}

}
