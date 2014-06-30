package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.RedBag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LiveAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<RedBag> list = new ArrayList<RedBag>();
	public LiveAdapter(Context context, ArrayList<RedBag> l){
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
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.list_redbag_item, null);
			holder = new ViewHolder();
			holder.price = (TextView)convertView.findViewById(R.id.price);
			holder.code = (TextView)convertView.findViewById(R.id.code);
			holder.time = (TextView)convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final RedBag r = list.get(position);
		initView(holder, r);
		return convertView;
	}

	public interface ItemListener{
		void OnClick(RedBag m, int type);
	}

	class ViewHolder{
		TextView price, code, time;
	}

	private void initView(ViewHolder holder, final RedBag m) {
		holder.price.setText(m.money + "元");
		holder.code.setText(m.code);
		holder.time.setText(m.time);
	}
}
