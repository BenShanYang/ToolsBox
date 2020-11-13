package com.benshanyang.toolsbox.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.benshanyang.toolsbox.R;
import com.benshanyang.toolslibrary.utils.DateUtils;
import com.benshanyang.toolslibrary.utils.SingleClickUtils;

public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SingleClickUtils.onClick(view,
                new int[]{R.id.btn_3, R.id.btn_4, R.id.btn_5},
                10000,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = DateUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                        switch (v.getId()) {
                            case R.id.btn_3:
                                ((Button) v).setText("btn3_" + date);
                                break;
                            case R.id.btn_4:
                                ((Button) v).setText("btn4_" + date);
                                break;
                            case R.id.btn_5:
                                ((Button) v).setText("btn5_" + date);
                                break;
                        }
                    }
                });

        SingleClickUtils.onClick(view, R.id.btn_1, 2000, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = DateUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                ((Button) v).setText("btn1_" + date);
            }
        });

        SingleClickUtils.onClick(view.findViewById(R.id.btn_2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = DateUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                ((Button) v).setText("btn2_" + date);
            }
        });
    }
}