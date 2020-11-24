package com.zykj.samplechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author  lc 
 * @date 创建时间：2016-1-13 上午10:13:27 
 * @version 1.0 
 * @Description 打印工具类
 */
public class ToolsUtils {
	static private Toast mToast = null;
	public static int M_SCREEN_WIDTH;
	public static int M_SCREEN_HEIGHT;


	/**
	 * 手机号验证
	 * @param mobiles 手机号
	 * @return 验证通过返回true
	 */
	static public boolean isMobile(String mobiles) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(mobiles);
		b = m.matches();
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}").matcher(mobiles).matches();
		return b;
	}

	// 打印方法
	public static void print(String tag, String msg) {
		if(true){
			Log.i(tag, msg);
		}
	}

	//设置状态栏颜色，true:黑色
	//false：白色
	public static void changStatusIconCollor(Activity activity, boolean setDark) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			View decorView = activity.getWindow().getDecorView();
			if(decorView != null){
				int vis = decorView.getSystemUiVisibility();
				if(setDark){
					vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
				} else{
					vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
				}
				decorView.setSystemUiVisibility(vis);
			}
		}
	}

	/**
	 * 获取版本名称
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("msg",e.getMessage());
		}
		return verName;
	}

	/**
	 * Toast提醒
	 * @param msg
	 */
	public static void toast (Context context,String msg) {
		// 防止Toast重复显示
		if (mToast == null) {  
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);  
        } else {  
            mToast.setText(msg);  
            mToast.setDuration(Toast.LENGTH_SHORT);  
        }  
        mToast.show(); 
	}

	/**
	 * 添加参数
	 * 根据传进来的Object对象来判断是String还是File类型的参数
	 */
	public static RequestBody getBody(/*String key,*/ Object o) {
		if (o instanceof String) {
			//params.put(key, body);
			return RequestBody.create(MediaType.parse("text/plain"), (String) o);
		} else if (o instanceof File) {
			//params.put(key + "\"; filename=\"" + ((File) o).getName() + "", body);
			return RequestBody.create(MediaType.parse("image/*"), (File)o);
		}
		return null;
	}

	/**
	 * 获取当前时间
	 * @param formatType
	 * @return
	 */
 	public static String getNowStr(String formatType) {
 		return new SimpleDateFormat(formatType, new Locale("zh_CN")).format(new Date());
 	}
 	
	/**
	 * 获取当前应用的版本号
	 */
	public static int getAppVersion(Context context) {
		int version = 0;
		try {
			PackageInfo packinfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			version = packinfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return version;
		}
		return version;
	}

	/**
	 *
	 * @param strDate
	 * @param formatType
	 * @return
     */
 	public static boolean isOverdue(String strDate, String formatType){
 		if(StringUtil.isEmpty(strDate)){
 	 		return true;//已过期
 		}else{
 	 		SimpleDateFormat formatter = new SimpleDateFormat(formatType,new Locale("zh_CN"));
			try {
				long time = formatter.parse(strDate).getTime();
	 	 		return time < System.currentTimeMillis();
			} catch (ParseException e) {
				e.printStackTrace();
	 	 		return false;
			}
 		}
 	}
 	
	/**
	 * 将dp转化为像素值
	 */
	public static int dp2px(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	
	/**
	 * @param uri
	 * @param imageUri
	 * @return 意图
	 */
	public static Intent getDefaultIntent(Uri uri, Uri imageUri){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//Uri.parse("file:///sdcard/temp.jpg")
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		return intent;
	}


	/**
	 * Display metrics display metrics.
	 *
	 * @param context the context
	 * @return the display metrics
	 */
	public static DisplayMetrics displayMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * encodeBase64File:(将文件转成base64 字符串). <br/>
	 * @param path 文件路径
	 * @return String 密文
	 * @since JDK 1.6
	 */
	public static String encodeBase64File(String path) {
		File file = new File(path);
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(file);

			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//把bitmap转换成String
	public static String bitmapToString(String filePath) {
		Bitmap bm = getSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	//计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/** 保存方法 */
	public static String saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
//			try {
//				MediaStore.Images.Media.insertImage(context.getContentResolver(),
//						file.getAbsolutePath(), fileName, null);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
		return "/sdcard/Boohee/"+fileName;
		// 最后通知图库更新
//			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
	}


	public static void showShare(Context context,String title,String titleurl,String content,String imageurl) {
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		//oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(titleurl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(content);
		// imagePath是图片的网络路径
		oks.setImageUrl(imageurl);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		oks.setImagePath("http://www.mob.com/static/app/icon/1480319099.png");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(titleurl);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(content);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(content);
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(titleurl);
//		//自定义复制
//		oks.setCustomerLogo(enableLogo, label, listener);
		// 启动分享GUI
		oks.show(context);
	}


	/**
	 * 获取视频文件截图
	 * @param path 视频文件的路径
	 * @return Bitmap 返回获取的Bitmap
	 */

	public  static Bitmap getVideoThumb(String path) {
		MediaMetadataRetriever media = new MediaMetadataRetriever();
		media.setDataSource(path);
		return  media.getFrameAtTime();
	}

	/**
	 *
	 * @param pw     popupWindow
	 * @param anchor v
	 * @param xoff   x轴偏移
	 * @param yoff   y轴偏移
	 */
	public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff, int type) {
		if (type != 0) {
			if (Build.VERSION.SDK_INT >= 24) {
				Rect visibleFrame = new Rect();
				anchor.getGlobalVisibleRect(visibleFrame);
				int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
				pw.setHeight(height);
				pw.showAsDropDown(anchor, xoff, yoff);
			} else {
				pw.showAsDropDown(anchor, xoff, yoff);
			}
		}else {
			pw.showAtLocation(anchor, Gravity.CENTER,xoff, yoff);
		}
	}

	//检查手机上是否安装了指定的软件
	public static boolean isAvilible(Context context, String packageName){
		//获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		//获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		//用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<String>();
		//从pinfo中将包名字逐一取出，压入pName list中
		if(packageInfos != null){
			for(int i = 0; i < packageInfos.size(); i++){
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		//判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains(packageName);
	}

	/**
	 * 滑动到指定位置
	 */
	public static void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
		// 第一个可见位置
		int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
		// 最后一个可见位置
		int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
		if (position < firstItem) {
			// 第一种可能:跳转位置在第一个可见位置之前
			mRecyclerView.smoothScrollToPosition(position);
		} else if (position <= lastItem) {
			// 第二种可能:跳转位置在第一个可见位置之后
			int movePosition = position - firstItem;
			if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
				int top = mRecyclerView.getChildAt(movePosition).getTop();
				mRecyclerView.smoothScrollBy(0, top);
			}
		} else {
			// 第三种可能:跳转位置在最后可见项之后
			mRecyclerView.smoothScrollToPosition(position);
//			mToPosition = position;
//			mShouldScroll = true;
		}
	}

	//   list转String
	public static String listToString(List<String> mList) {
		String convertedListStr = "";
		if (null != mList && mList.size() > 0) {
			String[] mListArray = mList.toArray(new String[mList.size()]);
			for (int i = 0; i < mListArray.length; i++) {
				if (i < mListArray.length - 1) {
					convertedListStr += mListArray[i] + ",";
				} else {
					convertedListStr += mListArray[i];
				}
			}
			return convertedListStr;
		} else return null;
	}

}