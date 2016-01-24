package com.world.workshop.universalworkshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/1/24.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取Fragment的tag,用于activity的识别
     *
     * @return
     */
    public static String getFragmentTag(Class<? extends BaseFragment> clazz) {
        return clazz.getName();
    }

}
