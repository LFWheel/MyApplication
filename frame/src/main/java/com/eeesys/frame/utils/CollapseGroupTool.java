package com.eeesys.frame.utils;

import android.widget.ExpandableListView;

/**
 * ExpandableListView关闭分组
 * @author eeesys
 *
 */
public class CollapseGroupTool {
        
	public static void CollapseGroup(ExpandableListView expandableListView, int groupPosition){
		for (int i = 0, count = expandableListView
                .getExpandableListAdapter().getGroupCount(); i < count; i++) {
            if (groupPosition != i) {// 关闭其他分组
           	 expandableListView.collapseGroup(i);
            }
        }
	}
}
