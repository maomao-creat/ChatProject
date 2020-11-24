package com.zykj.samplechat.network;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import Decoder.BASE64Encoder;

public class Test {
	private static Gson gson = new Gson();
	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("username", "15165561652");
		map.put("password", "123");
		System.out.println(getJSONParam("Login", map));
	}
	
    /*获取秘钥*/
	public static String getJSONParam(String method, HashMap<String, Object> params) {
		params.put("key", Const.KEY);
		params.put("uid", Const.UID);
		params.put("function", method);
		String json = gson.toJson(params);
		int jlen = json.length();
		try {
			json = replaceBlank(getBase64(jlen+"&"+json));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Log.e("csh---", json);
		return json;
	}

	/*过滤秘钥*/
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

	public static String getBase64(String str) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec skeySpec = new SecretKeySpec(Const.AESKEY.getBytes(), "AES");
		IvParameterSpec iv = new IvParameterSpec(Const.AESIV.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(str.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}
}