package com.world.workshop.universalworkshop.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.world.workshop.universalworkshop.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/23.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.initTitleBar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.setFitsSystemWindows(!this.fullScreen);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        this.setFitsSystemWindows(!this.fullScreen);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        this.setFitsSystemWindows(!this.fullScreen);
    }

    private SystemBarTintManager tintManager;//标题栏工具

    private boolean fullScreen = false;//是否全屏

    private int barTintColor = 0xFFDDDDDD;//标题栏的默认颜色

    /**
     * 是否全屏，默认不全屏
     *
     * @param fullScreen
     */
    protected void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        this.setBarTintColor(fullScreen);
        this.setFitsSystemWindows(!fullScreen);
    }

    /**
     * 设置根视图是否适应系统窗口而排布
     *
     * @param fitSystemWindows
     */
    private void setFitsSystemWindows(boolean fitSystemWindows) {
        ViewGroup viewGroup = (ViewGroup) this.getWindow().getDecorView().findViewById(android.R.id.content);
        if (viewGroup.getChildCount() == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                viewGroup.getChildAt(0).setFitsSystemWindows(fitSystemWindows);
            }
        }
    }

    /**
     * 设置标题栏的颜色
     *
     * @param color
     */
    protected void setBarTintColor(int color) {
        if (this.tintManager != null) {
            this.barTintColor = color;
            this.tintManager.setStatusBarTintColor(color);
        }
    }

    /**
     * 根据是否全屏来设置标题栏的颜色
     *
     * @param fullScreen
     */
    private void setBarTintColor(boolean fullScreen) {
        if (fullScreen) {
            this.setBarTintColor(0x00FFFFFF);
        } else {
            this.setBarTintColor(this.barTintColor);
        }
    }

    /**
     * 初始化标题栏状态
     */
    private void initTitleBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            this.setBarTintColor(this.fullScreen);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    private List<Class<? extends BaseFragment>> fragmentClasses = new ArrayList<>();//fragment列表

    private int layoutID;//被放置fragment列表的layoutID

    /**
     * 设置fragment列表
     *
     * @param fragmentClasses fragment列表
     * @param layoutID        被放置的地方layoutID
     */
    protected void setFragments(List<Class<? extends BaseFragment>> fragmentClasses, int layoutID) {
        this.fragmentClasses = fragmentClasses;
        this.layoutID = layoutID;
    }

    /**
     * 显示当前fragment
     *
     * @param position fragment对应的位置
     */
    protected void setCurrentFragment(int position) {
        for (int currPosition = 0; currPosition < this.fragmentClasses.size(); currPosition++) {
            Class<? extends BaseFragment> itemClazz = this.fragmentClasses.get(currPosition);
            if (currPosition == position) {
                this.showFragment(itemClazz);
            } else {
                this.hideFragment(itemClazz);
            }
        }
    }

    /**
     * 获取当前fragment的位置
     *
     * @return
     */
    protected int getCurrentFragmentPosition() {
        int position = 0;
        for (int currPosition = 0; currPosition < this.fragmentClasses.size(); currPosition++) {
            Class<? extends BaseFragment> itemClazz = this.fragmentClasses.get(currPosition);
            Fragment currFragment = this.getSupportFragmentManager().findFragmentByTag(BaseFragment.getFragmentTag(itemClazz));
            if (currFragment != null && !currFragment.isHidden()) {
                position = currPosition;
                break;
            }
        }
        return position;
    }

    /**
     * 获取当前fragment
     *
     * @return
     */
    protected Fragment getCurrentFragment() {
        Fragment fragment = null;
        for (int currPosition = 0; currPosition < this.fragmentClasses.size(); currPosition++) {
            Class<? extends BaseFragment> itemClazz = this.fragmentClasses.get(currPosition);
            Fragment currFragment = this.getSupportFragmentManager().findFragmentByTag(BaseFragment.getFragmentTag(itemClazz));
            if (currFragment != null && !currFragment.isHidden()) {
                fragment = currFragment;
                break;
            }
        }
        return fragment;
    }

    /**
     * 显示当前fragment
     *
     * @param clazz fragment对应的class
     */
    protected void setCurrentFragment(Class<? extends BaseFragment> clazz) {
        for (Class<? extends BaseFragment> itemClazz : this.fragmentClasses) {
            if (itemClazz.equals(clazz)) {
                this.showFragment(itemClazz);
            } else {
                this.hideFragment(itemClazz);
            }
        }
    }

    /**
     * 显示一个fragment
     *
     * @param clazz fragment对应的class
     */
    private void showFragment(Class<? extends BaseFragment> clazz) {
        FragmentManager manager = this.getSupportFragmentManager();
        String fragmentTag = BaseFragment.getFragmentTag(clazz);
        Fragment fragment = manager.findFragmentByTag(fragmentTag);
        //如果不存在就创建，存在就显示
        if (fragment == null) {
            try {
                fragment = clazz.newInstance();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(this.layoutID, fragment, fragmentTag);
                transaction.commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.show(fragment);
            transaction.commit();
        }
    }

    /**
     * 隐藏一个fragment
     *
     * @param clazz fragment对应的class
     */
    private void hideFragment(Class<? extends BaseFragment> clazz) {
        FragmentManager manager = this.getSupportFragmentManager();
        String fragmentTag = BaseFragment.getFragmentTag(clazz);
        Fragment fragment = manager.findFragmentByTag(fragmentTag);
        //如果已经存在就隐藏，不存在就忽略
        if (fragment == null) {

        } else {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
        }
    }

}
