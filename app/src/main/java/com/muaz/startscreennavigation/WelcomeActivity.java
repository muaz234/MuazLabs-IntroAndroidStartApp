package com.muaz.startscreennavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity {

    LinearLayout layoutDots;
    Button btn_next, btn_previous;
    ViewPager view_pager;
    Context context;
    PrefenceManager manager;
    public int[] layouts;
    public TextView[] dots;
    public MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        manager = new PrefenceManager(this);
        layoutDots = findViewById(R.id.layout_dots);
        btn_next = findViewById(R.id.next_btn);
        btn_previous = findViewById(R.id.previous_btn);
        view_pager = findViewById(R.id.view_pager);

        if (!manager.checkFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // add all layout

        layouts = new int[]{
                R.layout.first_intro,
                R.layout.second_intro,
                R.layout.third_intro,
                R.layout.fourth_intro
        };

        addBottomDots(0);

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        view_pager.setAdapter(myViewPagerAdapter);
        view_pager.addOnPageChangeListener(viewPagerPageChangeListener);
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    view_pager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

    }

    public void addBottomDots ( int currentPage){
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.dots_active_color);
        int[] colorsInActive = getResources().getIntArray(R.array.dots_inactive_array);

        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInActive[currentPage]);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }


    }

    public int getItem(int i) {
        return view_pager.getCurrentItem() + i;
    }

    public void launchHomeScreen() {
        manager.setFirstTimeLaunch(false);
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            addBottomDots(i);
            if(i == layouts.length  - 1) {
                btn_next.setText("Finish");
                btn_previous.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };



    public void changeStatusBarColor() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        LayoutInflater inflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layouts[position], container, false);
            return view;
        }



        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


}



