package com.example.naveen.bakingapp;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.naveen.bakingapp.fragments.IngredientFragment;
import com.example.naveen.bakingapp.fragments.ReceipeStepFragment;

import java.util.ArrayList;
import java.util.List;

public class ReceipeStepActivity extends AppCompatActivity {
    Adapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    int receipe_id,receipe_image;
    String receipe_name;
    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager(),this);
        adapter.addFragment(new IngredientFragment(),"Ingedients");
        adapter.addFragment(new ReceipeStepFragment(),"Steps");
        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        receipe_id=getIntent().getIntExtra("receipe_id",0);
        receipe_name=getIntent().getStringExtra("receipe_name");
        receipe_image=getIntent().getIntExtra("receipe_image",0);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public  class Adapter extends FragmentStatePagerAdapter {
        //int mNumOfTabs;

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        Context context;
        Bundle bundle=new Bundle();

        Adapter(FragmentManager fm, Context context) {
            super(fm);
            this.context=context;

        }

        void addFragment(Fragment fragment, String title) {
            bundle.putInt("receipe_id",receipe_id);
            bundle.putInt("receipe_image",receipe_image);
            bundle.putString("receipe_name",receipe_name);
            mFragments.add(fragment);
            fragment.setArguments(bundle);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {


            return mFragmentTitles.get(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);

    }
}
