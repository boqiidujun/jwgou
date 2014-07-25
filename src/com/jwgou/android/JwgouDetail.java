package com.jwgou.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.JwgouProduct;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.AdGallery;
import com.jwgou.android.widgets.NetImageView;

public class JwgouDetail extends BaseActivity implements OnClickListener, Callback {

	private int JwgouId;
	private AdGallery gallery;
	private LinearLayout dotLayout;
	private MyImageAdapter mImageAdapter;
	private PullToRefreshScrollView refreshScrollview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jwgoudetail);
		ShareSDK.initSDK(this);
		JwgouId = this.getIntent().getIntExtra("ID", 0);
		initView();
		GetData();
	}

	private void GetData() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				GetDialog().show();
			}

			@Override
			protected void onPostExecute(String result) {
				refreshScrollview.onRefreshComplete();
				GetDialog().dismiss();
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONObject data = o.optJSONObject("ResponseData");
							if (data != null) {
								initData(data);
							}
						}else
							ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetJwGouProduct(JwgouId);
			}
		});
	}
	
	private ArrayList<String> imageList = new ArrayList<String>();

	private void initDotLayout(int size) {
		dotLayout.removeAllViews();
		for (int i = 0; i < size; i++) {
			ImageView dot = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(11, 11);
			lp.leftMargin = Util.dip2px(this, 5);
			dot.setLayoutParams(lp);
			dot.setImageResource(R.drawable.ic_dot_black);
			dotLayout.addView(dot);
		}
		((ImageView)dotLayout.getChildAt(0)).setImageResource(R.drawable.ic_dot_red);
	}


	private boolean isActive;
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isActive = false;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isActive = true;
	}
	private JwgouProduct p;
	protected void initData(JSONObject data) {
		p = new JwgouProduct();
		p.Json2Self(data);
		p.id = JwgouId;
		if(!Util.isEmpty(p.Pic)){
			JSONArray array;
			try {
				array = new JSONArray(p.Pic);
				if(array != null && array.length() > 0)
					for (int i = 0; i < array.length(); i++) {
						imageList.add(array.optJSONObject(i).optString("FPic"));
					}
				initDotLayout(imageList.size());
				initGallery();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		((TextView)findViewById(R.id.timetopname)).setText(Html.fromHtml(p.timetopname));
		((TextView)findViewById(R.id.title_main)).setText(Html.fromHtml(p.Title));
		((TextView)findViewById(R.id.content)).setText(Html.fromHtml(p.FmoContent));

		CountDownTimer timer = new CountDownTimer(p.havetime, 1000) {

			@Override
			public void onFinish() {
				((TextView)findViewById(R.id.time)).setText("活动尚未开始");
			}

			@Override
			public void onTick(long millisUntilFinished) {
				((TextView)findViewById(R.id.time)).setText(Util.calculate(millisUntilFinished));
			}
		};
		timer.start();
		

		CountDownTimer timer1 = new CountDownTimer(Integer.MAX_VALUE, 60 * 1000) {

			@Override
			public void onFinish() {
			}

			@Override
			public void onTick(long millisUntilFinished) {
				if(isActive)
					Refresh();
			}
		};
		timer1.start();
		((TextView)findViewById(R.id.time)).setText(p.havetime + "");
		((TextView)findViewById(R.id.minprice)).setText(p.MinPrice);
		((TextView)findViewById(R.id.tonight)).setText(p.NowPrice);
		((TextView)findViewById(R.id.num)).setText(p.HaveJionNum + "人");
		((TextView)findViewById(R.id.rule)).setText(p.Rule);
		((TextView)findViewById(R.id.oriprice)).setText(p.YPrice);
	}

	protected void Refresh() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONArray data = o.optJSONArray("ResponseData");
							if (data != null && data.length() > 0) {
								RefreshData(data.optJSONObject(0));
							}
						}else
							ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().GetJwGouProductsJoinNum(p.id);
			}});
	}

	protected void RefreshData(JSONObject data) {
		((TextView)findViewById(R.id.tonight)).setText(data.optString("NowPrice"));
		((TextView)findViewById(R.id.num)).setText(data.optString("HaveJionNum") + "人");
		((TextView)findViewById(R.id.rule)).setText(data.optString("NextNeedNum"));
	}

	private void initGallery() {
		mImageAdapter.notifyDataSetChanged();
	}

	private void initView() {
		gallery = (AdGallery) findViewById(R.id.gallery);
		mImageAdapter = new MyImageAdapter();
		refreshScrollview = (PullToRefreshScrollView)findViewById(R.id.refreshScrollview);
		refreshScrollview.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				GetData();
			}
		});
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.buy)).setOnClickListener(this);
		((TextView)findViewById(R.id.share)).setOnClickListener(this);
		dotLayout = (LinearLayout) findViewById(R.id.dot_images);
		gallery.setAdapter(mImageAdapter);
		gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(imageList.size() > 0)
					setDotImage(position % imageList.size());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	protected void setDotImage(int arg0) {
		for (int i = 0; i < dotLayout.getChildCount(); i++) {
			if (i == arg0 % dotLayout.getChildCount()) {
				((ImageView) (dotLayout.getChildAt(i)))
						.setImageResource(R.drawable.ic_dot_red);

			} else {
				((ImageView) (dotLayout.getChildAt(i)))
						.setImageResource(R.drawable.ic_dot_black);

			}
		}
	}

	public class MyImageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE ;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public String getItemUrl(int position) {
			if(imageList.size() > 0){
				String url = imageList.get(position % imageList.size());
				return Util.GetImageUrl(url, Util.dip2px(JwgouDetail.this, 300), Util.dip2px(JwgouDetail.this, 300));
			}
			return "";
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			NetImageView i = new NetImageView(JwgouDetail.this);
			i.setImageUrl(getItemUrl(position), Config.PATH, null);
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return i;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK && requestCode == 1){
			startActivity(new Intent(this, JwgouCommitOrder.class).putExtra("PRODUCT", p));
		}
	}

	/**
	 * 快捷分享的监听
	 */
	class OneKeyShareCallback implements PlatformActionListener {

		@Override
		public void onCancel(Platform arg0, int arg1) {
			UIHandler.sendEmptyMessage(0, JwgouDetail.this);
		}

		@Override
		public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
			UIHandler.sendEmptyMessage(1, JwgouDetail.this);
		}

		@Override
		public void onError(Platform arg0, int arg1, Throwable arg2) {
			Message msg = new Message();
			msg.what = -1;
			msg.obj = arg2;
			UIHandler.sendMessage(msg, JwgouDetail.this);
		}

	}

	/**
	 * 快捷分享的方回调
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case -1:
			Toast.makeText(JwgouDetail.this, "分享失败", Toast.LENGTH_LONG).show();
			// System.out.println("分享失败，原因：" + msg.obj);
			break;
		case 1:
			Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
			break;
		case 0:
			Toast.makeText(JwgouDetail.this, "取消分享", Toast.LENGTH_SHORT).show();
			break;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.buy:
			if(p == null || p.id == 0)
				return;
			if(getApp().user.UId == 0){
				UserLoginForResult(1);
				return;
			}
			startActivity(new Intent(this, JwgouCommitOrder.class).putExtra("PRODUCT", p));
			break;
		case R.id.share:
			if(p == null || p.id == 0)
				return;
			Util.share(p.Title, Util.GetImageUrl(imageList.get(0), Util.dip2px(JwgouDetail.this, 300), Util.dip2px(JwgouDetail.this, 300)), this, new OneKeyShareCallback());
			break;

		default:
			break;
		}
	}

}
