package com.jwgou.android.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String UName;// 昵称
	public int UId;// id
	public double UserMoney;// 余额
	public int PhoneMessage;//站内信数量
	public String HeadStr;//用户头像
	public int UserState;//1 不需要激活
	
	public User Json2Self(JSONObject o){
		UName = o.optString("UName");
		UId = o.optInt("UId");
		UserMoney = o.optDouble("UserMoney");
		PhoneMessage = o.optInt("PhoneMessage");
		HeadStr = o.optString("HeadStr");
		UserState = o.optInt("UserState");
		return this;
	}

	@Override
	public String toString() {
		JSONObject o = new JSONObject();
		try {
			o.accumulate("UName", UName);
			o.accumulate("UId", UId);
			o.accumulate("UserMoney", UserMoney);
			o.accumulate("PhoneMessage", PhoneMessage);
			o.accumulate("HeadStr", HeadStr);
			o.accumulate("UserState", UserState);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return o.toString();
	}
}
