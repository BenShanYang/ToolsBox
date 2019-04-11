package com.benshanyang.toolsbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.benshanyang.toolslibrary.utils.DateUtils;
import com.benshanyang.toolslibrary.utils.SingleClickUtils;
import com.benshanyang.toolslibrary.utils.ToastUtils;

public class SingleClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_click);
        initListener();
    }

    private void initListener() {
        SingleClickUtils.onClick(this,
                new int[]{R.id.btn_3, R.id.btn_4, R.id.btn_5},
                10000,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = DateUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                        switch (v.getId()) {
                             case R.id.btn_3:
                                ((Button) findViewById(v.getId())).setText("btn3_" + date);
                                break;
                            case R.id.btn_4:
                                ((Button) findViewById(v.getId())).setText("btn4_" + date);
                                break;
                            case R.id.btn_5:
                                ((Button) findViewById(v.getId())).setText("btn5_" + date);
                                break;
                        }
                    }
                });

        SingleClickUtils.onClick(this, R.id.btn_1, 2000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = DateUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                ((Button) findViewById(v.getId())).setText("btn1_" + date);
            }
        });

        SingleClickUtils.onClick(findViewById(R.id.btn_2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = DateUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                ((Button) findViewById(v.getId())).setText("btn2_" + date);
            }
        });

    }
}
