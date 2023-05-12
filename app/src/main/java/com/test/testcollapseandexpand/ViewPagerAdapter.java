package com.test.testcollapseandexpand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

class ViewPagerAdapter extends PagerAdapter {

	Context context;
	int[] images;
	String[] locationName;
	String[] locationDesc;
	String[] locationPrice;
	LayoutInflater mLayoutInflater;
	LinearLayout base_cardview;
	BottomNavigationView hidden_view;
	BaseClass  baseClass = new BaseClass();
	ConstraintLayout viewPager;
	boolean isclicked;

	public ViewPagerAdapter(Context context, int[] images, LinearLayout base_cardview, BottomNavigationView hidden_view, ConstraintLayout mViewPager, String[] locationName, String[] locationDesc, String[] locationPrice,Boolean isclicked) {
		this.context = context;
		this.images = images;
		this.base_cardview = base_cardview;
		this.hidden_view = hidden_view;
		this.viewPager = mViewPager;
		this.locationName = locationName;
		this.locationDesc = locationDesc;
		this.locationPrice = locationPrice;
		this.isclicked = isclicked;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == ((CardView) object);
	}

	@NonNull
	@Override
	@SuppressLint("MissingInflatedId")
	public Object instantiateItem(@NonNull ViewGroup container, final int position) {

		View itemView = mLayoutInflater.inflate(R.layout.row_theme, container, false);
		ImageView imageView = itemView.findViewById(R.id.iv_image);
		TextView loc_name = itemView.findViewById(R.id.loc_name);
		TextView loc_desc = itemView.findViewById(R.id.loc_desc);
		TextView tv_price = itemView.findViewById(R.id.tv_price);

		imageView.setImageResource(images[position]);
		loc_name.setText(locationName[position]);
		loc_desc.setText(locationDesc[position]);
		tv_price.setText(locationPrice[position]+" BTC");

		imageView.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){

				baseClass.expand(base_cardview,context,images[position],"viewpager");
				baseClass.collapse(hidden_view,context);
				baseClass.collapse(viewPager,context);
				int newHeight = ViewGroup.LayoutParams.MATCH_PARENT;;
				int newWidth = ViewGroup.LayoutParams.MATCH_PARENT;;
				imageView.requestLayout();
				imageView.getLayoutParams().height = newHeight;
				imageView.getLayoutParams().width = newWidth;
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);


			}
		});

		Objects.requireNonNull(container).addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView((CardView) object);
	}

}
