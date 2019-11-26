package com.example.solist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.solist.Adapter.PagerAdapter;
import com.example.solist.View.HomeFragment;
import com.example.solist.View.ListFragment;
import com.example.solist.View.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xml 파일에서 ViewPager 가져오기
        mViewPager = findViewById(R.id.viewPager);
        // adapter 생성 후 연결하기
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mAdapter.addFragmentList(new HomeFragment());
        mAdapter.addFragmentList(new ListFragment());
        mAdapter.addFragmentList(new SettingFragment());
        mViewPager.setAdapter(mAdapter);
    }
}
