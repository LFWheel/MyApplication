package com.eeesys.myapplication.service;

import com.eeesys.myapplication.entity.Recomend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by ASUS on 2016/7/21.
 */
public interface RecomendSrvice {
    @POST("GetRecomendServlet")
    Call<List<Recomend>> getRecomend();
}
