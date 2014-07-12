package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Buyer;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyBuyerAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ItemListener listener;
	private ArrayList<Buyer> list = new ArrayList<Buyer>();
	public MyBuyerAdapter(Context context, ArrayList<Buyer> l, ItemListener lis){
		this.mContext = context;
		this.list = l;
		mInflater = LayoutInflater.from(mContext);
		this.listener = lis;
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
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_my_follower, null);
			holder = new ViewHolder();
			holder.ivFollowerIcon = (CircleNetImageView)convertView.findViewById(R.id.ivFollowerIcon);
			holder.tvFollowerName = (TextView)convertView.findViewById(R.id.tvFollowerName);
			holder.tvFollowerRecentLiveDesc = (TextView)convertView.findViewById(R.id.tvFollowerRecentLiveDesc);
			holder.tvFollowerRecentLiveTime = (TextView)convertView.findViewById(R.id.tvFollowerRecentLiveTime);
			holder.attention = (Button)convertView.findViewById(R.id.attention);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final Buyer m = list.get(position);
		initView(holder, m);
		holder.attention.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.OnClick(m);
			}
		});
		return convertView;
	}

	public interface ItemListener{
		void OnClick(Buyer m);
	}

	class ViewHolder{
		CircleNetImageView ivFollowerIcon;
		TextView tvFollowerName,tvFollowerRecentLiveDesc,tvFollowerRecentLiveTime;
		Button attention;
	}

	private void initView(ViewHolder holder, final Buyer m) {
		holder.ivFollowerIcon.setImageUrl(Util.GetImageUrl(m.Headstr, Util.dip2px(mContext, 60), Util.dip2px(mContext, 60)), Config.PATH, null);
		holder.tvFollowerName.setText(m.FShopName);
		holder.tvFollowerRecentLiveDesc.setText(m.FShopName);
		holder.tvFollowerRecentLiveTime.setText(m.FShopName);
	}
}
