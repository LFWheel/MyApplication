package com.eeesys.myapplication.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeesys.myapplication.R;
import com.eeesys.myapplication.ui.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private TabLayout fragment_titles;
    private ViewPager vp_content;
    private List<Fragment> fragments;
    private Fragment hotFargment,recentFragment;
    private FragmentPagerAdapter fpAdapter;
    private List<String> titles=new ArrayList<String>();
    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.content_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fragment_titles = (TabLayout) view.findViewById(R.id.tab_fragment_title);
        vp_content = (ViewPager) view.findViewById(R.id.vp_content);

        fragments = new ArrayList<Fragment>();
        hotFargment = new HotFragment();
        recentFragment = new RecentFragment();
        fragments.add(recentFragment);
        fragments.add(hotFargment);

        titles.add("最近新闻");
        titles.add("热门");
        fpAdapter=new MyFragmentPagerAdapter(getChildFragmentManager(),fragments,titles);
        vp_content.setAdapter(fpAdapter);
        fragment_titles.setupWithViewPager(vp_content);
        fragment_titles.setTabsFromPagerAdapter(fpAdapter);
    }

}
