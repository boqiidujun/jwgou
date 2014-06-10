package com.jwgou.android.entities;

import org.json.JSONObject;

public class Order extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String AddDate;// 下单时间
	public String OrderID;// 订单编号
	public double Deposit;// 定金
	public double FEstimateRef;// 实际到手价
	public int Num;//  订购数量
	public String ListName;// 产品名称
	public String Pic;//  产品图片
	public int State;// 产品状态
	public int OId;//  订单ID
	public int StateInt;// 是否需要按钮操作
	public double RedEnvelopeTotal;//  红包使用金额
	public String CustomerLeaveMsg;//用户备注
	
	public Order Json2Self(JSONObject o){
		AddDate = o.optString("AddDate");
		OrderID = o.optString("OrderID");
		Deposit = o.optDouble("Deposit");
		FEstimateRef = o.optDouble("FEstimateRef");
		Num = o.optInt("Num");
		ListName = o.optString("ListName");
		Pic = o.optString("Pic");
		State = o.optInt("State");
		OId = o.optInt("OId");
		StateInt = o.optInt("StateInt");
		RedEnvelopeTotal = o.optDouble("RedEnvelopeTotal");
		//TODO
		CustomerLeaveMsg = o.optString("CustomerLeaveMsg");
		return this;
	}
}
