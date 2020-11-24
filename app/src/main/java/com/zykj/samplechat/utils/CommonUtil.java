package com.zykj.samplechat.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zykj.samplechat.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *
 * 常用工具类
 * 保存数据 setshuju
 * 读取数据 getshuju
 * 拨打电话  callPhone
 * 将时间转换为时间戳  dateToStamp
 * 将时间戳转换为时间 stampToDate
 * 保存图片文件到指定路径 saveImageToGallery
 * 判断指定地址是否为图片 isIma
 * 判断指定地址是否为视频 isplay
 * 获取网络视频 第一帧 createVideoThumbnail
 * 压缩图片（质量压缩） compressImage
 * 功能 ：显示一个进度条对话框 showProcessDialog
 * 取消一个进度条对话框 endProcessDialog()
 * 复制内容到剪辑版 copy
 * 设置延时1秒后 主动刷新 防止 加载2个界面出来 OpenTime  !!!暂时没啥用 先备用吧
 * 判断是否为手机号码 isPhone
 * 判断是否为email    isEmail
 * 生成随机用户名     getRandoStr
 * 锁定状态栏颜色   建议在所有Activity的基类 使用   setWindowStatusBarColor
 * 	设置角标  setjb()  https://github.com/stefanjauker/BadgeView 下载地址   https://blog.csdn.net/u013439635/article/details/51614588 说明地址
 * 	判断textView 控件内容是否为空并且输出提示	 *  isEmpty() 是否为空 null或者长度为0 如果为空 返回false  并且输出提示
 * 	获取控件内容 gettext
 * 	获取控件在当前屏幕中的位置 getkjwz
 * 	获取指定日期 到当前日期的距离 dateDiff
 * 两个时间之间相差距离多少天 getDistanceDays
 * 	IOS风格的弹窗 IOSTanChuang
 * 	设置EditText可输入和不可输入状态 editTextable
 * 	String 转int  去掉小数后面几位 Stringtoint
 * String 转double  去掉小数后面几位 Stringtodouble
 * @author liu
 */
public class CommonUtil {
	public static String rong_appkey="2653dc27dff57";
	public static String rong_AppSecre="a9125235c3eda2158cac09a2f85a03e1";
	//保存数据
	public static void setshuju(String userBean,Context c){
		String USERBEAN = "User";
		Gson gson = new Gson();
		String json = gson.toJson(userBean);
		SharedPreferences preferences = c.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = preferences.edit();
		edit.putString(USERBEAN,json);
		edit.commit();

	}
	//读取
	public static String getshuju(Context c){
		String USERBEAN = "User";
		Gson gson = new Gson();
		SharedPreferences preferences = c.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		String json = preferences.getString(USERBEAN,"");
		String bean = null;
		if(!StringUtil.isEmpty(json)){
			bean = gson.fromJson(json,String.class);
		}
		return bean;
	}
	//保存数据
	public static void setshuju1(String userBean,Context c){
		String USERBEAN = "User1";
		Gson gson = new Gson();
		String json = gson.toJson(userBean);
		SharedPreferences preferences = c.getSharedPreferences("user1",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = preferences.edit();
		edit.putString(USERBEAN,json);
		edit.commit();

	}
	//读取
	public static String getshuju1(Context c){
		String USERBEAN = "User1";
		Gson gson = new Gson();
		SharedPreferences preferences = c.getSharedPreferences("user1",
				Context.MODE_PRIVATE);
		String json = preferences.getString(USERBEAN,"");
		String bean = null;
		if(!StringUtil.isEmpty(json)){
			bean = gson.fromJson(json,String.class);
		}
		return bean;
	}

	/**
	 * 拨打电话
	 * @param context
	 * @param phone  电话号码
	 */
	public static void callPhone(final Context context, final String phone) {
		AlertDialog dialog = null;
		if (dialog == null) {
			dialog = new AlertDialog.Builder(context).setTitle("提示")
					.setMessage("是否拨打电话：" + phone)
					.setNegativeButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									context.startActivity(new Intent(Intent.ACTION_CALL)
											.setData(Uri.parse("tel:" + phone)));
								}
							})
					.setPositiveButton("取消", null).setCancelable(false).show();
		} else {
			dialog.show();
		}
	}
	/*
    * 将时间转换为时间戳
    */
	public static String dateToStamp(String s) throws ParseException, java.text.ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	/*
     * 将时间戳转换为时间  时间接受请用长整型 Long
     */
	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}


	/**
	 * 保存图片文件到指定路径
	 * @param context
	 * @param bmp  保存的图片 Bitmap bm =((BitmapDrawable)  tupian.getDrawable()).getBitmap();
	 * @return
	 */
	public static boolean saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
		File appDir = new File(storePath);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//通过io流的方式来压缩保存图片
			boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
			fos.flush();
			fos.close();

			//把文件插入到系统图库
			MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

			//保存图片后发送广播通知更新数据库
			Uri uri = Uri.fromFile(file);
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
			if (isSuccess) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取网络视频 第一帧 createVideoThumbnail
	 * @param filePath 播放地址
	 * @param kind 帧数 传 MediaStore.Images.Thumbnails.MINI_KIND
	 * @return
	 */
	public static Bitmap createVideoThumbnail(String filePath, int kind)
	{
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try
		{
			if (filePath.startsWith("http://")
					|| filePath.startsWith("https://")
					|| filePath.startsWith("widevine://"))
			{
				retriever.setDataSource(filePath, new Hashtable<String, String>());
			}
			else
			{
				retriever.setDataSource(filePath);
			}
			bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
		}
		catch (IllegalArgumentException ex)
		{
			// Assume this is a corrupt video file
			ex.printStackTrace();
		}
		catch (RuntimeException ex)
		{
			// Assume this is a corrupt video file.
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				retriever.release();
			}
			catch (RuntimeException ex)
			{
				// Ignore failures while cleaning up.
				ex.printStackTrace();
			}
		}

		if (bitmap == null)
		{
			return null;
		}

		if (kind == MediaStore.Images.Thumbnails.MINI_KIND)
		{//压缩图片 开始处
			// Scale down the bitmap if it's too large.
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int max = Math.max(width, height);
			if (max > 512)
			{
				float scale = 512f / max;
				int w = Math.round(scale * width);
				int h = Math.round(scale * height);
				bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
			}//压缩图片 结束处
		}
		else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND)
		{
			bitmap = ThumbnailUtils.extractThumbnail(bitmap,
					96,
					96,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		return bitmap;
	}

	/**
	 *  判断是否为图片文件
	 * @param path  地址
	 * @return
	 */
	public static boolean isIma(String path) {
		if (path.toLowerCase().endsWith(".jpg") //全部改为小写 是否以指定内容结尾
				|| path.toLowerCase().endsWith(".gif")
				|| path.toLowerCase().endsWith(".png")
				|| path.toLowerCase().endsWith(".jpeg")) {
			return true;
		}
		return false;
	}

	/**
	 *  判断是否为视频文件
	 * @param path 地址
	 * @return
	 */
	public static boolean isplay(String path) {
		if (path.toLowerCase().endsWith(".mov")
				|| path.toLowerCase().endsWith(".mkv")
				|| path.toLowerCase().endsWith(".mp4")
				|| path.toLowerCase().endsWith(".avi")) {
			return true;
		}
		return false;
	}
	/**
	 * 压缩图片（质量压缩） compressImage
	 * @param bitmap
	 */
	public static File compressImage(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			long length = baos.toByteArray().length;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String filename = format.format(date);
		File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(baos.toByteArray());
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}

	private static ProgressDialog dialog;

	/**
	 * 功能 ：显示一个进度条对话框showProcessDialog
	 * @param context
	 */
	public static void showProcessDialog(Context context){
		if(dialog==null){
			try{dialog = new ProgressDialog(context);

			}catch (Exception e){}

		}
		try{
			dialog.setTitle("");
			dialog.setMessage(IsZH.Transformation(context, R.string.qsh));
			dialog.setCancelable(true);
			dialog.show();
		}catch (Exception e){}
	}

	/**
	 * 功能 ：取消一个进度条对话框 endProcessDialog()
	 */
	public static  void endProcessDialog(){
		if(dialog!=null){
			dialog.dismiss();
		}
	}

	/**
	 * 复制内容到剪辑版 copy
	 * @param name
	 */
	public static  void copy(String name,Context content){
		// 从API11开始android推荐使用android.content.ClipboardManager
		// 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
		ClipboardManager cm = (ClipboardManager) content.getSystemService(Context.CLIPBOARD_SERVICE);
		// 将文本内容放到系统剪贴板里。
		cm.setText(name);
		Toast.makeText(content, "复制成功", Toast.LENGTH_LONG).show();
	}

	//************************************************* 延时刷新部分
	private static final int SHOW_TIME_MIN = 1000;// 最小显示时间
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//暂时设置3秒后进入系统
			mHandler.postDelayed(goToMainActivity,SHOW_TIME_MIN);
		}
	};
	Runnable goToMainActivity = new Runnable() {

		@Override
		public void run() {
			//自动执行一次刷新
			tr.startRefresh();
		}
	};
	private static TwinklingRefreshLayout tr;
	//设置延时1秒后 主动刷新 防止 加载2个界面出来 OpenTime
	public void OpenTime(TwinklingRefreshLayout tr){
		this.tr=tr;
		mHandler.sendMessage(new Message());

	}
	//↑ ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑延时刷新部分

	/**
	 * 判断是否为手机号码
	 * @param inputText 输入的数据
	 * @return true 为手机号码
	 */
	public static boolean isPhone(String inputText) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(inputText);
		return m.matches();
	}

	/**
	 * 判断是否为email
	 * @param email 输入的数据
	 * @return true 为邮箱 false 为其他数据
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	/**
	 * 生成随机用户名
	 * @param usename_len 生成的用户名的总长度
	 * @return  密码的字符串
	 */
	public static String getRandoStr(int usename_len){
		//35是因为数组是从0开始的，26个字母+10个数字
		final int  maxNum = 36;
		int i;  //生成的随机数
		int count = 0; //生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer username = new StringBuffer("");
		Random r = new Random();
		while(count < usename_len){
			//生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				username.append(str[i]);
				count ++;
			}
		}

		return username.toString();
	}

	/**
	 * 锁定状态栏颜色   建议在所有Activity的基类 使用   setWindowStatusBarColor
	 * @param activity
	 * @param colorResId
	 */
	public static void setWindowStatusBarColor(Activity activity, int colorResId) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = activity.getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(activity.getResources().getColor(colorResId));

				//底部导航栏
				//window.setNavigationBarColor(activity.getResources().getColor(colorResId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置角标  setjb()  https://github.com/stefanjauker/BadgeView 下载地址   https://blog.csdn.net/u013439635/article/details/51614588 说明地址
	 * @param c  Context
	 * @param view 被设置角标的控件
	 * @param sl  角标数量
	 */
	public static void setjb(Context c,View view, int sl){//设置角标符号
		if(view!=null){
			if(c!=null){
//				BadgeView badge = new BadgeView(c);
//				badge.setTargetView(view);
//				//	badge.setBackgroundColor(Color.parseColor("#FF0000"));
//				badge.setBackground(9, Color.parseColor("#FF0000"));
//				badge.setBadgeCount(sl);
}
		}

	}
	/**
	 *  isEmpty() 是否为空 null或者长度为0 如果为空 返回false  并且输出提示
	 *
	 * @param tv 要检测控件
	 * @param content
	 * @param t  要输出的内容
	 * @return
	 */
	public static boolean isEmpty(TextView tv, Context content, String t) {
		CharSequence str = gettext(tv);
		if (str == null || str.length() == 0){
			IsZH.getToast(content, t);  //吐司t
			return true;
		} else{
			return false;
		}
	}
	/**
	 *  isEmpty() 是否为空 null或者长度为0 如果为空 返回false  并且输出提示
	 *
	 * @param tv 要检测控件
	 * @param content
	 * @param t  要输出的内容
	 * @return
	 */
	public static boolean isEmpty(EditText tv, Context content, String t) {
		CharSequence str = tv.getText().toString().trim();
		if (str == null || str.length() == 0){
			IsZH.getToast(content, t);  //吐司t
			return true;
		} else{
			return false;
		}
	}

	/**
	 * 获取控件内容 gettext
	 * @param tv
	 * @return
	 */
	public static String gettext(TextView tv) {
		return tv.getText().toString().trim();
	}

	/**
	 * 获取控件在当前屏幕中的位置 getkjwz
	 * @param view 控件(绑定好的)
	 * @return location [0]--->x坐标,location [1]--->y坐标

	 */
	public static int[] getkjwz(View view){
		int[] location = new int[2] ;
		view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
		view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
		//System.out.println(location[0] + "  -xxxxxx- " + location[1]);
		return location;
	}

	/***
	 * 获取指定日期 到当前日期的距离 dateDiff
	 * @param str
	 * @return
	 */
	public static String dateDiff(String str) {

		String strTime = null;
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String endTime = sd.format(curDate);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime()
					- sd.parse(str).getTime();
			day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			if (day >= 1) {
				strTime = day + "天" + hour + "时";
			} else {

				if (hour >= 1) {
					strTime = day + "天" + hour + "时" + min + "分";

				} else {
					if (sec >= 1) {
						strTime = day + "天" + hour + "时" + min + "分" + sec + "秒";
					} else {
						strTime = "显示即将到期";
					}
				}
			}

			return strTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	/***
	 * 获取指定日期 到当前日期的距离 dateDiff
	 * @param str1
	 * @return
	 */
	public static double dateDiff2(String str1) {

		//String str1 = "2014-11-20";
		DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		try {
			date1 = fmtDateTime.parse(str1.toString());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar  today = Calendar.getInstance();
		double days = (today.getTimeInMillis() - calendar1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		return days;
	}
	/**
	 * 两个时间之间相差距离多少天 getDistanceDays
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差天数
	 */
	public static long getDistanceDays(String str1, String str2) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date one;
		Date two;
		long days=0;
		Boolean be=false;//true  str1>str2
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			//if(time1<time2) {
			diff = time2 - time1;
			//	be=false;
			//} else {
			//	diff = time1 - time2;
			//	be=true;
			//}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//if(!be){
		//days=-days;
		//}
		return days;
	}


	/**
	 * 使用需要导入第三方控件包 详细地址 https://blog.csdn.net/xzylx1/article/details/50394168
	 * 	//IOS风格的弹窗
	 * @param shuju  要弹窗的数据
	 * @param tv  选择之后 复制的TextView
	 * @param c Context
	 */
	public static void IOSTanChuang(List<String> shuju, final TextView tv,Context c){
//		ActionSheetDialog asd =  new ActionSheetDialog(c)
//				.builder()
//				.setCancelable(true)
//				.setCanceledOnTouchOutside(true);
//		for( final String sj:shuju){
//			asd.addSheetItem(""+sj,
//					ActionSheetDialog.SheetItemColor.Blue,
//					new ActionSheetDialog.OnSheetItemClickListener() {
//
//						@Override
//						public void onClick(int which) {
//							tv.setText(sj);
//						}
//					});
//		}
//		asd.show();
	}



	/**
	 * 	//设置EditText可输入和不可输入状态 editTextable
	 * @param editText
	 * @param editable
	 */
	public static void editTextable(EditText editText, boolean editable) {
		if (!editable) { // disable editing password
			editText.setFocusable(false);
			editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
			editText.setClickable(false); // user navigates with wheel and selects widget
		} else { // enable editing of password
			editText.setFocusable(true);
			editText.setFocusableInTouchMode(true);
			editText.setClickable(true);
		}
	}
	/**
	 * String 转int  去掉小数后面几位 Stringtoint
	 * @param str
	 * @return
	 */
	public static int Stringtoint(String str){
		double d=Double.parseDouble(str);
		int i = (new Double(d)).intValue();
		return i;
	}
	/**
	 * String 转double  去掉小数后面几位 Stringtodouble
	 * @return
	 */
	public static String Stringtodouble(String a){
		Double d= Double.parseDouble(a);
		DecimalFormat df = new DecimalFormat("0.00");
		String s = df.format(d);
		return s;
	}

}
