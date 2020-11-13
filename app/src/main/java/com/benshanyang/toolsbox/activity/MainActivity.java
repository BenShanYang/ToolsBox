package com.benshanyang.toolsbox.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.benshanyang.toolsbox.R;
import com.benshanyang.toolsbox.fragment.FifthFragment;
import com.benshanyang.toolsbox.fragment.FirstFragment;
import com.benshanyang.toolsbox.fragment.FourthFragment;
import com.benshanyang.toolsbox.fragment.SecondFragment;
import com.benshanyang.toolsbox.fragment.SixthFragment;
import com.benshanyang.toolsbox.fragment.ThirdFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            List<Fragment> list = Arrays.asList(new FirstFragment(), new SecondFragment(), new ThirdFragment(), new FourthFragment(), new FifthFragment(), new SixthFragment());

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int index) {
                return list.get(index);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return String.format("Tab %s", position + 1);
            }
        });
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}