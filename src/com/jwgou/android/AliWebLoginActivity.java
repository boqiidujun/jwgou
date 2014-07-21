package com.jwgou.android;


import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.utils.Util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class AliWebLoginActivity extends BaseActivity implements OnClickListener{

	private WebView webView;
	private Handler mHandler = new Handler();
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aliweb_login);
		webView = (WebView)findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		String url = this.getIntent().getStringExtra("URL");
		if(!TextUtils.isEmpty(url)){
			webView.loadUrl(url);
		}
		webView.addJavascriptInterface(new JavaScriptInterface(), "AliPay");
		webView.setWebViewClient(new MyWebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;

			}
			
		});
	}
	
	final class JavaScriptInterface{
		JavaScriptInterface(){
			
		}
		public void Login(final String json){
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if(!Util.isEmpty(json)){
						Bundle bundle = new Bundle();
						bundle.putString("RESULT", json);
						setResult(RESULT_OK, new Intent().putExtras(bundle));
						AliWebLoginActivity.this.finish();
					}
				}
			});
		}
	}

	public class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			AliWebLoginActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
