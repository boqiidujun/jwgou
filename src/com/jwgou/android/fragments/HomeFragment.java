package com.jwgou.android.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jwgou.android.R;
import com.jwgou.android.adapter.MyListViewAdapter;
import com.jwgou.android.adapter.MyViewPagerAdapter;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.Action;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;
import com.jwgou.android.utils.Utility;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements Callback {

	private View view;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private RelativeLayout livingShowContainer;
	private ViewPager viewpager;
	private TextView jumpToAllLiving;
	private ImageView livingEmptyTip;
	private LinearLayout comingLivingContainer;
	private TextView jumpToAllCommingLives;
	private ListView listview;
	private LinearLayout fullscreen_loading_root;
	private ArrayList<Action> actionlist = new ArrayList<Action>();
	private ArrayList<Action> willActionlist = new ArrayList<Action>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(view);
		return view;
	}

	private void initView(View v) {
		mPullRefreshScrollView = (PullToRefreshScrollView) v.findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				getData();
			}
		});
		livingShowContainer = (RelativeLayout) v.findViewById(R.id.LivingShowContainer);
		viewpager = (ViewPager) v.findViewById(R.id.viewpager);
		jumpToAllLiving = (TextView) v.findViewById(R.id.tvJumpToAllLiving);
		livingEmptyTip = (ImageView) v.findViewById(R.id.ivLivingsEmptyTip);
		comingLivingContainer = (LinearLayout) v.findViewById(R.id.llComingLivesContainer);
		jumpToAllCommingLives = (TextView) v.findViewById(R.id.tvJumpToAllCommingLives);
		listview = (ListView) v.findViewById(R.id.lvCommingLives);
		fullscreen_loading_root = (LinearLayout) v.findViewById(R.id.fullscreen_loading_root);
		getData();
//		getWillData();
		// ((Button)v.findViewById(R.id.button)).setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Util.share(getActivity(), new OneKeyShareCallback());
		// }
		// });
	}

	private void getWillData() {
		new HttpManager(getActivity()).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetActivityWillDoing();
			}

			@Override
			protected void onPostExecute(String result) {
				mPullRefreshScrollView.onRefreshComplete();
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								willActionlist.clear();
								for (int i = 0; i < data.length(); i++) {
									Action a = new Action();
									a.Json2Self(data.optJSONObject(i));
									willActionlist.add(a);
								}
								MyListViewAdapter mAdapter = new MyListViewAdapter(getActivity(), willActionlist);
								listview.setAdapter(mAdapter);
								new Utility().setListViewHeightBasedOnChildren(getActivity(), listview, mAdapter);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void getData() {
		new HttpManager(getActivity()).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetActivityDoingSpecial();
			}

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								actionlist.clear();
								for (int i = 0; i < data.length(); i++) {
									Action a = new Action();
									a.Json2Self(data.optJSONObject(i));
									actionlist.add(a);
								}
								MyViewPagerAdapter mAdapter = new MyViewPagerAdapter(getActivity(), actionlist);
								viewpager.setAdapter(mAdapter);
								getWillData();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 快捷分享的监听
	 */
	class OneKeyShareCallback implements PlatformActionListener {

		@Override
		public void onCancel(Platform arg0, int arg1) {
			UIHandler.sendEmptyMessage(0, HomeFragment.this);
		}

		@Override
		public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
			UIHandler.sendEmptyMessage(1, HomeFragment.this);
		}

		@Override
		public void onError(Platform arg0, int arg1, Throwable arg2) {
			Message msg = new Message();
			msg.what = -1;
			msg.obj = arg2;
			UIHandler.sendMessage(msg, HomeFragment.this);
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case -1:
			Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_LONG).show();
			break;
		case 1:
			break;
		case 0:
			Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
			break;
		}
		return false;
	}

	
}
