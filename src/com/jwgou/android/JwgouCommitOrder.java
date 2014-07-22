package com.jwgou.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.jwgou.android.entities.JwgouProduct;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.NetImageView;

public class JwgouCommitOrder extends BaseActivity implements OnClickListener {

	private JwgouProduct p;
	private Address a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jwgoucommitorder);
		p = (JwgouProduct) this.getIntent().getSerializableExtra("PRODUCT");
		initView();
	}

	private void initView() {
		((TextView)findViewById(R.id.address)).setOnClickListener(this);
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((Button)findViewById(R.id.commit)).setOnClickListener(this);
		((TextView)findViewById(R.id.title_main)).setText(p.Title);
		if(!Util.isEmpty(p.Pic)){
			JSONArray a;
			try {
				a = new JSONArray(p.Pic);
				String url = a.optJSONObject(0).optString("FPic");
				((NetImageView)findViewById(R.id.image1)).setImageUrl(Util.GetImageUrl(url, Util.dip2px(this, 300), Util.dip2px(this, 150)), Config.PATH, null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		((TextView)findViewById(R.id.num)).setText(p.Num +"");
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.commit:
			Commit();
			break;
		case R.id.address:
			startActivityForResult(new Intent(this, AddressManagerActivity.class), 1);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK && requestCode == 1){
			a = (Address) data.getSerializableExtra("ADDRESS");
			((TextView)findViewById(R.id.address)).setText(a.FullAddress);
		}
	}

	private void Commit() {
		if(Util.isEmpty(((EditText)findViewById(R.id.edit)).getText().toString())){
			ShowToast("请选择数量");
			return;
		}
//		if(Util.isEmpty(((EditText)findViewById(R.id.edit_content)).getText().toString())){
//			ShowToast("请");
//			return;
//		}
		if(a == null || a.AddressId == 0){
			ShowToast("请选择地址");
			return;
		}
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							//TODO
							startActivity(new Intent(JwgouCommitOrder.this, MainActivity.class).putExtra("INDEX", 1).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
				return NetworkService.getInstance().PostJwGouJoin(getApp().user.UId, Integer.parseInt(((EditText)findViewById(R.id.edit)).getText().toString()), p.id, a.AddressId, ((EditText)findViewById(R.id.edit_content)).getText().toString());
			}
		});
		
	}

}
