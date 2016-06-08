package com.eeesys.myapplication.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eeesys.myapplication.R;
import com.eeesys.myapplication.entity.RecentNews;
import com.eeesys.myapplication.ui.adapter.RecentNewsAdapter;
import com.eeesys.myapplication.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecentFragment extends Fragment {

    private RecyclerView rv_news;
    private List<RecentNews> newsList=new ArrayList<RecentNews>();
    private RecentNewsAdapter adapter;
    public RecentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recent, container, false);
        rv_news= (RecyclerView) view.findViewById(R.id.rv_news);
        initData();
        adapter = new RecentNewsAdapter(getContext(),newsList);
        adapter.setOnItemClickListener(new RecentNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(getContext(),postion+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        rv_news.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
       // layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        rv_news.setAdapter(adapter);
        rv_news.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    private void initData(){
        RecentNews news1=new RecentNews();
        news1.setContent("退役之战！2016年4月");
        news1.setNews_img(R.mipmap.ic_launcher);
        newsList.add(news1);

        RecentNews news2=new RecentNews();
        news2.setContent("2016得分集锦");
        news2.setNews_img(R.mipmap.ic_launcher);
        newsList.add(news2);
    }



}
