package com.jwgou.android;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

import com.jwgou.android.baseactivities.BaseApplication;
import com.jwgou.android.baseactivities.BaseFragmentActivity;
import com.jwgou.android.fragments.Jwgou3Fragment;
import com.jwgou.android.fragments.HomeFragment;
import com.jwgou.android.fragments.HomeFragment2;
import com.jwgou.android.fragments.Jwgou2Fragment;
import com.jwgou.android.fragments.UserinfoFragment;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.DummyTabContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

public class MainActivity extends BaseFragmentActivity {

	TabHost tabHost;
	TabWidget tabWidget;
	LinearLayout bottom_layout;
	HomeFragment2 homeFragment;
	Jwgou2Fragment liveFragment;
	Jwgou3Fragment buyerFragment;
	UserinfoFragment userinfoFragment;
	android.support.v4.app.FragmentTransaction ft;
	RelativeLayout tabIndicator1, tabIndicator2, tabIndicator3, tabIndicator4;

	int CURRENT_TAB = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Toast.makeText(this, this.getIntent().getScheme() + "----" +
		// this.getIntent().getDataString(), Toast.LENGTH_LONG).show();
		findTabView();
		tabHost.setup();

		ShareSDK.initSDK(this);
		BaseApplication app = (BaseApplication) getApplication();
		try {
			app.user.Json2Self(new JSONObject(Util.loadFile(this.getExternalFilesDir(null) + "/userinfo")));
			if (app.user.UId == 0)
				app.user.Json2Self(new JSONObject(Util.loadFile(this.getFilesDir().getAbsolutePath() + "/userinfo")));
			JPushInterface.setAlias(this, app.user.UId + "", null);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				if (getApp().user.UId == 0) {
					if (tabId.equalsIgnoreCase("live")) {
						setTab(0);
						CURRENT_TAB = 2;
						UserLoginForResult(1);
						return;
					} else if (tabId.equalsIgnoreCase("buyer")) {
						setTab(0);
						CURRENT_TAB = 3;
						UserLoginForResult(1);
						return;
					} else if (tabId.equalsIgnoreCase("userinfo")) {
						setTab(0);
						CURRENT_TAB = 4;
						UserLoginForResult(1);
						return;
					}
				}
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				homeFragment = (HomeFragment2) fm.findFragmentByTag("home");
				liveFragment = (Jwgou2Fragment) fm.findFragmentByTag("live");
				buyerFragment = (Jwgou3Fragment) fm.findFragmentByTag("buyer");
				userinfoFragment = (UserinfoFragment) fm.findFragmentByTag("userinfo");
				ft = fm.beginTransaction();

				if (homeFragment != null)
					ft.hide(homeFragment);

				if (liveFragment != null) {
					ft.hide(liveFragment);
					// ft.remove(liveFragment);
					// liveFragment = null;
				}
				// ft.hide(liveFragment);

				if (buyerFragment != null)
					ft.hide(buyerFragment);

				if (userinfoFragment != null)
					ft.hide(userinfoFragment);

				if (tabId.equalsIgnoreCase("home")) {
					CURRENT_TAB = 1;
					isTabHome();
				} else if (tabId.equalsIgnoreCase("live")) {
					CURRENT_TAB = 2;
					isTabNear();
				} else if (tabId.equalsIgnoreCase("buyer")) {
					CURRENT_TAB = 3;
					isTabMine();
				} else if (tabId.equalsIgnoreCase("userinfo")) {
					CURRENT_TAB = 4;
					isTabMore();
				}
				ft.commit();
			}

		};
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(tabChangeListener);
		initTab();
		int index = getIntent().getIntExtra("INDEX", 0);
		tabHost.setCurrentTab(index);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 1) {
			switch (CURRENT_TAB) {
			case 2:
				setTab(1);
				break;
			case 3:
				setTab(2);
				break;
			case 4:
				setTab(3);
				break;

			default:
				break;
			}
		} else {
			CURRENT_TAB = 1;
			setTab(0);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	public void SetCurrentTab(int index) {
		tabHost.setCurrentTab(index);
	}

	public void isTabHome() {

		if (homeFragment == null) {
			ft.add(R.id.realtabcontent, new HomeFragment2(), "home");
		} else {
			ft.show(homeFragment);
		}
	}

	public void isTabNear() {
		if (liveFragment == null) {
			ft.add(R.id.realtabcontent, new Jwgou2Fragment(), "live");
		} else {
			ft.show(liveFragment);
		}
	}

	public void isTabMine() {

		if (buyerFragment == null) {
			ft.add(R.id.realtabcontent, new Jwgou3Fragment(), "buyer");
		} else {
			ft.show(buyerFragment);
		}
	}

	public void isTabMore() {

		if (userinfoFragment == null) {
			ft.add(R.id.realtabcontent, new UserinfoFragment(), "userinfo");
		} else {
			ft.show(userinfoFragment);
		}
	}

	public void findTabView() {

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		LinearLayout layout = (LinearLayout) tabHost.getChildAt(0);
		TabWidget tw = (TabWidget) layout.getChildAt(1);

		tabIndicator1 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_indicator, tw, false);
		((ImageView) tabIndicator1.getChildAt(0)).setBackgroundResource(R.drawable.ic_tab1);

		tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_indicator, tw, false);
		((ImageView) tabIndicator2.getChildAt(0)).setBackgroundResource(R.drawable.ic_tab2);

		tabIndicator3 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_indicator, tw, false);
		((ImageView) tabIndicator3.getChildAt(0)).setBackgroundResource(R.drawable.ic_tab3);

		tabIndicator4 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_indicator, tw, false);
		((ImageView) tabIndicator4.getChildAt(0)).setBackgroundResource(R.drawable.ic_tab4);

	}

	public void initTab() {

		TabHost.TabSpec tSpecHome = tabHost.newTabSpec("home");
		tSpecHome.setIndicator(tabIndicator1);
		tSpecHome.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecHome);

		TabHost.TabSpec tSpecNear = tabHost.newTabSpec("live");
		tSpecNear.setIndicator(tabIndicator2);
		tSpecNear.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecNear);

		TabHost.TabSpec tSpecMine = tabHost.newTabSpec("buyer");
		tSpecMine.setIndicator(tabIndicator3);
		tSpecMine.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecMine);

		TabHost.TabSpec tSpecMore = tabHost.newTabSpec("userinfo");
		tSpecMore.setIndicator(tabIndicator4);
		tSpecMore.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecMore);

	}

	public void setTab(int index) {
		tabHost.setCurrentTab(index);
	}

}
