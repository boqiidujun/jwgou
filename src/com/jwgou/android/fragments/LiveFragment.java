package com.jwgou.android.fragments;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jwgou.android.MainActivity;
import com.jwgou.android.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LiveFragment extends Fragment implements OnClickListener {

	private View view;
	private PullToRefreshListView listview;
	private RelativeLayout rlMyLiveEmptyTip;
	private LinearLayout fullscreen_loading_root;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_live, container, false);
		initView(view);
		return view;
	}

	private void initView(View v) {
		((Button)v.findViewById(R.id.back)).setOnClickListener(this);
		((TextView)v.findViewById(R.id.title)).setText("直播现场");
		listview = (PullToRefreshListView)v.findViewById(R.id.lvALlLivingProducts);
		rlMyLiveEmptyTip = (RelativeLayout)v.findViewById(R.id.rlMyLiveEmptyTip);
		fullscreen_loading_root = (LinearLayout)v.findViewById(R.id.fullscreen_loading_root);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			((MainActivity)getActivity()).setTab(0);
			break;

		default:
			break;
		}
	}
}
