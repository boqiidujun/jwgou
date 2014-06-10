package com.jwgou.android.entities;

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
	
	public User Json2Self(JSONObject o){
		UName = o.optString("UName");
		UId = o.optInt("UId");
		UserMoney = o.optDouble("UserMoney");
		PhoneMessage = o.optInt("PhoneMessage");
		HeadStr = o.optString("HeadStr");
		return this;
	}
}
