package com.bobble.bobblesampleapp.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.database.Morepacks;
import com.bobble.bobblesampleapp.database.repository.MorePacksRepository;
import com.bobble.bobblesampleapp.util.BobbleConstants;
import com.facebook.drawee.view.SimpleDraweeView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Collections;
import java.util.List;

/**
 * Created by varunjain on 6/27/17.
 */

public class AdBoardActivity extends AppCompatActivity {

    private Context context;

    private ViewPager viewPager;

    private CirclePageIndicator pageIndicator;

    private int pagePosition = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adboard);
        context = this;
        if(getIntent()!=null){
            pagePosition = getIntent().getIntExtra("position",0);
        }
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        pageIndicator = (CirclePageIndicator)findViewById(R.id.pageIndicator);
        setViewPager();
    }

    private void setViewPager() {
        AdBoardPagerAdapter adapter = new AdBoardPagerAdapter(context, MorePacksRepository.getAllPacks(context));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(MorePacksRepository.getAllPacks(context).size());
        viewPager.setCurrentItem(pagePosition);
        pageIndicator.setViewPager(viewPager);
        pageIndicator.setOnPageChangeListener(adapter);
    }


    public class AdBoardPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        private Context context;
        private List<Morepacks>list;

        public AdBoardPagerAdapter(Context context, List<Morepacks> list) {
            this.context = context;
            this.list = list;
            Collections.reverse(list);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v =null;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            if(list.size()==position){
                v = layoutInflater.inflate(R.layout.item_adboard_last, null);
                ImageView ivGoogleplaystore = (ImageView)v.findViewById(R.id.ivGoogleplaystore);
                ivGoogleplaystore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setData(Uri.parse(BobbleConstants.GOOGLE_PLAY_STORE_LINK_TO_BOOBLE));
                        startActivity(i);
                    }
                });
            }else{
                v = layoutInflater.inflate(R.layout.item_adboard_pager, null);
                SimpleDraweeView imageView = (SimpleDraweeView)v.findViewById(R.id.imageView);
                imageView.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).getId())));
            }
            container.addView(v);
            return v;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public int getCount() {
            return list.size()+1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
