package com.jwgou.android;

import com.jwgou.android.baseactivities.BaseActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends BaseActivity implements OnClickListener{

	private WebView webView;
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		webView = (WebView)findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		String url = this.getIntent().getStringExtra("URL");
		if(!TextUtils.isEmpty(url)){
			webView.loadUrl(url);
		}
		webView.setWebViewClient(new MyWebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;

			}
			
		});
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
			WebViewActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
