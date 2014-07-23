package com.jwgou.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jwgou.android.baseactivities.BaseActivity;
import com.jwgou.android.baseservice.NetworkService;
import com.jwgou.android.utils.Config;
import com.jwgou.android.utils.HttpManager;
import com.jwgou.android.utils.Util;

public class Active3 extends BaseActivity implements OnClickListener {

	private ImageView photo;
	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_3);
		initView();
	}

	private void initView() {
		mDialog = new ProgressDialog(this);
		((TextView) findViewById(R.id.title)).setText("用户激活");
		photo = (ImageView) findViewById(R.id.photo);
		photo.setOnClickListener(this);
		((Button) findViewById(R.id.back)).setOnClickListener(this);
		((Button) findViewById(R.id.send)).setOnClickListener(this);
		((Button) findViewById(R.id.login)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			CheckName(((EditText) findViewById(R.id.name)).getText().toString());
			break;
		case R.id.photo:
			SelectPhoto();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.login:
			if (Util.isEmpty(name)) {
				ShowToast("请先检查用户名");
				return;
			}
			Active();
			break;

		default:
			break;
		}
	}

	private void Active() {
		String Phone1, password1, paypassword1;
		Phone1 = this.getIntent().getStringExtra("email");
		paypassword1 = this.getIntent().getStringExtra("pay");
		final String Phone = Phone1;
		final String paypassword = paypassword1;
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				mDialog.dismiss();
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							startActivity(new Intent(Active3.this, Active4.class));
						}else
							ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				mDialog.setMessage("正在激活...");
				mDialog.show();
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().ActivetionEnd(getApp().user.UId, Phone, paypassword, UserImgUrl, name);
			}});
	}

	private String name;

	private void CheckName(final String string) {
		if (Util.isEmpty(string)) {
			ShowToast("请输入用户名");
			return;
		}
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (!Util.isEmpty(result)) {
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							name = string;
							ShowToast(o.optString("ResponseData"));
						}else
							ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().RegisterJCUserName(string);
			}
		});
	}

	private void SelectPhoto() {

		String[] arg0 = { "拍照", "手机相册" };
		new AlertDialog.Builder(Active3.this).setTitle("选择头像").setItems(arg0, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {

						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Config.PATH + "/", "camera.png")));
						startActivityForResult(intent, 1);

					} else {
						Toast.makeText(Active3.this, "没有sd卡", Toast.LENGTH_SHORT).show();
					}
					break;
				case 1:
					try {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						intent.addCategory(Intent.CATEGORY_OPENABLE);
						startActivityForResult(intent, 0);
					} catch (Exception e) {

					}
					break;
				default:
					break;
				}
			}
		}).show();
	}

	private Bitmap myBitmap;
	private InputStream in;
	private String filePath;
	private StringBuffer picturePath;
	private String cameraFilePath;
	private boolean isOriginalPic = true;

	public String getRealPathFromURI(Activity act, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = act.managedQuery(contentUri, proj, // Which columns to
															// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private Uri imageUri;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (resultCode != RESULT_OK) {
			return;
		}

		ContentResolver resolver = getContentResolver();
		if (requestCode == 0) {
			try {
				Uri uri = data.getData();
				filePath = getRealPathFromURI(Active3.this, uri);
				// file = new File(filePath);
				try {
					in = resolver.openInputStream(uri);
					myBitmap = BitmapFactory.decodeStream(in);
					isOriginalPic = false;
					Bitmap headimg = Util.setBitmapSize(myBitmap, Util.dip2px(this, 160), Util.dip2px(this, 160));
					photo.setImageBitmap(headimg);
					UploadImg(headimg);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (requestCode == 1) {
			Bitmap headimg = null;
			try {
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					File file = new File(Config.PATH + "/" + "camera.png");
					isOriginalPic = false;
					if (file.exists()) {
						try {
							Bitmap bitmap = BitmapFactory.decodeFile(Config.PATH + "/" + "camera.png");
							if (bitmap != null) {
								myBitmap = bitmap;
								headimg = Util.setBitmapSize(myBitmap, Util.dip2px(this, 160), Util.dip2px(this, 160));
							}
						} catch (OutOfMemoryError e) {
							ShowToast("图片太大");
						}
					}
				} else {
					Bundle extras = data.getExtras();
					myBitmap = (Bitmap) extras.get("data");
					isOriginalPic = false;
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					headimg = Util.setBitmapSize(myBitmap, Util.dip2px(this, 160), Util.dip2px(this, 160));

				}
				photo.setImageBitmap(headimg);
				filePath = cameraFilePath;
				UploadImg(headimg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	private String UserImgUrl;

	private void UploadImg(final Bitmap headimg) {
		new HttpManager(this).Excute(new AsyncTask<Void, Void, String>(){

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				mDialog.setMessage("正在上传头像...");
				mDialog.show();
			}

			@Override
			protected void onPostExecute(String result) {
				mDialog.dismiss();
				if(!Util.isEmpty(result)){
					try {
						JSONObject o = new JSONObject(result);
						if (o.optInt("ResponseStatus") == Config.SUCCESS) {
							UserImgUrl = o.optString("ResponseData");
						}
						ShowToast(o.optString("ResponseMsg"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				return NetworkService.getInstance().uploadUserImg(headimg);
			}});
	}

}
