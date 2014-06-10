package com.jwgou.android.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jwgou.android.R;
import com.jwgou.android.entities.Action;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;
import com.jwgou.android.widgets.UnScrollGridView;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewPagerAdapter extends PagerAdapter {
	private List<View> mListViews = new ArrayList<View>();
	private ArrayList<Action> actionList;
	private Context mContext;

	public MyViewPagerAdapter(Context context, ArrayList<Action> actionlist) {
		this.actionList = actionlist;
		this.mContext = context;
		for (int i = 0; i < actionlist.size(); i++) {
			this.mListViews.add(LayoutInflater.from(context).inflate(R.layout.item_home_living, null));
		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));// 删除页卡
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		Action a = actionList.get(position);
		container.addView(mListViews.get(position), 0);// 添加页卡
		// ((CircleImageView)mContext.findViewById(R.id.ivSellerIcon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
		((CircleNetImageView) ((Activity) ((Activity) mContext)).findViewById(R.id.ivSellerIcon)).setImageUrl(Util.GetImageUrl(a.HeadStr, Util.dip2px(((Activity) mContext), 64), Util.dip2px(((Activity) mContext), 64)), Config.PATH, null);
		((TextView) ((Activity) mContext).findViewById(R.id.tvSellerName)).setText(a.FLoginName);
		((TextView) ((Activity) mContext).findViewById(R.id.tvLivingLeftTime)).setText(a.Time + "");
		((TextView) ((Activity) mContext).findViewById(R.id.tvFansNum)).setText(a.FansNum + "");
		((TextView) ((Activity) mContext).findViewById(R.id.tvLiveAddress)).setText(a.WhereToBuy);
		((ImageView) ((Activity) mContext).findViewById(R.id.ivPrevLivingShow)).setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
		((ImageView) ((Activity) mContext).findViewById(R.id.ivNextLivingShow)).setVisibility(position == actionList.size() - 1 ? View.INVISIBLE : View.VISIBLE);
		// TODO
		((TextView) ((Activity) mContext).findViewById(R.id.tvLivingDesc)).setText("");
		UnScrollGridView gridview = (UnScrollGridView) ((Activity) mContext).findViewById(R.id.gvLivingProdutPics);
		MyGridViewAdapter adapter = new MyGridViewAdapter(mContext, a.products);
		gridview.setAdapter(adapter);
		return mListViews.get(position);
	}

	@Override
	public int getCount() {
		return mListViews.size();// 返回页卡的数量
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 官方提示这样写
	}
}
