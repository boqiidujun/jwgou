package com.jwgou.android.entities;

import org.json.JSONObject;

public class Address extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TODO
	public String Name, Telephone;
	public String FullAddress; //全地址
	public int UserDefault; //是否为默认 0:false 1:true
	public int AddressId; //地址ID

	public Address Json2Self(JSONObject o){
		//TODO
		Name = o.optString("FullName");
		Telephone = o.optString("Phone");
		FullAddress = o.optString("FullAddress");
		UserDefault = o.optInt("UserDefault");
		AddressId = o.optInt("AddressId");
		return this;
	}
	
}
