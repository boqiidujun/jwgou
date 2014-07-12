package com.jwgou.android.entities;

import org.json.JSONObject;

public class JwgouProduct extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Pic, YPrice, NowPrice, Rule, FmoContent,MinPrice;
	public int HaveJionNum,havetime,Num;
	public int id;
	public JwgouProduct Json2Self(JSONObject o){
		Pic = o.optJSONArray("Pic").toString();
		YPrice = o.optString("YPrice");
		NowPrice = o.optString("NowPrice");
		Rule = o.optString("Rule");
		FmoContent = o.optString("FmoContent");
		MinPrice = o.optString("MinPrice");
		HaveJionNum = o.optInt("HaveJionNum");
		havetime = o.optInt("havetime");
		Num = o.optInt("Num");
		return this;
	}
}
