package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Action;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;
import com.jwgou.android.widgets.UnScrollGridView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActionAdapter extends BaseAdapter {
	
	private ArrayList<Action> actionList;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public MyActionAdapter(Context context, ArrayList<Action> actionlist) {
		this.actionList = actionlist;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return actionList.size();
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
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home_living, null);
			holder.gridview = (UnScrollGridView) convertView.findViewById(R.id.gvLivingProdutPics);
			holder.ivNextLivingShow = (ImageView) convertView.findViewById(R.id.ivNextLivingShow);
			holder.ivPrevLivingShow = (ImageView) convertView.findViewById(R.id.ivPrevLivingShow);
			holder.ivSellerIcon = (CircleNetImageView) convertView.findViewById(R.id.ivSellerIcon);
			holder.tvFansNum = (TextView) convertView.findViewById(R.id.tvFansNum);
			holder.tvLiveAddress = (TextView) convertView.findViewById(R.id.tvLiveAddress);
			holder.tvLivingDesc = (TextView) convertView.findViewById(R.id.tvLivingDesc);
			holder.tvLivingLeftTime = (TextView) convertView.findViewById(R.id.tvLivingLeftTime);
			holder.tvSellerName = (TextView) convertView.findViewById(R.id.tvSellerName);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Action a = actionList.get(position);
		initView(holder, a);
		return convertView;
	}

	private void initView(ViewHolder holder, Action a) {
		holder.ivSellerIcon.setImageUrl(Util.GetImageUrl(a.HeadStr, Util.dip2px(((Activity) mContext), 64), Util.dip2px(((Activity) mContext), 64)), Config.PATH, null);
		holder.tvSellerName.setText(a.FLoginName);
		holder.tvLivingLeftTime.setText(a.Time + "");
		holder.tvFansNum.setText(a.FansNum + "");
		holder.tvLiveAddress.setText(a.WhereToBuy);
		holder.ivPrevLivingShow.setVisibility(View.INVISIBLE);
		holder.ivNextLivingShow.setVisibility(View.INVISIBLE);
		// TODO
		holder.tvLivingDesc.setText("");
		MyGridViewAdapter adapter = new MyGridViewAdapter(mContext, a.products);
		holder.gridview.setAdapter(adapter);
		
	}

	class ViewHolder{
		CircleNetImageView ivSellerIcon;
		TextView tvSellerName,tvLivingLeftTime,tvFansNum,tvLiveAddress,tvLivingDesc;
		UnScrollGridView gridview;
		ImageView ivPrevLivingShow,ivNextLivingShow;
	}

}
