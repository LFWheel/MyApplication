package com.eeesys.frame.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eeesys.frame.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

public class Tools {
	private Tools()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 可传入字符串或字符资源id
	 * 
	 * @param context
	 * @param obj
	 * @return
	 */
	public static String Object2String(Context context, Object obj) {
		if (obj == null) {
			throw new RuntimeException("obj参数不能为空");
		}
		if (obj.getClass() == Integer.class) {
			return context.getString(Integer.parseInt(String.valueOf(obj)));
		}
		return obj.toString();
	}

	/**
	 * 一个或多个数组合并后添加到list中
	 * 
	 * @param objects
	 *            Object类型的可变数组参数
	 * @return 合并后的list
	 */
	public static List<Object> combineArrays(Object[]... objects) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < objects.length; i++) {
			list.addAll(Arrays.asList(objects[i]));
		}
		return list;
	}

	/**
	 * 获得对象的所有成员变量
	 * 
	 * @param classTye
	 *            对象类型
	 * @return 成员变量list
	 */
	public static List<Field> getDeclaredFields(Class<?> classTye) {
		// 子类成员变量
		Field[] subFiled = classTye.getDeclaredFields();
		// 父类成员变量
		Field[] superField = classTye.getSuperclass().getDeclaredFields();
		// 合并后的成员变量
		List<Object> list = Tools.combineArrays(subFiled, superField);
		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < list.size(); i++) {
			fields.add((Field) list.get(i));
		}
		return fields;
	}

	/**
	 * 对象转map
	 * 
	 * @param object
	 *            Object类型对象
	 * @return map
	 */
	public static HashMap<String, Object> toMap(Object object) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (object == null) {
			return map;
		}

		try {
			// 对象类型
			Class<?> classTye = object.getClass();
			// 获得对象的所有成员变量
			List<Field> list = Tools.getDeclaredFields(classTye);
			for (int i = 0; i < list.size(); i++) {
				Field field = (Field) list.get(i);
				String fieldName = field.getName();
				field.setAccessible(true);
				Object o = field.get(object);
				if (o != null) {
					map.put(fieldName, o.toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	
	public static HashMap<String, Object> toObjectMap(Object object) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (object == null) {
			return map;
		}

		try {
			// 对象类型
			Class<?> classTye = object.getClass();
			// 获得对象的所有成员变量
			List<Field> list = Tools.getDeclaredFields(classTye);
			for (int i = 0; i < list.size(); i++) {
				Field field = (Field) list.get(i);
				String fieldName = field.getName();
				field.setAccessible(true);
				Object o = field.get(object);
				if (o != null) {
					map.put(fieldName, o);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	/**
	 * map转对象
	 * 
	 * @param classTye
	 *            对象类型
	 * @param map
	 * @return 对象
	 */
	@SuppressLint("DefaultLocale")
	public static Object toObject(Class<?> classTye, Map<String, Object> map) {
		try {
			// 生成一个实例
			Object object = classTye.getConstructor(new Class[] {})
					.newInstance(new Object[]{});
			// 获得对象的所有成员变量
			List<Field> list = Tools.getDeclaredFields(classTye);
			for (int i = 0; i < list.size(); i++) {
				Field field = (Field) list.get(i);
				// 获取成员变量的名字
				String name = field.getName();

				// 获取set方法的名字
				String firstLetter = name.substring(0, 1).toUpperCase();
				String setMethodName = "set" + firstLetter + name.substring(1);

				// 获取方法对象
				Method setMethod = classTye.getMethod(setMethodName,
						new Class[]{field.getType()});
				// map中键值对set到对象中
				if (map != null && map.get(name) != null) {
					setMethod.invoke(object, new Object[]{map.get(name)});
				}
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * InputStream转String
	 * 
	 * @param stream
	 *            输入流
	 * @param encode
	 *            编码
	 * @return
	 */
	public static String streamToString(InputStream stream, String encode) {
		if (stream == null) {
			return null;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = null;
		try {
			while ((len = stream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			result = new String(outputStream.toByteArray(), encode);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return result;
	}

	/**
	 * 编码
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		return encode(str, "utf-8");
	}

	/**
	 * 编码
	 * 
	 * @param str
	 * @param encode
	 * @return
	 */
	public static String encode(String str, String encode) {
		try {
			return URLEncoder.encode(str, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		return decode(str, "utf-8");
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @param encode
	 * @return
	 */
	public static String decode(String str, String encode) {
		try {
			return URLDecoder.decode(str, encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把null对象换成“”
	 */
	public static String replaceNull(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 转数字类型
	 */
	public static Number String2Number(Object str, Class<?> classType) {
		try {
			return (Number) classType.getConstructor(String.class).newInstance(
					replaceNull(str));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * String转Int类型
	 */
	public static Integer String2Int(Object str) {
		return String2Number(str, Integer.class).intValue();
	}
	
	/**
	 * String转Long类型
	 */
	public static Long String2Long(Object str) {
		return String2Number(str, Long.class).longValue();
	}
	
	/**
	 * String转Float类型
	 */
	public static Float string2Float(Object str) {
		return String2Number(str, Float.class).floatValue();
	}
	
	/**
	 * String转Double类型
	 */
	public static Double string2Double(Object str) {
		return String2Number(str, Double.class).doubleValue();
	}
	
	public static String assembleURL(String path, Map<String, Object> params) {
		// 如果参数不为空，添加参数到url中
		StringBuilder sb = new StringBuilder(path);
		if (params != null && !params.isEmpty()) {
			sb.append("?");
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(Tools.encode(entry.getValue().toString())).append("&");
			}
			return sb.deleteCharAt(sb.length()-1).toString();
		}
		return path;
	}
	
	
	/**
	 * 反射得到组件的id号
	 */
	public static int getCompentID(String packageName, String className,String idName) {
		int id = 0;
		try {
			Class<?> cls = Class.forName(packageName + ".R$" + className);
			id = cls.getField(idName).getInt(cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 线程睡眠
	 * @param mil
	 */
	public static void sleep(long mil){
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	// 插入表情
	public static void insertFace(Context context, TextView view) {
		insertImage(context, view, "f000");
	}

	private static void insertImage(Context context, TextView view, String image) {
		try {
			// R.drawable类中获得相应资源ID（静态变量）的Field对象
			Field field = R.drawable.class.getDeclaredField(image);
			// 获得资源ID的值，也就是静态变量的值
			int resourceId = Integer.parseInt(field.get(null).toString());
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), resourceId);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(context, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString("#" + image + "#");
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, 0, spannableString.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 将随机获得的图像追加到EditText控件的最后
			view.append(spannableString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 替换图片
	public static void replaceFace(Context context, TextView view, String source) {
			int index = 0;
			Pattern p = Pattern.compile("#f([0-9]){0,}#");
			Matcher m = p.matcher(source);
			List<String> list = new ArrayList<String>();
			while(m.find()){
				String str = m.group();
				list.add(str.substring(1, str.length() - 1));
				source = source.replace(str, "%##%");
			}
			for (int i = 0; i < source.length(); i++) {
				String ch = String.valueOf(source.charAt(i));
				if(ch.startsWith("%")){
					if("%##%".equals(source.substring(i, i + 4))){
						insertImage(context, view, list.get(index++));
						i = i + 3;
						continue;
					}
				}
				view.append(ch);
			}
	}
	
	/**
	 * 判断对象是否为空
	 * 不为空返回true
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o){
		if(o != null){
			if(o instanceof List){
				return ((List)o).size() > 0;    //List数量大于0时返回true
			}else if(o instanceof String){
				return !("".equals(((String)o).trim()));   //""返回false
			}
			return true;
		}
		return false;   //对象为空时返回false
	}
	
	/**
	 * 字符全角化
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {  
		   char[] c = input.toCharArray();  
		   for (int i = 0; i< c.length; i++) {  
		       if (c[i] == 12288) {  
		         c[i] = (char) 32;  
		         continue;  
		       }if (c[i]> 65280&& c[i]< 65375)  
		          c[i] = (char) (c[i] - 65248);  
		       }  
		   return new String(c);  
		}

	
	/**
	 * md5算法
	 * 
	 * @param source
	 * @param encode
	 * @return
	 */
	public static String getMD5(String source, String encode) {
		String s = null;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (encode == null)
				md.update(source.getBytes());
			else {
				md.update(source.getBytes(encode));
			}
			byte[] tmp = md.digest();
			char[] str = new char[32];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 检测中文
	 * 
	 * @param 要检测的字符串
	 * @return
	 */
	public static boolean checkChinese(String source) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(source);
		return m.find();
	}
	
	/**
	 * 检测中文、字母、下划线
	 * 
	 * @param 要检测的字符串
	 * @return
	 */
	public static boolean checkCharacter(String source) {
		String rex = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{0,}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(source);
		return m.find();
	}
	
	/**
	 * 检测手机号码
	 * "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}"手机固话
	 * @param 要检测的字符串
	 * @return
	 */
	
	public static boolean checkPhone(String source) {
		String rex = "^1([\\d]{10})$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(source);
		return m.find();
	}
	
	/**
	 * 输出图片路径
	 * 
	 * @param context
	 * @return
	 */
	public static File getOutputMediaFile(Context context) {

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				context.getPackageName());

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(context.getPackageName(), "failed to create directory");
				return null;
			}
		}
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}
	
	/**
	 * 得到图片路径
	 * 
	 * @param uri
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getImageLoad(Uri uri, Activity activity) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String imagePath = null;
		Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				imagePath = cursor.getString(column_index);
			}
		}
		return imagePath;
	}

	/**
	 * MD5加密
	 * @param plainText
	 * @return
	 */
	public static String encryptMD5(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
}
