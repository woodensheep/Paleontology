package com.nandity.paleontology.relicdata;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.nandity.paleontology.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lemon on 2017/5/18.
 */

public class ReLicDataActivity extends FragmentActivity {


    @BindView(R.id.category_strip)
    CategoryTabStrip tabs;
    @BindView(R.id.view_pager)
    ViewPager pager;

    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relic_data);
        ButterKnife.bind(this);

        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabs.setViewPager(pager);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            catalogs.add(getString(R.string.category_hot));
            catalogs.add("\u672c\u5730");
            catalogs.add(getString(R.string.category_video));
            catalogs.add(getString(R.string.category_society));
            catalogs.add(getString(R.string.category_entertainment));
            catalogs.add(getString(R.string.category_tech));
            catalogs.add(getString(R.string.category_finance));
            catalogs.add(getString(R.string.category_military));
            catalogs.add(getString(R.string.category_world));
            catalogs.add(getString(R.string.category_image_ppmm));
            catalogs.add(getString(R.string.category_health));
            catalogs.add(getString(R.string.category_government));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }

        @Override
        public Fragment getItem(int position) {
            return NewsFragment.newInstance(position);
        }

    }

}
