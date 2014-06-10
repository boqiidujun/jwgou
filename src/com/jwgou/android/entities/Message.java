package com.jwgou.android.entities;

import org.json.JSONObject;

public class Message extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//回复我的评论内容
	public String ToMeReplyContent;
	//回复我的评论时间
	public String ToMeReplyTime;
	//回复我评论的用户名
	public String FromUserName;
	//我的评论内容
	public String MyContent;
	//我评论的时间
	public String MyLetterTime;
	//该评论的Id编号  
	public int Id;
	//用户头像  
	public String HeadStr;
	//产品ID编号
	public int PhoneListingId;
	//活动ID编号
	public int ActivityId;
	//给谁的信息
	public int ToUserId;
	
	//我回复评论的用户名
	public String ToUserName;
	//我回复的评论内容
	public String ToOtherContent;
	//我回复的评论时间
	public String ToOtherLetterTime;
	//留言产品名称
	public String ListingTitle;
	//产品图片  
	public String Pic;
	
	public Message Json2Self(JSONObject o){
		ToMeReplyContent = o.optString("ToMeReplyContent");
		ToMeReplyTime = o.optString("ToMeReplyTime");
		FromUserName = o.optString("FromUserName");
		MyContent = o.optString("MyContent");
		MyLetterTime = o.optString("MyLetterTime");
		Id = o.optInt("Id");
		HeadStr = o.optString("HeadStr");
		PhoneListingId = o.optInt("PhoneListingId");
		ActivityId = o.optInt("ActivityId");
		ToUserId = o.optInt("ToUserId");
		ToUserName = o.optString("ToUserName");
		ToOtherContent = o.optString("ToOtherContent");
		ToOtherLetterTime = o.optString("ToOtherLetterTime");
		ListingTitle = o.optString("ListingTitle");
		Pic = o.optString("Pic");
		return this;
	}
}
