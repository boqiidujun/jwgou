package com.jwgou.android.entities;

import org.json.JSONObject;

public class JwgouProduct extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Pic, YPrice, NowPrice, Rule, FmoContent,MinPrice,Title,timetopname;
	public int HaveJionNum,havetime,Num;
	public int id;
	public JwgouProduct Json2Self(JSONObject o){
		Pic = o.optJSONArray("Pic").toString();
		YPrice = o.optString("YPrice");
		NowPrice = o.optString("NowPrice");
		Rule = o.optString("Rule");
		FmoContent = o.optString("FmoContent");
		MinPrice = o.optString("MinPrice");
		Title = o.optString("Title");
		timetopname = o.optString("timetopname");
		HaveJionNum = o.optInt("HaveJionNum");
		havetime = o.optInt("havetime");
		Num = o.optInt("Num");
		return this;
	}
}
