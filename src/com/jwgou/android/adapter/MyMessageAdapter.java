package com.jwgou.android.adapter;

import java.util.ArrayList;

import com.jwgou.android.R;
import com.jwgou.android.entities.Message;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;
import com.jwgou.android.widgets.NetImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyMessageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ItemListener listener;
	private int type;//1 2
	private ArrayList<Message> list = new ArrayList<Message>();
	public MyMessageAdapter(Context context, ArrayList<Message> l, ItemListener lis, int type){
		this.type = type;
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
			convertView = (type == 1) ? mInflater.inflate(R.layout.list_message_item, null) : mInflater.inflate(R.layout.list_message_item_mine, null);
			holder = new ViewHolder();
			holder.context_txt = (TextView)convertView.findViewById(R.id.content_txt);
			holder.date_txt = (TextView)convertView.findViewById(R.id.date_txt);
			holder.reply_content_txt = (TextView)convertView.findViewById(R.id.reply_content_txt);
			holder.reply_date_txt = (TextView)convertView.findViewById(R.id.reply_date_txt);
			holder.product_image = (NetImageView)convertView.findViewById(R.id.product_image);
			holder.product_title_txt = (TextView)convertView.findViewById(R.id.product_title_txt);
			holder.product_price_txt = (TextView)convertView.findViewById(R.id.product_price_txt);
			holder.product_stock_txt = (TextView)convertView.findViewById(R.id.product_stock_txt);
			holder.product_time_txt = (TextView)convertView.findViewById(R.id.product_time_txt);
			holder.header_image = (CircleNetImageView)convertView.findViewById(R.id.header_image);
			holder.name_txt = (TextView)convertView.findViewById(R.id.name_txt);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final Message m = list.get(position);
		initView(holder, m);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null)
					listener.OnClick(m, type);
			}
		});
		return convertView;
	}

	public interface ItemListener{
		void OnClick(Message m, int type);
	}

	class ViewHolder{
		TextView context_txt, date_txt,reply_content_txt,reply_date_txt;
		NetImageView product_image;
		TextView product_title_txt, product_price_txt, product_stock_txt,product_time_txt;
		CircleNetImageView header_image;
		TextView name_txt;
	}

	private void initView(ViewHolder holder, final Message m) {
		if(type == 1){//回复我的
			holder.context_txt.setText(m.ToMeReplyContent);
			holder.date_txt.setText(m.ToMeReplyTime);
			holder.reply_content_txt.setText(m.MyContent);
			holder.reply_date_txt.setText(m.MyLetterTime);
			holder.product_image.setImageUrl(Util.GetImageUrl(m.Pic, Util.dip2px(mContext, 64), Util.dip2px(mContext, 64)), Config.PATH, null);
			holder.product_title_txt.setText(m.ListingTitle);
			//TODO
			//holder.product_price_txt.setText(m.);
			//holder.product_stock_txt.setText(m.);
			//holder.product_time_txt.setText(m.);
			holder.header_image.setImageUrl(Util.GetImageUrl(m.HeadStr, Util.dip2px(mContext, 60), Util.dip2px(mContext, 60)), Config.PATH, null);
			holder.name_txt.setText(m.ToUserName);
			
		}else{//我回复的
			holder.context_txt.setText(m.ToOtherContent);
			holder.date_txt.setText(m.ToOtherLetterTime);
			holder.reply_content_txt.setText(m.ToMeReplyContent);
			holder.reply_date_txt.setText(m.ToMeReplyTime);
			holder.product_image.setImageUrl(Util.GetImageUrl(m.Pic, Util.dip2px(mContext, 64), Util.dip2px(mContext, 64)), Config.PATH, null);
			holder.product_title_txt.setText(m.ListingTitle);
			//TODO
			//holder.product_price_txt.setText(m.);
			//holder.product_stock_txt.setText(m.);
			//holder.product_time_txt.setText(m.);
			holder.header_image.setImageUrl(Util.GetImageUrl(m.HeadStr, Util.dip2px(mContext, 60), Util.dip2px(mContext, 60)), Config.PATH, null);
			holder.name_txt.setText(m.FromUserName);
			
		}
	}
}
