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
import android.support.v4.app.Fragment;
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
			View v = mInflater.inflate(i % 2 == 0 ? R.layout.item_layout1_right : R.layout.item_layout1_left, null);
			((NetImageView)v.findViewById(R.id.image)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 320), Util.dip2px(getActivity(), 100)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text1)).setText(item.YPrice);
			((TextView)v.findViewById(R.id.text2)).setText("最终价："+ item.PayPrice);
			((TextView)v.findViewById(R.id.text3)).setText("下单人数"+ item.HaveJionNum + "人");
			((TextView)v.findViewById(R.id.text4)).setVisibility(View.GONE);
			layout3.addView(v);
		}
	}

	class Item{
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
			HaveJionNum = o.optInt("HaveJionNum");
			MinPrice = o.optString("MinPrice");
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
			View v = mInflater.inflate(i % 2 == 0 ? R.layout.item_layout1_right : R.layout.item_layout1_left, null);
			((NetImageView)v.findViewById(R.id.image)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 320), Util.dip2px(getActivity(), 100)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text1)).setText(item.YPrice);
			((TextView)v.findViewById(R.id.text2)).setText("冰点价："+ item.MinPrice);
			((TextView)v.findViewById(R.id.text3)).setText("下单人数"+ item.HaveJionNum + "人");
			((TextView)v.findViewById(R.id.text4)).setText(item.NextNeedNum);
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
		for (int i = 0; i < data.length() / 2; i++) {
			Item item = new Item();
			item.Json2Self(data.optJSONObject(i * 2));
			Item item2 = new Item();
			item2.Json2Self(data.optJSONObject(i * 2 + 1));
			View v = mInflater.inflate(R.layout.item_layout2, null);
			((NetImageView)v.findViewById(R.id.image1)).setImageUrl(Util.GetImageUrl(item.Pic, Util.dip2px(getActivity(), 150), Util.dip2px(getActivity(), 150)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text1)).setText(item.Title);
			((NetImageView)v.findViewById(R.id.image2)).setImageUrl(Util.GetImageUrl(item2.Pic, Util.dip2px(getActivity(), 150), Util.dip2px(getActivity(), 150)), Config.PATH, null);
			((TextView)v.findViewById(R.id.text2)).setText(item2.Title);
			layout2.addView(v);
		}
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
