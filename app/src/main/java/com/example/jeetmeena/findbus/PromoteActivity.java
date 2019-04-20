package com.example.jeetmeena.findbus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PromoteActivity extends AppCompatActivity {
    String PREFERENCES_FILE ="com.example.jeetmeena.findbus";

    ViewPager mViewPager;
    ImageView indacter_0, indacter_1, indacter_2,indacter_3, indacter_4;
    ImageButton mNextBtn;
    Button mFinishBtn,mSkip;
    private PagerAdapter mPagerAdapter;
    static int NUM_PAGES=5;
    static int count=0;
    int[] colorList ;
    int[] screen;
    Button   buttonFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote);

        indacter_0=findViewById(R.id.intro_indicator_0);
        indacter_1=findViewById(R.id.intro_indicator_1);
        indacter_2=findViewById(R.id.intro_indicator_2);
        indacter_3=findViewById(R.id.intro_indicator_3);
        indacter_4=findViewById(R.id.intro_indicator_4);
        // colorList = new int[]{color1, color2, color3};
        buttonFinish = (Button)findViewById(R.id.finish);
        //mNextBtn=findViewById(R.id.intro_btn_next);
      //  mFinishBtn=findViewById(R.id.intro_btn_finish);
        mViewPager=findViewById(R.id.container);
        screen=new int[] {R.layout.fragment_first,R.layout.fragment_second,R.layout.fragment_third,R.layout.fragment_fourth,R.layout.fragment_fifthragment};
          colorList= new int[]{R.color.darkblue,R.color.darkteal,R.color.darkgreen,R.color.darklime,R.color.darkamber};

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              // Toast.makeText(PromoteActivity.this,"hiii",Toast.LENGTH_SHORT).show();
              RelativeLayout linearLayout=findViewById(R.id.ralati);

             //  linearLayout.setBackground(BitmapFactory.decodeByteArray(R.drawable.,positionOffsetPixels,1));
               //linearLayout.setBackgroundColor(positionOffsetPixels,colorList[position]);
           }

           @Override
           public void onPageSelected(int position) {
               updateIndicators(position);
               if (position == 4) {
                   buttonFinish.setVisibility(View.VISIBLE);
               } else {
                   buttonFinish.setVisibility(View.GONE);
               }
               Toast.makeText(PromoteActivity.this,"hiiiPage",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
        mViewPager.setAdapter(mPagerAdapter);
        // Watch for button clicks.

      Button  button = (Button)findViewById(R.id.goto_last);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateIndicators(mViewPager.getCurrentItem()+1);

                if (mViewPager.getCurrentItem() == 3) {
                    buttonFinish.setVisibility(View.VISIBLE);
                } else {
                    buttonFinish.setVisibility(View.GONE);
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }

        });


        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem()==4){
                    MainActivity mainActivity=MainActivity.getMainActivity();
                    mainActivity.savesharedPerferns("User_First_Time","false");
                    startActivity(new Intent(PromoteActivity.this,MainActivity.class));
                    finish();
                }

                 }

        });
         }


    @Override
    public void onBackPressed() {

        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            updateIndicators(mViewPager.getCurrentItem()-1);
            // Otherwise, select the previous step.
            buttonFinish.setVisibility(View.GONE);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);

        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            ;
        }


        @Override
        public int getCount() {
            return screen.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(screen[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }








    void updateIndicators(int position) {
        View[] indicators = {indacter_0,indacter_1,indacter_2,indacter_3,indacter_4};
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? colorList[i]: R.color.orange
            );
        }
    }


    public void savesharedPerferns(String key,String value){
        SharedPreferences sharedPreferences =this.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(key,value);
        editor.apply();


    }
    public String getsharedPerfernces(String key,String difa){
        SharedPreferences sharedPreferences=  this.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);

        return sharedPreferences.getString(key,difa);
    }

}
