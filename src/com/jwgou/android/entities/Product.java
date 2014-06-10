package com.jwgou.android.entities;

import org.json.JSONObject;

public class Product extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String PPic;
	public double EstimateRef;
	public int PId;
	
	public Product Json2Self(JSONObject o){
		PPic = o.optString("PPic");
		EstimateRef = o.optDouble("EstimateRef");
		PId = o.optInt("PId");
		return this;
	}
}
