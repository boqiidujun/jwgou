package com.jwgou.android.baseservice;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;

import com.jwgou.android.baseservice.ClientHelper.TimeOutListener;
import com.jwgou.android.utils.Config;

public class NetworkService {
	public static final String SERVER = "http://211.144.76.91:180/";
	public static final String SERVER_URL = SERVER + "PhoneDate.asmx/";
	private static NetworkService instance;
	private static ClientHelper clientHelper;
	private static timeoutListener listener;

	// private static Context context;

	public static NetworkService getInstance(/* Context c */) {
		// context = c;
		instance = new NetworkService();
		clientHelper = ClientHelper.getInstance();
		clientHelper.setTimeoutListener(new TimeOutListener() {

			@Override
			public void timeoutListener() {
				listener.TimeOutListener();
			}
		});
		return instance;
	}

	public String getUrl(String method) {
		return SERVER_URL + method;
	}

	public String GetJwGouProductsDoing() {
		String result = "";
		String url = getUrl("GetJwGouProductsDoing");
		RequestParameters params = new RequestParameters();
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	public String GetJwGouProductsWillDoing() {
		String result = "";
		String url = getUrl("GetJwGouProductsWillDoing");
		RequestParameters params = new RequestParameters();
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	public String GetJwGouProductsOver() {
		String result = "";
		String url = getUrl("GetJwGouProductsOver");
		RequestParameters params = new RequestParameters();
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	public String GetJwGouOrderList_Pay(int UserId) {
		String result = "";
		String url = getUrl("GetJwGouOrderList_Pay") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	public String GetJwGouOrderList_Creat(int UserId) {
		String result = "";
		String url = getUrl("GetJwGouOrderList_Creat") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	private File convertBitmapToFile(File file, Bitmap bitmap) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 0, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return file;
	}

	public String uploadUserImg(Bitmap bitmap) {
//		String actionUrl = SERVER_URL + "UpLoadImg";
		String actionUrl = "http://localhost:27239/PhoneDate.asmx/UpLoadImg";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//			Bitmap tempBitmap = Helper.setBitmapSize(bitmap, 192, 192);
			File fold = new File(Config.PATH);
			if (!fold.exists()) {
				fold.mkdir();
			}
			File file = new File(Config.PATH + "/temp.png");
			file = convertBitmapToFile(file, bitmap);
			FileInputStream fStream = new FileInputStream(file);
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = fStream.read(buffer)) != -1) {
				ds.write(buffer, 0, length);
			}
			fStream.close();
			ds.flush();
			InputStream is = con.getInputStream();

			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			ds.close();
			return b.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	public String RegisterJCUserName(String UserName) {
		String result = "";
		String url = getUrl("RegisterJCUserName") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserName", UserName);
		result = clientHelper.execute(url, params, ClientHelper.POST);
		return result;
	}

	public String PostPayOrderFun(int OrderId, String PayPassword) {
		String result = "";
		String url = getUrl("PostPayOrderFun") + "?";
		RequestParameters params = new RequestParameters();
		params.add("OrderId", OrderId);
		params.add("PayPassword", PayPassword);
		result = clientHelper.execute(url, params, ClientHelper.POST);
		return result;
	}

	public String PostJwGouJoin(int UserId, int Num, int JwGouListId, int AddressId, String BuyerMessage) {
		String result = "";
		String url = getUrl("PostJwGouJoin") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		params.add("Num", Num);
		params.add("JwGouListId", JwGouListId);
		params.add("AddressId", AddressId);
		params.add("BuyerMessage", BuyerMessage);
		result = clientHelper.execute(url, params, ClientHelper.POST);
		return result;
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码 (不用加密)
	 * @return
	 */
	public String UserLogin(String username, String password) {
		String result = "";
		String url = getUrl("UserLogin") + "?";
		RequestParameters params = new RequestParameters();
		params.add("username", username);
		params.add("password", password);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	public String GetJwGouProduct(int JwgouId) {
		String result = "";
		String url = getUrl("GetJwGouProduct") + "?";
		RequestParameters params = new RequestParameters();
		params.add("JwgouId", JwgouId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 得到可用余额和可用积分
	 * 
	 * @param UserId
	 * @return
	 */
	public String GetUserMoney(int UserId) {
		String result = "";
		String url = getUrl("GetUserMoney") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 得到用户信息
	 * 
	 * @param UserId
	 * @return
	 */
	public String GetUserInformation(int UserId) {
		String result = "";
		String url = getUrl("GetUserInformation") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 粉丝关注
	 * 
	 * @param UserId
	 * @return
	 */
	public String DoFance(int UserId, int BuyerId) {
		String result = "";
		String url = getUrl("DoFance") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		params.add("BuyerId", BuyerId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 关注的买手
	 * 
	 * @param UserId
	 * @return
	 */
	public String FocusBuyer(int UserId, int BuyerId) {
		String result = "";
		String url = getUrl("FocusBuyer") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		params.add("BuyerId", BuyerId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 用户注册
	 * 
	 * @param Email
	 * @return
	 */
	public String RegisterStart(String Email) {
		String result = "";
		String url = getUrl("RegisterStart") + "?";
		RequestParameters params = new RequestParameters();
		params.add("Phone", Email);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}
	
	/**
	 * 
	Phone手机号码
	password登陆密码
	paypassword支付密码
	Filename头图存放地址
	LoginName用户名
	 * @param Phone
	 * @param password
	 * @param paypassword
	 * @param Filename
	 * @param LoginName
	 * @return
	 */
	public String RegisterEnd(String Phone, String password, String paypassword, String Filename, String LoginName) {
		String result = "";
		String url = getUrl("RegisterEnd") + "?";
		RequestParameters params = new RequestParameters();
		params.add("Phone", Phone);
		params.add("password", password);
		params.add("paypassword", paypassword);
		params.add("Filename", Filename);
		params.add("LoginName", LoginName);
		result = clientHelper.execute(url, params, ClientHelper.POST);
		return result;
	}

	/**
	 * 下单
	 * 
	 * @param PhoneListingId
	 *            扫货产品id
	 * @param PhoneLstingNum
	 *            订货数量
	 * @param RedEnvelopeId
	 *            红包编码
	 * @param AddressId
	 *            地址id
	 * @param UserId
	 *            用户id
	 * @param BuyerMessage
	 *            备注
	 * @return
	 */
	public String PlaceOrder(int PhoneListingId, int PhoneLstingNum, String RedEnvelopeId, int AddressId, int UserId, String BuyerMessage) {
		String result = "";
		String url = getUrl("PlaceOrder") + "?";
		RequestParameters params = new RequestParameters();
		params.add("PhoneListingId", PhoneListingId);
		params.add("PhoneLstingNum", PhoneLstingNum);
		params.add("RedEnvelopeId", RedEnvelopeId);
		params.add("AddressId", AddressId);
		params.add("UserId", UserId);
		params.add("BuyerMessage", BuyerMessage);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 支付订单定金
	 * 
	 * @param OrderId
	 *            订单id
	 * @param UserId
	 *            用户id
	 * @param PayPassword
	 *            支付密码（明文）
	 * @return
	 */
	public String PayOrder(int OrderId, int UserId, String PayPassword) {
		String result = "";
		String url = getUrl("PayOrder") + "?";
		RequestParameters params = new RequestParameters();
		params.add("OrderId", OrderId);
		params.add("UserId", UserId);
		params.add("PayPassword", PayPassword);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 支付订单尾款
	 * 
	 * @param OrderId
	 * @param PayPassword
	 * @return
	 */
	public String PayOrderFPayment(int OrderId, String PayPassword) {
		String result = "";
		String url = getUrl("PayOrderFPayment") + "?";
		RequestParameters params = new RequestParameters();
		params.add("OrderId", OrderId);
		params.add("PayPassword", PayPassword);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 确认收货
	 * 
	 * @param OrderId
	 * @param PayPassword
	 * @return
	 */
	public String GetGoods(int OrderId, String PayPassword) {
		String result = "";
		String url = getUrl("GetGoods") + "?";
		RequestParameters params = new RequestParameters();
		params.add("OrderId", OrderId);
		params.add("PayPassword", PayPassword);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 订单列表
	 * 
	 * @param UserId
	 * @param OrderState
	 *            0：代付款 2：待发货 -1：全部
	 * @param PageNums
	 * @return
	 */
	public String GetOrderList(int UserId, int OrderState, int PageNums) {
		String result = "";
		String url = getUrl("GetOrderList") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		params.add("OrderState", OrderState);
		params.add("PageNums", PageNums);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 结算界面收货地址
	 * 
	 * @param UserId
	 * @return
	 */
	public String GetUserAddress(int UserId) {
		String result = "";
		String url = getUrl("GetUserAddress") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 收货地址列表
	 * 
	 * @param UserId
	 * @param PageNums
	 * @return
	 */
	public String GetAddressListing(int UserId, int PageNums) {
		String result = "";
		String url = getUrl("GetAddressListing") + "?";
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		params.add("PageNums", PageNums);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 添加收货地址
	 * 
	 * @param FullName
	 * @param FProvinceName
	 * @param FCityName
	 * @param FAreaName
	 * @param AddrLine
	 * @param Phone
	 * @param UserId
	 * @return
	 */
	public String AddAddress(String FullName, String FProvinceName, String FCityName, String FAreaName, String AddrLine, String Phone, int UserId) {
		String result = "";
		String url = getUrl("AddAddress") + "?";
		RequestParameters params = new RequestParameters();
		params.add("FullName", FullName);
		params.add("FProvinceName", FProvinceName);
		params.add("FCityName", FCityName);
		params.add("FAreaName", FAreaName);
		params.add("AddrLine", AddrLine);
		params.add("Phone", Phone);
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 删除收货地址
	 * 
	 * @param AddressId
	 * @param UserId
	 * @return
	 */
	public String DelAddress(int AddressId, int UserId) {
		String result = "";
		String url = getUrl("DelAddress") + "?";
		RequestParameters params = new RequestParameters();
		params.add("AddressId", AddressId);
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 回复我的评论
	 * 
	 * @param ToUserId
	 *            当前登录用户id
	 * @param PageNums
	 * @return
	 */
	public String GetReplyMyLetterListing(int ToUserId, int PageNums) {
		String result = "";
		String url = getUrl("GetReplyMyLetterListing") + "?";
		RequestParameters params = new RequestParameters();
		params.add("ToUserId", ToUserId);
		params.add("PageNums", PageNums);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 我回复的评论
	 * 
	 * @param FromUserId
	 *            当前登录用户id
	 * @param PageNums
	 * @return
	 */
	public String GetIReplyOtherLetterListing(int FromUserId, int PageNums) {
		String result = "";
		String url = getUrl("GetIReplyOtherLetterListing") + "?";
		RequestParameters params = new RequestParameters();
		params.add("FromUserId", FromUserId);
		params.add("PageNums", PageNums);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 得到正在直播的所有活动
	 * 
	 * @return
	 */
	public String GetActivityDoing() {
		String result = "";
		String url = getUrl("GetActivityDoing");
		RequestParameters params = new RequestParameters();
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 得到正在直播的精品活动
	 * 
	 * @return
	 */
	public String GetActivityDoingSpecial() {
		String result = "";
		String url = getUrl("GetActivityDoingSpecial");
		RequestParameters params = new RequestParameters();
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 得到正在直播的将要开始的活动
	 * 
	 * @return
	 */
	public String GetActivityWillDoing() {
		String result = "";
		String url = getUrl("GetActivityWillDoing");
		RequestParameters params = new RequestParameters();
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 发表产品评论或回复某条回复
	 * 
	 * @param PhoneListingId
	 * @param FromUserId
	 * @param ReplyContent
	 * @param ToUserId
	 * @param ReplyId
	 *            对产品评论不需要次参数（传-1）
	 * @return
	 */
	public String PostSendLetter(int PhoneListingId, int FromUserId, String ReplyContent, int ToUserId, int ReplyId) {
		String result = "";
		String url = getUrl("PostSendLetter");
		RequestParameters params = new RequestParameters();
		params.add("PhoneListingId", PhoneListingId);
		params.add("FromUserId", FromUserId);
		params.add("ReplyContent", ReplyContent);
		params.add("ToUserId", ToUserId);
		if (ReplyId != -1)
			params.add("ReplyId", ReplyId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 活动产品列表
	 * 
	 * @param ActivityId
	 * @param PageNums
	 * @return
	 */
	public String GetActivityPhoneListing(int ActivityId, int PageNums) {
		String result = "";
		String url = getUrl("GetActivityPhoneListing");
		RequestParameters params = new RequestParameters();
		params.add("ActivityId", ActivityId);
		params.add("PageNums", PageNums);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 产品单品页，返回产品信息
	 * 
	 * @param PhoneListingId
	 * @return
	 */
	public String GetPhonelistMsg(int PhoneListingId) {
		String result = "";
		String url = getUrl("GetPhonelistMsg");
		RequestParameters params = new RequestParameters();
		params.add("PhoneListingId", PhoneListingId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 产品单品页，返回评论信息
	 * 
	 * @param PhoneListingId
	 * @param PageNums
	 * @return
	 */
	public String GetReplyletterList(int PhoneListingId, int PageNums) {
		String result = "";
		String url = getUrl("GetReplyletterList");
		RequestParameters params = new RequestParameters();
		params.add("PhoneListingId", PhoneListingId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 红包列表（未过期）
	 * 
	 * @param UserId
	 * @return
	 */
	public String GetUserRedList(int UserId) {
		String result = "";
		String url = getUrl("GetUserRedList");
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	/**
	 * 红包列表（已过期）
	 * 
	 * @param UserId
	 * @return
	 */
	public String GetUserOverdueRedList(int UserId) {
		String result = "";
		String url = getUrl("GetUserOverdueRedList");
		RequestParameters params = new RequestParameters();
		params.add("UserId", UserId);
		result = clientHelper.execute(url, params, ClientHelper.GET);
		return result;
	}

	public String HttpConnect(URL url) {
		String result = "";
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			conn.setConnectTimeout(20000);
			InputStream inputStream = conn.getInputStream();
			if (conn.getResponseCode() == 408) {
				listener.TimeOutListener();
			}
			byte[] b = new byte[1024];
			int readedLength = -1;
			ByteArrayOutputStream outputS = new ByteArrayOutputStream();
			while ((readedLength = inputStream.read(b)) != -1) {
				outputS.write(b, 0, readedLength);
			}
			result = outputS.toString();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public interface timeoutListener {
		void TimeOutListener();
	}

	public void setListener(timeoutListener listener) {
		NetworkService.listener = listener;
	}

}
