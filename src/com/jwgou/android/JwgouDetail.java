package com.jwgou.android;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.JwgouProduct;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.AdGallery;
import com.jwgou.android.widgets.NetImageView;

public class JwgouDetail extends BaseActivity implements OnClickListener {

	private int JwgouId;
	private AdGallery gallery;
	private LinearLayout dotLayout;
	private MyImageAdapter mImageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jwgoudetail);
		JwgouId = this.getIntent().getIntExtra("ID", 0);
		initView();
		GetData();
	}

	private void GetData() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONObject data = o.optJSONObject("ResponseData");
							if (data != null) {
								initData(data);
							}
						}
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
		((TextView)findViewById(R.id.title_main)).setText(p.FmoContent);
		((TextView)findViewById(R.id.time)).setText(p.havetime + "");
		((TextView)findViewById(R.id.tonight)).setText(p.NowPrice);
		((TextView)findViewById(R.id.num)).setText(p.Num + "人");
		((TextView)findViewById(R.id.minprice)).setText(p.MinPrice);
		((TextView)findViewById(R.id.rule)).setText(p.Rule);
		((TextView)findViewById(R.id.oriprice)).setText(p.YPrice);
	}

	private void initGallery() {
		mImageAdapter.notifyDataSetChanged();
	}

	private void initView() {
		gallery = (AdGallery) findViewById(R.id.gallery);
		mImageAdapter = new MyImageAdapter();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.buy:
			if(getApp().user.UId == 0){
				UserLoginForResult(1);
				return;
			}
			startActivity(new Intent(this, JwgouCommitOrder.class).putExtra("PRODUCT", p));
			break;
		case R.id.share:
			break;

		default:
			break;
		}
	}

}
