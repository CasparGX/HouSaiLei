package com.casparx.housailei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActionActivity extends AppCompatActivity {

    @Bind(R.id.bg)
    FrameLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        switch (id) {
            case 0:
                bg.setBackgroundResource(R.drawable.biyejihuodong);
                break;
            case 1:
                bg.setBackgroundResource(R.drawable.paihang);
                break;
            case 2:
                bg.setBackgroundResource(R.drawable.sheyingdaren);
                break;
            case 3:
                bg.setBackgroundResource(R.drawable.shangxian);
                break;
        }
    }
}
