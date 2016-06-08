package com.eeesys.frame.chat.demo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.eeesys.frame.R;
import com.eeesys.frame.chat.adapter.ChatLeftAdapter;
import com.eeesys.frame.chat.adapter.ChatRightAdapter;
import com.eeesys.frame.chat.model.ChatRight;
import com.eeesys.frame.listview.adapter.MultipleAdapter;
import com.eeesys.frame.listview.inter.IAdapter;
import com.eeesys.frame.listview.inter.IProvider;
import com.eeesys.frame.utils.ToastTool;
import com.eeesys.frame.utils.Tools;

public class ChatActivity extends ActionBarActivity {
	private Button mButton;
	private EditText mEditText;
	private ImageButton mImageButton;
	private ListView mListView;
	private MultipleAdapter adapter;
	private List<IAdapter> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		mImageButton = (ImageButton) findViewById(R.id.image_button);
		mImageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tools.insertFace(ChatActivity.this, mEditText);
			}
		});
		mButton = (Button) findViewById(R.id.send);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String str = mEditText.getText().toString();
				if(str.length() == 0){
					ToastTool.show(ChatActivity.this, "您还没输入");
					return;
				}
				list.add(new ChatRight(str));
				adapter.notifyDataSetChanged();
				mListView.smoothScrollToPosition(adapter.getCount()-1);
				mEditText.setText(null);
			}
		});
		mEditText = (EditText) findViewById(R.id.content);
		
		mListView = (ListView) findViewById(R.id.chat_content);
		list = new ArrayList<IAdapter>();
		List<Class<? extends IProvider>> providers = new ArrayList<Class<? extends IProvider>>();
		providers.add(ChatLeftAdapter.class);
		providers.add(ChatRightAdapter.class);
		adapter = new MultipleAdapter(this, list, providers);
		mListView.setAdapter(adapter);
	}

}
