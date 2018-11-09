package com.power.mercenary.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.power.mercenary.MyApplication;
import com.power.mercenary.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by power on 2018/3/30.
 */

public class HomePostPicActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<String> mPics;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pic);
        ButterKnife.bind(this);
        titleBackIv.setOnClickListener(this);
        mPics = (List<String>) getIntent().getSerializableExtra("pics");
        mPosition = getIntent().getIntExtra("position", 0);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setCurrentItem(mPosition);
        mViewPager.setOffscreenPageLimit(mPics.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPics.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(MyApplication.getGloableContext()).load(mPics.get(position)).into(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
