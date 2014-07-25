package com.jwgou.android.fragments;

import java.io.File;

import com.jwgou.android.MainActivity;
import com.jwgou.android.MyMessageActivity;
import com.jwgou.android.R;
import com.jwgou.android.RedPackage;
import com.jwgou.android.WebViewActivity;
import com.jwgou.android.entities.User;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.Util;
import com.jwgou.android.widgets.CircleNetImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserinfoFragment extends Fragment implements OnClickListener {

	private View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_userinfo, container, false);
		initView(view);
		return view;
	}

	private void initView(View v) {
		((Button)v.findViewById(R.id.back)).setVisibility(View.GONE);
		((TextView)v.findViewById(R.id.title)).setText("个人中心");
		((TextView)v.findViewById(R.id.right_text)).setText("注销");
		((TextView)v.findViewById(R.id.right_text)).setOnClickListener(this);
		((LinearLayout)v.findViewById(R.id.llSelfOrders)).setOnClickListener(this);
		((LinearLayout)v.findViewById(R.id.llSelfMessageCenter)).setOnClickListener(this);
		((LinearLayout)v.findViewById(R.id.llVersionTip)).setOnClickListener(this);
		((LinearLayout)v.findViewById(R.id.llQandA)).setOnClickListener(this);
		((LinearLayout)v.findViewById(R.id.llAddressManager)).setOnClickListener(this);
		// TODO Auto-generated method stub
		initUser();
		
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initUser();
	}

	private void initUser() {
		User u = ((MainActivity)getActivity()).getApp().user;
		((CircleNetImageView)view.findViewById(R.id.civSelf)).setImageUrl(Util.GetImageUrl(u.HeadStr, Util.dip2px(getActivity(), 64), Util.dip2px(getActivity(), 64)), Config.PATH, null);
		((TextView)view.findViewById(R.id.tvSelfName)).setText(u.UName);
		((TextView)view.findViewById(R.id.tvSelfBalance)).setText(u.UserMoney + "");
		((TextView)view.findViewById(R.id.tvMessageCenterNum)).setText(u.PhoneMessage + "");
		((TextView)view.findViewById(R.id.tvMessageCenterNum)).setVisibility(View.VISIBLE);
		((TextView)view.findViewById(R.id.tvtolook)).setOnClickListener(this);;
	}

	public static void delFile( String filePath){
		File mFile = new File(filePath);
		if(mFile.isFile() && mFile.exists())
		{
			mFile.delete();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//TODO
		case R.id.right_text:
			((MainActivity)getActivity()).getApp().user = new User();
			((MainActivity)getActivity()).setTab(0);
			delFile(getActivity().getFilesDir().getAbsolutePath() + "/userinfo");
			delFile(getActivity().getExternalFilesDir(null) + "/userinfo");
			break;
		case R.id.llSelfOrders:
//			startActivity(new Intent(getActivity(), MyOrderActivity.class));
			((MainActivity)getActivity()).setTab(1);
			break;
		case R.id.llSelfMessageCenter:
			startActivity(new Intent(getActivity(), MyMessageActivity.class));
			break;
		case R.id.llVersionTip:
			//TODO
			Toast.makeText(getActivity(), "当前已经是最新版", Toast.LENGTH_SHORT).show();
			break;
		case R.id.llQandA:
			startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("URL", "http://211.144.76.91:180/Q_A.aspx"));
			break;
		case R.id.llAddressManager:
//			startActivity(new Intent(getActivity(), AddressManagerActivity.class));
			((MainActivity)getActivity()).setTab(2);
			break;
		case R.id.tvtolook:
			startActivity(new Intent(getActivity(), RedPackage.class));
			break;
		default:
			break;
		}
		
	}
}
