package com.eeesys.myapplication.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eeesys.myapplication.R;
import com.eeesys.myapplication.entity.RecentNews;
import com.eeesys.myapplication.entity.Recomend;
import com.eeesys.myapplication.service.RecomendSrvice;
import com.eeesys.myapplication.service.ServiceGenerator;
import com.eeesys.myapplication.ui.adapter.RecentNewsAdapter;
import com.eeesys.myapplication.customview.DividerItemDecoration;
import com.eeesys.myapplication.ui.adapter.RecomendAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        rv_news.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
       // layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter

        rv_news.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    private void initData(){
        RecomendSrvice service = ServiceGenerator.createService(RecomendSrvice.class);
        Call<List<Recomend>> call = service.getRecomend();
        final ProgressDialog pd = ProgressDialog.show(getActivity(),"Loding...","加载中...");
        call.enqueue(new Callback<List<Recomend>>() {
            @Override
            public void onResponse(Call<List<Recomend>> call, Response<List<Recomend>> response) {
                if(response.isSuccessful()){
                   List<Recomend> recomends = response.body();
                    for(Recomend r : recomends){
                        RecentNews news = new RecentNews();
                        news.setContent(r.getMark());
                        news.setImgUrl(r.getImgUrl());
                        newsList.add(news);
                    }
                    adapter = new RecentNewsAdapter(getActivity(),newsList);
                    rv_news.setAdapter(adapter);
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<Recomend>> call, Throwable t) {
                Log.d("recomend err",t.getMessage());
                pd.dismiss();
            }
        });
    }



}
