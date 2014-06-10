package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Action;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MyListViewAdapter extends BaseAdapter{

	private ArrayList<Action> actionList;
	private Context mContext;
	private LayoutInflater mInflater;
	public MyListViewAdapter(Context context, ArrayList<Action> list){
		this.actionList = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return actionList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Action a = actionList.get(position);
		convertView = mInflater.inflate(R.layout.item_coming_live, null);
		((CircleNetImageView)convertView.findViewById(R.id.ivSellerIcon)).setImageUrl(Util.GetImageUrl(a.HeadStr, Util.dip2px(mContext, 64), Util.dip2px(mContext, 64)), Config.PATH, null);
		((TextView)convertView.findViewById(R.id.tvSellerName)).setText(a.FLoginName);
		((TextView)convertView.findViewById(R.id.tvLiveBeginTime)).setText(a.Time + "");
		((TextView)convertView.findViewById(R.id.tvFansNum)).setText(a.FansNum + "");
		((TextView)convertView.findViewById(R.id.tvLiveTime)).setText(a.WhereToBuy);
		//TODO
		((TextView)convertView.findViewById(R.id.tvLiveDescription)).setText("");
		return convertView;
	}
	
}
