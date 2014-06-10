package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.entities.RedBag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyRedAdapter extends BaseAdapter {

	private ArrayList<RedBag> list;
	private Context mContext;
	private LayoutInflater mInflater;
	public MyRedAdapter(Context context, ArrayList<RedBag> l){
		this.mContext = context;
		this.list = l;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
