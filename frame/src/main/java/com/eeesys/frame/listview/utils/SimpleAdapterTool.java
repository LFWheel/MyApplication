package com.eeesys.frame.listview.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.eeesys.frame.listview.model.ImageModel;
import com.eeesys.frame.utils.ImageTool;
import com.eeesys.frame.utils.Tools;

/**
 * 简单适配器
 */
public class SimpleAdapterTool {

	public static <T> SimpleAdapter getAdapter(final Context context, List<T> list,
			int resource, String[] from, int[] to, int[] string) {
		List<String[]> list1 = new ArrayList<String[]>(); // 如果有多个字段通过/分割，分开后的key
		List<Object[]> list2 = new ArrayList<Object[]>(); // 存放从对象的中取出的值
		List<String> key = new ArrayList<String>(); // 存放原始的key
		for (int j = 0; string != null && j < from.length; j++) {
			if (from[j].contains("/")) {
				key.add(from[j]);
				list1.add(from[j].split("/"));
			}
		}
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			HashMap<String, Object> item = Tools.toObjectMap(t);
			for (int j = 0; j < list1.size(); j++) {
				String[] keys = list1.get(j);
				int count = keys.length;
				Object[] values = null;
				if (keys != null && count > 0) {
					values = new Object[count];
					for (int k = 0; k < count; k++) { // 如果有存在/分割的key
						if (item.containsKey(keys[k])) {
							values[k] = item.get(keys[k]);
						}
					}
				}
				list2.add(values);
			}
			for (int j = 0; string != null && j < string.length; j++) {
				item.put(
						key.get(j),
						String.format(context.getString(string[j]),
								list2.get(j)));
			}
			data.add(item);
		}
		SimpleAdapter sa = new SimpleAdapter(context, data, resource, from, to);
		/*sa.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				 if(view instanceof ImageView && data instanceof Integer){
                     ImageView iv=(ImageView)view;
                     iv.setImageDrawable(ImageTool.getBitmapDrawable(context, (Integer)data, false));
                     return true;
             }else{
				return false;
             }
			}
		});*/
		return sa;
	}

	public static void clearImage(SimpleAdapter sa,
			List<? extends ImageModel> list) {
		for (int i = 0; i < list.size(); i++) {
			ImageModel model = list.get(i);
			int imageId_1 = model.imageId_1;
			int imageId_2 = model.imageId_2;
			int imageId_3 = model.imageId_3;
			if(imageId_1 != 0){
				ImageTool.removeCache(imageId_1);
			}
			if(imageId_2 != 0){
				ImageTool.removeCache(imageId_2);
			}
			if(imageId_3 != 0){
				ImageTool.removeCache(imageId_3);
			}
		}
	}
}
