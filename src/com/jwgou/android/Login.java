package com.jwgou.android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.entities.User;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class Login extends BaseActivity implements OnClickListener {

	private EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		((Button) findViewById(R.id.back)).setOnClickListener(this);
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
