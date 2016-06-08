package com.eeesys.frame.chat.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.eeesys.frame.R;
import com.eeesys.frame.chat.model.ChatItem;
import com.eeesys.frame.chat.model.ChatRight;
import com.eeesys.frame.listview.abs.OneTypeAdapter;
import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.utils.Tools;

public class ChatRightAdapter extends OneTypeAdapter<ChatItem> {

	public ChatRightAdapter(Context context) {
		super(context);
	}

	@Override
	public ChatItem getViewHolder() {
		return new ChatItem();
	}

	@Override
	public void initViewHolder(ChatItem t, View view) {
		t.textView = (TextView) view.findViewById(R.id.chat_r);
	}

	@Override
	public int getLayoutId() {
		return R.layout.chat_r_item;
	}

	@Override
	public void showView(ChatItem t, IAdapter iAdapter) {
		ChatRight chat = (ChatRight) iAdapter;
		t.textView.setText(null);
		Tools.replaceFace(context, t.textView, chat.getContent());
	}

	

}
