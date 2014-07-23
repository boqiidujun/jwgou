package com.jwgou.android.entities;

import org.json.JSONObject;

public class JwgouOrder extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String Title;
	public String Pic;
	public String Status;
	public int Num;
    public String AddTime;
    public String BuyerMessage;
    public float NowPrice;
    public int OrderId;
    public int StateInt;
	
	public JwgouOrder Json2Self(JSONObject o){
		Title = o.optString("Title");
		Pic = o.optString("Pic");
		Status = o.optString("Status");
		NowPrice = (float) o.optDouble("NowPrice");
		Num = o.optInt("Num");
		OrderId = o.optInt("OrderId");
		StateInt = o.optInt("StateInt");
		AddTime = o.optString("AddTime");
		BuyerMessage = o.optString("BuyerMessage");
		return this;
	}
}
