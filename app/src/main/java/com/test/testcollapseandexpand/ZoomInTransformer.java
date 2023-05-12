package com.test.testcollapseandexpand;
import static java.lang.Math.min;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ZoomInTransformer implements ViewPager.PageTransformer {

    public static final float MAX_ROTATION = 90.0f;

    @Override
    public void transformPage( View page, float pos ) {
        final float height = page.getHeight();
        final float width = page.getWidth();
        final float scale = min( pos > 0 ? 1f : Math.abs(1f + pos), 1f );

        page.setScaleX( scale );
        page.setScaleY( scale );
        page.setPivotX( width * 0.5f );
        page.setPivotY( height * 0.5f );
        page.setTranslationX( pos > 0 ? width * pos : -width * pos * 0.25f );




      /*  final float scale = pos < 0 ? pos + 1f : Math.abs( 1f - pos );
        page.setScaleX( scale );
        page.setScaleY( scale );
        page.setPivotX( page.getWidth() * 0.5f );
        page.setPivotY( page.getHeight() * 0.5f );
        page.setAlpha( pos < -1f || pos > 1f ? 0f : 1f - (scale - 1f) );*/
    }
}