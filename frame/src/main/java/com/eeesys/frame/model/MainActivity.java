package com.eeesys.frame.model;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eeesys.frame.R;
import com.eeesys.frame.listview.abs.BaseAdapterTool;
import com.eeesys.frame.net.impl.NetWorkImpl;
import com.eeesys.frame.net.model.URLComplete;
import com.eeesys.frame.utils.ToastTool;


/*public class MainActivity extends JActivity implements OnItemClickListener {
	ListView ttt;
	List<ListBean> list;

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initContentView() {
		ttt = (ListView) findViewById(R.id.ttt);
		ttt.setOnItemClickListener(this);
		new NetWorkImpl(this){

			@Override
			public void onSucess(URLComplete urlComplete) {

				System.out.println(urlComplete.getResponseCode());
				list = new ArrayList<ListBean>();
				for (int i = 0; i < 100; i++) {
					if(i % 3 == 0){
						list.add(new ListBean("name" + i, R.drawable.ic_launcher));
					}else{
						list.add(new ListBean("name" + i, 0));	
					}
				}
				ttt.setAdapter(new BaseAdapterTool<ListItem1, ListBean>(MainActivity.this, list) {

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
						return R.layout.list_item;
					}

					@Override
					public int getItemViewType(int position) {
						ListBean l = (ListBean)getItem(position);
						if(l.photo == 0){
							return 0;
						}else{
							return 1;
						}
					}

					@Override
					public int getViewTypeCount() {
						return 2;
					}

					@Override
					public void showView(ListItem1 t, ListBean v) {
						t.textView.setText(v.name);
						if(v.photo != 0)
						t.imageView.setImageResource(v.photo);
					}
				});
				*//*List<IAdapter> list = new ArrayList<IAdapter>();
				for (int i = 0; i < 10; i++) {
					list.add(new ListBean("name1" + i, R.drawable.ic_launcher));
					list.add(new ListBean2("name2" + i, R.drawable.ic_launcher));
				}
				List<Class<? extends IProvider>> adapter = new ArrayList<Class<? extends IProvider>>();
				adapter.add(ListAdapter1.class);
				adapter.add(ListAdapter2.class);
				ttt.setAdapter(new MultipleAdapter(getApplicationContext(), list, adapter));*//*
			
			}

			@Override
			public void onFail(URLComplete urlComplete) {
				ToastTool.show(getApplicationContext(), "---------------");
			}
			}.LoadUrl("https://www.baidu.com/");
	}

	@Override
	public void initTitleView() {
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		*//*toMap.put("asd", list.get(position));
		toMap.put(Constants.TITLE, list.get(position).name);
		startActivity(IntentTool.getIntent(this, Test2.class, toMap));*//*
		
	}

	@Override
	public boolean hasCustomTitle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTitleViewId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasTitle() {
		// TODO Auto-generated method stub
		return false;
	}

	

}*/
