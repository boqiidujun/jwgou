package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Order;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.NetImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyOrderAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private BtnListener listener;
	private ArrayList<Order> list = new ArrayList<Order>();
	public MyOrderAdapter(Context context, ArrayList<Order> l, BtnListener lis){
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
			convertView = mInflater.inflate(R.layout.item_buyer_order, null);
			holder = new ViewHolder();
			holder.ivProductPic = (NetImageView)convertView.findViewById(R.id.ivProductPic);
			holder.tvProductDesc = (TextView)convertView.findViewById(R.id.tvProductDesc);
			holder.tvOrderNumber = (TextView)convertView.findViewById(R.id.tvOrderNumber);
			holder.tvOrderTime = (TextView)convertView.findViewById(R.id.tvOrderTime);
			holder.tvCustomerLeaveMsg = (TextView)convertView.findViewById(R.id.tvCustomerLeaveMsg);
			holder.tvProductEarnest = (TextView)convertView.findViewById(R.id.tvProductEarnest);
			holder.tvProductPrice = (TextView)convertView.findViewById(R.id.tvProductPrice);
			holder.tvProductStock = (TextView)convertView.findViewById(R.id.tvProductStock);
			holder.tvOrderStateTip = (TextView)convertView.findViewById(R.id.tvOrderStateTip);
			holder.tvRecvButton = (TextView)convertView.findViewById(R.id.tvRecvButton);
			holder.tvPayButton = (TextView)convertView.findViewById(R.id.tvPayButton);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Order o = list.get(position);
		initView(holder, o);
		return convertView;
	}

	private void initView(ViewHolder holder, final Order o) {
		holder.ivProductPic.setImageUrl(Util.GetImageUrl(o.Pic, Util.dip2px(mContext, 80), Util.dip2px(mContext, 80)), Config.PATH, null);
		holder.tvProductDesc.setText(o.ListName);
		holder.tvOrderNumber.setText(o.OrderID);
		holder.tvOrderTime.setText(o.AddDate);
		holder.tvCustomerLeaveMsg.setText(o.CustomerLeaveMsg);
		holder.tvProductEarnest.setText(o.Deposit + "");
		holder.tvProductPrice.setText(o.FEstimateRef + "");
		holder.tvProductStock.setText(o.Num + "");
		holder.tvOrderStateTip.setText(o.AddDate);
		holder.tvRecvButton.setVisibility(View.GONE);
		holder.tvPayButton.setVisibility(View.GONE);
		if(o.StateInt == 0 || o.StateInt == 14){
			holder.tvPayButton.setVisibility(View.VISIBLE);
		}else if(o.StateInt == 2){
			holder.tvRecvButton.setVisibility(View.VISIBLE);
		}
		holder.tvPayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.Pay(o);
			}
		});
		holder.tvRecvButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.Receive(o);
			}
		});
	}
	
	public interface BtnListener{
		void Pay(Order o);
		void Receive(Order o);
	}

	class ViewHolder{
		NetImageView ivProductPic;
		TextView tvProductDesc;
		TextView tvOrderNumber;
		TextView tvOrderTime;
		TextView tvCustomerLeaveMsg;
		TextView tvProductEarnest;
		TextView tvProductPrice;
		TextView tvProductStock;
		TextView tvOrderStateTip;
		TextView tvRecvButton,tvPayButton;
	}
}
