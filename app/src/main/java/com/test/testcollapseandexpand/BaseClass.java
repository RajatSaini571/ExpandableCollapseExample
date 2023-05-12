package com.test.testcollapseandexpand;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class BaseClass extends AppCompatActivity {



    public void expand(final View v, Context context, int image, String viewpager) {

        Animation slide_up = AnimationUtils.loadAnimation(context,
                R.anim.slide_up);

        v.startAnimation(slide_up);
        if(viewpager.equals("viewpager")){
            v.setBackgroundResource(image);
        }
        v.setVisibility(View.VISIBLE);
    }

    public void collapse(final View v, Context context) {
     /*   final int sourceHeight = v.getLayoutParams().height;
        final int targetHeight = v.getMeasuredHeight();
        DropDownAnim a = new DropDownAnim(v, sourceHeight,targetHeight, false);
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Your code on end of animation
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        Animation a = AnimationUtils.loadAnimation(context,
                R.anim.slide_down);
        v.startAnimation(a);
        v.setVisibility(View.GONE);
    }
    public void setMargins (View view, int left, int right, int top, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

}
