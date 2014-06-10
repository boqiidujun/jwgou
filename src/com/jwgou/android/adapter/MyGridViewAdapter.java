package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Product;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.NetImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter {

	private ArrayList<Product> products;
	private Context mContext;
	private LayoutInflater mInflater;

	public MyGridViewAdapter(Context context, ArrayList<Product> ps) {
		this.products = ps;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return Math.min(4, products.size());
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
		convertView = mInflater.inflate(R.layout.item_living_product_preview, null);
		Product p = products.get(position);
		((NetImageView) convertView.findViewById(R.id.ivProductPreviewPic)).setImageUrl(Util.GetImageUrl(p.PPic, Util.dip2px(mContext, 60), Util.dip2px(mContext, 80)), Config.PATH, null);
		((TextView) convertView.findViewById(R.id.tvProductPreviewPrice)).setText("￥" + p.EstimateRef);
		return convertView;
	}

}
