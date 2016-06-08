package com.eeesys.frame.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeesys.frame.R;
import com.eeesys.frame.listview.abs.OneTypeAdapter;
import com.eeesys.frame.listview.inter.IAdapter;

public class ListAdapter2 extends OneTypeAdapter<ListItem1> {

	@Override
	public ListItem1 getViewHolder() {
		return new ListItem1();
	}

	@Override
	public void initViewHolder(ListItem1 t, View view) {
		t.textView = (TextView) view.findViewById(R.id.text);
		t.imageView = (ImageView) view.findViewById(R.id.image);
	}

	@Override
	public int getLayoutId() {
		return R.layout.list_item2;
	}

	@Override
	public void showView(ListItem1 t, IAdapter iAdapter) {
		ListBean2 bean = (ListBean2) iAdapter;
		t.textView.setText(bean.name);
		t.imageView.setImageResource(bean.photo);
	}

	

}
