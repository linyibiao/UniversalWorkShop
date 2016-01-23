package com.world.workshop.universalworkshop.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.world.workshop.universalworkshop.R;

/**
 * Created by Administrator on 2016/1/23.
 */
public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.homepage_layout);
        Toast.makeText(this,"what the hell",Toast.LENGTH_LONG).show();
    }
}
