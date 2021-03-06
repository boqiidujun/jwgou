package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MyAddressAdapter extends BaseAdapter {

	private ArrayList<Address> list;
	private Context mContext;
	private LayoutInflater mInflater;
	private ItemListener listener;
	private boolean manager;
	public MyAddressAdapter(Context context, ArrayList<Address> l, ItemListener lis, boolean manager){
		this.mContext = context;
		this.listener = lis;
		this.list = l;
		this.manager = manager;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_address, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.fulladdress = (TextView) convertView.findViewById(R.id.fulladdress);
			holder.telephone = (TextView) convertView.findViewById(R.id.telephone);
			holder.delete = (TextView) convertView.findViewById(R.id.delete);
			holder.select = (TextView) convertView.findViewById(R.id.select);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Address a = list.get(position);
		initView(holder, a, position);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.ClickListener(position, 3);
			}
		});
		return convertView;
	}

	private void initView(ViewHolder holder, Address a, final int index) {
		holder.name.setText(a.Name);
		holder.fulladdress.setText(a.FullAddress);
		holder.telephone.setText(a.Telephone);
		if(manager)
			holder.select.setVisibility(View.GONE);
		holder.select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.ClickListener(index, 3);
			}
		});
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.ClickListener(index, 2);
			}
		});
		holder.checkbox.setChecked(a.UserDefault == 1);
		holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked && listener != null){
					listener.ClickListener(index, 1);
				}
			}
		});
	}

	public interface ItemListener{
		void ClickListener(int index, int type);//1: default 2: delete3:select
	}
	
	class ViewHolder{
		TextView name, fulladdress, telephone, delete, select;
		CheckBox checkbox;
	}

}
