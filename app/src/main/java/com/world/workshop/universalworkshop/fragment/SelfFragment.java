package com.world.workshop.universalworkshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.world.workshop.universalworkshop.R;

/**
 * Created by Administrator on 2016/1/24.
 */
public class SelfFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }
}
