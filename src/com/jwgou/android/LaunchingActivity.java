package com.jwgou.android;

import com.jwgou.android.baseactivities.BaseActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LaunchingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launching);
	}
	
/*
	private PackageInfo getPackageInfo() throws Exception {
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		return packInfo;
	}

	private void GetNewestVersion() {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
				try {
					return NetworkService.getInstance(LaunchingActivity.this).GetLastAndroidVersion(getPackageInfo().versionCode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";
			}

			@Override
			protected void onPostExecute(String result) {
				if(!Util.isEmpty(result)){
					try {
						JSONObject obj = new JSONObject(result);
						if (obj.optInt("ResponseStatus", -1) == Constants.RESPONSE_OK) {
							JSONObject data = obj.optJSONObject("ResponseData");
							int VersionStatus = data.optInt("VersionStatus");
							switch (VersionStatus) {
							case 1:
								if(!isFinishing())
									showHardDialog(data.optString("UpdateMsg"), "http://www.boqii.com/resource/mobile/boqii.apk");
								break;
							case 2:
								if(!isFinishing())
									showSoftDialog(data.optString("UpdateMsg"), "http://www.boqii.com/resource/mobile/boqii.apk");
								break;
							case 3:
								startActivity(new Intent(LaunchingActivity.this, MainActivity.class));
								finish();
								break;

							default:
								break;
							}
						}else{
							Toast.makeText(LaunchingActivity.this, obj.optString("ResponseMsg"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
				}
			}
			
		});
	}
	
	*//**
	 * 显示强制升级dialog
	 *//*
	public void showHardDialog(String msg, final String downloadUrl) {
		View v = LayoutInflater.from(this).inflate(R.layout.dialog_hard, null);
		final Dialog dialog = new Dialog(this, R.style.MyDialog);
		dialog.setContentView(v);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.8);
		lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		((TextView)v.findViewById(R.id.message)).setText(msg);
		((TextView)v.findViewById(R.id.update)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				downApk(downloadUrl);
			}
		});
	}
	
	*//**
	 * 显示可选升级dialog
	 */@SuppressLint("HandlerLeak")
	/*
	public void showSoftDialog(String msg, final String downloadUrl) {
		View v = LayoutInflater.from(this).inflate(R.layout.dialog_soft, null);
		final Dialog dialog = new Dialog(this, R.style.MyDialog);
		dialog.setContentView(v);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.8);
		lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		((TextView)v.findViewById(R.id.message)).setText(msg);
		((TextView)v.findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				startActivity(new Intent(LaunchingActivity.this, MainActivity.class));
				finish();
			}
		});
		((TextView)v.findViewById(R.id.sure)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				downApk(downloadUrl);
			}
		});
	}

	protected void downApk(String downloadUrl) {
		if(Util.isEmpty(downloadUrl))
			return;
		String uri = new String(downloadUrl);
		Uri u = Uri.parse(uri);
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(u);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
	}
	*/
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			startActivity(new Intent(LaunchingActivity.this, MainActivity.class));
			finish();
		}
		
	};

	@Override
	protected void onResume() {
		super.onResume();
		mHandler.sendEmptyMessageDelayed(0, 1);
	}
	
}