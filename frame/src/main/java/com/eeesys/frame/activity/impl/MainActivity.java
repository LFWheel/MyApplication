package com.eeesys.frame.activity.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.eeesys.frame.R;
import com.eeesys.frame.activity.inter.MActivity;
import com.eeesys.frame.app.CusApp;
import com.eeesys.frame.net.impl.NetWorkImpl;
import com.eeesys.frame.utils.IntentTool;

import java.util.HashMap;

public abstract class MainActivity extends AppCompatActivity implements MActivity{

    //传过来的值
    protected HashMap<String, Object> bundle;
    //传入的值
    protected HashMap<String, Object> param;
    protected ImageView left;
    protected ImageView right;
    protected TextView title;
    protected android.support.v7.app.ActionBar ab;
    protected NetWorkImpl netWork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CusApp)getApplication()).getActivities().add(this);
        param = new HashMap<String, Object>();
        bundle = IntentTool.getParameter(this);
        netWork = new NetWorkImpl(this, NetWorkImpl.POST);
        if (hasTitle()) {
            ab = getSupportActionBar();
            ab.setDisplayShowCustomEnabled(true);
            ab.setDisplayShowHomeEnabled(false);
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setDisplayUseLogoEnabled(false);
            int titleLayout = getTitleLayout();
            if (titleLayout > 0) {
                ab.setCustomView(titleLayout);
            } else {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
            }
            initTitleView();
        }
        creatView();


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    public  void creatView() {
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
        }
        initContentView();
    }

    @Override
    public abstract void initTitleView();
    public abstract int getTitleLayout();

    @Override
    public boolean hasTitle() {
        return true;
    }
}
