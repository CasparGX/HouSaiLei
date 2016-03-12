package com.casparx.housailei;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.btn_friends)
    LinearLayout btnFriends;
    @Bind(R.id.btn_classify)
    LinearLayout btnClassify;
    @Bind(R.id.btn_msg)
    LinearLayout btnMsg;
    @Bind(R.id.btn_me)
    LinearLayout btnMe;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.btn_camera)
    ImageView btnCamera;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @OnClick(R.id.btn_camera) void onClickBtnCamera(){
        Intent intent = new Intent();
        intent.setClass(this,CameraActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_friends)
    void onClickBtnFriends() {
        title.setText(R.string.friends);
        setFragmentIndicator();
        fragmentTransaction.show(fragmentFriends).commit();
        btnFriends.setBackgroundColor(resources.getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.btn_classify)
    void onClickBtnClassify() {
        title.setText(R.string.classify);
        setFragmentIndicator();
        fragmentTransaction.show(fragmentClassify).commit();
        btnClassify.setBackgroundColor(resources.getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.btn_msg)
    void onClickBtnMsg() {
        title.setText(R.string.message);
        setFragmentIndicator();
        fragmentTransaction.show(fragmentMsg).commit();
        btnMsg.setBackgroundColor(resources.getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.btn_me)
    void onClickBtnMe() {
        title.setText(R.string.me);
        setFragmentIndicator();
        fragmentTransaction.show(fragmentMe).commit();
        btnMe.setBackgroundColor(resources.getColor(R.color.colorPrimary));
    }

    private Fragment fragmentFriends;
    private Fragment fragmentClassify;
    private Fragment fragmentMsg;
    private Fragment fragmentMe;

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        resources = getResources();
        initView();
    }

    private void initView() {
        fragmentManager = this.getSupportFragmentManager();
        fragmentClassify = fragmentManager.findFragmentById(R.id.fragment_classify);
        fragmentFriends = fragmentManager.findFragmentById(R.id.fragment_friends);
        fragmentMsg = fragmentManager.findFragmentById(R.id.fragment_msg);
        fragmentMe = fragmentManager.findFragmentById(R.id.fragment_me);
        setFragmentIndicator();
        fragmentTransaction.show(fragmentClassify).commit();
        btnClassify.setBackgroundColor(resources.getColor(R.color.colorPrimary));
    }

    private void setFragmentIndicator() {
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(fragmentClassify).hide(fragmentFriends).hide(fragmentMsg).hide(fragmentMe);
        btnFriends.setBackgroundColor(resources.getColor(R.color.grey_e));
        btnClassify.setBackgroundColor(resources.getColor(R.color.grey_e));
        btnMsg.setBackgroundColor(resources.getColor(R.color.grey_e));
        btnMe.setBackgroundColor(resources.getColor(R.color.grey_e));
    }
}
