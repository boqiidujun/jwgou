package com.jwgou.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;

public class CityData {

	private static Context context;
	private JSONArray categoryData;
	private static CityData instance = null;
	
	public JSONArray GetData(){
		if(categoryData == null){
			String cg = getSharedPreferences().getString("CategoryData", "");
			if(!cg.equals("")){
				try {
					categoryData = new JSONArray(cg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				categoryData = getTicketCategoryByCache();
			}
		}
		return categoryData;
	}

	public static CityData getinstance(Context mcontext){
		context = mcontext;
		if(instance == null){
			instance = new CityData();
		}
		return instance;
	}
	
//	public void execute(){
//		getCategoryDataByNet();
//	}
	
//	private void getCategoryDataByNet(){
//		
//		new AsyncTask<Void, Void, String>() {
//
//			@Override
//			protected String doInBackground(Void... params) {
//				return NetworkService.getInstance(context).GetSortType();
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				if(!Util.isEmpty(result)){
//					JSONObject o;
//					try {
//						o = new JSONObject(result);
//						categoryData = o.optJSONArray("ResponseData");
//						if(categoryData != null){
//							saveCategoryData();
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			
//		}.execute();
//
//	}

	private JSONArray getTicketCategoryByCache() {
		try {
			InputStream in = context.getAssets().open("citylist");
			if(in != null){
				byte[] b = new byte[1024];
				ByteArrayOutputStream outputs = new ByteArrayOutputStream();
				int len = -1;
				while((len = in.read(b)) != -1){
					outputs.write(b, 0, len);
				}
				return new JSONArray(outputs.toString());
//				return new JSONObject(outputs.toString()).getJSONArray("ResponseData");
			}
		} catch (Exception e) {
		}
		return null;
	}
	
//	private void saveCategoryData(){
//		getSharedPreferences().edit().putString("CategoryData", categoryData.toString()).commit();
//	}

	private SharedPreferences getSharedPreferences(){
		return context.getSharedPreferences("CategoryData",Context.MODE_APPEND);
	}
}
