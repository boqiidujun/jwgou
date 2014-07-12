package com.jwgou.android.entities;

import org.json.JSONObject;

public class Buyer extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//店铺名
	public String FShopName;
	//头图
	public String Headstr;
	//粉丝数量
	public int FanNum;
	//产品数量
	public int PhoneListNum;
	//活动数量
	public int ActivityNum;
	
	public Buyer Json2Self(JSONObject o){
		this.FShopName = o.optString("FShopName");
		this.Headstr = o.optString("Headstr");
		this.FanNum = o.optInt("FanNum");
		this.PhoneListNum = o.optInt("PhoneListNum");
		this.ActivityNum = o.optInt("ActivityNum");
		return this;
	}

}
