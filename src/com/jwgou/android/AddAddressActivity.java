package com.jwgou.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class AddAddressActivity extends BaseActivity implements OnClickListener {

	private TextView region_1,region_2,region_3;
	private String FullName, FProvinceName, FCityName, FAreaName, AddrLine, ZIP, Phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addaddress);
		initView();
	}

	private void initView() {
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
			break;
		case R.id.region_2:
			break;
		case R.id.region_3:
			break;
		case R.id.save:
			FullName = ((EditText)findViewById(R.id.customerName)).getText().toString();
			Phone = ((EditText)findViewById(R.id.mobile)).getText().toString();
			AddrLine = ((EditText)findViewById(R.id.address)).getText().toString();
			ZIP = ((EditText)findViewById(R.id.zipcode)).getText().toString();
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
