package com.jwgou.android.entities;

import org.json.JSONObject;

public class RedBag extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
	public String code;
	public double money;
	public String time;
//	红包金额RedMoney
//	红包编码RedCode
//	过期时间DueDate

	public RedBag Json2Self(JSONObject o){
		code = o.optString("RedCode");
		money = o.optDouble("RedMoney");
		time = o.optString("DueDate");
		return this;
	}
	
}
