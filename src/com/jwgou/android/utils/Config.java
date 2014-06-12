package com.jwgou.android.utils;

import android.os.Environment;


public class Config {

	public static final boolean DEBUG = true;
	public static boolean IsCanDownloadPicWhenNoWifi = true;
	public static final int SUCCESS = 0;
	public static String PATH = Environment.getExternalStorageDirectory().getPath() + "/jwgou/pics";//图片存放路径
//	public static String PATH = "sdcard" + "/jwgou/pics";//图片存放路径
}
