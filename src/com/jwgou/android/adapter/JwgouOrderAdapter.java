package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.JwgouOrder;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.NetImageView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JwgouOrderAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private BtnListener listener;
	private ArrayList<JwgouOrder> list = new ArrayList<JwgouOrder>();
	private int index;
	public JwgouOrderAdapter(Context context, ArrayList<JwgouOrder> l, BtnListener lis, int index){
		this.mContext = context;
		this.list = l;
		this.index = index;
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
			convertView = mInflater.inflate(R.layout.item_jwgou_order, null);
			holder = new ViewHolder();
			holder.image = (NetImageView)convertView.findViewById(R.id.image);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.time = (TextView)convertView.findViewById(R.id.time);
			holder.message = (TextView)convertView.findViewById(R.id.message);
			holder.num = (TextView)convertView.findViewById(R.id.num);
			holder.price = (TextView)convertView.findViewById(R.id.price);
			holder.pay = (TextView)convertView.findViewById(R.id.pay);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		JwgouOrder o = list.get(position);
		initView(holder, o);
		return convertView;
	}

	private void initView(ViewHolder holder, final JwgouOrder o) {
		holder.image.setImageUrl(Util.GetImageUrl(o.Pic, Util.dip2px(mContext, 300), Util.dip2px(mContext, 150)), Config.PATH, null);
		holder.title.setText(o.Title);
		holder.time.setText("生成时间：" + o.AddTime);
		holder.message.setText(o.BuyerMessage);
		if(index == 0){
			holder.pay.setBackgroundColor(Color.RED);
			holder.pay.setText("订单已生成请付款");
			holder.price.setVisibility(View.VISIBLE);
			holder.price.setText(o.NowPrice + "");
			holder.pay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(listener != null)
						listener.Pay(o);
				}
			});
		}else{
			holder.pay.setBackgroundColor(Color.parseColor("#00000000"));
			holder.pay.setText(o.Status);
			holder.price.setVisibility(View.GONE);
		}
		holder.num.setText("" + o.Num);
	}
	
	public interface BtnListener{
		void Pay(JwgouOrder o);
	}

	class ViewHolder{
		NetImageView image;
		TextView title, time, message, pay, num, price;
	}
}
