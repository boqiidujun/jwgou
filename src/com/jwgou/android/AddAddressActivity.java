package com.jwgou.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.Address;
import com.jwgou.android.utils.CityData;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class AddAddressActivity extends BaseActivity implements OnClickListener {

	private TextView region_1,region_2,region_3;
	private String FullName, FProvinceName, FCityName, FAreaName, AddrLine, ZIP, Phone;
	private JSONArray array;
	private Builder dialog;
	private int firstID = -1,secondID = -1,thirdID = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addaddress);
		initView();
	}

	private void initView() {
		dialog = new AlertDialog.Builder(this);
		array = CityData.getinstance(this).GetData();
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.save)).setOnClickListener(this);
		(region_1 = (TextView)findViewById(R.id.region_1)).setOnClickListener(this);
		(region_2 = (TextView)findViewById(R.id.region_2)).setOnClickListener(this);
		(region_3 = (TextView)findViewById(R.id.region_3)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.region_1:
			final String[] items = new String[array.length()];
			for (int i = 0; i < items.length; i++) {
				JSONObject o;
				try {
					o = array.getJSONObject(i);
					items[i] = o.getString("RegionName");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			dialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					firstID = which;
					secondID = -1;
					thirdID = -1;
					region_1.setText(items[which]);
					region_2.setText("市");
					region_3.setText("区");
					dialog.dismiss();
				}}).show();
			break;
		case R.id.region_2:
			if(firstID == -1)
				return;
			JSONObject object;
			try {
				object = array.getJSONObject(firstID);
				JSONArray arr = object.getJSONArray("Children");
				final String[] cities = new String[arr.length()];
				for (int i = 0; i < cities.length; i++) {
					JSONObject o;
					try {
						o = arr.getJSONObject(i);
						cities[i] = o.getString("RegionName");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				dialog.setSingleChoiceItems(cities, -1, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						secondID = which;
						thirdID = -1;
						region_2.setText(cities[which]);
						region_3.setText("区");
						dialog.dismiss();
					}}).show();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.region_3:
			if(secondID == -1)
				return;
			JSONObject obj;
			try {
				obj = array.getJSONObject(firstID).getJSONArray("Children").getJSONObject(secondID);
				JSONArray a = obj.getJSONArray("Children");
				final String[] cities = new String[a.length()];
				final int[] cityIDs = new int[a.length()];
				for (int i = 0; i < cities.length; i++) {
					JSONObject o;
					try {
						o = a.getJSONObject(i);
						cities[i] = o.getString("RegionName");
						cityIDs[i] = o.getInt("RegionID");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				dialog.setSingleChoiceItems(cities, -1, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						thirdID = which;
						region_3.setText(cities[which]);
						dialog.dismiss();
					}}).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case R.id.save:
			FullName = ((EditText)findViewById(R.id.customerName)).getText().toString();
			Phone = ((EditText)findViewById(R.id.mobile)).getText().toString();
			AddrLine = ((EditText)findViewById(R.id.address)).getText().toString();
			ZIP = ((EditText)findViewById(R.id.zipcode)).getText().toString();
			FProvinceName = region_1.getText().toString();
			FCityName = region_2.getText().toString();
			FAreaName = region_3.getText().toString();
			if(Util.isEmpty(FullName)){
				ShowToast("请输入名字");
				return;
			}
			if(Util.isEmpty(Phone)){
				ShowToast("请输入手机号");
				return;
			}
			if(Util.isEmpty(AddrLine)){
				ShowToast("请输入详细地址");
				return;
			}
			if(Util.isEmpty(ZIP)){
				ShowToast("请输入邮编");
				return;
			}
			if(thirdID == -1){
				ShowToast("请选择省市区信息");
				return;
			}
			AddAddress();
			break;
		default:
			break;
		}
	}

	private void AddAddress() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus", -1) == Config.SUCCESS) {
							finish();
						}else{
							ShowToast(o.optString("ResponseMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().AddAddress(FullName, FProvinceName, FCityName, FAreaName, AddrLine, Phone, ZIP, getApp().user.UId);
			}});
	}

}
