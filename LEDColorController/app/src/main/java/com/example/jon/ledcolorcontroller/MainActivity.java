package com.example.jon.ledcolorcontroller;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        viewPager= (ViewPager) findViewById(R.id.viewpager);

        adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final TabLayout.Tab tab1 = tabLayout.newTab();
        final TabLayout.Tab tab2 = tabLayout.newTab();
        final TabLayout.Tab tab3 = tabLayout.newTab();
        final TabLayout.Tab tab4 = tabLayout.newTab();

        tab1.setText("Set Color");
        tab2.setText("Presets");
        tab3.setText("Create Preset");
        tab4.setText("Devices");


        tabLayout.addTab(tab1,0);
        tabLayout.addTab(tab2,1);
        tabLayout.addTab(tab3,2);
        tabLayout.addTab(tab4,3);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this,R.color.tabs));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.selector));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
