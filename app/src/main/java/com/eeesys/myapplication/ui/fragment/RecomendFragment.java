package com.eeesys.myapplication.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.eeesys.myapplication.R;
import com.eeesys.myapplication.entity.Recomend;
import com.eeesys.myapplication.service.RecomendSrvice;
import com.eeesys.myapplication.service.ServiceGenerator;
import com.eeesys.myapplication.ui.adapter.RecomendAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 2016/7/19.
 */
public class RecomendFragment extends Fragment {

    private GridView gv_recomend;
    private TextView tv_getMore;
    private List<Recomend> recomends = new ArrayList<>();
    RecomendAdapter adapter;
    public RecomendFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomend,container,false);
        gv_recomend = (GridView) view.findViewById(R.id.gv_recomend);
        tv_getMore = (TextView) view.findViewById(R.id.tv_getMore);
        getRecomendData();
        return view;
    }

    private void getRecomendData(){
        RecomendSrvice service = ServiceGenerator.createService(RecomendSrvice.class);
        Call<List<Recomend>> call = service.getRecomend();
        final ProgressDialog pd = ProgressDialog.show(getActivity(),"Loding...","加载中...");
        call.enqueue(new Callback<List<Recomend>>() {
            @Override
            public void onResponse(Call<List<Recomend>> call, Response<List<Recomend>> response) {
                if(response.isSuccessful()){
                    recomends = response.body();
                    adapter = new RecomendAdapter(recomends,getActivity());
                    gv_recomend.setAdapter(adapter);
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
