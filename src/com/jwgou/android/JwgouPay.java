package com.jwgou.android;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.JwgouOrder;
import com.jwgou.android.service.purchase.AlixId;
import com.jwgou.android.service.purchase.BaseHelper;
import com.jwgou.android.service.purchase.MobileSecurePayHelper;
import com.jwgou.android.service.purchase.MobileSecurePayer;
import com.jwgou.android.service.purchase.ResultChecker;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class JwgouPay extends BaseActivity implements OnClickListener {

	private JwgouOrder o;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jwgoupay);
		o = (JwgouOrder) this.getIntent().getSerializableExtra("ORDER");
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
				return NetworkService.getInstance().GetUserMoney(getApp().user.UId);
			}
		});
	}
	
	protected void initData(JSONObject data) {
		((TextView)findViewById(R.id.text2)).setText(data.optString("AccountBalances") + "元");
		DecimalFormat df = new DecimalFormat("#0.00");
		float money = (float) (o.NowPrice - Math.min(o.NowPrice, data.optDouble("AccountBalances")));
		((TextView)findViewById(R.id.text3)).setText(df.format(money) + "");
	}
	private View view;
	private EditText password;
	private TextView sure;

	private void initView() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		((ImageView)findViewById(R.id.pay)).setOnClickListener(this);
		((TextView)findViewById(R.id.text1)).setText(o.NowPrice + "");
		((TextView)findViewById(R.id.text3)).setText(o.NowPrice + "");
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.pay:
			view = LayoutInflater.from(this).inflate(R.layout.dialog_password, null);
			final Dialog dialog = new Dialog(this, R.style.MyDialog);
			dialog.setContentView(view);
			dialog.show();
			Window dialogWindow = dialog.getWindow();
	        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
	        dialogWindow.setGravity(Gravity.CENTER);
	        lp.width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8);
	        dialogWindow.setAttributes(lp);
			
//			final AlertDialog dialog = new AlertDialog.Builder(JwgouPay.this, R.style.MyDialog).setView(view).show();
//			final AlertDialog dialog = new AlertDialog.Builder(JwgouPay.this).setView(view).show();
			password = (EditText)view.findViewById(R.id.password);
			sure = (TextView)view.findViewById(R.id.sure);
			sure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					PayPassword = password.getText().toString();
					Pay();
					dialog.dismiss();
				}
			});
			break;

		default:
			break;
		}
	}
	
	private String PayPassword;

	private void Pay() {
	
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							JSONObject data = o.optJSONObject("ResponseData");
							if(data == null){
								finish();
							}else{
								String info = data.optString("OrderInfo");
								startAlipay(info);	
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
				return NetworkService.getInstance().PostPayOrderFun(o.OrderId, PayPassword);
			}
		});
	}
	public void startAlipay(String info){
		if(Util.isEmpty(info))
			return;
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		if (!isMobile_spExist)
			return;
		try {
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(info, mAliHandler, AlixId.RQF_PAY, this);

			if (bRet) {
				closeProgressForAlipay();
				mAlipayProgress = BaseHelper.showProgress(this, null,"正在支付...", false, true);
			} else
				;
		} catch (Exception ex) {
			ShowToast("远程调用失败");
		}
		
	}
	
	private Handler mAliHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String strRet = (String) msg.obj;

				String memoDisplay = null;

				switch (msg.what) {
				case AlixId.RQF_PAY: {
					closeProgressForAlipay();

					try {
						String memo = "memo={";
						int imemoStart = strRet.indexOf("memo={");
						imemoStart += memo.length();
						int imemoEnd = strRet.indexOf("};result=");
						memo = strRet.substring(imemoStart, imemoEnd);
						memoDisplay = new String(memo);

						ResultChecker resultChecker = new ResultChecker(strRet);

//						if (resultChecker.isPayOk()) { // alipay success
//							ShowToast("支付成功");
//							Intent intent = new Intent();
//							intent.putExtra("ORDER", order);
//							setResult(RESULT_OK, intent);
//							PayOrder.this.finish();
//						}

					} catch (Exception e) {
						e.printStackTrace();
						BaseHelper.showDialog(JwgouPay.this,"提示", memoDisplay, R.drawable.infoicon);
					}
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	private ProgressDialog mAlipayProgress = null;

	void closeProgressForAlipay() {
		try {
			if (mAlipayProgress != null) {
				mAlipayProgress.dismiss();
				mAlipayProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
