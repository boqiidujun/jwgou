package com.jwgou.android.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jwgou.android.JwgouDetail;
import com.jwgou.android.R;
import com.jwgou.android.RedPackage;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.NetImageView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment2 extends Fragment {

	private View view;
	private LinearLayout layout1,layout2,layout3;
	private LayoutInflater mInflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home2, container, false);
		mInflater = LayoutInflater.from(getActivity());
		initView(view);
		return view;
	}

	private void initView(View v) {
		layout1 = (LinearLayout)v.findViewById(R.id.layout1);
		layout2 = (LinearLayout)v.findViewById(R.id.layout2);
		layout3 = (LinearLayout)v.findViewById(R.id.layout3);
		getData();
		getWillData();
		getOverData();
	}
	
	private void getOverData() {
		new HttpManager(getActivity()).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetJwGouProductsOver();
			}

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								initLayout3(data);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	protected void initLayout3(JSONArray data) {
		layout3.removeAllViews();
		for (int i = 0; i < data.length(); i++) {
			Item item = new Item();
			item.Json2Self(data.optJSONObject(i));
			View v = mInflater.inflate(R.layout.item_layout1_right, null);
//			View v = mInflater.inflate(i % 2 == 0 ? R.layout.item_layout1_right : R.layout.item_layout1_left, null);
			((NetImageView)v.findViewById(R.id.image)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 300), Util.dip2px(getActivity(), 200)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text1)).setText(item.NowPrice);
			((TextView)v.findViewById(R.id.text0)).setCompoundDrawables(null, null, null, null);
			((TextView)v.findViewById(R.id.text0)).setText("");
			((TextView)v.findViewById(R.id.text2)).setText(Html.fromHtml("最终价：" + "<font color=\"#e4007f\">"+item.PayPrice+"</font>"));
			((TextView)v.findViewById(R.id.text22)).setVisibility(View.GONE);
			((TextView)v.findViewById(R.id.text3)).setText(Html.fromHtml("下单人数：" + "<font color=\"#e4007f\">"+item.HaveJionNum + "人" +"</font>"));
			((TextView)v.findViewById(R.id.text4)).setVisibility(View.GONE);
			layout3.addView(v);
		}
	}

	class Item{
		String cutPrice;
		String NextNum;
		long HaveTime;
		String Pic;
		int HaveJionNum;
		String MinPrice;
		String YPrice;
		String NextNeedNum;
		int JwgouId;
		String Title;
		String PayPrice;
		String NowPrice;
		public Item Json2Self(JSONObject o){
			Pic = o.optString("Pic");
			HaveTime = o.optLong("HaveTime");
			HaveJionNum = o.optInt("HaveJionNum");
			MinPrice = o.optString("MinPrice");
			NextNum = o.optString("NextNum");
			cutPrice = o.optString("cutPrice");
			YPrice = o.optString("YPrice");
			NextNeedNum = o.optString("NextNeedNum");
			JwgouId = o.optInt("JwgouId");
			Title = o.optString("Title");
			PayPrice = o.optString("PayPrice");
			NowPrice = o.optString("NowPrice");
			return this;
		}
	}

	protected void initLayout1(JSONArray data) {
		layout1.removeAllViews();
		for (int i = 0; i < data.length(); i++) {
			Item item = new Item();
			item.Json2Self(data.optJSONObject(i));
			final View v = mInflater.inflate(R.layout.item_layout1_right, null);
//			View v = mInflater.inflate(i % 2 == 0 ? R.layout.item_layout1_right : R.layout.item_layout1_left, null);
			((NetImageView)v.findViewById(R.id.image)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 300), Util.dip2px(getActivity(), 200)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text1)).setText(item.NowPrice);
			CountDownTimer timer = new CountDownTimer(item.HaveTime, 1000) {

				@Override
				public void onFinish() {
					((TextView)v.findViewById(R.id.text0)).setText("活动已结束");
				}

				@Override
				public void onTick(long millisUntilFinished) {
					((TextView)v.findViewById(R.id.text0)).setText(Util.calculate(millisUntilFinished));
				}
			};
			timer.start();
			((TextView)v.findViewById(R.id.text0)).setText(item.HaveTime + "");
			((TextView)v.findViewById(R.id.text2)).setText(Html.fromHtml("冰点价：" + "<font color=\"#e4007f\">"+item.MinPrice+"</font>"));
			((TextView)v.findViewById(R.id.text22)).setText(Html.fromHtml("原价：" + "<font color=\"#e4007f\">"+item.YPrice+"</font>"));
			((TextView)v.findViewById(R.id.text3)).setText(Html.fromHtml("下单人数：" + "<font color=\"#e4007f\">"+item.HaveJionNum+ "人" +"</font>"));
			((TextView)v.findViewById(R.id.text4)).setText(Html.fromHtml(item.NextNeedNum));
			final int id = item.JwgouId;
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), JwgouDetail.class).putExtra("ID", id));
					
				}
			});
			layout1.addView(v);
		}
	}

	private void getWillData() {
		new HttpManager(getActivity()).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetJwGouProductsWillDoing();
			}

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								initLayout2(data);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	protected void initLayout2(JSONArray data) {
		layout2.removeAllViews();
		for (int i = 0; i < data.length(); i++) {
			Item item = new Item();
			item.Json2Self(data.optJSONObject(i));
			View v = mInflater.inflate(R.layout.item_layout1_right, null);
//			View v = mInflater.inflate(i % 2 == 0 ? R.layout.item_layout1_right : R.layout.item_layout1_left, null);
			((NetImageView)v.findViewById(R.id.image)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 300), Util.dip2px(getActivity(), 200)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text1)).setText("￥？");
			((TextView)v.findViewById(R.id.text0)).setCompoundDrawables(null, null, null, null);
			((TextView)v.findViewById(R.id.text0)).setText("");
			((TextView)v.findViewById(R.id.text22)).setText(Html.fromHtml("初始价：" + "<font color=\"#e4007f\">"+item.YPrice+"</font>"));
			((TextView)v.findViewById(R.id.text2)).setText(Html.fromHtml("降价条件：" + "<font color=\"#e4007f\">"+item.NextNum+"</font>"));
			((TextView)v.findViewById(R.id.text3)).setText(Html.fromHtml("降价金额：" + "<font color=\"#e4007f\">"+item.cutPrice+"</font>")); 
			((TextView)v.findViewById(R.id.text4)).setText(Html.fromHtml("冰点价：" + "<font color=\"#e4007f\">"+item.MinPrice+"</font>"));
			final int id = item.JwgouId;
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), JwgouDetail.class).putExtra("ID", id));
					
				}
			});
			layout2.addView(v);
		}
	
//		layout2.removeAllViews();
//		for (int i = 0; i < data.length() / 2; i++) {
//			Item item = new Item();
//			item.Json2Self(data.optJSONObject(i * 2));
//			Item item2 = new Item();
//			item2.Json2Self(data.optJSONObject(i * 2 + 1));
//			View v = mInflater.inflate(R.layout.item_layout2, null);
//			((NetImageView)v.findViewById(R.id.image1)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 150), Util.dip2px(getActivity(), 150)), Config.PATH, null);
//			((TextView)v.findViewById(R.id.text1)).setText(item.Title);
//			((NetImageView)v.findViewById(R.id.image2)).setImageUrl(Util.GetImageUrl(item2.Pic, Util.dip2px(getActivity(), 150), Util.dip2px(getActivity(), 150)), Config.PATH, null);
//			((TextView)v.findViewById(R.id.text2)).setText(item2.Title);
//			layout2.addView(v);
//		}
	}

	private void getData() {
		new HttpManager(getActivity()).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetJwGouProductsDoing();
			}

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								initLayout1(data);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
