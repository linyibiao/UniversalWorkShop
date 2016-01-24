package com.world.workshop.universalworkshop.activity;

import android.os.Bundle;

import com.world.workshop.universalworkshop.R;
import com.world.workshop.universalworkshop.fragment.BaseFragment;
import com.world.workshop.universalworkshop.fragment.HomeFragment;
import com.world.workshop.universalworkshop.fragment.MallFragment;
import com.world.workshop.universalworkshop.fragment.MessageFragment;
import com.world.workshop.universalworkshop.fragment.SelfFragment;
import com.world.workshop.universalworkshop.fragment.TradeFragment;

import java.util.ArrayList;
import java.util.List;

/** 首页activity
 * Created by Administrator on 2016/1/23.
 */
public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_homepage);
        List<Class<? extends BaseFragment>> fragmentClasses = new ArrayList<>();
        fragmentClasses.add(HomeFragment.class);
        fragmentClasses.add(MallFragment.class);
        fragmentClasses.add(TradeFragment.class);
        fragmentClasses.add(MessageFragment.class);
        fragmentClasses.add(SelfFragment.class);
        this.setFragments(fragmentClasses, R.id.home_frame);
        this.setCurrentFragment(this.getCurrentFragmentPosition());
    }

}
