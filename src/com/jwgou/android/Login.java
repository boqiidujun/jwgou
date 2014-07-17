package com.jwgou.android;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.User;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class Login extends BaseActivity implements OnClickListener {

	private EditText username, password;

	private SharedPreferences sp;// 存储数据对象
	private Editor editor;// 编辑存储的对象
	public static final String LOGIN_SP = "LoginSP";
	public static final String USER_INFO = "UserInfo";
	private static final int ALIWEB = 1;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);
		setContentView(R.layout.activity_login);
		sp = getSharedPreferences(LOGIN_SP, MODE_PRIVATE);
		editor = sp.edit();// 初始化
		editor.clear();// 进入登录页就清空所有账号信息
		editor.commit();// 提交修改
		new QZone(this).removeAccount();
		new SinaWeibo(this).removeAccount();
		initView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == ALIWEB) {
				pd.show();
				Bundle b = data.getExtras();
				String userid = b.getString("USERID");
				login("alipay", userid, null);
			}
		}
	}
	
	/**
	 * 登陆授权
	 */
	private void authorize(Platform plat) {
		if (plat == null) {
			return;
		}
		plat.removeAccount();
		plat.setPlatformActionListener(new LoginListener());
		plat.showUser(null);
		pd.show();
	}

	/**
	 * 使用第三方信息登录
	 * @param plat渠道
	 * @param pdb用户信息
	 */
	private void login(String plat,String Userid,PlatformDb pdb) {

		if (plat.equals(QZone.NAME)) {
			plat = "qq";
//			fastLogin(Userid, pdb.getToken(), plat);
		} else if (plat.equals(SinaWeibo.NAME)) {
			plat = "sina";
//			fastLogin(Userid, pdb.getToken(), plat);
		} else {
//			fastLogin(Userid, pdb.getToken(), plat);
		}
	}

	/**
	 * 快登
	 * 
	 * @param UserId
	 * @param ChannelType渠道： qq,sina,alipay
	 */
//	private void fastLogin(final String UserId, final String AccessToken, final String ChannelType) {
//		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {
//
//			@Override
//			protected String doInBackground(Void... params) {
//				return NetworkService.getInstance().FastLogin(UserId, AccessToken, ChannelType);
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
//				if (!Util.isEmpty(result)) {
//					try {
//						JSONObject obj = new JSONObject(result);
//						int status = obj.optInt("ResponseStatus", -1);
//						if (status == Constants.RESPONSE_OK) {
//							JSONObject data = obj.optJSONObject("ResponseData");
//							// System.out.println("data==" + data);
//							BaseApplication application = (BaseApplication) getApplication();
//							User user = new User();
//							user = User.JsonToSelf(data, "", "");
//							application.user = user;
//							// //返回数据标识是否登录成功
//							Intent mIntent = new Intent();
//							mIntent.putExtra(LOGIN_RESULT, true);
//							setResult(RESULT_OK, mIntent);
//							String userInfo = user.toString();
//							SaveUserInfo(userInfo);
//							finish();
//						} else {
//							ShowToast(obj.optString("ResponseMsg"));
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						if (pd != null) {
//							pd.dismiss();
//						}
//					}
//				}
//			}
//		});
//	}

	private void SaveUserInfo(String userinfo) {
		Util.saveFile(userinfo, this.getExternalFilesDir(null) + "/userinfo");
		Util.saveFile(userinfo, this.getFilesDir().getAbsolutePath() + "/userinfo");
	}

	/*********************************** 授权的监听和回调 *************************************************/
	LoginCallBack lcb = new LoginCallBack();

	/**
	 * 登陆的监听
	 */
	class LoginListener implements PlatformActionListener {

		@Override
		public void onCancel(Platform platform, int action) {
			if (action == Platform.ACTION_USER_INFOR) {
				UIHandler.sendEmptyMessage(0, lcb);
			}
		}

		// 登陆成功
		@Override
		public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
			if (action == Platform.ACTION_USER_INFOR) {
				Message message = new Message();
				message.what = 1;// 登陆成功后提示消息1
				message.obj = res;
				UIHandler.sendMessage(message, lcb);
				login(platform.getName(),platform.getDb().getUserId(), platform.getDb());// 进入登陆页面
			}
			// System.out.println("res"+res+"|");
			 System.out.println("getToken()="+platform.getDb().getToken()+"|");
			 System.out.println("id="+platform.getDb().getUserId()+"|");
		}

		@Override
		public void onError(Platform arg0, int action, Throwable t) {
			if (action == Platform.ACTION_USER_INFOR) {
				Message msg = new Message();
				msg.what = -1;
				msg.obj = t;
				UIHandler.sendMessage(msg, lcb);
			}
			t.printStackTrace();
		}

	}

	/**
	 * 登陆的回调
	 */
	class LoginCallBack implements Callback {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case -1:
				pd.dismiss();
				ShowToast("验证失败!");
				// System.out.println("share error：" + msg.obj);
				break;
			case 1:
				ShowToast("验证第三方账号成功!");
				break;
			case 2:
				ShowToast("用户信息已存在，正在跳转登录操作");
				break;
			case 0:
				ShowToast("取消验证第三方账登陆");
				pd.dismiss();
				break;
			}
			return false;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	public static final String LOGIN_RESULT = "LoginResult";

	private void initView() {
		pd = new ProgressDialog(this);
		pd.setTitle("提示");
		pd.setMessage("正在登录...");
		pd.setCanceledOnTouchOutside(false);
		((Button) findViewById(R.id.back)).setOnClickListener(this);
		((TextView)findViewById(R.id.tvregister)).setOnClickListener(this);
		((TextView)findViewById(R.id.alipay)).setOnClickListener(this);
		((TextView)findViewById(R.id.sina)).setOnClickListener(this);
		((TextView)findViewById(R.id.qq)).setOnClickListener(this);
		((TextView)findViewById(R.id.tvResetPassword)).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText("登录中心");
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		((Button) findViewById(R.id.login)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.login:
			doLogin();
			break;
		case R.id.tvregister:
			startActivity(new Intent(this, Register.class));
			break;
		case R.id.tvResetPassword:
			break;
		case R.id.alipay:
			startActivityForResult(
					new Intent(this, AliWebLoginActivity.class).putExtra("URL", "http://www.baidu.com"), ALIWEB);
			break;
		case R.id.qq:
			authorize(new QZone(this));
			break;
		case R.id.sina:
			authorize(new SinaWeibo(this));
			break;

		default:
			break;
		}
	}

	private void doLogin() {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("登录中...");
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().UserLogin(username.getText().toString(), password.getText().toString());
			}

			@Override
			protected void onPostExecute(String result) {
				if(dialog != null && dialog.isShowing() && dialog.getWindow().isActive())
					dialog.dismiss();
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							User u = new User();
							u.Json2Self(o.optJSONObject("ResponseData"));
							getApp().user = u;
							setResult(RESULT_OK);
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
