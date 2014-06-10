package com.jwgou.android.baseactivities;

import com.jwgou.android.entities.User;
import com.jwgou.android.exception.CrashLog;
import com.jwgou.android.utils.Config;

import android.app.Application;

public class BaseApplication extends Application {

	public User user = new User();
	@Override
	public void onCreate() {

		super.onCreate();
		if(Config.DEBUG){
//			JPushInterface.setDebugMode(true);
//			JPushInterface.init(this);
			Thread.setDefaultUncaughtExceptionHandler(new CrashLog(
					this.getApplicationContext()));
		}
	}
	
}
