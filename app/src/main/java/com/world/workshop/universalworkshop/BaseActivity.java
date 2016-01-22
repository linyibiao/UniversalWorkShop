package com.world.workshop.universalworkshop;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2016/1/23.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 是否全屏，默认不全屏
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    private boolean fullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initTitleBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewGroup viewGroup = (ViewGroup) this.getWindow().getDecorView().findViewById(android.R.id.content);
        if (viewGroup.getChildCount() == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if (this.fullScreen){
                    viewGroup.getChildAt(0).setFitsSystemWindows(false);
                    this.tintManager.setStatusBarTintColor(0x00ffffff);
                }else {
                    viewGroup.getChildAt(0).setFitsSystemWindows(true);
                }
            }
        }
    }

    private SystemBarTintManager tintManager;

    /**
     * 初始化标题栏状态
     */
    private void initTitleBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(0xFFDDDDDD);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

}
