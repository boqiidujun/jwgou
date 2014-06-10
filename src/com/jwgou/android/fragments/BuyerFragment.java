package com.jwgou.android.fragments;

import com.jwgou.android.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BuyerFragment extends Fragment {

	private View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_buyer, container, false);
		initView(view);
		return view;
	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		
	}
}
