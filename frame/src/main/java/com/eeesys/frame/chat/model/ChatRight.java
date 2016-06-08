package com.eeesys.frame.chat.model;

import com.eeesys.frame.chat.adapter.ChatRightAdapter;
import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.listview.inter.IProvider;

public class ChatRight implements IAdapter{
	private String content;

	public ChatRight(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public Class<? extends IProvider> getProviderClass() {
		return ChatRightAdapter.class;
	}

}
