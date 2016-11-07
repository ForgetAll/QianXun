package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.ActivityUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.widget.LocalImageHolderView;

import java.util.Arrays;

/**
 * Created by 15859 on 2016/10/28.
 *引导页
 */
public class GuideActivity extends BaseActivity  implements ViewPager.OnPageChangeListener{
    /*//保存进入状态,此页面执行到最后的时候加入这个
    PreferenceUtil.putBoolean(ConstantsBean.KEY_SPLASH, true);*/
    private ConvenientBanner convenientBanner;
    //图片资源
    private Integer[] imageIds = {R.mipmap.ic_sp_1, R.mipmap.ic_sp_2, R.mipmap.ic_sp_3};
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        intView();
        setData();
    }


    private void intView() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.viewPager);
        view = findViewById(R.id.iv_enter);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.jumpActivity(GuideActivity.this, LoginActivity.class);
                //保存进入状态
                PreferenceUtil.putBoolean(ConstantsBean.KEY_SPLASH, true);
                finish();
            }
        });
    }

    private void setData() {
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, Arrays.asList(imageIds))
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        convenientBanner.setOnPageChangeListener(this);
        convenientBanner.setCanLoop(false);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //设置按钮的显示
        if (position == (imageIds.length - 1)) {
            view.setVisibility(View.VISIBLE);
            convenientBanner.setPointViewVisible(false);
        } else {
            view.setVisibility(View.GONE);
            convenientBanner.setPointViewVisible(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
