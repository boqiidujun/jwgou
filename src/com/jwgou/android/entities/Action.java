package com.jwgou.android.entities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Action extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String ActivityPic;
	public String Time;
	public String FLoginName;
	public int AId;
	public String WhereToBuy;
	public int SellerId;
	public String HeadStr;
	public int FansNum;
	public ArrayList<Product> products = new ArrayList<Product>();
	
	public Action Json2Self(JSONObject o){
		ActivityPic = o.optString("ActivityPic");
		Time = o.optString("Time");
		FLoginName = o.optString("FLoginName");
		AId = o.optInt("AId");
		WhereToBuy = o.optString("WhereToBuy");
		SellerId = o.optInt("SellerId");
		HeadStr = o.optString("HeadStr");
		FansNum = o.optInt("FansNum");
		JSONArray array = o.optJSONArray("ProductsDate");
		if(array != null && array.length() > 0){
			for (int i = 0; i < array.length(); i++) {
				Product p = new Product();
				products.add(p.Json2Self(array.optJSONObject(i)));
			}
		}
		return this;
	}
}
